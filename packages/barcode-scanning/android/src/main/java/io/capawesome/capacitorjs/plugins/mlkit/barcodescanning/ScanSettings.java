/**
 * Copyright (c) 2023 Robin Genz
 */
package io.capawesome.capacitorjs.plugins.mlkit.barcodescanning;

import android.util.Size;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraSelector;
import com.google.mlkit.vision.barcode.common.Barcode;

public class ScanSettings {

    @Barcode.BarcodeFormat
    public int[] formats = new int[] {};

    @Nullable
    public boolean autoZoom = false;

    @Nullable
    @CameraSelector.LensFacing
    public Integer lensFacing;

    public Size resolution;
}
