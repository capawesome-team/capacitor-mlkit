package io.capawesome.capacitorjs.plugins.mlkit.genaiimagedescription;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.mlkit.genaiimagedescription.classes.events.DownloadProgressEvent;
import io.capawesome.capacitorjs.plugins.mlkit.genaiimagedescription.classes.events.InferenceProgressEvent;
import io.capawesome.capacitorjs.plugins.mlkit.genaiimagedescription.classes.options.DescribeImageOptions;
import io.capawesome.capacitorjs.plugins.mlkit.genaiimagedescription.classes.results.CheckFeatureStatusResult;
import io.capawesome.capacitorjs.plugins.mlkit.genaiimagedescription.classes.results.DescribeImageResult;
import io.capawesome.capacitorjs.plugins.mlkit.genaiimagedescription.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.mlkit.genaiimagedescription.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.mlkit.genaiimagedescription.interfaces.Result;

@CapacitorPlugin(name = "GenAiImageDescription")
public class GenAiImageDescriptionPlugin extends Plugin {

    public static final String TAG = "GenAiImageDescription";

    public static final String EVENT_DOWNLOAD_PROGRESS = "downloadProgress";
    public static final String EVENT_INFERENCE_PROGRESS = "inferenceProgress";

    public static final String ERROR_IMAGE_LOAD_FAILED = "image could not be loaded.";
    public static final String ERROR_PATH_MISSING = "path must be provided.";
    public static final String ERROR_UNKNOWN_ERROR = "An unknown error occurred.";

    @Nullable
    private GenAiImageDescription implementation;

    @PluginMethod
    public void checkFeatureStatus(PluginCall call) {
        try {
            NonEmptyResultCallback<CheckFeatureStatusResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull CheckFeatureStatusResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.checkFeatureStatus(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void describeImage(PluginCall call) {
        try {
            DescribeImageOptions options = new DescribeImageOptions(call);
            NonEmptyResultCallback<DescribeImageResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull DescribeImageResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.describeImage(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void downloadFeature(PluginCall call) {
        try {
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.downloadFeature(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @Override
    public void load() {
        implementation = new GenAiImageDescription(this);
    }

    public void notifyDownloadProgressListeners(@NonNull DownloadProgressEvent event) {
        notifyListeners(EVENT_DOWNLOAD_PROGRESS, event.toJSObject());
    }

    public void notifyInferenceProgressListeners(@NonNull InferenceProgressEvent event) {
        notifyListeners(EVENT_INFERENCE_PROGRESS, event.toJSObject());
    }

    @Override
    protected void handleOnDestroy() {
        if (implementation != null) {
            implementation.handleOnDestroy();
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

    private void resolveCall(@NonNull PluginCall call) {
        call.resolve();
    }

    private void resolveCall(@NonNull PluginCall call, @Nullable Result result) {
        if (result == null) {
            call.resolve();
        } else {
            call.resolve(result.toJSObject());
        }
    }
}
