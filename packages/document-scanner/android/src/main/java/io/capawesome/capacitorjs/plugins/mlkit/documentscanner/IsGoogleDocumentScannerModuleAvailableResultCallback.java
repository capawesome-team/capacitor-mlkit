package io.capawesome.capacitorjs.plugins.mlkit.documentscanner;

public interface IsGoogleDocumentScannerModuleAvailableResultCallback {
    void success(boolean isAvailable);
    void error(Exception exception);
}
