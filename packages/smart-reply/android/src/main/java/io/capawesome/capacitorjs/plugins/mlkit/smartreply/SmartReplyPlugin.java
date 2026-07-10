package io.capawesome.capacitorjs.plugins.mlkit.smartreply;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.mlkit.smartreply.classes.options.SuggestRepliesOptions;
import io.capawesome.capacitorjs.plugins.mlkit.smartreply.classes.results.SuggestRepliesResult;
import io.capawesome.capacitorjs.plugins.mlkit.smartreply.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.mlkit.smartreply.interfaces.Result;

@CapacitorPlugin(name = "SmartReply")
public class SmartReplyPlugin extends Plugin {

    public static final String TAG = "SmartReply";

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error occurred.";

    private SmartReply implementation;

    @Override
    public void load() {
        implementation = new SmartReply();
    }

    @Override
    protected void handleOnDestroy() {
        if (implementation != null) {
            implementation.close();
        }
    }

    @PluginMethod
    public void suggestReplies(PluginCall call) {
        try {
            SuggestRepliesOptions options = new SuggestRepliesOptions(call);
            NonEmptyResultCallback<SuggestRepliesResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull SuggestRepliesResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.suggestReplies(options, callback);
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
