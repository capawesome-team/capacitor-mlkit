package io.capawesome.capacitorjs.plugins.mlkit.genaiproofreading;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.mlkit.genaiproofreading.classes.events.DownloadProgressEvent;
import io.capawesome.capacitorjs.plugins.mlkit.genaiproofreading.classes.events.InferenceProgressEvent;
import io.capawesome.capacitorjs.plugins.mlkit.genaiproofreading.classes.options.FeatureOptions;
import io.capawesome.capacitorjs.plugins.mlkit.genaiproofreading.classes.options.ProofreadOptions;
import io.capawesome.capacitorjs.plugins.mlkit.genaiproofreading.classes.results.CheckFeatureStatusResult;
import io.capawesome.capacitorjs.plugins.mlkit.genaiproofreading.classes.results.ProofreadResult;
import io.capawesome.capacitorjs.plugins.mlkit.genaiproofreading.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.mlkit.genaiproofreading.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.mlkit.genaiproofreading.interfaces.Result;

@CapacitorPlugin(name = "GenAiProofreading")
public class GenAiProofreadingPlugin extends Plugin {

    public static final String TAG = "GenAiProofreading";

    public static final String EVENT_DOWNLOAD_PROGRESS = "downloadProgress";
    public static final String EVENT_INFERENCE_PROGRESS = "inferenceProgress";

    public static final String ERROR_INPUT_TYPE_INVALID = "inputType is invalid.";
    public static final String ERROR_LANGUAGE_INVALID = "language is invalid.";
    public static final String ERROR_TEXT_MISSING = "text must be provided.";
    public static final String ERROR_UNKNOWN_ERROR = "An unknown error occurred.";

    @Nullable
    private GenAiProofreading implementation;

    @PluginMethod
    public void checkFeatureStatus(PluginCall call) {
        try {
            FeatureOptions options = new FeatureOptions(call);
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
            implementation.checkFeatureStatus(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void downloadFeature(PluginCall call) {
        try {
            FeatureOptions options = new FeatureOptions(call);
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
            implementation.downloadFeature(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @Override
    public void load() {
        implementation = new GenAiProofreading(this);
    }

    public void notifyDownloadProgressListeners(@NonNull DownloadProgressEvent event) {
        notifyListeners(EVENT_DOWNLOAD_PROGRESS, event.toJSObject());
    }

    public void notifyInferenceProgressListeners(@NonNull InferenceProgressEvent event) {
        notifyListeners(EVENT_INFERENCE_PROGRESS, event.toJSObject());
    }

    @PluginMethod
    public void proofread(PluginCall call) {
        try {
            ProofreadOptions options = new ProofreadOptions(call);
            NonEmptyResultCallback<ProofreadResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull ProofreadResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.proofread(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
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
