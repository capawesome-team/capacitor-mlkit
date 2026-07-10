package io.capawesome.capacitorjs.plugins.mlkit.imagelabeling;

import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;
import io.capawesome.capacitorjs.plugins.mlkit.imagelabeling.classes.options.ProcessImageOptions;
import io.capawesome.capacitorjs.plugins.mlkit.imagelabeling.classes.results.ProcessImageResult;
import io.capawesome.capacitorjs.plugins.mlkit.imagelabeling.interfaces.NonEmptyResultCallback;

public class ImageLabeling {

    @NonNull
    private final ImageLabelingPlugin plugin;

    public ImageLabeling(@NonNull ImageLabelingPlugin plugin) {
        this.plugin = plugin;
    }

    public void processImage(@NonNull ProcessImageOptions options, @NonNull NonEmptyResultCallback<ProcessImageResult> callback) {
        InputImage inputImage = createInputImageFromFilePath(options.getPath());
        if (inputImage == null) {
            callback.error(new Exception(ImageLabelingPlugin.ERROR_LOAD_IMAGE_FAILED));
            return;
        }
        float confidenceThreshold = options.getConfidenceThreshold();

        ImageLabelerOptions imageLabelerOptions = new ImageLabelerOptions.Builder().setConfidenceThreshold(confidenceThreshold).build();

        final ImageLabeler imageLabeler = com.google.mlkit.vision.label.ImageLabeling.getClient(imageLabelerOptions);
        plugin
            .getActivity()
            .runOnUiThread(() -> {
                imageLabeler
                    .process(inputImage)
                    .addOnSuccessListener(labels -> {
                        imageLabeler.close();
                        ProcessImageResult result = new ProcessImageResult(labels);
                        callback.success(result);
                    })
                    .addOnCanceledListener(() -> {
                        imageLabeler.close();
                        callback.error(new Exception(ImageLabelingPlugin.ERROR_PROCESS_IMAGE_CANCELED));
                    })
                    .addOnFailureListener(exception -> {
                        imageLabeler.close();
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
