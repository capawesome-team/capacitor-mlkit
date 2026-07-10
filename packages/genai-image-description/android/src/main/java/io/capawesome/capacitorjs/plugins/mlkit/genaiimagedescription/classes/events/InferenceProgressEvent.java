package io.capawesome.capacitorjs.plugins.mlkit.genaiimagedescription.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.mlkit.genaiimagedescription.interfaces.Result;

public class InferenceProgressEvent implements Result {

    @NonNull
    private final String text;

    public InferenceProgressEvent(@NonNull String text) {
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
