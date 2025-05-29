package io.capawesome.capacitorjs.plugins.mlkit.documentscanner;

import android.app.Activity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import com.google.android.gms.common.moduleinstall.InstallStatusListener;
import com.google.android.gms.common.moduleinstall.ModuleInstall;
import com.google.android.gms.common.moduleinstall.ModuleInstallClient;
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest;
import com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate;
import com.google.mlkit.vision.documentscanner.GmsDocumentScanner;
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions;
import com.google.mlkit.vision.documentscanner.GmsDocumentScanning;

public class DocumentScanner {

    private DocumentScannerPlugin plugin;
    private InstallStatusListener moduleInstallProgressListener;

    public DocumentScanner(DocumentScannerPlugin plugin) {
        this.plugin = plugin;
    }

    public void startScan(Activity activity, PluginCall call, ActivityResultLauncher<IntentSenderRequest> scannerLauncher) {
        boolean galleryImportAllowed = call.getBoolean("galleryImportAllowed", false);
        int pageLimit = call.getInt("pageLimit", 10);
        String resultFormatsOption = call.getString("resultFormats", "JPEG_PDF");
        String scannerModeOption = call.getString("scannerMode", "FULL");

        int gmsScannerMode;
        switch (scannerModeOption.toUpperCase()) {
            case "BASE":
                gmsScannerMode = GmsDocumentScannerOptions.SCANNER_MODE_BASE;
                break;
            case "BASE_WITH_FILTER":
                gmsScannerMode = GmsDocumentScannerOptions.SCANNER_MODE_BASE_WITH_FILTER;
                break;
            case "FULL":
            default:
                gmsScannerMode = GmsDocumentScannerOptions.SCANNER_MODE_FULL;
                break;
        }

        GmsDocumentScannerOptions.Builder optionsBuilder = new GmsDocumentScannerOptions.Builder()
            .setGalleryImportAllowed(galleryImportAllowed)
            .setPageLimit(pageLimit)
            .setScannerMode(gmsScannerMode);

        switch (resultFormatsOption.toUpperCase()) {
            case "JPEG":
                optionsBuilder.setResultFormats(GmsDocumentScannerOptions.RESULT_FORMAT_JPEG);
                break;
            case "PDF":
                optionsBuilder.setResultFormats(GmsDocumentScannerOptions.RESULT_FORMAT_PDF);
                break;
            case "JPEG_PDF":
            default:
                optionsBuilder.setResultFormats(GmsDocumentScannerOptions.RESULT_FORMAT_JPEG, GmsDocumentScannerOptions.RESULT_FORMAT_PDF);
                break;
        }

        GmsDocumentScanner scanner = GmsDocumentScanning.getClient(optionsBuilder.build());

        scanner
            .getStartScanIntent(activity)
            .addOnSuccessListener(intentSender -> {
                scannerLauncher.launch(new IntentSenderRequest.Builder(intentSender).build());
            })
            .addOnFailureListener(e -> {
                call.reject("Failed to start scan intent: " + e.getMessage());
            });
    }

    public void isGoogleDocumentScannerModuleAvailable(IsGoogleDocumentScannerModuleAvailableResultCallback callback) {
        GmsDocumentScanner scanner = GmsDocumentScanning.getClient(new GmsDocumentScannerOptions.Builder().build());
        ModuleInstallClient moduleInstallClient = ModuleInstall.getClient(plugin.getContext());
        moduleInstallClient
            .areModulesAvailable(scanner)
            .addOnSuccessListener(response -> {
                boolean isAvailable = response.areModulesAvailable();
                callback.success(isAvailable);
            })
            .addOnFailureListener(callback::error);
    }

    public void installGoogleDocumentScannerModule(InstallDocumentScannerModuleResultCallback callback) {
        GmsDocumentScanner scanner = GmsDocumentScanning.getClient(new GmsDocumentScannerOptions.Builder().build());
        moduleInstallProgressListener = new ModuleInstallProgressListener(this);
        ModuleInstallRequest moduleInstallRequest = ModuleInstallRequest.newBuilder()
            .addApi(scanner)
            .setListener(moduleInstallProgressListener)
            .build();
        ModuleInstallClient moduleInstallClient = ModuleInstall.getClient(plugin.getContext());
        moduleInstallClient
            .installModules(moduleInstallRequest)
            .addOnSuccessListener(moduleInstallResponse -> {
                if (moduleInstallResponse.areModulesAlreadyInstalled()) {
                    callback.error(new Exception(DocumentScannerPlugin.ERROR_GOOGLE_DOCUMENT_SCANNER_MODULE_ALREADY_INSTALLED));
                } else {
                    callback.success();
                }
            })
            .addOnFailureListener(callback::error);
    }

    public void handleGoogleDocumentScannerModuleInstallProgress(
        @ModuleInstallStatusUpdate.InstallState int state,
        @Nullable Integer progress
    ) {
        plugin.notifyGoogleDocumentScannerModuleInstallProgressListener(state, progress);
        boolean isTerminateState = ModuleInstallProgressListener.isTerminateState(state);
        if (isTerminateState && moduleInstallProgressListener != null) {
            ModuleInstallClient moduleInstallClient = ModuleInstall.getClient(plugin.getContext());
            moduleInstallClient.unregisterListener(moduleInstallProgressListener);
            moduleInstallProgressListener = null;
        }
    }
}
