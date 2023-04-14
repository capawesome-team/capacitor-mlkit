/**
 * Copyright (c) 2023 Robin Genz
 */
package io.capawesome.capacitorjs.plugins.mlkit.barcodescanning;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.Image;
import android.net.Uri;
import android.provider.Settings;
import android.util.Size;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.CameraController;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import com.getcapacitor.Logger;
import com.getcapacitor.PermissionState;
import com.getcapacitor.PluginCall;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;
import com.google.mlkit.vision.common.InputImage;

public class BarcodeScanner implements ImageAnalysis.Analyzer {

    private final BarcodeScannerPlugin plugin;
    private final Point displaySize;

    @Nullable
    private com.google.mlkit.vision.barcode.BarcodeScanner barcodeScannerInstance;

    @Nullable
    private Camera camera;

    @Nullable
    private ProcessCameraProvider processCameraProvider;

    @Nullable
    private PreviewView previewView;

    @Nullable
    private ScanSettings scanSettings;

    private boolean isTorchEnabled = false;

    public BarcodeScanner(BarcodeScannerPlugin plugin) {
        this.plugin = plugin;
        this.displaySize = this.getDisplaySize();
    }

    /**
     * Must run on UI thread.
     */
    public void startScan(ScanSettings scanSettings, StartScanResultCallback callback) {
        // Stop the camera if running
        stopScan();
        // Hide WebView background
        hideWebViewBackground();

        this.scanSettings = scanSettings;

        BarcodeScannerOptions options = buildBarcodeScannerOptions(scanSettings);
        barcodeScannerInstance = BarcodeScanning.getClient(options);

        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder().setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).build();
        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(plugin.getContext()), this);

        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(plugin.getContext());
        cameraProviderFuture.addListener(
            () -> {
                try {
                    processCameraProvider = cameraProviderFuture.get();

                    CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(this.scanSettings.lensFacing).build();

                    previewView = plugin.getActivity().findViewById(R.id.preview_view);
                    previewView.setScaleType(PreviewView.ScaleType.FILL_CENTER);

                    Preview preview = new Preview.Builder().build();
                    preview.setSurfaceProvider(previewView.getSurfaceProvider());

                    // Start the camera
                    camera =
                        processCameraProvider.bindToLifecycle((LifecycleOwner) plugin.getContext(), cameraSelector, preview, imageAnalysis);
                    callback.success();
                } catch (Exception exception) {
                    callback.error(exception);
                }
            },
            ContextCompat.getMainExecutor(plugin.getContext())
        );
    }

    /**
     * Must run on UI thread.
     */
    public void stopScan() {
        showWebViewBackground();
        disableTorch();
        // Stop the camera
        if (processCameraProvider != null) {
            processCameraProvider.unbindAll();
        }
        processCameraProvider = null;
        camera = null;
        barcodeScannerInstance = null;
        scanSettings = null;
    }

    public void readBarcodesFromImage(String path, ScanSettings scanSettings, ReadBarcodesFromImageResultCallback callback)
        throws Exception {
        InputImage inputImage;
        try {
            inputImage = InputImage.fromFilePath(plugin.getContext(), Uri.parse(path));
        } catch (Exception exception) {
            throw new Exception(BarcodeScannerPlugin.ERROR_LOAD_IMAGE_FAILED);
        }

        BarcodeScannerOptions options = buildBarcodeScannerOptions(scanSettings);
        com.google.mlkit.vision.barcode.BarcodeScanner barcodeScannerInstance = BarcodeScanning.getClient(options);
        barcodeScannerInstance
            .process(inputImage)
            .addOnSuccessListener(
                barcodes -> {
                    callback.success(barcodes);
                }
            )
            .addOnFailureListener(
                exception -> {
                    callback.error(exception);
                }
            );
    }

    public void scan(ScanSettings scanSettings, ScanResultCallback callback) {
        GmsBarcodeScannerOptions options = buildGmsBarcodeScannerOptions(scanSettings);
        GmsBarcodeScanner scanner = GmsBarcodeScanning.getClient(plugin.getContext(), options);
        scanner
            .startScan()
            .addOnSuccessListener(
                barcode -> {
                    callback.success(barcode);
                }
            )
            .addOnCanceledListener(
                () -> {
                    callback.cancel();
                }
            )
            .addOnFailureListener(
                exception -> {
                    callback.error(exception);
                }
            );
    }

    public boolean isSupported() {
        return plugin.getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    public void enableTorch() {
        if (camera == null) {
            return;
        }
        camera.getCameraControl().enableTorch(true);
        isTorchEnabled = true;
    }

    public void disableTorch() {
        if (camera == null) {
            return;
        }
        camera.getCameraControl().enableTorch(false);
        isTorchEnabled = false;
    }

    public void toggleTorch() {
        if (isTorchEnabled) {
            disableTorch();
        } else {
            enableTorch();
        }
    }

    public boolean isTorchEnabled() {
        return isTorchEnabled;
    }

    public boolean isTorchAvailable() {
        return plugin.getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    public void openSettings(PluginCall call) {
        Uri uri = Uri.fromParts("package", plugin.getAppId(), null);
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri);
        plugin.startActivityForResult(call, intent, "openSettingsResult");
    }

    public PermissionState getCameraPermission() {
        return plugin.getPermissionState(BarcodeScannerPlugin.CAMERA);
    }

    public void requestCameraPermission(PluginCall call) {
        plugin.requestPermissionForAlias(BarcodeScannerPlugin.CAMERA, call, "cameraPermissionsCallback");
    }

    public boolean requestCameraPermissionIfNotDetermined(PluginCall call) throws Exception {
        PermissionState state = getCameraPermission();
        if (state == PermissionState.GRANTED) {
            return true;
        } else if (state == PermissionState.DENIED) {
            throw new Exception(BarcodeScannerPlugin.ERROR_PERMISSION_DENIED);
        } else {
            requestCameraPermission(call);
            return false;
        }
    }

    @Override
    public void analyze(@NonNull ImageProxy imageProxy) {
        @SuppressLint("UnsafeOptInUsageError")
        Image image = imageProxy.getImage();

        if (image == null || barcodeScannerInstance == null) {
            return;
        }

        InputImage inputImage = InputImage.fromMediaImage(image, imageProxy.getImageInfo().getRotationDegrees());
        Point imageSize = new Point(inputImage.getWidth(), inputImage.getHeight());
        barcodeScannerInstance
            .process(inputImage)
            .addOnSuccessListener(
                barcodes -> {
                    if (scanSettings == null) {
                        // Scanning stopped while processing the image
                        return;
                    }
                    for (Barcode barcode : barcodes) {
                        handleScannedBarcode(barcode, imageSize);
                    }
                }
            )
            .addOnFailureListener(
                exception -> {
                    handleScanError(exception);
                }
            )
            .addOnCompleteListener(
                task -> {
                    imageProxy.close();
                    image.close();
                }
            );
    }

    private Point getDisplaySize() {
        WindowManager wm = (WindowManager) plugin.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        return size;
    }

    /**
     * Must run on UI thread.
     */
    private void hideWebViewBackground() {
        plugin.getBridge().getWebView().setBackgroundColor(Color.TRANSPARENT);
    }

    /**
     * Must run on UI thread.
     */
    private void showWebViewBackground() {
        plugin.getBridge().getWebView().setBackgroundColor(Color.WHITE);
    }

    private void handleScannedBarcode(Barcode barcode, Point imageSize) {
        plugin.notifyBarcodeScannedListener(barcode, imageSize);
    }

    private void handleScanError(Exception exception) {
        plugin.notifyScanErrorListener(exception.getMessage());
    }

    private BarcodeScannerOptions buildBarcodeScannerOptions(ScanSettings scanSettings) {
        int[] formats = scanSettings.formats.length == 0 ? new int[] { Barcode.FORMAT_ALL_FORMATS } : scanSettings.formats;
        BarcodeScannerOptions options = new BarcodeScannerOptions.Builder().setBarcodeFormats(formats[0], formats).build();
        return options;
    }

    private GmsBarcodeScannerOptions buildGmsBarcodeScannerOptions(ScanSettings scanSettings) {
        int[] formats = scanSettings.formats.length == 0 ? new int[] { Barcode.FORMAT_ALL_FORMATS } : scanSettings.formats;
        GmsBarcodeScannerOptions options = new GmsBarcodeScannerOptions.Builder().setBarcodeFormats(formats[0], formats).build();
        return options;
    }
}
