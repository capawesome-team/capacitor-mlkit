package io.capawesome.capacitorjs.plugins.mlkit.genairewriting.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.mlkit.genairewriting.GenAiRewritingPlugin;

public class RewriteOptions extends FeatureOptions {

    @NonNull
    private final String text;

    public RewriteOptions(@NonNull PluginCall call) throws Exception {
        super(call);
        this.text = RewriteOptions.getTextFromCall(call);
    }

    @NonNull
    public String getText() {
        return text;
    }

    @NonNull
    private static String getTextFromCall(@NonNull PluginCall call) throws Exception {
        String text = call.getString("text");
        if (text == null) {
            throw new Exception(GenAiRewritingPlugin.ERROR_TEXT_MISSING);
        }
        return text;
    }
}
