package io.capawesome.capacitorjs.plugins.mlkit.posedetection;

import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.pose.PoseDetector;
import com.google.mlkit.vision.pose.PoseDetectorOptionsBase;
import com.google.mlkit.vision.pose.accurate.AccuratePoseDetectorOptions;
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions;
import io.capawesome.capacitorjs.plugins.mlkit.posedetection.classes.options.ProcessImageOptions;
import io.capawesome.capacitorjs.plugins.mlkit.posedetection.classes.results.ProcessImageResult;
import io.capawesome.capacitorjs.plugins.mlkit.posedetection.interfaces.NonEmptyResultCallback;

public class PoseDetection {

    @NonNull
    private final PoseDetectionPlugin plugin;

    public PoseDetection(@NonNull PoseDetectionPlugin plugin) {
        this.plugin = plugin;
    }

    public void processImage(@NonNull ProcessImageOptions options, @NonNull NonEmptyResultCallback<ProcessImageResult> callback) {
        InputImage inputImage = createInputImageFromFilePath(options.getPath());
        if (inputImage == null) {
            callback.error(new Exception(PoseDetectionPlugin.ERROR_LOAD_IMAGE_FAILED));
            return;
        }

        PoseDetectorOptionsBase poseDetectorOptions;
        if (options.isAccurateModeEnabled()) {
            poseDetectorOptions = new AccuratePoseDetectorOptions.Builder()
                .setDetectorMode(AccuratePoseDetectorOptions.SINGLE_IMAGE_MODE)
                .build();
        } else {
            poseDetectorOptions = new PoseDetectorOptions.Builder().setDetectorMode(PoseDetectorOptions.SINGLE_IMAGE_MODE).build();
        }

        final PoseDetector poseDetector = com.google.mlkit.vision.pose.PoseDetection.getClient(poseDetectorOptions);
        plugin
            .getActivity()
            .runOnUiThread(() -> {
                poseDetector
                    .process(inputImage)
                    .addOnSuccessListener(pose -> {
                        poseDetector.close();
                        ProcessImageResult result = new ProcessImageResult(pose);
                        callback.success(result);
                    })
                    .addOnCanceledListener(() -> {
                        poseDetector.close();
                        callback.error(new Exception(PoseDetectionPlugin.ERROR_PROCESS_IMAGE_CANCELED));
                    })
                    .addOnFailureListener(exception -> {
                        poseDetector.close();
                        callback.error(exception);
                    });
            });
    }

    @Nullable
    private InputImage createInputImageFromFilePath(@NonNull String path) {
        try {
            return InputImage.fromFilePath(plugin.getContext(), Uri.parse(path));
        } catch (Exception exception) {
            return null;
        }
    }
}
