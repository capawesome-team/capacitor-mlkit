package io.capawesome.capacitorjs.plugins.mlkit.smartreply.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.mlkit.smartreply.interfaces.Result;
import java.util.List;

public class SuggestRepliesResult implements Result {

    @NonNull
    private final String status;

    @NonNull
    private final List<String> suggestions;

    public SuggestRepliesResult(@NonNull String status, @NonNull List<String> suggestions) {
        this.status = status;
        this.suggestions = suggestions;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSArray suggestionsArray = new JSArray();
        for (String suggestion : suggestions) {
            suggestionsArray.put(suggestion);
        }
        JSObject result = new JSObject();
        result.put("status", status);
        result.put("suggestions", suggestionsArray);
        return result;
    }
}
