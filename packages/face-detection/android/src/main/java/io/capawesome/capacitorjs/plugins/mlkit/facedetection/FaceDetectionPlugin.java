package io.capawesome.capacitorjs.plugins.mlkit.facedetection;

import android.graphics.PointF;
import android.graphics.Rect;
import android.net.Uri;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceContour;
import com.google.mlkit.vision.face.FaceDetectorOptions;
import com.google.mlkit.vision.face.FaceLandmark;
import io.capawesome.capacitorjs.plugins.mlkit.facedetection.classes.ProcessImageOptions;
import io.capawesome.capacitorjs.plugins.mlkit.facedetection.classes.ProcessImageResult;
import java.util.List;

@CapacitorPlugin(name = "FaceDetection")
public class FaceDetectionPlugin extends Plugin {

    public static final String TAG = "FaceDetection";
    public static final String ERROR_PROCESS_IMAGE_CANCELED = "processImage canceled.";
    public static final String ERROR_PATH_MISSING = "path must be provided.";
    public static final String ERROR_LOAD_IMAGE_FAILED = "image could not be loaded.";

    private FaceDetection implementation;

    @Override
    public void load() {
        try {
            implementation = new FaceDetection(this);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
        }
    }

    @PluginMethod
    public void processImage(PluginCall call) {
        try {
            String path = call.getString("path", null);
            if (path == null) {
                call.reject(ERROR_PATH_MISSING);
                return;
            }
            Integer performanceMode = call.getInt("performanceMode", FaceDetectorOptions.PERFORMANCE_MODE_FAST);
            Integer landmarkMode = call.getInt("landmarkMode", FaceDetectorOptions.LANDMARK_MODE_NONE);
            Integer contourMode = call.getInt("contourMode", FaceDetectorOptions.CONTOUR_MODE_NONE);
            Integer classificationMode = call.getInt("classificationMode", FaceDetectorOptions.CLASSIFICATION_MODE_NONE);
            Float minFaceSize = call.getFloat("minFaceSize", 0.1f);
            Boolean enableTracking = call.getBoolean("enableTracking", false);

            InputImage image = implementation.createInputImageFromFilePath(path);
            if (image == null) {
                call.reject(ERROR_LOAD_IMAGE_FAILED);
                return;
            }
            ProcessImageOptions options = new ProcessImageOptions(
                image,
                performanceMode,
                landmarkMode,
                contourMode,
                classificationMode,
                minFaceSize,
                enableTracking
            );

            implementation.processImage(
                options,
                new ProcessImageResultCallback() {
                    @Override
                    public void success(ProcessImageResult result) {
                        call.resolve(result.toJSObject());
                    }

                    @Override
                    public void cancel() {
                        call.reject(ERROR_PROCESS_IMAGE_CANCELED);
                    }

                    @Override
                    public void error(Exception exception) {
                        String message = exception.getMessage();
                        Logger.error(TAG, message, exception);
                        call.reject(message);
                    }
                }
            );
        } catch (Exception exception) {
            String message = exception.getMessage();
            Logger.error(TAG, message, exception);
            call.reject(message);
        }
    }
}
