package io.capawesome.capacitorjs.plugins.mlkit.barcodescanning;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.getcapacitor.JSObject;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;

public class GoogleBarcodeScannerActivity extends AppCompatActivity {

    public static final String EXTRA_FORMATS = "formats";
    public static final String EXTRA_AUTO_ZOOM = "autoZoom";
    public static final String EXTRA_BARCODE_RESULT_JSON = "barcodeResultJson";
    public static final String EXTRA_ERROR_MESSAGE = "errorMessage";
    public static final int RESULT_ERROR = Activity.RESULT_FIRST_USER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Intent intent = getIntent();
            int[] formats = intent != null ? intent.getIntArrayExtra(EXTRA_FORMATS) : null;
            boolean autoZoom = intent != null && intent.getBooleanExtra(EXTRA_AUTO_ZOOM, false);

            if (formats == null || formats.length == 0) {
                formats = new int[] { Barcode.FORMAT_ALL_FORMATS };
            }

            GmsBarcodeScannerOptions options;
            if (autoZoom) {
                options = new GmsBarcodeScannerOptions.Builder().setBarcodeFormats(formats[0], formats).enableAutoZoom().build();
            } else {
                options = new GmsBarcodeScannerOptions.Builder().setBarcodeFormats(formats[0], formats).build();
            }

            GmsBarcodeScanner scanner = GmsBarcodeScanning.getClient(this, options);
            scanner
                .startScan()
                .addOnSuccessListener(barcode -> {
                    JSObject barcodeResult = BarcodeScannerHelper.createBarcodeResultForBarcode(barcode, null, null);
                    Intent result = new Intent();
                    result.putExtra(EXTRA_BARCODE_RESULT_JSON, barcodeResult.toString());
                    setResult(Activity.RESULT_OK, result);
                    finish();
                })
                .addOnCanceledListener(() -> {
                    setResult(Activity.RESULT_CANCELED);
                    finish();
                })
                .addOnFailureListener(exception -> {
                    Intent result = new Intent();
                    result.putExtra(EXTRA_ERROR_MESSAGE, exception.getMessage());
                    setResult(RESULT_ERROR, result);
                    finish();
                });
        } catch (Exception exception) {
            Intent result = new Intent();
            result.putExtra(EXTRA_ERROR_MESSAGE, exception.getMessage());
            setResult(RESULT_ERROR, result);
            finish();
        }
    }
}
