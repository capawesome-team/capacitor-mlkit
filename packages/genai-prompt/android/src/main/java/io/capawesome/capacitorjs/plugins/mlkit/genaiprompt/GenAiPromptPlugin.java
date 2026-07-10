package io.capawesome.capacitorjs.plugins.mlkit.genaiprompt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.mlkit.genaiprompt.classes.events.DownloadProgressEvent;
import io.capawesome.capacitorjs.plugins.mlkit.genaiprompt.classes.events.InferenceProgressEvent;
import io.capawesome.capacitorjs.plugins.mlkit.genaiprompt.classes.options.GenerateContentOptions;
import io.capawesome.capacitorjs.plugins.mlkit.genaiprompt.classes.results.CheckFeatureStatusResult;
import io.capawesome.capacitorjs.plugins.mlkit.genaiprompt.classes.results.GenerateContentResult;
import io.capawesome.capacitorjs.plugins.mlkit.genaiprompt.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.mlkit.genaiprompt.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.mlkit.genaiprompt.interfaces.Result;

@CapacitorPlugin(name = "GenAiPrompt")
public class GenAiPromptPlugin extends Plugin {

    public static final String TAG = "GenAiPrompt";

    public static final String EVENT_DOWNLOAD_PROGRESS = "downloadProgress";
    public static final String EVENT_INFERENCE_PROGRESS = "inferenceProgress";

    public static final String ERROR_IMAGE_LOAD_FAILED = "image could not be loaded.";
    public static final String ERROR_PROMPT_MISSING = "prompt must be provided.";
    public static final String ERROR_UNKNOWN_ERROR = "An unknown error occurred.";

    @Nullable
    private GenAiPrompt implementation;

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

    @PluginMethod
    public void generateContent(PluginCall call) {
        try {
            GenerateContentOptions options = new GenerateContentOptions(call);
            NonEmptyResultCallback<GenerateContentResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GenerateContentResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.generateContent(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @Override
    public void load() {
        implementation = new GenAiPrompt(this);
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
