package io.capawesome.capacitorjs.plugins.mlkit.documentscanner;

import android.app.Activity;
import android.content.Intent;
import androidx.activity.result.ActivityResult;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.ActivityCallback;
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

    @Override
    public void load() {
        super.load();
        try {
            implementation = new DocumentScanner(this);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
        }
    }

    @PluginMethod
    public void scanDocument(PluginCall call) {
        implementation.startScan(call);
    }

    @ActivityCallback
    public void scanDocumentResult(PluginCall call, ActivityResult result) {
        if (call == null) {
            Logger.debug(TAG, "scanDocumentResult was called with empty call parameter.");
            return;
        }
        if (result.getResultCode() != Activity.RESULT_OK) {
            call.reject("Scan cancelled or failed. Result code: " + result.getResultCode());
            return;
        }
        Intent data = result.getData();
        if (data == null) {
            call.reject("Scan completed but no data was returned.");
            return;
        }
        GmsDocumentScanningResult gmsResult = GmsDocumentScanningResult.fromActivityResultIntent(data);
        if (gmsResult == null) {
            call.reject("Failed to retrieve scanning result.");
            return;
        }
        call.resolve(createScanResult(gmsResult));
    }

    private JSObject createScanResult(GmsDocumentScanningResult gmsResult) {
        JSObject ret = new JSObject();
        List<String> imageUris = new ArrayList<>();
        if (gmsResult.getPages() != null) {
            for (GmsDocumentScanningResult.Page page : gmsResult.getPages()) {
                imageUris.add(page.getImageUri().toString());
            }
        }
        ret.put("scannedImages", new JSONArray(imageUris));
        if (gmsResult.getPdf() != null) {
            JSObject pdfInfo = new JSObject();
            pdfInfo.put("uri", gmsResult.getPdf().getUri().toString());
            pdfInfo.put("pageCount", gmsResult.getPdf().getPageCount());
            ret.put("pdf", pdfInfo);
        }
        return ret;
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
