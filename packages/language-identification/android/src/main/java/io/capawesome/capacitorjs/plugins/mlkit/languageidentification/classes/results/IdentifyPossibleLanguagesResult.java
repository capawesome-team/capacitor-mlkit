package io.capawesome.capacitorjs.plugins.mlkit.languageidentification.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.google.mlkit.nl.languageid.IdentifiedLanguage;
import io.capawesome.capacitorjs.plugins.mlkit.languageidentification.interfaces.Result;
import java.util.List;

public class IdentifyPossibleLanguagesResult implements Result {

    @NonNull
    private final List<IdentifiedLanguage> identifiedLanguages;

    public IdentifyPossibleLanguagesResult(@NonNull List<IdentifiedLanguage> identifiedLanguages) {
        this.identifiedLanguages = identifiedLanguages;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSArray identifiedLanguagesArray = new JSArray();
        for (IdentifiedLanguage identifiedLanguage : identifiedLanguages) {
            JSObject identifiedLanguageObject = new JSObject();
            identifiedLanguageObject.put("language", identifiedLanguage.getLanguageTag());
            identifiedLanguageObject.put("confidence", identifiedLanguage.getConfidence());
            identifiedLanguagesArray.put(identifiedLanguageObject);
        }
        JSObject result = new JSObject();
        result.put("identifiedLanguages", identifiedLanguagesArray);
        return result;
    }
}
