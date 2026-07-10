package io.capawesome.capacitorjs.plugins.mlkit.genaispeechrecognition.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.mlkit.genaispeechrecognition.interfaces.Result;

public class ErrorEvent implements Result {

    @NonNull
    private final String message;

    public ErrorEvent(@NonNull String message) {
        this.message = message;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("message", message);
        return result;
    }
}
