package io.capawesome.capacitorjs.plugins.mlkit.textrecognition.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.mlkit.textrecognition.TextRecognitionPlugin;

public class ProcessImageOptions {

    @NonNull
    private final String path;

    @NonNull
    private final String script;

    public ProcessImageOptions(@NonNull PluginCall call) throws Exception {
        String path = call.getString("path", null);
        if (path == null) {
            throw new Exception(TextRecognitionPlugin.ERROR_PATH_MISSING);
        }
        this.path = path;
        this.script = ProcessImageOptions.getScriptFromCall(call);
    }

    @NonNull
    public String getPath() {
        return path;
    }

    @NonNull
    public String getScript() {
        return script;
    }

    @NonNull
    private static String getScriptFromCall(@NonNull PluginCall call) throws Exception {
        String script = call.getString("script", "LATIN");
        switch (script) {
            case "LATIN":
            case "CHINESE":
            case "DEVANAGARI":
            case "JAPANESE":
            case "KOREAN":
                return script;
            default:
                throw new Exception(TextRecognitionPlugin.ERROR_SCRIPT_INVALID);
        }
    }
}
