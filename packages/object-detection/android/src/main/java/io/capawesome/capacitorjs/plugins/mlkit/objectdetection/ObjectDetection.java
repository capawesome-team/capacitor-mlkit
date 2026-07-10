package io.capawesome.capacitorjs.plugins.mlkit.objectdetection;

import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.objects.ObjectDetector;
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions;
import io.capawesome.capacitorjs.plugins.mlkit.objectdetection.classes.options.ProcessImageOptions;
import io.capawesome.capacitorjs.plugins.mlkit.objectdetection.classes.results.ProcessImageResult;
import io.capawesome.capacitorjs.plugins.mlkit.objectdetection.interfaces.NonEmptyResultCallback;

public class ObjectDetection {

    @NonNull
    private final ObjectDetectionPlugin plugin;

    public ObjectDetection(@NonNull ObjectDetectionPlugin plugin) {
        this.plugin = plugin;
    }

    public void processImage(@NonNull ProcessImageOptions options, @NonNull NonEmptyResultCallback<ProcessImageResult> callback) {
        InputImage inputImage = createInputImageFromFilePath(options.getPath());
        if (inputImage == null) {
            callback.error(new Exception(ObjectDetectionPlugin.ERROR_LOAD_IMAGE_FAILED));
            return;
        }

        ObjectDetectorOptions.Builder optionsBuilder = new ObjectDetectorOptions.Builder()
            .setDetectorMode(ObjectDetectorOptions.SINGLE_IMAGE_MODE);
        if (options.isClassificationEnabled()) {
            optionsBuilder.enableClassification();
        }
        if (options.isMultipleObjectsEnabled()) {
            optionsBuilder.enableMultipleObjects();
        }

        final ObjectDetector objectDetector = com.google.mlkit.vision.objects.ObjectDetection.getClient(optionsBuilder.build());
        plugin
            .getActivity()
            .runOnUiThread(() -> {
                objectDetector
                    .process(inputImage)
                    .addOnSuccessListener(detectedObjects -> {
                        objectDetector.close();
                        ProcessImageResult result = new ProcessImageResult(detectedObjects);
                        callback.success(result);
                    })
                    .addOnCanceledListener(() -> {
                        objectDetector.close();
                        callback.error(new Exception(ObjectDetectionPlugin.ERROR_PROCESS_IMAGE_CANCELED));
                    })
                    .addOnFailureListener(exception -> {
                        objectDetector.close();
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
