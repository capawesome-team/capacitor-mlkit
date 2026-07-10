package io.capawesome.capacitorjs.plugins.mlkit.entityextraction.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.mlkit.entityextraction.EntityExtractionHelper;

public class DownloadModelOptions {

    private static final String ERROR_LANGUAGE_MISSING = "language must be provided.";

    @NonNull
    private final String modelIdentifier;

    public DownloadModelOptions(@NonNull PluginCall call) throws Exception {
        this.modelIdentifier = DownloadModelOptions.getModelIdentifierFromCall(call);
    }

    @NonNull
    public String getModelIdentifier() {
        return modelIdentifier;
    }

    @NonNull
    private static String getModelIdentifierFromCall(@NonNull PluginCall call) throws Exception {
        String language = call.getString("language");
        if (language == null) {
            throw new Exception(ERROR_LANGUAGE_MISSING);
        }
        return EntityExtractionHelper.convertLanguageToModelIdentifier(language);
    }
}
