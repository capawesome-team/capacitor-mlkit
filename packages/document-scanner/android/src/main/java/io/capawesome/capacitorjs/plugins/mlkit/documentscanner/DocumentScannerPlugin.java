package io.capawesome.capacitorjs.plugins.mlkit.documentscanner;

import android.app.Activity;
import android.content.IntentSender;
import android.net.Uri;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;

@CapacitorPlugin(name = "DocumentScanner")
public class DocumentScannerPlugin extends Plugin {

    private DocumentScanner implementation = new DocumentScanner();
    private ActivityResultLauncher<IntentSenderRequest> scannerLauncher;
    private PluginCall savedCall;

    @Override
    public void load() {
        super.load();
        scannerLauncher = getActivity()
            .registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(), activityResult -> {
                if (savedCall == null) {
                    return;
                }
                if (activityResult.getResultCode() == Activity.RESULT_OK) {
                    if (activityResult.getData() == null) {
                        savedCall.reject("Scan completed but no data was returned.");
                        savedCall = null;
                        return;
                    }
                    GmsDocumentScanningResult gmsResult = GmsDocumentScanningResult.fromActivityResultIntent(activityResult.getData());
                    if (gmsResult != null) {
                        JSObject ret = new JSObject();
                        List<String> imageUris = new ArrayList<>();
                        if (gmsResult.getPages() != null) {
                            for (GmsDocumentScanningResult.Page page : gmsResult.getPages()) {
                                imageUris.add(page.getImageUri().toString());
                            }
                        }
                        // only add 'scannedImages' if it;s not empty, or if JPEG was requested.
                        ret.put("scannedImages", new JSONArray(imageUris));

                        if (gmsResult.getPdf() != null) {
                            JSObject pdfInfo = new JSObject();
                            pdfInfo.put("uri", gmsResult.getPdf().getUri().toString());
                            pdfInfo.put("pageCount", gmsResult.getPdf().getPageCount());
                            ret.put("pdf", pdfInfo);
                        }
                        savedCall.resolve(ret);
                    } else {
                        savedCall.reject("Failed to retrieve scanning result.");
                    }
                } else {
                    savedCall.reject("Scan cancelled or failed. Result code: " + activityResult.getResultCode());
                }
                savedCall = null;
            });
    }

    @PluginMethod
    public void scanDocument(PluginCall call) {
        this.savedCall = call;
        implementation.startScan(getActivity(), call, scannerLauncher);
    }
}
