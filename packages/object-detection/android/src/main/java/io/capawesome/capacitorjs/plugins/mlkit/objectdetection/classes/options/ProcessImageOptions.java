package io.capawesome.capacitorjs.plugins.mlkit.objectdetection.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.mlkit.objectdetection.ObjectDetectionPlugin;

public class ProcessImageOptions {

    @NonNull
    private final String path;

    private final boolean shouldEnableClassification;

    private final boolean shouldEnableMultipleObjects;

    public ProcessImageOptions(@NonNull PluginCall call) throws Exception {
        String path = call.getString("path", null);
        if (path == null) {
            throw new Exception(ObjectDetectionPlugin.ERROR_PATH_MISSING);
        }
        this.path = path;
        this.shouldEnableClassification = call.getBoolean("shouldEnableClassification", false);
        this.shouldEnableMultipleObjects = call.getBoolean("shouldEnableMultipleObjects", false);
    }

    @NonNull
    public String getPath() {
        return path;
    }

    public boolean isClassificationEnabled() {
        return shouldEnableClassification;
    }

    public boolean isMultipleObjectsEnabled() {
        return shouldEnableMultipleObjects;
    }
}
