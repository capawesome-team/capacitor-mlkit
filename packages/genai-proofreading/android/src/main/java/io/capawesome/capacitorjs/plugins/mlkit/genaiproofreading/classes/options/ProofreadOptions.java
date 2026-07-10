package io.capawesome.capacitorjs.plugins.mlkit.genaiproofreading.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.mlkit.genaiproofreading.GenAiProofreadingPlugin;

public class ProofreadOptions extends FeatureOptions {

    @NonNull
    private final String text;

    public ProofreadOptions(@NonNull PluginCall call) throws Exception {
        super(call);
        this.text = ProofreadOptions.getTextFromCall(call);
    }

    @NonNull
    public String getText() {
        return text;
    }

    @NonNull
    private static String getTextFromCall(@NonNull PluginCall call) throws Exception {
        String text = call.getString("text");
        if (text == null) {
            throw new Exception(GenAiProofreadingPlugin.ERROR_TEXT_MISSING);
        }
        return text;
    }
}
