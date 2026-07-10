package io.capawesome.capacitorjs.plugins.mlkit.languageidentification.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;

public class IdentifyLanguageOptions {

    private static final String ERROR_TEXT_MISSING = "text must be provided.";

    @NonNull
    private final String text;

    @Nullable
    private final Float confidenceThreshold;

    public IdentifyLanguageOptions(@NonNull PluginCall call) throws Exception {
        this.text = IdentifyLanguageOptions.getTextFromCall(call);
        this.confidenceThreshold = IdentifyLanguageOptions.getConfidenceThresholdFromCall(call);
    }

    @Nullable
    public Float getConfidenceThreshold() {
        return confidenceThreshold;
    }

    @NonNull
    public String getText() {
        return text;
    }

    @Nullable
    private static Float getConfidenceThresholdFromCall(@NonNull PluginCall call) {
        Double confidenceThreshold = call.getDouble("confidenceThreshold");
        if (confidenceThreshold == null) {
            return null;
        }
        return confidenceThreshold.floatValue();
    }

    @NonNull
    private static String getTextFromCall(@NonNull PluginCall call) throws Exception {
        String text = call.getString("text");
        if (text == null) {
            throw new Exception(ERROR_TEXT_MISSING);
        }
        return text;
    }
}
