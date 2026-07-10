package io.capawesome.capacitorjs.plugins.mlkit.genairewriting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.mlkit.genairewriting.classes.events.DownloadProgressEvent;
import io.capawesome.capacitorjs.plugins.mlkit.genairewriting.classes.events.InferenceProgressEvent;
import io.capawesome.capacitorjs.plugins.mlkit.genairewriting.classes.options.FeatureOptions;
import io.capawesome.capacitorjs.plugins.mlkit.genairewriting.classes.options.RewriteOptions;
import io.capawesome.capacitorjs.plugins.mlkit.genairewriting.classes.results.CheckFeatureStatusResult;
import io.capawesome.capacitorjs.plugins.mlkit.genairewriting.classes.results.RewriteResult;
import io.capawesome.capacitorjs.plugins.mlkit.genairewriting.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.mlkit.genairewriting.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.mlkit.genairewriting.interfaces.Result;

@CapacitorPlugin(name = "GenAiRewriting")
public class GenAiRewritingPlugin extends Plugin {

    public static final String TAG = "GenAiRewriting";

    public static final String EVENT_DOWNLOAD_PROGRESS = "downloadProgress";
    public static final String EVENT_INFERENCE_PROGRESS = "inferenceProgress";

    public static final String ERROR_LANGUAGE_INVALID = "language is invalid.";
    public static final String ERROR_OUTPUT_TYPE_INVALID = "outputType is invalid.";
    public static final String ERROR_TEXT_MISSING = "text must be provided.";
    public static final String ERROR_UNKNOWN_ERROR = "An unknown error occurred.";

    @Nullable
    private GenAiRewriting implementation;

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
        implementation = new GenAiRewriting(this);
    }

    public void notifyDownloadProgressListeners(@NonNull DownloadProgressEvent event) {
        notifyListeners(EVENT_DOWNLOAD_PROGRESS, event.toJSObject());
    }

    public void notifyInferenceProgressListeners(@NonNull InferenceProgressEvent event) {
        notifyListeners(EVENT_INFERENCE_PROGRESS, event.toJSObject());
    }

    @PluginMethod
    public void rewrite(PluginCall call) {
        try {
            RewriteOptions options = new RewriteOptions(call);
            NonEmptyResultCallback<RewriteResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull RewriteResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.rewrite(options, callback);
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
