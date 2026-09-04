/**
 * Copyright (c) 2023 Robin Genz
 */
package io.capawesome.capacitorjs.plugins.mlkit.barcodescanning;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;

/**
 * Invisible activity that hosts the Google code scanner so that its result is delivered as an
 * activity result. This lets Capacitor restore the pending plugin call and emit the
 * `appRestoredResult` event if the app was destroyed while the scanner was open.
 */
public class ScanActivity extends Activity {

    public static final String EXTRA_AUTO_ZOOM = "autoZoom";
    public static final String EXTRA_BARCODE = "barcode";
    public static final String EXTRA_ERROR = "error";
    public static final String EXTRA_FORMATS = "formats";

    // Shared across instances so that a recreated activity can re-attach to the running scan.
    @Nullable
    private static Task<Barcode> pendingScan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            pendingScan = startScan();
        }
        if (pendingScan == null) {
            finishWithError(BarcodeScannerPlugin.ERROR_SCAN_RESULT_LOST);
            return;
        }
        pendingScan.addOnCompleteListener(this::handleScanCompleted);
    }

    private GmsBarcodeScannerOptions buildGmsBarcodeScannerOptions() {
        int[] formats = getIntent().getIntArrayExtra(EXTRA_FORMATS);
        if (formats == null || formats.length == 0) {
            formats = new int[] { Barcode.FORMAT_ALL_FORMATS };
        }
        GmsBarcodeScannerOptions.Builder builder = new GmsBarcodeScannerOptions.Builder().setBarcodeFormats(formats[0], formats);
        if (getIntent().getBooleanExtra(EXTRA_AUTO_ZOOM, false)) {
            builder.enableAutoZoom();
        }
        return builder.build();
    }

    private void finishWithBarcode(@NonNull Barcode barcode) {
        JSObject barcodeResult = BarcodeScannerHelper.createBarcodeResultForBarcode(barcode, null, null);
        Intent data = new Intent().putExtra(EXTRA_BARCODE, barcodeResult.toString());
        finishWithResult(RESULT_OK, data);
    }

    private void finishWithError(@NonNull String message) {
        Intent data = new Intent().putExtra(EXTRA_ERROR, message);
        finishWithResult(RESULT_CANCELED, data);
    }

    private void finishWithResult(int resultCode, @Nullable Intent data) {
        setResult(resultCode, data);
        finish();
    }

    private void handleScanCompleted(@NonNull Task<Barcode> task) {
        if (isDestroyed()) {
            return;
        }
        pendingScan = null;
        if (task.isSuccessful()) {
            finishWithBarcode(task.getResult());
        } else if (task.isCanceled()) {
            finishWithResult(RESULT_CANCELED, null);
        } else {
            String message = task.getException().getMessage();
            finishWithError(message == null ? BarcodeScannerPlugin.ERROR_SCAN_FAILED : message);
        }
    }

    private Task<Barcode> startScan() {
        GmsBarcodeScannerOptions options = buildGmsBarcodeScannerOptions();
        return GmsBarcodeScanning.getClient(this, options).startScan();
    }
}
