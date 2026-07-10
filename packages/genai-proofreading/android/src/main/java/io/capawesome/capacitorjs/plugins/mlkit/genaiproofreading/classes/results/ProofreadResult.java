package io.capawesome.capacitorjs.plugins.mlkit.genaiproofreading.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.mlkit.genaiproofreading.interfaces.Result;
import java.util.List;

public class ProofreadResult implements Result {

    @NonNull
    private final List<String> results;

    public ProofreadResult(@NonNull List<String> results) {
        this.results = results;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSArray resultsArray = new JSArray();
        for (String result : results) {
            resultsArray.put(result);
        }
        JSObject result = new JSObject();
        result.put("results", resultsArray);
        return result;
    }
}
