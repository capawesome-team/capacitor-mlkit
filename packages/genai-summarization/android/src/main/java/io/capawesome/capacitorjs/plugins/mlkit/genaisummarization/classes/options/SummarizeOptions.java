package io.capawesome.capacitorjs.plugins.mlkit.genaisummarization.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.mlkit.genaisummarization.GenAiSummarizationPlugin;

public class SummarizeOptions extends FeatureOptions {

    @NonNull
    private final String text;

    public SummarizeOptions(@NonNull PluginCall call) throws Exception {
        super(call);
        this.text = SummarizeOptions.getTextFromCall(call);
    }

    @NonNull
    public String getText() {
        return text;
    }

    @NonNull
    private static String getTextFromCall(@NonNull PluginCall call) throws Exception {
        String text = call.getString("text");
        if (text == null) {
            throw new Exception(GenAiSummarizationPlugin.ERROR_TEXT_MISSING);
        }
        return text;
    }
}
