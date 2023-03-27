/**
 * Copyright (c) 2023 Robin Genz
 */
package io.capawesome.capacitorjs.plugins.mlkit.barcodescanning;

import android.Manifest;
import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.camera.core.CameraSelector;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.PermissionState;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;
import com.google.mlkit.vision.barcode.common.Barcode;
import java.util.List;

@CapacitorPlugin(
    name = "BarcodeScanner",
    permissions = { @Permission(strings = { Manifest.permission.CAMERA }, alias = BarcodeScannerPlugin.CAMERA) }
)
public class BarcodeScannerPlugin extends Plugin {

    // Permission alias constants
    public static final String CAMERA = "camera";

    public static final String BARCODE_SCANNED_EVENT = "barcodeScanned";
    public static final String SCAN_ERROR_EVENT = "scanError";
    public static final String ERROR_SCAN_CANCELED = "scan canceled.";
    public static final String ERROR_PATH_MISSING = "path must be provided.";
    public static final String ERROR_LOAD_IMAGE_FAILED = "The image could not be loaded.";
    public static final String ERROR_PERMISSION_DENIED = "User denied access to camera.";

    private BarcodeScanner implementation;

    @Override
    public void load() {
        try {
            implementation = new BarcodeScanner(this);
        } catch (Exception exception) {
            Logger.error(exception.getMessage(), exception);
        }
    }

    @PluginMethod
    public void startScan(PluginCall call) {
        try {
            List<String> formatsOption = call.getArray("formats", new JSArray()).toList();
            int[] formats = BarcodeScannerHelper.convertStringsToBarcodeScannerFormats(formatsOption.toArray(new String[0]));

            String lensFacingOption = call.getString("lensFacing", "BACK");
            int lensFacing = lensFacingOption.equals("FRONT") ? CameraSelector.LENS_FACING_FRONT : CameraSelector.LENS_FACING_BACK;

            ScanSettings scanSettings = new ScanSettings();
            scanSettings.formats = formats;
            scanSettings.lensFacing = lensFacing;

            boolean granted = implementation.requestCameraPermissionIfNotDetermined(call);
            if (!granted) {
                return;
            }

            getActivity()
                .runOnUiThread(
                    () -> {
                        implementation.startScan(
                            scanSettings,
                            new StartScanResultCallback() {
                                @Override
                                public void success() {
                                    call.resolve();
                                }

                                @Override
                                public void error(Exception exception) {
                                    Logger.error("startScan failed.", exception);
                                    call.reject(exception.getMessage());
                                }
                            }
                        );
                    }
                );
        } catch (Exception exception) {
            String message = exception.getMessage();
            Logger.error(message, exception);
            call.reject(message);
        }
    }

    @PluginMethod
    public void stopScan(PluginCall call) {
        try {
            getActivity()
                .runOnUiThread(
                    () -> {
                        implementation.stopScan();
                        call.resolve();
                    }
                );
        } catch (Exception exception) {
            String message = exception.getMessage();
            Logger.error(message, exception);
            call.reject(message);
        }
    }

    @PluginMethod
    public void readBarcodesFromImage(PluginCall call) {
        try {
            String path = call.getString("path");
            if (path == null) {
                call.reject(ERROR_PATH_MISSING);
                return;
            }

            List<String> formatsOption = call.getArray("formats", new JSArray()).toList();
            int[] formats = BarcodeScannerHelper.convertStringsToBarcodeScannerFormats(formatsOption.toArray(new String[0]));

            ScanSettings scanSettings = new ScanSettings();
            scanSettings.formats = formats;

            implementation.readBarcodesFromImage(
                path,
                scanSettings,
                new ReadBarcodesFromImageResultCallback() {
                    @Override
                    public void success(List<Barcode> barcodes) {
                        JSArray barcodeResults = new JSArray();
                        for (Barcode barcode : barcodes) {
                            barcodeResults.put(BarcodeScannerHelper.createBarcodeResultForBarcode(barcode));
                        }

                        JSObject result = new JSObject();
                        result.put("barcodes", barcodeResults);
                        call.resolve(result);
                    }

                    @Override
                    public void error(Exception exception) {
                        Logger.error("readBarcodeFromImage failed.", exception);
                        call.reject(exception.getMessage());
                    }
                }
            );
        } catch (Exception exception) {
            String message = exception.getMessage();
            Logger.error(message, exception);
            call.reject(message);
        }
    }

