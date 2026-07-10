package io.capawesome.capacitorjs.plugins.mlkit.posedetection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.mlkit.posedetection.classes.options.ProcessImageOptions;
import io.capawesome.capacitorjs.plugins.mlkit.posedetection.classes.results.ProcessImageResult;
import io.capawesome.capacitorjs.plugins.mlkit.posedetection.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.mlkit.posedetection.interfaces.Result;

@CapacitorPlugin(name = "PoseDetection")
public class PoseDetectionPlugin extends Plugin {

    public static final String TAG = "PoseDetection";
    public static final String ERROR_PROCESS_IMAGE_CANCELED = "processImage canceled.";
    public static final String ERROR_PATH_MISSING = "path must be provided.";
    public static final String ERROR_PERFORMANCE_MODE_INVALID = "performanceMode is invalid.";
    public static final String ERROR_LOAD_IMAGE_FAILED = "image could not be loaded.";
    public static final String ERROR_UNKNOWN_ERROR = "An unknown error occurred.";

    private PoseDetection implementation;

    @Override
    public void load() {
        implementation = new PoseDetection(this);
    }

    @PluginMethod
    public void processImage(PluginCall call) {
        try {
            ProcessImageOptions options = new ProcessImageOptions(call);
            NonEmptyResultCallback<ProcessImageResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull ProcessImageResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.processImage(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    private void rejectCall(@NonNull PluginCall call, @NonNull Exception exception) {
        String message = exception.getMessage();
        if (message == null) {
            message = ERROR_UNKNOWN_ERROR;
        }
        Logger.error(TAG, message, exception);
        call.reject(message);
    }

    private void resolveCall(@NonNull PluginCall call, @Nullable Result result) {
        if (result == null) {
            call.resolve();
        } else {
            call.resolve(result.toJSObject());
        }
    }
}
