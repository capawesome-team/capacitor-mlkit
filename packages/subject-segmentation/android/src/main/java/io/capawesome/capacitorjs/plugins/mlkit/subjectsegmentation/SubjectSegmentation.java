package io.capawesome.capacitorjs.plugins.mlkit.subjectsegmentation;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.moduleinstall.InstallStatusListener;
import com.google.android.gms.common.moduleinstall.ModuleInstall;
import com.google.android.gms.common.moduleinstall.ModuleInstallClient;
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest;
import com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.segmentation.subject.Subject;
import com.google.mlkit.vision.segmentation.subject.SubjectSegmenter;
import com.google.mlkit.vision.segmentation.subject.SubjectSegmenterOptions;
import io.capawesome.capacitorjs.plugins.mlkit.subjectsegmentation.classes.ProcessImageOptions;
import io.capawesome.capacitorjs.plugins.mlkit.subjectsegmentation.classes.ProcessImageResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.FloatBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class SubjectSegmentation {

    @NonNull
    private final SubjectSegmentationPlugin plugin;

    @Nullable
    private ModuleInstallProgressListener moduleInstallProgressListener;

    public SubjectSegmentation(@NonNull SubjectSegmentationPlugin plugin) {
        this.plugin = plugin;
    }

    @Nullable
    public InputImage createInputImageFromFilePath(@NonNull String path) {
        try {
            return InputImage.fromFilePath(this.plugin.getContext(), Uri.parse(path));
        } catch (Exception exception) {
            return null;
        }
    }

    public void processImage(ProcessImageOptions options, ProcessImageResultCallback callback) {
        InputImage inputImage = options.getInputImage();
        Float threshold = options.getConfidence();
        SubjectSegmenterOptions.SubjectResultOptions subjectResultOptions = new SubjectSegmenterOptions.SubjectResultOptions.Builder()
            .enableConfidenceMask()
            .build();

        SubjectSegmenterOptions.Builder builder = new SubjectSegmenterOptions.Builder();
        SubjectSegmenterOptions subjectSegmenterOptions = builder
            .enableForegroundBitmap()
            .enableMultipleSubjects(subjectResultOptions)
            .enableForegroundConfidenceMask()
            .build();

        final SubjectSegmenter segmenter = com.google.mlkit.vision.segmentation.subject.SubjectSegmentation.getClient(
            subjectSegmenterOptions
        );

        plugin
            .getActivity()
            .runOnUiThread(() ->
                segmenter
                    .process(inputImage)
                    .addOnSuccessListener(segmentationResult -> {
                        segmenter.close();

                        List<Subject> subjects = segmentationResult.getSubjects();

                        int[] colors = new int[inputImage.getWidth() * inputImage.getHeight()];
                        for (Subject subject : subjects) {
                            FloatBuffer mask = subject.getConfidenceMask();
                            assert mask != null;
                            for (int i = 0; i < subject.getWidth() * subject.getHeight(); i++) {
                                float confidence = mask.get(i);
                                if (confidence > threshold) {
                                    int x = subject.getStartX() + (i % subject.getWidth());
                                    int y = subject.getStartY() + (i / subject.getWidth());
                                    int position = y * inputImage.getWidth() + x;

                                    colors[position] = Color.argb(128, 255, 0, 255);
                                }
                            }
                        }

                        Bitmap maskBitmap = Bitmap.createBitmap(
                            colors,
                            inputImage.getWidth(),
                            inputImage.getHeight(),
                            Bitmap.Config.ARGB_8888
                        );

                        Bitmap originalBitmap = Bitmap.createScaledBitmap(
                            Objects.requireNonNull(inputImage.getBitmapInternal()),
                            maskBitmap.getWidth(),
                            maskBitmap.getHeight(),
                            true
                        );

                        Bitmap resultBitmap = Bitmap.createBitmap(maskBitmap.getWidth(), maskBitmap.getHeight(), Bitmap.Config.ARGB_8888);

                        Canvas canvas = new Canvas(resultBitmap);
                        canvas.drawBitmap(originalBitmap, 0, 0, null);

                        Paint paint = new Paint();
                        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
                        canvas.drawBitmap(maskBitmap, 0, 0, paint);

                        // Create an image file name
                        @SuppressLint("SimpleDateFormat")
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                        String imageFileName = "PNG_" + timeStamp + "_";

                        try {
                            File image = File.createTempFile(imageFileName, ".png");

                            OutputStream stream = new FileOutputStream(image);
                            resultBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            stream.close();

                            ProcessImageResult result = new ProcessImageResult(
                                image.getAbsolutePath(),
                                resultBitmap.getWidth(),
                                resultBitmap.getHeight()
                            );

                            callback.success(result);
                        } catch (Exception exception) {
                            callback.error(exception);
                        }
                    })
                    .addOnCanceledListener(() -> {
                        segmenter.close();
                        callback.cancel();
                    })
                    .addOnFailureListener(exception -> {
                        segmenter.close();
                        callback.error(exception);
                    })
            );
    }

    public void isSubjectSegmentationScannerModuleAvailable(IsGoogleSubjectSegmentationModuleAvailableResultCallback callback) {
        SubjectSegmenter segmenter = com.google.mlkit.vision.segmentation.subject.SubjectSegmentation.getClient(
            new SubjectSegmenterOptions.Builder().build()
        );
        ModuleInstallClient moduleInstallClient = ModuleInstall.getClient(plugin.getContext());
        moduleInstallClient
            .areModulesAvailable(segmenter)
            .addOnSuccessListener(response -> {
                boolean isAvailable = response.areModulesAvailable();
                callback.success(isAvailable);
            })
            .addOnFailureListener(callback::error);
    }

    public void installSubjectSegmentationScannerModule(InstallSubjectSegmentationScannerModuleResultCallback callback) {
        SubjectSegmenter segmenter = com.google.mlkit.vision.segmentation.subject.SubjectSegmentation.getClient(
            new SubjectSegmenterOptions.Builder().build()
        );
        InstallStatusListener listener = new ModuleInstallProgressListener(this);
        ModuleInstallRequest moduleInstallRequest = ModuleInstallRequest.newBuilder().addApi(segmenter).setListener(listener).build();
        ModuleInstallClient moduleInstallClient = ModuleInstall.getClient(plugin.getContext());
        moduleInstallClient
            .installModules(moduleInstallRequest)
            .addOnSuccessListener(moduleInstallResponse -> {
                if (moduleInstallResponse.areModulesAlreadyInstalled()) {
                    callback.error(new Exception(SubjectSegmentationPlugin.ERROR_GOOGLE_SUBJECT_SEGMENTATION_MODULE_ALREADY_INSTALLED));
                } else {
                    callback.success();
                }
            })
            .addOnFailureListener(callback::error);
    }

    public void handleGoogleSubjectSegmentationScannerModuleInstallProgress(
        @ModuleInstallStatusUpdate.InstallState int state,
        @Nullable Integer progress
    ) {
        plugin.notifyGoogleSubjectSegmentationScannerModuleInstallProgressListener(state, progress);
        boolean isTerminateState = ModuleInstallProgressListener.isTerminateState(state);
        if (isTerminateState && moduleInstallProgressListener != null) {
            ModuleInstallClient moduleInstallClient = ModuleInstall.getClient(plugin.getContext());
            moduleInstallClient.unregisterListener(moduleInstallProgressListener);
            moduleInstallProgressListener = null;
        }
    }
}
