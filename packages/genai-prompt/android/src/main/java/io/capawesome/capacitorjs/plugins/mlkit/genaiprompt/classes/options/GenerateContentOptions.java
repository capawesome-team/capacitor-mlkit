package io.capawesome.capacitorjs.plugins.mlkit.genaiprompt.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.mlkit.genaiprompt.GenAiPromptPlugin;

public class GenerateContentOptions {

    @Nullable
    private final String imagePath;

    @Nullable
    private final Integer maxOutputTokens;

    @NonNull
    private final String prompt;

    @Nullable
    private final Integer seed;

    @Nullable
    private final Float temperature;

    @Nullable
    private final Integer topK;

    public GenerateContentOptions(@NonNull PluginCall call) throws Exception {
        this.imagePath = call.getString("imagePath");
        this.maxOutputTokens = call.getInt("maxOutputTokens");
        this.prompt = GenerateContentOptions.getPromptFromCall(call);
        this.seed = call.getInt("seed");
        this.temperature = call.getFloat("temperature");
        this.topK = call.getInt("topK");
    }

    @Nullable
    public String getImagePath() {
        return imagePath;
    }

    @Nullable
    public Integer getMaxOutputTokens() {
        return maxOutputTokens;
    }

    @NonNull
    public String getPrompt() {
        return prompt;
    }

    @Nullable
    public Integer getSeed() {
        return seed;
    }

    @Nullable
    public Float getTemperature() {
        return temperature;
    }

    @Nullable
    public Integer getTopK() {
        return topK;
    }

    @NonNull
    private static String getPromptFromCall(@NonNull PluginCall call) throws Exception {
        String prompt = call.getString("prompt");
        if (prompt == null) {
            throw new Exception(GenAiPromptPlugin.ERROR_PROMPT_MISSING);
        }
        return prompt;
    }
}
