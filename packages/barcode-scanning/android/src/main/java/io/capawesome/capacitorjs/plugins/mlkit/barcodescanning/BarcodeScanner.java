/**
 * Copyright (c) 2023 Robin Genz
 */
package io.capawesome.capacitorjs.plugins.mlkit.barcodescanning;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.media.Image;
import android.net.Uri;
import android.provider.Settings;
import android.view.Display;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import com.getcapacitor.PermissionState;
import com.getcapacitor.PluginCall;
import com.google.android.gms.common.moduleinstall.InstallStatusListener;
import com.google.android.gms.common.moduleinstall.ModuleInstall;
import com.google.android.gms.common.moduleinstall.ModuleInstallClient;
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest;
import com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;
import com.google.mlkit.vision.common.InputImage;
import io.capawesome.capacitorjs.plugins.mlkit.barcodescanning.classes.options.SetZoomRatioOptions;
import io.capawesome.capacitorjs.plugins.mlkit.barcodescanning.classes.results.GetMaxZoomRatioResult;
import io.capawesome.capacitorjs.plugins.mlkit.barcodescanning.classes.results.GetMinZoomRatioResult;
import io.capawesome.capacitorjs.plugins.mlkit.barcodescanning.classes.results.GetZoomRatioResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BarcodeScanner implements ImageAnalysis.Analyzer {

    @Nullable
    private static Camera camera;

    @NonNull
    private final BarcodeScannerPlugin plugin;

    @Nullable
    private com.google.mlkit.vision.barcode.BarcodeScanner barcodeScannerInstance;

    @Nullable
    private ProcessCameraProvider processCameraProvider;

    @Nullable
    private PreviewView previewView;

    @Nullable
    private ScanSettings scanSettings;

    @Nullable
    private ModuleInstallProgressListener moduleInstallProgressListener;

    private HashMap<String, Integer> barcodeRawValueVotes = new HashMap<String, Integer>();

    private boolean isTorchEnabled = false;

    public BarcodeScanner(BarcodeScannerPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Do NOT change this method signature. It is used by the Torch plugin.
     */
    @Nullable
    public static CameraControl getCameraControl() {
        if (camera == null) {
            return null;
        }
        return camera.getCameraControl();
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

        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .setTargetResolution(scanSettings.resolution)
            .build();
        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(plugin.getContext()), this);

        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(plugin.getContext());
        cameraProviderFuture.addListener(
            () -> {
                try {
                    processCameraProvider = cameraProviderFuture.get();

                    CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(this.scanSettings.lensFacing).build();

                    previewView = new PreviewView(plugin.getActivity());
                    previewView.setLayoutParams(
                        new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
                    );
                    previewView.setScaleType(PreviewView.ScaleType.FILL_CENTER);
                    previewView.setBackgroundColor(Color.BLACK);

                    // Add preview view behind the WebView
                    ((ViewGroup) plugin.getBridge().getWebView().getParent()).addView(previewView, 0);

                    Preview preview = new Preview.Builder().build();
                    preview.setSurfaceProvider(previewView.getSurfaceProvider());

                    // Start the camera
                    camera = processCameraProvider.bindToLifecycle(
                        (LifecycleOwner) plugin.getContext(),
                        cameraSelector,
                        preview,
                        imageAnalysis
                    );

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
        if (previewView != null) {
            ((ViewGroup) previewView.getParent()).removeView(previewView);
            previewView = null;
        }
        barcodeScannerInstance = null;
        scanSettings = null;
        barcodeRawValueVotes.clear();
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
            .addOnSuccessListener(barcodes -> {
                callback.success(barcodes);
            })
            .addOnFailureListener(exception -> {
                callback.error(exception);
            });
    }

    public void scan(ScanSettings scanSettings, ScanResultCallback callback) {
        GmsBarcodeScannerOptions options = buildGmsBarcodeScannerOptions(scanSettings);
        GmsBarcodeScanner scanner = GmsBarcodeScanning.getClient(plugin.getContext(), options);

        scanner
            .startScan()
            .addOnSuccessListener(barcode -> {
                callback.success(barcode);
            })
            .addOnCanceledListener(() -> {
                callback.cancel();
            })
            .addOnFailureListener(exception -> {
                callback.error(exception);
            });
    }

    public void isGoogleBarcodeScannerModuleAvailable(IsGoogleBarodeScannerModuleAvailableResultCallback callback) {
        GmsBarcodeScanner scanner = GmsBarcodeScanning.getClient(plugin.getContext());
        ModuleInstallClient moduleInstallClient = ModuleInstall.getClient(plugin.getContext());
        moduleInstallClient
            .areModulesAvailable(scanner)
            .addOnSuccessListener(response -> {
                boolean isAvailable = response.areModulesAvailable();
                callback.success(isAvailable);
            })
            .addOnFailureListener(exception -> {
                callback.error(exception);
            });
    }

    public void installGoogleBarcodeScannerModule(InstallGoogleBarcodeScannerModuleResultCallback callback) {
        GmsBarcodeScanner scanner = GmsBarcodeScanning.getClient(plugin.getContext());
        InstallStatusListener listener = new ModuleInstallProgressListener(this);
        ModuleInstallRequest moduleInstallRequest = ModuleInstallRequest.newBuilder().addApi(scanner).setListener(listener).build();
        ModuleInstallClient moduleInstallClient = ModuleInstall.getClient(plugin.getContext());
        moduleInstallClient
            .installModules(moduleInstallRequest)
            .addOnSuccessListener(moduleInstallResponse -> {
                if (moduleInstallResponse.areModulesAlreadyInstalled()) {
                    callback.error(new Exception(BarcodeScannerPlugin.ERROR_GOOGLE_BARCODE_SCANNER_MODULE_ALREADY_INSTALLED));
                } else {
                    callback.success();
                }
            })
            .addOnFailureListener(exception -> {
                callback.error(exception);
            });
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

    public void setZoomRatio(SetZoomRatioOptions options) {
        float zoomRatio = options.getZoomRatio();
        if (camera == null) {
            return;
        }
        camera.getCameraControl().setZoomRatio(zoomRatio);
    }

    @Nullable
    public GetZoomRatioResult getZoomRatio() {
        if (camera == null) {
            return null;
        }
        float zoomRatio = camera.getCameraInfo().getZoomState().getValue().getZoomRatio();
        return new GetZoomRatioResult(zoomRatio);
    }

    @Nullable
    public GetMinZoomRatioResult getMinZoomRatio() {
        if (camera == null) {
            return null;
        }
        float minZoomRatio = camera.getCameraInfo().getZoomState().getValue().getMinZoomRatio();
        return new GetMinZoomRatioResult(minZoomRatio);
    }

    @Nullable
    public GetMaxZoomRatioResult getMaxZoomRatio() {
        if (camera == null) {
            return null;
        }
        float maxZoomRatio = camera.getCameraInfo().getZoomState().getValue().getMaxZoomRatio();
        return new GetMaxZoomRatioResult(maxZoomRatio);
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

    public boolean isCameraActive() {
        return camera != null;
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
            .addOnSuccessListener(barcodes -> {
                if (scanSettings == null) {
                    // Scanning stopped while processing the image
                    return;
                }
                List<Barcode> barcodesWithEnoughVotes = voteForBarcodes(barcodes);
                for (Barcode barcode : barcodesWithEnoughVotes) {
                    handleScannedBarcode(barcode, imageSize);
                }
                if (barcodesWithEnoughVotes.size() > 0) {
                    handleScannedBarcodes(barcodesWithEnoughVotes.toArray(new Barcode[0]), imageSize);
                }
            })
            .addOnFailureListener(exception -> {
                handleScanError(exception);
            })
            .addOnCompleteListener(task -> {
                imageProxy.close();
                image.close();
            });
    }

    public void handleGoogleBarcodeScannerModuleInstallProgress(
        @ModuleInstallStatusUpdate.InstallState int state,
        @Nullable Integer progress
    ) {
        plugin.notifyGoogleBarcodeScannerModuleInstallProgressListener(state, progress);
        boolean isTerminateState = ModuleInstallProgressListener.isTerminateState(state);
        if (isTerminateState && moduleInstallProgressListener != null) {
            ModuleInstallClient moduleInstallClient = ModuleInstall.getClient(plugin.getContext());
            moduleInstallClient.unregisterListener(moduleInstallProgressListener);
            moduleInstallProgressListener = null;
        }
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

    private void handleScannedBarcodes(Barcode[] barcodes, Point imageSize) {
        plugin.notifyBarcodesScannedListener(barcodes, imageSize);
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
        boolean autoZoom = scanSettings.autoZoom;
        GmsBarcodeScannerOptions options;

        if (autoZoom) {
            options = new GmsBarcodeScannerOptions.Builder().setBarcodeFormats(formats[0], formats).enableAutoZoom().build();
        } else {
            options = new GmsBarcodeScannerOptions.Builder().setBarcodeFormats(formats[0], formats).build();
        }

        return options;
    }

    @Nullable
    private Integer voteForBarcode(Barcode barcode) {
        String rawValue = barcode.getRawValue();
        if (rawValue == null) {
            return null;
        } else {
            if (barcodeRawValueVotes.containsKey(rawValue)) {
                barcodeRawValueVotes.put(rawValue, barcodeRawValueVotes.get(rawValue) + 1);
            } else {
                barcodeRawValueVotes.put(rawValue, 1);
            }
            return barcodeRawValueVotes.get(rawValue);
        }
    }

    private List<Barcode> voteForBarcodes(List<Barcode> barcodes) {
        List<Barcode> barcodesWithEnoughVotes = new ArrayList<>();
        for (Barcode barcode : barcodes) {
            Integer votes = voteForBarcode(barcode);
            if (votes == null || votes >= 10) {
                // Do not filter out barcodes without raw value.
                barcodesWithEnoughVotes.add(barcode);
            }
        }
        return barcodesWithEnoughVotes;
    }
}
