package io.capawesome.capacitorjs.plugins.mlkit.facedetection;

import androidx.annotation.NonNull;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;

public class FaceDetection {

    @NonNull
    private final FaceDetectionPlugin plugin;

    public FaceDetection(@NonNull FaceDetectionPlugin plugin) {
        this.plugin = plugin;
    }

    public void processImage(InputImage image, FaceDetectorOptions options, ProcessImageResultCallback callback) {
        // Gets a new instance of FaceDetector that detects faces in a supplied image.
        // https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceDetection
        final FaceDetector faceDetector = com.google.mlkit.vision.face.FaceDetection.getClient(
            // The options for the face detector
            options
        );

        plugin
            .getActivity()
            .runOnUiThread(
                () -> {
                    // Detects human faces from the supplied image.
                    // A Task that asynchronously returns a List of detected Faces
                    faceDetector
                        .process(image)
                        .addOnSuccessListener(
                            faces -> {
                                // Closes the detector and releases its resources.
                                // https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceDetector#close()
                                faceDetector.close();

                                callback.success(faces);
                            }
                        )
                        .addOnCanceledListener(
                            () -> {
                                // Closes the detector and releases its resources.
                                // https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceDetector#close()
                                faceDetector.close();

                                callback.cancel();
                            }
                        )
                        .addOnFailureListener(
                            exception -> {
                                // Closes the detector and releases its resources.
                                // https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceDetector#close()
                                faceDetector.close();

                                callback.error(exception);
                            }
                        );
                }
            );
    }
}
