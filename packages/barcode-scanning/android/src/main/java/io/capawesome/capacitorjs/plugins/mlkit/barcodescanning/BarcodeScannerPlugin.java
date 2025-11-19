/**
 * Copyright (c) 2023 Robin Genz
 */
package io.capawesome.capacitorjs.plugins.mlkit.barcodescanning;

import android.Manifest;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Size;
import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraSelector;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;
import com.google.mlkit.vision.barcode.common.Barcode;
import io.capawesome.capacitorjs.plugins.mlkit.barcodescanning.classes.options.SetZoomRatioOptions;
import io.capawesome.capacitorjs.plugins.mlkit.barcodescanning.classes.results.GetMaxZoomRatioResult;
import io.capawesome.capacitorjs.plugins.mlkit.barcodescanning.classes.results.GetMinZoomRatioResult;
import io.capawesome.capacitorjs.plugins.mlkit.barcodescanning.classes.results.GetZoomRatioResult;
import java.util.List;

@CapacitorPlugin(
    name = "BarcodeScanner",
    permissions = { @Permission(strings = { Manifest.permission.CAMERA }, alias = BarcodeScannerPlugin.CAMERA) }
)
public class BarcodeScannerPlugin extends Plugin {

    public static final String TAG = "BarcodeScanner";

    // Permission alias constants
    public static final String CAMERA = "camera";

    public static final String BARCODE_SCANNED_EVENT = "barcodeScanned";
    public static final String BARCODES_SCANNED_EVENT = "barcodesScanned";
    public static final String SCAN_ERROR_EVENT = "scanError";
    public static final String GOOGLE_BARCODE_SCANNER_MODULE_INSTALL_PROGRESS_EVENT = "googleBarcodeScannerModuleInstallProgress";
    public static final String ERROR_SCAN_CANCELED = "scan canceled.";
    public static final String ERROR_PATH_MISSING = "path must be provided.";
    public static final String ERROR_LOAD_IMAGE_FAILED = "The image could not be loaded.";
    public static final String ERROR_ZOOM_RATIO_MISSING = "zoomRatio must be provided.";
    public static final String ERROR_NO_ACTIVE_SCAN_SESSION = "There is no active scan session.";
    public static final String ERROR_GOOGLE_BARCODE_SCANNER_MODULE_NOT_AVAILABLE =
        "The Google Barcode Scanner Module is not available. You must install it first using the installGoogleBarcodeScannerModule method.";
    public static final String ERROR_GOOGLE_BARCODE_SCANNER_MODULE_ALREADY_INSTALLED =
        "The Google Barcode Scanner Module is already installed.";
    public static final String ERROR_PERMISSION_DENIED = "User denied access to camera.";

    private BarcodeScanner implementation;

