package io.capawesome.capacitorjs.plugins.mlkit.digitalinkrecognition;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.mlkit.digitalinkrecognition.classes.CustomException;
import io.capawesome.capacitorjs.plugins.mlkit.digitalinkrecognition.classes.options.DeleteDownloadedModelOptions;
import io.capawesome.capacitorjs.plugins.mlkit.digitalinkrecognition.classes.options.DownloadModelOptions;
import io.capawesome.capacitorjs.plugins.mlkit.digitalinkrecognition.classes.options.RecognizeOptions;
import io.capawesome.capacitorjs.plugins.mlkit.digitalinkrecognition.classes.results.GetDownloadedModelsResult;
import io.capawesome.capacitorjs.plugins.mlkit.digitalinkrecognition.classes.results.RecognizeResult;
import io.capawesome.capacitorjs.plugins.mlkit.digitalinkrecognition.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.mlkit.digitalinkrecognition.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.mlkit.digitalinkrecognition.interfaces.Result;

@CapacitorPlugin(name = "DigitalInkRecognition")
public class DigitalInkRecognitionPlugin extends Plugin {

    public static final String TAG = "DigitalInkRecognition";

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error occurred.";

    private DigitalInkRecognition implementation;

    @Override
    public void load() {
        implementation = new DigitalInkRecognition();
    }

    @PluginMethod
    public void deleteDownloadedModel(PluginCall call) {
        try {
            DeleteDownloadedModelOptions options = new DeleteDownloadedModelOptions(call);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.deleteDownloadedModel(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void downloadModel(PluginCall call) {
        try {
            DownloadModelOptions options = new DownloadModelOptions(call);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.downloadModel(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getDownloadedModels(PluginCall call) {
        try {
            NonEmptyResultCallback<GetDownloadedModelsResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetDownloadedModelsResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.getDownloadedModels(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void recognize(PluginCall call) {
        try {
            RecognizeOptions options = new RecognizeOptions(call);
            NonEmptyResultCallback<RecognizeResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull RecognizeResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.recognize(options, callback);
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
