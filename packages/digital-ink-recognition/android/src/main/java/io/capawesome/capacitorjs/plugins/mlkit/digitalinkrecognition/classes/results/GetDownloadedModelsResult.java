package io.capawesome.capacitorjs.plugins.mlkit.digitalinkrecognition.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.google.mlkit.vision.digitalink.recognition.DigitalInkRecognitionModel;
import io.capawesome.capacitorjs.plugins.mlkit.digitalinkrecognition.interfaces.Result;
import java.util.Set;

public class GetDownloadedModelsResult implements Result {

    @NonNull
    private final Set<DigitalInkRecognitionModel> models;

    public GetDownloadedModelsResult(@NonNull Set<DigitalInkRecognitionModel> models) {
        this.models = models;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSArray languageTagsResult = new JSArray();
        for (DigitalInkRecognitionModel model : models) {
            languageTagsResult.put(model.getModelIdentifier().getLanguageTag());
        }
        JSObject result = new JSObject();
        result.put("languageTags", languageTagsResult);
        return result;
    }
}
