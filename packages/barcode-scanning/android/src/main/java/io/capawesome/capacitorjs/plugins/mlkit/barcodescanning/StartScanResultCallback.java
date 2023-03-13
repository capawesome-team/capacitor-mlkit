/**
 * Copyright (c) 2023 Robin Genz
 */
package io.capawesome.capacitorjs.plugins.mlkit.barcodescanning;

public interface StartScanResultCallback {
    void success();
    void error(Exception exception);
}
