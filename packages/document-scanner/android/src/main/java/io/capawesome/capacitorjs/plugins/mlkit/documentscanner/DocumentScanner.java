package io.capawesome.capacitorjs.plugins.mlkit.documentscanner;

import android.app.Activity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import com.getcapacitor.PluginCall;
import com.google.mlkit.vision.documentscanner.GmsDocumentScanner;
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions;
import com.google.mlkit.vision.documentscanner.GmsDocumentScanning;

public class DocumentScanner {

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
}