    @Override
    public void load() {
        try {
            implementation = new BarcodeScanner(this);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
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
            scanSettings.resolution = BarcodeScannerHelper.convertIntegerToResolution(call.getInt("resolution", 1));

            boolean granted = implementation.requestCameraPermissionIfNotDetermined(call);
            if (!granted) {
                return;
            }

            getActivity()
                .runOnUiThread(() -> {
                    implementation.startScan(
                        scanSettings,
                        new StartScanResultCallback() {
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
                });
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void stopScan(PluginCall call) {
        try {
            getActivity()
                .runOnUiThread(() -> {
                    implementation.stopScan();
                    call.resolve();
                });
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
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
                            barcodeResults.put(BarcodeScannerHelper.createBarcodeResultForBarcode(barcode, null, null));
                        }

                        JSObject result = new JSObject();
                        result.put("barcodes", barcodeResults);
                        call.resolve(result);
                    }

                    @Override
                    public void error(Exception exception) {
                        Logger.error(TAG, "readBarcodeFromImage failed.", exception);
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
    public void scan(PluginCall call) {
        try {
            List<String> formatsOption = call.getArray("formats", new JSArray()).toList();
            boolean autoZoom = call.getBoolean("autoZoom", false);

            int[] formats = BarcodeScannerHelper.convertStringsToBarcodeScannerFormats(formatsOption.toArray(new String[0]));

            ScanSettings scanSettings = new ScanSettings();
            scanSettings.formats = formats;
            scanSettings.autoZoom = autoZoom;

            implementation.isGoogleBarcodeScannerModuleAvailable(
                new IsGoogleBarodeScannerModuleAvailableResultCallback() {
                    @Override
                    public void success(boolean isAvailable) {
                        if (isAvailable) {
                            implementation.scan(
                                scanSettings,
                                (new ScanResultCallback() {
                                        @Override
                                        public void success(Barcode barcode) {
                                            JSObject barcodeResult = BarcodeScannerHelper.createBarcodeResultForBarcode(
                                                barcode,
                                                null,
                                                null
                                            );

                                            JSArray barcodeResults = new JSArray();
                                            barcodeResults.put(barcodeResult);

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
                                            Logger.error(TAG, exception.getMessage(), exception);
                                            call.reject(exception.getMessage());
                                        }
                                    })
                            );
                        } else {
                            call.reject(ERROR_GOOGLE_BARCODE_SCANNER_MODULE_NOT_AVAILABLE);
                        }
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
    public void isSupported(PluginCall call) {
        try {
            JSObject result = new JSObject();
            result.put("supported", implementation.isSupported());
            call.resolve(result);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void enableTorch(PluginCall call) {
        try {
            implementation.enableTorch();
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void disableTorch(PluginCall call) {
        try {
            implementation.disableTorch();
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void toggleTorch(PluginCall call) {
        try {
            implementation.toggleTorch();
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void isTorchEnabled(PluginCall call) {
        try {
            JSObject result = new JSObject();
            result.put("enabled", implementation.isTorchEnabled());
            call.resolve(result);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void isTorchAvailable(PluginCall call) {
        try {
            JSObject result = new JSObject();
            result.put("available", implementation.isTorchAvailable());
            call.resolve(result);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void setZoomRatio(PluginCall call) {
        try {
            Float zoomRatio = call.getFloat("zoomRatio");
            if (zoomRatio == null) {
                call.reject(ERROR_ZOOM_RATIO_MISSING);
                return;
            }

            SetZoomRatioOptions options = new SetZoomRatioOptions(zoomRatio);
            implementation.setZoomRatio(options);
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void getZoomRatio(PluginCall call) {
        try {
            boolean isCameraActive = implementation.isCameraActive();
            if (!isCameraActive) {
                call.reject(ERROR_NO_ACTIVE_SCAN_SESSION);
                return;
            }

            GetZoomRatioResult result = implementation.getZoomRatio();
            call.resolve(result.toJSObject());
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void getMinZoomRatio(PluginCall call) {
        try {
            boolean isCameraActive = implementation.isCameraActive();
            if (!isCameraActive) {
                call.reject(ERROR_NO_ACTIVE_SCAN_SESSION);
                return;
            }

            GetMinZoomRatioResult result = implementation.getMinZoomRatio();
            call.resolve(result.toJSObject());
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void getMaxZoomRatio(PluginCall call) {
        try {
            boolean isCameraActive = implementation.isCameraActive();
            if (!isCameraActive) {
                call.reject(ERROR_NO_ACTIVE_SCAN_SESSION);
                return;
            }

            GetMaxZoomRatioResult result = implementation.getMaxZoomRatio();
            call.resolve(result.toJSObject());
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void openSettings(PluginCall call) {
        try {
            implementation.openSettings(call);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void isGoogleBarcodeScannerModuleAvailable(PluginCall call) {
        try {
            implementation.isGoogleBarcodeScannerModuleAvailable(
                new IsGoogleBarodeScannerModuleAvailableResultCallback() {
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
    public void installGoogleBarcodeScannerModule(PluginCall call) {
        try {
            implementation.installGoogleBarcodeScannerModule(
                new InstallGoogleBarcodeScannerModuleResultCallback() {
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
            Logger.error(TAG, exception.getMessage(), exception);
        }
    }

    @PermissionCallback
    private void cameraPermissionsCallback(PluginCall call) {
        if (call.getMethodName().equals("startScan")) {
            startScan(call);
        }
    }

    public void notifyBarcodeScannedListener(Barcode barcode, Point imageSize) {
        try {
            Point screenSize = this.getScreenSize();
            JSObject barcodeResult = BarcodeScannerHelper.createBarcodeResultForBarcode(barcode, imageSize, screenSize);

            JSObject result = new JSObject();
            result.put("barcode", barcodeResult);
            notifyListeners(BARCODE_SCANNED_EVENT, result);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
        }
    }

    public void notifyBarcodesScannedListener(Barcode[] barcodes, Point imageSize) {
        try {
            Point screenSize = this.getScreenSize();
            JSArray barcodesResult = new JSArray();
            for (Barcode barcode : barcodes) {
                JSObject barcodeResult = BarcodeScannerHelper.createBarcodeResultForBarcode(barcode, imageSize, screenSize);
                barcodesResult.put(barcodeResult);
            }

            JSObject result = new JSObject();
            result.put("barcodes", barcodesResult);
            notifyListeners(BARCODES_SCANNED_EVENT, result);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
        }
    }

    public void notifyScanErrorListener(String message) {
        try {
            JSObject result = new JSObject();
            result.put("message", message);

            notifyListeners(SCAN_ERROR_EVENT, result);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
        }
    }

    public void notifyGoogleBarcodeScannerModuleInstallProgressListener(int state, @Nullable Integer progress) {
        try {
            JSObject result = new JSObject();
            result.put("state", state);
            if (progress != null) {
                result.put("progress", progress);
            }

            notifyListeners(GOOGLE_BARCODE_SCANNER_MODULE_INSTALL_PROGRESS_EVENT, result);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
        }
    }

    /**
     * Returns the display size without navigation bar height and status bar height.
     */
    private Point getScreenSize() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Point displaySize = new Point(displayMetrics.widthPixels, displayMetrics.heightPixels);
        return displaySize;
    }
}
