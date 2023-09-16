package io.capawesome.capacitorjs.plugins.mlkit.facemeshdetection;

import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.facemesh.FaceMeshDetectorOptions;
import io.capawesome.capacitorjs.plugins.mlkit.facemeshdetection.classes.ProcessImageOptions;
import io.capawesome.capacitorjs.plugins.mlkit.facemeshdetection.classes.ProcessImageResult;

@CapacitorPlugin(name = "FaceMeshDetection")
public class FaceMeshDetectionPlugin extends Plugin {

    public static final String TAG = "FaceMeshDetection";

    public static final String ERROR_PROCESS_IMAGE_CANCELED = "processImage canceled.";
    public static final String ERROR_PATH_MISSING = "path must be provided.";
    public static final String ERROR_LOAD_IMAGE_FAILED = "The image could not be loaded.";

    private FaceMeshDetection implementation;

    @Override
    public void load() {
        try {
            implementation = new FaceMeshDetection(this);
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

            Integer useCase = call.getInt("useCase", FaceMeshDetectorOptions.FACE_MESH);

            InputImage image = implementation.createInputImageFromFilePath(path);
            if (image == null) {
                call.reject(ERROR_LOAD_IMAGE_FAILED);
                return;
            }
            ProcessImageOptions options = new ProcessImageOptions(image, useCase);

            implementation.processImage(
                options,
                new ProcessImageResultCallback() {
                    @Override
                    public void success(ProcessImageResult result) {
                        try {
                            call.resolve(result.toJSObject());
                        } catch (Exception exception) {
                            String message = exception.getMessage();
                            Logger.error(TAG, message, exception);
                            call.reject(message);
                        }
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
