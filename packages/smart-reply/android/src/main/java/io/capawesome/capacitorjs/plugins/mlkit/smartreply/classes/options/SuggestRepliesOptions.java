package io.capawesome.capacitorjs.plugins.mlkit.smartreply.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.PluginCall;
import com.google.mlkit.nl.smartreply.TextMessage;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class SuggestRepliesOptions {

    private static final String ERROR_MESSAGES_MISSING = "messages must be provided.";
    private static final String ERROR_TEXT_MISSING = "text must be provided.";
    private static final String ERROR_TIMESTAMP_MISSING = "timestamp must be provided.";
    private static final String ERROR_USER_ID_MISSING = "userId must be provided.";

    @NonNull
    private final List<TextMessage> messages;

    public SuggestRepliesOptions(@NonNull PluginCall call) throws Exception {
        this.messages = SuggestRepliesOptions.getMessagesFromCall(call);
    }

    @NonNull
    public List<TextMessage> getMessages() {
        return messages;
    }

    @NonNull
    private static List<TextMessage> getMessagesFromCall(@NonNull PluginCall call) throws Exception {
        JSArray messagesArray = call.getArray("messages");
        if (messagesArray == null || messagesArray.length() == 0) {
            throw new Exception(ERROR_MESSAGES_MISSING);
        }
        List<TextMessage> messages = new ArrayList<>();
        for (int i = 0; i < messagesArray.length(); i++) {
            JSONObject messageObject = messagesArray.getJSONObject(i);
            messages.add(SuggestRepliesOptions.createTextMessage(messageObject));
        }
        return messages;
    }

    @NonNull
    private static TextMessage createTextMessage(@NonNull JSONObject messageObject) throws Exception {
        String text = messageObject.optString("text", null);
        if (text == null) {
            throw new Exception(ERROR_TEXT_MISSING);
        }
        if (!messageObject.has("timestamp")) {
            throw new Exception(ERROR_TIMESTAMP_MISSING);
        }
        long timestamp = messageObject.getLong("timestamp");
        boolean isLocalUser = messageObject.optBoolean("isLocalUser", false);
        if (isLocalUser) {
            return TextMessage.createForLocalUser(text, timestamp);
        }
        String userId = messageObject.optString("userId", null);
        if (userId == null) {
            throw new Exception(ERROR_USER_ID_MISSING);
        }
        return TextMessage.createForRemoteUser(text, timestamp, userId);
    }
}