    @PluginMethod
    public void scan(PluginCall call) {
        try {
            List<String> formatsOption = call.getArray("formats", new JSArray()).toList();
            int[] formats = BarcodeScannerHelper.convertStringsToBarcodeScannerFormats(formatsOption.toArray(new String[0]));

            ScanSettings scanSettings = new ScanSettings();
            scanSettings.formats = formats;

            implementation.scan(
                scanSettings,
                (
                    new ScanResultCallback() {
                        @Override
                        public void success(Barcode barcode) {
                            JSArray barcodeResults = new JSArray();
                            barcodeResults.put(BarcodeScannerHelper.createBarcodeResultForBarcode(barcode));

                            JSObject result = new JSObject();
                            result.put("barcodes", barcodeResults);
                            call.resolve(result);
                        }

                        @Override
                        public void cancel() {
                            call.reject(ERROR_SCAN_CANCELED);
                        }

                        @Override
                        public void error(Exception exception) {
                            Logger.error("scan failed.", exception);
                            call.reject(exception.getMessage());
                        }
                    }
                )
            );
        } catch (Exception exception) {
            String message = exception.getMessage();
            Logger.error(message, exception);
            call.reject(message);
        }
    }

    @PluginMethod
    public void isSupported(PluginCall call) {
        try {
            JSObject result = new JSObject();
            result.put("supported", implementation.isSupported());
            call.resolve(result);
        } catch (Exception exception) {
            String message = exception.getMessage();
            Logger.error(message, exception);
            call.reject(message);
        }
    }

    @PluginMethod
    public void enableTorch(PluginCall call) {
        try {
            implementation.enableTorch();
            call.resolve();
        } catch (Exception exception) {
            String message = exception.getMessage();
            Logger.error(message, exception);
            call.reject(message);
        }
    }

    @PluginMethod
    public void disableTorch(PluginCall call) {
        try {
            implementation.disableTorch();
            call.resolve();
        } catch (Exception exception) {
            String message = exception.getMessage();
            Logger.error(message, exception);
            call.reject(message);
        }
    }

    @PluginMethod
    public void toggleTorch(PluginCall call) {
        try {
            implementation.toggleTorch();
            call.resolve();
        } catch (Exception exception) {
            String message = exception.getMessage();
            Logger.error(message, exception);
            call.reject(message);
        }
    }

    @PluginMethod
    public void isTorchEnabled(PluginCall call) {
        try {
            JSObject result = new JSObject();
            result.put("enabled", implementation.isTorchEnabled());
            call.resolve(result);
        } catch (Exception exception) {
            String message = exception.getMessage();
            Logger.error(message, exception);
            call.reject(message);
        }
    }

    @PluginMethod
    public void isTorchAvailable(PluginCall call) {
        try {
            JSObject result = new JSObject();
            result.put("available", implementation.isTorchAvailable());
            call.resolve(result);
        } catch (Exception exception) {
            String message = exception.getMessage();
            Logger.error(message, exception);
            call.reject(message);
        }
    }

    @PluginMethod
    public void openSettings(PluginCall call) {
        try {
            implementation.openSettings(call);
        } catch (Exception exception) {
            String message = exception.getMessage();
            Logger.error(message, exception);
            call.reject(message);
        }
    }

    @Override
    @PluginMethod
    public void requestPermissions(PluginCall call) {
        // Check if the permission is already declared in manifest.
        if (isPermissionDeclared(CAMERA)) {
            // Prompt the user for permission.
            super.requestPermissions(call);
        } else {
            checkPermissions(call);
        }
    }

    public void requestPermissionForAlias(@NonNull String alias, @NonNull PluginCall call, @NonNull String callbackName) {
        super.requestPermissionForAlias(alias, call, callbackName);
    }

    @ActivityCallback
    public void openSettingsResult(PluginCall call, ActivityResult result) {
        try {
            if (call == null) {
                Logger.debug("openSettingsResult was called with empty call parameter.");
                return;
            }
            call.resolve();
        } catch (Exception exception) {
            Logger.error(exception.getMessage(), exception);
        }
    }

    @PermissionCallback
    private void cameraPermissionsCallback(PluginCall call) {
        if (call.getMethodName().equals("startScan")) {
            startScan(call);
        }
    }

    public void notifyBarcodeScannedListener(Barcode barcode) {
        try {
            JSObject result = new JSObject();
            result.put("barcode", BarcodeScannerHelper.createBarcodeResultForBarcode(barcode));
            notifyListeners(BARCODE_SCANNED_EVENT, result);
        } catch (Exception exception) {
            Logger.error(exception.getMessage(), exception);
        }
    }

    public void notifyScanErrorListener(String message) {
        try {
            JSObject result = new JSObject();
            result.put("message", message);

            notifyListeners(SCAN_ERROR_EVENT, result);
        } catch (Exception exception) {
            Logger.error(exception.getMessage(), exception);
        }
    }
}
