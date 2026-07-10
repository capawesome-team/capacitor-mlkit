package io.capawesome.capacitorjs.plugins.mlkit.genairewriting.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import com.google.mlkit.genai.rewriting.RewriterOptions;
import io.capawesome.capacitorjs.plugins.mlkit.genairewriting.GenAiRewritingPlugin;

public class FeatureOptions {

    private final int language;
    private final int outputType;

    public FeatureOptions(@NonNull PluginCall call) throws Exception {
        this.language = FeatureOptions.getLanguageFromCall(call);
        this.outputType = FeatureOptions.getOutputTypeFromCall(call);
    }

    public int getLanguage() {
        return language;
    }

    public int getOutputType() {
        return outputType;
    }

    public boolean matches(@Nullable FeatureOptions other) {
        return other != null && language == other.language && outputType == other.outputType;
    }

    private static int getLanguageFromCall(@NonNull PluginCall call) throws Exception {
        String language = call.getString("language", "ENGLISH");
        switch (language) {
            case "ENGLISH":
                return RewriterOptions.Language.ENGLISH;
            case "FRENCH":
                return RewriterOptions.Language.FRENCH;
            case "GERMAN":
                return RewriterOptions.Language.GERMAN;
            case "ITALIAN":
                return RewriterOptions.Language.ITALIAN;
            case "JAPANESE":
                return RewriterOptions.Language.JAPANESE;
            case "KOREAN":
                return RewriterOptions.Language.KOREAN;
            case "SPANISH":
                return RewriterOptions.Language.SPANISH;
            default:
                throw new Exception(GenAiRewritingPlugin.ERROR_LANGUAGE_INVALID);
        }
    }

    private static int getOutputTypeFromCall(@NonNull PluginCall call) throws Exception {
        String outputType = call.getString("outputType", "ELABORATE");
        switch (outputType) {
            case "ELABORATE":
                return RewriterOptions.OutputType.ELABORATE;
            case "EMOJIFY":
                return RewriterOptions.OutputType.EMOJIFY;
            case "SHORTEN":
                return RewriterOptions.OutputType.SHORTEN;
            case "FRIENDLY":
                return RewriterOptions.OutputType.FRIENDLY;
            case "PROFESSIONAL":
                return RewriterOptions.OutputType.PROFESSIONAL;
            case "REPHRASE":
                return RewriterOptions.OutputType.REPHRASE;
            default:
                throw new Exception(GenAiRewritingPlugin.ERROR_OUTPUT_TYPE_INVALID);
        }
    }
}
