package io.capawesome.capacitorjs.plugins.mlkit.smartreply;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.common.model.RemoteModelManager;
import com.google.mlkit.nl.smartreply.SmartReply;
import com.google.mlkit.nl.smartreply.SmartReplySuggestion;
import com.google.mlkit.nl.smartreply.SmartReplySuggestionResult;
import com.google.mlkit.nl.smartreply.TextMessage;
import java.util.Set;

public class SmartReply {

    public static final String ERROR_MESSAGES_MISSING = "messages must be provided.";
    public static final String ERROR_LANGUAGE_NOT_SUPPORTED = "the conversation's language isn't supported";

    public SmartReply() {
    }

    public void smartReply(String[] messages, SmartReplyResultCallback resultCallback) {
        SmartReplyGenerator smartReply = SmartReply.getClient();
        smartReply.suggestReplies(conversation)
            .addOnSuccessListener(new OnSuccessListener() {
                @Override
                public void onSuccess(SmartReplySuggestionResult result) {
                    if (result.getStatus() == SmartReplySuggestionResult.STATUS_NOT_SUPPORTED_LANGUAGE) {
                        // The conversation's language isn't supported, so
                        // the result doesn't contain any suggestions.
                        resultCallback(nil, ERROR_LANGUAGE_NOT_SUPPORTED);
                    } else if (result.getStatus() == SmartReplySuggestionResult.STATUS_SUCCESS) {
                        // Task completed successfully
                        resultCallback({ "value": result.getSuggestions() }, nil)
                    }
                }
           })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Task failed with an exception
                // ...
            }
        });
    }
}
