package io.capawesome.capacitorjs.plugins.mlkit.genaisummarization.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import com.google.mlkit.genai.summarization.SummarizerOptions;
import io.capawesome.capacitorjs.plugins.mlkit.genaisummarization.GenAiSummarizationPlugin;

public class FeatureOptions {

    private final int inputType;
    private final int language;
    private final int outputType;

    public FeatureOptions(@NonNull PluginCall call) throws Exception {
        this.inputType = FeatureOptions.getInputTypeFromCall(call);
        this.language = FeatureOptions.getLanguageFromCall(call);
        this.outputType = FeatureOptions.getOutputTypeFromCall(call);
    }

    public int getInputType() {
        return inputType;
    }

    public int getLanguage() {
        return language;
    }

    public int getOutputType() {
        return outputType;
    }

    public boolean matches(@Nullable FeatureOptions other) {
        return other != null && inputType == other.inputType && language == other.language && outputType == other.outputType;
    }

    private static int getInputTypeFromCall(@NonNull PluginCall call) throws Exception {
        String inputType = call.getString("inputType", "ARTICLE");
        switch (inputType) {
            case "ARTICLE":
                return SummarizerOptions.InputType.ARTICLE;
            case "CONVERSATION":
                return SummarizerOptions.InputType.CONVERSATION;
            default:
                throw new Exception(GenAiSummarizationPlugin.ERROR_INPUT_TYPE_INVALID);
        }
    }

    private static int getLanguageFromCall(@NonNull PluginCall call) throws Exception {
        String language = call.getString("language", "ENGLISH");
        switch (language) {
            case "ENGLISH":
                return SummarizerOptions.Language.ENGLISH;
            case "JAPANESE":
                return SummarizerOptions.Language.JAPANESE;
            case "KOREAN":
                return SummarizerOptions.Language.KOREAN;
            default:
                throw new Exception(GenAiSummarizationPlugin.ERROR_LANGUAGE_INVALID);
        }
    }

    private static int getOutputTypeFromCall(@NonNull PluginCall call) throws Exception {
        String outputType = call.getString("outputType", "ONE_BULLET");
        switch (outputType) {
            case "ONE_BULLET":
                return SummarizerOptions.OutputType.ONE_BULLET;
            case "TWO_BULLETS":
                return SummarizerOptions.OutputType.TWO_BULLETS;
            case "THREE_BULLETS":
                return SummarizerOptions.OutputType.THREE_BULLETS;
            default:
                throw new Exception(GenAiSummarizationPlugin.ERROR_OUTPUT_TYPE_INVALID);
        }
    }
}
