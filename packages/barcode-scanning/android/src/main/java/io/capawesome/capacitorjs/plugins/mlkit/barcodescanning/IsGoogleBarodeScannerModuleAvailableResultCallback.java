package io.capawesome.capacitorjs.plugins.mlkit.barcodescanning;

public interface IsGoogleBarodeScannerModuleAvailableResultCallback {
    void success(boolean isAvailable);
    void error(Exception exception);
}
