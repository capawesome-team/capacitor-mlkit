package io.capawesome.capacitorjs.plugins.mlkit.genaispeechrecognition.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.mlkit.genaispeechrecognition.interfaces.Result;

public class PartialResultEvent implements Result {

    @NonNull
    private final String text;

    public PartialResultEvent(@NonNull String text) {
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
