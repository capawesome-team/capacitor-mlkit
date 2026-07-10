package io.capawesome.capacitorjs.plugins.mlkit.digitalinkrecognition.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;

public class DeleteDownloadedModelOptions {

    private static final String ERROR_LANGUAGE_TAG_MISSING = "languageTag must be provided.";

    @NonNull
    private final String languageTag;

    public DeleteDownloadedModelOptions(@NonNull PluginCall call) throws Exception {
        this.languageTag = DeleteDownloadedModelOptions.getLanguageTagFromCall(call);
    }

    @NonNull
    public String getLanguageTag() {
        return languageTag;
    }

    @NonNull
    private static String getLanguageTagFromCall(@NonNull PluginCall call) throws Exception {
        String languageTag = call.getString("languageTag");
        if (languageTag == null) {
            throw new Exception(ERROR_LANGUAGE_TAG_MISSING);
        }
        return languageTag;
    }
}
