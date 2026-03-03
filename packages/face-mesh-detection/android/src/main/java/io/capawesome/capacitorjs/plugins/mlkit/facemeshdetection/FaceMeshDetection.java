package io.capawesome.capacitorjs.plugins.mlkit.facemeshdetection;

import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.facemesh.FaceMeshDetector;
import com.google.mlkit.vision.facemesh.FaceMeshDetectorOptions;
import io.capawesome.capacitorjs.plugins.mlkit.facemeshdetection.classes.ProcessImageOptions;
import io.capawesome.capacitorjs.plugins.mlkit.facemeshdetection.classes.ProcessImageResult;

public class FaceMeshDetection {

    @NonNull
    private final FaceMeshDetectionPlugin plugin;

    public FaceMeshDetection(@NonNull FaceMeshDetectionPlugin plugin) {
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
        int useCase = options.getUseCase();

        FaceMeshDetectorOptions.Builder builder = new FaceMeshDetectorOptions.Builder();
        builder.setUseCase(useCase);
        builder.build();
        FaceMeshDetectorOptions faceMeshDetectorOptions = builder.build();

        final FaceMeshDetector faceMeshDetector = com.google.mlkit.vision.facemesh.FaceMeshDetection.getClient(faceMeshDetectorOptions);
        plugin
            .getActivity()
            .runOnUiThread(() -> {
                faceMeshDetector
                    .process(inputImage)
                    .addOnSuccessListener(faceMeshs -> {
                        faceMeshDetector.close();
                        ProcessImageResult result = new ProcessImageResult(faceMeshs);
                        callback.success(result);
                    })
                    .addOnCanceledListener(() -> {
                        faceMeshDetector.close();
                        callback.cancel();
                    })
                    .addOnFailureListener(exception -> {
                        faceMeshDetector.close();
                        callback.error(exception);
                    });
            });
    }
}
