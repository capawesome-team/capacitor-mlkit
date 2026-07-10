package io.capawesome.capacitorjs.plugins.mlkit.genaispeechrecognition;

import android.Manifest;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.PermissionState;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;
import io.capawesome.capacitorjs.plugins.mlkit.genaispeechrecognition.classes.CustomException;
import io.capawesome.capacitorjs.plugins.mlkit.genaispeechrecognition.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.mlkit.genaispeechrecognition.classes.events.DownloadProgressEvent;
import io.capawesome.capacitorjs.plugins.mlkit.genaispeechrecognition.classes.events.ErrorEvent;
import io.capawesome.capacitorjs.plugins.mlkit.genaispeechrecognition.classes.events.FinalResultEvent;
import io.capawesome.capacitorjs.plugins.mlkit.genaispeechrecognition.classes.events.PartialResultEvent;
import io.capawesome.capacitorjs.plugins.mlkit.genaispeechrecognition.classes.options.FeatureOptions;
import io.capawesome.capacitorjs.plugins.mlkit.genaispeechrecognition.classes.results.CheckFeatureStatusResult;
import io.capawesome.capacitorjs.plugins.mlkit.genaispeechrecognition.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.mlkit.genaispeechrecognition.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.mlkit.genaispeechrecognition.interfaces.Result;

@CapacitorPlugin(
    name = "GenAiSpeechRecognition",
    permissions = @Permission(alias = GenAiSpeechRecognitionPlugin.PERMISSION_MICROPHONE, strings = { Manifest.permission.RECORD_AUDIO })
)
public class GenAiSpeechRecognitionPlugin extends Plugin {

    public static final String TAG = "GenAiSpeechRecognition";

    public static final String PERMISSION_MICROPHONE = "microphone";

    public static final String EVENT_DOWNLOAD_PROGRESS = "downloadProgress";
    public static final String EVENT_ERROR = "error";
    public static final String EVENT_FINAL_RESULT = "finalResult";
    public static final String EVENT_PARTIAL_RESULT = "partialResult";

    public static final String ERROR_LOCALE_INVALID = "locale is invalid.";
    public static final String ERROR_MODE_INVALID = "mode is invalid.";
    public static final String ERROR_UNKNOWN_ERROR = "An unknown error occurred.";

    private static final String CALLBACK_MICROPHONE_PERMISSION = "handleMicrophonePermissionCallback";

    @Nullable
    private GenAiSpeechRecognition implementation;

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
        implementation = new GenAiSpeechRecognition(this);
    }

    public void notifyDownloadProgressListeners(@NonNull DownloadProgressEvent event) {
        notifyListeners(EVENT_DOWNLOAD_PROGRESS, event.toJSObject());
    }

    public void notifyErrorListeners(@NonNull ErrorEvent event) {
        notifyListeners(EVENT_ERROR, event.toJSObject());
    }

    public void notifyFinalResultListeners(@NonNull FinalResultEvent event) {
        notifyListeners(EVENT_FINAL_RESULT, event.toJSObject());
    }

    public void notifyPartialResultListeners(@NonNull PartialResultEvent event) {
        notifyListeners(EVENT_PARTIAL_RESULT, event.toJSObject());
    }

    @PluginMethod
    public void startRecognition(PluginCall call) {
        try {
            if (getPermissionState(PERMISSION_MICROPHONE) != PermissionState.GRANTED) {
                requestPermissionForAlias(PERMISSION_MICROPHONE, call, CALLBACK_MICROPHONE_PERMISSION);
                return;
            }
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
            implementation.startRecognition(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void stopRecognition(PluginCall call) {
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
            implementation.stopRecognition(callback);
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

    @PermissionCallback
    private void handleMicrophonePermissionCallback(PluginCall call) {
        if (getPermissionState(PERMISSION_MICROPHONE) == PermissionState.GRANTED) {
            if (call.getMethodName().equals("startRecognition")) {
                startRecognition(call);
            }
        } else {
            rejectCall(call, CustomExceptions.MICROPHONE_PERMISSION_DENIED);
        }
    }

    private void rejectCall(@NonNull PluginCall call, @NonNull Exception exception) {
        String message = exception.getMessage();
        if (message == null) {
            message = ERROR_UNKNOWN_ERROR;
        }
        Logger.error(TAG, message, exception);
        if (exception instanceof CustomException) {
            call.reject(message, ((CustomException) exception).getCode());
        } else {
            call.reject(message);
        }
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
