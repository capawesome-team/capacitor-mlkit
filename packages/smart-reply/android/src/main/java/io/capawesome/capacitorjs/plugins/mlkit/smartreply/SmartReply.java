package io.capawesome.capacitorjs.plugins.mlkit.smartreply;

import androidx.annotation.NonNull;
import com.google.mlkit.nl.smartreply.SmartReplyGenerator;
import com.google.mlkit.nl.smartreply.SmartReplySuggestion;
import com.google.mlkit.nl.smartreply.SmartReplySuggestionResult;
import io.capawesome.capacitorjs.plugins.mlkit.smartreply.classes.options.SuggestRepliesOptions;
import io.capawesome.capacitorjs.plugins.mlkit.smartreply.classes.results.SuggestRepliesResult;
import io.capawesome.capacitorjs.plugins.mlkit.smartreply.interfaces.NonEmptyResultCallback;
import java.util.ArrayList;
import java.util.List;

public class SmartReply {

    private static final String STATUS_SUCCESS = "SUCCESS";
    private static final String STATUS_NOT_SUPPORTED_LANGUAGE = "NOT_SUPPORTED_LANGUAGE";
    private static final String STATUS_NO_REPLY = "NO_REPLY";

    @NonNull
    private final SmartReplyGenerator smartReplyGenerator;

    public SmartReply() {
        this.smartReplyGenerator = com.google.mlkit.nl.smartreply.SmartReply.getClient();
    }

    public void close() {
        smartReplyGenerator.close();
    }

    public void suggestReplies(@NonNull SuggestRepliesOptions options, @NonNull NonEmptyResultCallback<SuggestRepliesResult> callback) {
        smartReplyGenerator
            .suggestReplies(options.getMessages())
            .addOnSuccessListener(result -> callback.success(createResult(result)))
            .addOnFailureListener(callback::error);
    }

    @NonNull
    private SuggestRepliesResult createResult(@NonNull SmartReplySuggestionResult result) {
        List<String> suggestions = new ArrayList<>();
        if (result.getStatus() == SmartReplySuggestionResult.STATUS_SUCCESS) {
            for (SmartReplySuggestion suggestion : result.getSuggestions()) {
                suggestions.add(suggestion.getText());
            }
        }
        return new SuggestRepliesResult(mapStatus(result.getStatus()), suggestions);
    }

    @NonNull
    private String mapStatus(int status) {
        switch (status) {
            case SmartReplySuggestionResult.STATUS_NOT_SUPPORTED_LANGUAGE:
                return STATUS_NOT_SUPPORTED_LANGUAGE;
            case SmartReplySuggestionResult.STATUS_NO_REPLY:
                return STATUS_NO_REPLY;
            default:
                return STATUS_SUCCESS;
        }
    }
}
