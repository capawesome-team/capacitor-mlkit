package io.capawesome.capacitorjs.plugins.mlkit.languageidentification.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.mlkit.languageidentification.interfaces.Result;

public class IdentifyLanguageResult implements Result {

    @NonNull
    private final String language;

    public IdentifyLanguageResult(@NonNull String language) {
        this.language = language;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("language", language);
        return result;
    }
}
