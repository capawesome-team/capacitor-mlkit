package io.capawesome.capacitorjs.plugins.mlkit.smartreply;

import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.google.mlkit.nl.SmartReply.
// import com.google.mlkit.nl.translate.TranslateRemoteModel;
import java.util.Set;

public interface SmartReplyResultCallback {
    void success();
    void error(Exception exception);
}


@CapacitorPlugin(name = "SmartReply")
public class SmartReplyPlugin extends Plugin {

    public static final String ERROR_MESSAGES_MISSING = "messages must be provided.";
    public static final String ERROR_NOT_SUPPORTED_MISSING = "the conversation's language isn't supported";

    private SmartReply implementation = new SmartReply();

    @PluginMethod
    public void smartReply(PluginCall call) {
        String[] messages = call.getStringArray("messages");
        try {
            if (messages.length == 0) {
                throw new Exception(ERROR_MESSAGES_MISSING);
            }

            implementation.smartReply(
                messages,
                new SmartReplySuggestionResult() {
                    @Override
                    public void success(JSObject res) {
                        result.put("value", res.getArray("value")); 
                        call.resolve(result);
                    }

                    @Override
                    public void error(Exception exception) {
                        String message = exception.getMessage();
                        Logger.error(message, exception);
                        call.reject(message);
                    }
                }
            );
        } catch (Exception exception) {
            String message = exception.getMessage();
            Logger.error(message, exception);
            call.reject(message);
        }
    }
}
