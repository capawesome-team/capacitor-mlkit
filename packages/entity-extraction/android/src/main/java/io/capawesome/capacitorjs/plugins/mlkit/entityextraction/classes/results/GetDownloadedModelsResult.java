package io.capawesome.capacitorjs.plugins.mlkit.entityextraction.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.google.mlkit.nl.entityextraction.EntityExtractionRemoteModel;
import io.capawesome.capacitorjs.plugins.mlkit.entityextraction.EntityExtractionHelper;
import io.capawesome.capacitorjs.plugins.mlkit.entityextraction.interfaces.Result;
import java.util.Set;

public class GetDownloadedModelsResult implements Result {

    @NonNull
    private final Set<EntityExtractionRemoteModel> models;

    public GetDownloadedModelsResult(@NonNull Set<EntityExtractionRemoteModel> models) {
        this.models = models;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSArray languagesArray = new JSArray();
        for (EntityExtractionRemoteModel model : models) {
            languagesArray.put(EntityExtractionHelper.convertModelIdentifierToLanguage(model.getModelIdentifier()));
        }
        JSObject result = new JSObject();
        result.put("languages", languagesArray);
        return result;
    }
}
