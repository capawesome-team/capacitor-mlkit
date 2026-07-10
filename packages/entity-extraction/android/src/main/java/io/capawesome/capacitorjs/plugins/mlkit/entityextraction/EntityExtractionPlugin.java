package io.capawesome.capacitorjs.plugins.mlkit.entityextraction;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.mlkit.entityextraction.classes.CustomException;
import io.capawesome.capacitorjs.plugins.mlkit.entityextraction.classes.options.DeleteDownloadedModelOptions;
import io.capawesome.capacitorjs.plugins.mlkit.entityextraction.classes.options.DownloadModelOptions;
import io.capawesome.capacitorjs.plugins.mlkit.entityextraction.classes.options.ExtractEntitiesOptions;
import io.capawesome.capacitorjs.plugins.mlkit.entityextraction.classes.results.ExtractEntitiesResult;
import io.capawesome.capacitorjs.plugins.mlkit.entityextraction.classes.results.GetDownloadedModelsResult;
import io.capawesome.capacitorjs.plugins.mlkit.entityextraction.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.mlkit.entityextraction.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.mlkit.entityextraction.interfaces.Result;

@CapacitorPlugin(name = "EntityExtraction")
public class EntityExtractionPlugin extends Plugin {

    public static final String TAG = "EntityExtraction";

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error occurred.";

    private EntityExtraction implementation;

    @Override
    public void load() {
        implementation = new EntityExtraction();
    }

    @Override
    protected void handleOnDestroy() {
        if (implementation != null) {
            implementation.close();
        }
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
    public void extractEntities(PluginCall call) {
        try {
            ExtractEntitiesOptions options = new ExtractEntitiesOptions(call);
            NonEmptyResultCallback<ExtractEntitiesResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull ExtractEntitiesResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.extractEntities(options, callback);
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

    private void rejectCall(@NonNull PluginCall call, @NonNull Exception exception) {
        String message = exception.getMessage();
        if (message == null) {
            message = ERROR_UNKNOWN_ERROR;
        }
        String code = null;
        if (exception instanceof CustomException) {
            code = ((CustomException) exception).getCode();
        }
        Logger.error(TAG, message, exception);
        call.reject(message, code);
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
