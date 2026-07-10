package io.capawesome.capacitorjs.plugins.mlkit.imagelabeling.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.mlkit.imagelabeling.ImageLabelingPlugin;

public class ProcessImageOptions {

    @NonNull
    private final String path;

    private final float confidenceThreshold;

    public ProcessImageOptions(@NonNull PluginCall call) throws Exception {
        String path = call.getString("path", null);
        if (path == null) {
            throw new Exception(ImageLabelingPlugin.ERROR_PATH_MISSING);
        }
        this.path = path;
        this.confidenceThreshold = call.getFloat("confidenceThreshold", 0.5f);
    }

    @NonNull
    public String getPath() {
        return path;
    }

    public float getConfidenceThreshold() {
        return confidenceThreshold;
    }
}
