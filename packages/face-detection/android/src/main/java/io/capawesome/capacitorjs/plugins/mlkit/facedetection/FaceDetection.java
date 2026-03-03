package io.capawesome.capacitorjs.plugins.mlkit.facedetection;

import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;
import io.capawesome.capacitorjs.plugins.mlkit.facedetection.classes.ProcessImageOptions;
import io.capawesome.capacitorjs.plugins.mlkit.facedetection.classes.ProcessImageResult;

public class FaceDetection {

    @NonNull
    private final FaceDetectionPlugin plugin;

    public FaceDetection(@NonNull FaceDetectionPlugin plugin) {
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
        int performanceMode = options.getPerformanceMode();
        int landmarkMode = options.getLandmarkMode();
        int contourMode = options.getContourMode();
        int classificationMode = options.getClassificationMode();
        float minFaceSize = options.getMinFaceSize();
        boolean enableTracking = options.isTrackingEnabled();

        FaceDetectorOptions.Builder builder = new FaceDetectorOptions.Builder();
        builder.setPerformanceMode(performanceMode);
        builder.setLandmarkMode(landmarkMode);
        builder.setContourMode(contourMode);
        builder.setClassificationMode(classificationMode);
        builder.setMinFaceSize(minFaceSize);
        if (enableTracking) {
            builder.enableTracking();
        }
        FaceDetectorOptions faceDetectorOptions = builder.build();

        final FaceDetector faceDetector = com.google.mlkit.vision.face.FaceDetection.getClient(faceDetectorOptions);
        plugin
            .getActivity()
            .runOnUiThread(() -> {
                faceDetector
                    .process(inputImage)
                    .addOnSuccessListener(faces -> {
                        faceDetector.close();
                        ProcessImageResult result = new ProcessImageResult(faces);
                        callback.success(result);
                    })
                    .addOnCanceledListener(() -> {
                        faceDetector.close();
                        callback.cancel();
                    })
                    .addOnFailureListener(exception -> {
                        faceDetector.close();
                        callback.error(exception);
                    });
            });
    }
}
