package io.capawesome.capacitorjs.plugins.mlkit.genaiproofreading.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import com.google.mlkit.genai.proofreading.ProofreaderOptions;
import io.capawesome.capacitorjs.plugins.mlkit.genaiproofreading.GenAiProofreadingPlugin;

public class FeatureOptions {

    private final int inputType;
    private final int language;

    public FeatureOptions(@NonNull PluginCall call) throws Exception {
        this.inputType = FeatureOptions.getInputTypeFromCall(call);
        this.language = FeatureOptions.getLanguageFromCall(call);
    }

    public int getInputType() {
        return inputType;
    }

    public int getLanguage() {
        return language;
    }

    public boolean matches(@Nullable FeatureOptions other) {
        return other != null && inputType == other.inputType && language == other.language;
    }

    private static int getInputTypeFromCall(@NonNull PluginCall call) throws Exception {
        String inputType = call.getString("inputType", "KEYBOARD");
        switch (inputType) {
            case "KEYBOARD":
                return ProofreaderOptions.InputType.KEYBOARD;
            case "VOICE":
                return ProofreaderOptions.InputType.VOICE;
            default:
                throw new Exception(GenAiProofreadingPlugin.ERROR_INPUT_TYPE_INVALID);
        }
    }

    private static int getLanguageFromCall(@NonNull PluginCall call) throws Exception {
        String language = call.getString("language", "ENGLISH");
        switch (language) {
            case "ENGLISH":
                return ProofreaderOptions.Language.ENGLISH;
            case "FRENCH":
                return ProofreaderOptions.Language.FRENCH;
            case "GERMAN":
                return ProofreaderOptions.Language.GERMAN;
            case "ITALIAN":
                return ProofreaderOptions.Language.ITALIAN;
            case "JAPANESE":
                return ProofreaderOptions.Language.JAPANESE;
            case "KOREAN":
                return ProofreaderOptions.Language.KOREAN;
            case "SPANISH":
                return ProofreaderOptions.Language.SPANISH;
            default:
                throw new Exception(GenAiProofreadingPlugin.ERROR_LANGUAGE_INVALID);
        }
    }
}
