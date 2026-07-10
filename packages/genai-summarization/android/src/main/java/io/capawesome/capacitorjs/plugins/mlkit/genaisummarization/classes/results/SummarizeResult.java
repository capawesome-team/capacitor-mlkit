package io.capawesome.capacitorjs.plugins.mlkit.genaisummarization.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.mlkit.genaisummarization.interfaces.Result;

public class SummarizeResult implements Result {

    @NonNull
    private final String summary;

    public SummarizeResult(@NonNull String summary) {
        this.summary = summary;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("summary", summary);
        return result;
    }
}
