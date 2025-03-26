package io.capawesome.capacitorjs.plugins.mlkit.subjectsegmentation;

import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.google.mlkit.vision.common.InputImage;
import io.capawesome.capacitorjs.plugins.mlkit.subjectsegmentation.classes.ProcessImageOptions;
import io.capawesome.capacitorjs.plugins.mlkit.subjectsegmentation.classes.ProcessImageResult;

@CapacitorPlugin(name = "SubjectSegmentation")
public class SubjectSegmentationPlugin extends Plugin {

    public static final String TAG = "SubjectSegmentation";

    public static final String GOOGLE_SUBJECT_SEGMENTATION_MODULE_INSTALL_PROGRESS_EVENT = "googleSubjectSegmentationModuleInstallProgress";
    public static final String ERROR_GOOGLE_SUBJECT_SEGMENTATION_MODULE_NOT_AVAILABLE =
        "The Google Subject Segmentation Module is not available. You must install it first using the installSubjectSegmentationScannerModule method.";
    public static final String ERROR_GOOGLE_SUBJECT_SEGMENTATION_MODULE_ALREADY_INSTALLED =
        "The Google Subject Segmentation Module is already installed.";
    public static final String ERROR_PROCESS_IMAGE_CANCELED = "processImage canceled.";
    public static final String ERROR_PATH_MISSING = "path must be provided.";
    public static final String ERROR_LOAD_IMAGE_FAILED = "The image could not be loaded.";

    public static final float CONFIDENCE = 0.9f;

    private SubjectSegmentation implementation;

    @Override
    public void load() {
        try {
            implementation = new SubjectSegmentation(this);
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

            Integer width = call.getInt("width", null);
            Integer height = call.getInt("height", null);

            Float confidence = call.getFloat("confidence", CONFIDENCE);

            InputImage image = implementation.createInputImageFromFilePath(path);
            if (image == null) {
                call.reject(ERROR_LOAD_IMAGE_FAILED);
                return;
            }
            ProcessImageOptions options = new ProcessImageOptions(image, width, height, confidence);

            implementation.isSubjectSegmentationScannerModuleAvailable(
                new IsGoogleSubjectSegmentationModuleAvailableResultCallback() {
                    @Override
                    public void success(boolean isAvailable) {
                        if (isAvailable) {
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
                        } else {
                            call.reject(ERROR_GOOGLE_SUBJECT_SEGMENTATION_MODULE_NOT_AVAILABLE);
                        }
                    }

                    @Override
                    public void error(Exception exception) {
                        Logger.error(TAG, exception.getMessage(), exception);
                        call.reject(exception.getMessage());
                    }
                }
            );
        } catch (Exception exception) {
            String message = exception.getMessage();
            Logger.error(TAG, message, exception);
            call.reject(message);
        }
    }

    @PluginMethod
    public void isSubjectSegmentationScannerModuleAvailable(PluginCall call) {
        try {
            implementation.isSubjectSegmentationScannerModuleAvailable(
                new IsGoogleSubjectSegmentationModuleAvailableResultCallback() {
                    @Override
                    public void success(boolean isAvailable) {
                        JSObject result = new JSObject();
                        result.put("available", isAvailable);
                        call.resolve(result);
                    }

                    @Override
                    public void error(Exception exception) {
                        Logger.error(TAG, exception.getMessage(), exception);
                        call.reject(exception.getMessage());
                    }
                }
            );
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void installGoogleSubjectSegmentationModule(PluginCall call) {
        try {
            implementation.installSubjectSegmentationScannerModule(
                new InstallSubjectSegmentationScannerModuleResultCallback() {
                    @Override
                    public void success() {
                        call.resolve();
                    }

                    @Override
                    public void error(Exception exception) {
                        Logger.error(TAG, exception.getMessage(), exception);
                        call.reject(exception.getMessage());
                    }
                }
            );
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    public void notifyGoogleSubjectSegmentationScannerModuleInstallProgressListener(int state, @Nullable Integer progress) {
        try {
            JSObject result = new JSObject();
            result.put("state", state);
            if (progress != null) {
                result.put("progress", progress);
            }

            notifyListeners(GOOGLE_SUBJECT_SEGMENTATION_MODULE_INSTALL_PROGRESS_EVENT, result);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
        }
    }
}
