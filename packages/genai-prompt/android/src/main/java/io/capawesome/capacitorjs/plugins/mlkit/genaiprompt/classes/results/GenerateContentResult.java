package io.capawesome.capacitorjs.plugins.mlkit.genaiprompt.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.mlkit.genaiprompt.interfaces.Result;

public class GenerateContentResult implements Result {

    @NonNull
    private final String text;

    public GenerateContentResult(@NonNull String text) {
        this.text = text;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("text", text);
        return result;
    }
}
