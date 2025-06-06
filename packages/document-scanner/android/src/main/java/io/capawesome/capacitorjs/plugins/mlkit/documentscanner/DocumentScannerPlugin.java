package io.capawesome.capacitorjs.plugins.mlkit.documentscanner;

import android.app.Activity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
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

    public static final String TAG = "DocumentScanner";

    public static final String GOOGLE_DOCUMENT_SCANNER_MODULE_INSTALL_PROGRESS_EVENT = "googleDocumentScannerModuleInstallProgress";
    public static final String ERROR_GOOGLE_DOCUMENT_SCANNER_MODULE_NOT_AVAILABLE =
        "The Google Document Scanner Module is not available. You must install it first using the installGoogleDocumentScannerModule method.";
    public static final String ERROR_GOOGLE_DOCUMENT_SCANNER_MODULE_ALREADY_INSTALLED =
        "The Google Document Scanner Module is already installed.";

    private DocumentScanner implementation;
    private ActivityResultLauncher<IntentSenderRequest> scannerLauncher;
    private PluginCall savedCall;

    @Override
    public void load() {
        super.load();
        try {
            implementation = new DocumentScanner(this);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
        }
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

    @PluginMethod
    public void isGoogleDocumentScannerModuleAvailable(PluginCall call) {
        try {
            implementation.isGoogleDocumentScannerModuleAvailable(
                new IsGoogleDocumentScannerModuleAvailableResultCallback() {
                    @Override
                    public void success(boolean isAvailable) {
                        JSObject result = new JSObject();
                        result.put("available", isAvailable);
                        call.resolve(result);
                    }

                    @Override
                    public void error(Exception exception) {
                        Logger.error(TAG, exception.getMessage(), exception);
                        call.reject(exception.getMessage());
                    }
                }
            );
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void installGoogleDocumentScannerModule(PluginCall call) {
        try {
            implementation.installGoogleDocumentScannerModule(
                new InstallDocumentScannerModuleResultCallback() {
                    @Override
                    public void success() {
                        call.resolve();
                    }

                    @Override
                    public void error(Exception exception) {
                        Logger.error(TAG, exception.getMessage(), exception);
                        call.reject(exception.getMessage());
                    }
                }
            );
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    public void notifyGoogleDocumentScannerModuleInstallProgressListener(int state, @Nullable Integer progress) {
        try {
            JSObject result = new JSObject();
            result.put("state", state);
            if (progress != null) {
                result.put("progress", progress);
            }

            notifyListeners(GOOGLE_DOCUMENT_SCANNER_MODULE_INSTALL_PROGRESS_EVENT, result);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
        }
    }
}
