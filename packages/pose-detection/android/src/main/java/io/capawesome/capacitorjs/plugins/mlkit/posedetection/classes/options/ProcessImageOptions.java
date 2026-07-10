package io.capawesome.capacitorjs.plugins.mlkit.posedetection.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.mlkit.posedetection.PoseDetectionPlugin;

public class ProcessImageOptions {

    private static final String PERFORMANCE_MODE_BASE = "BASE";
    private static final String PERFORMANCE_MODE_ACCURATE = "ACCURATE";

    @NonNull
    private final String path;

    private final boolean accurate;

    public ProcessImageOptions(@NonNull PluginCall call) throws Exception {
        String path = call.getString("path", null);
        if (path == null) {
            throw new Exception(PoseDetectionPlugin.ERROR_PATH_MISSING);
        }
        this.path = path;
        String performanceMode = call.getString("performanceMode", PERFORMANCE_MODE_BASE);
        if (!PERFORMANCE_MODE_BASE.equals(performanceMode) && !PERFORMANCE_MODE_ACCURATE.equals(performanceMode)) {
            throw new Exception(PoseDetectionPlugin.ERROR_PERFORMANCE_MODE_INVALID);
        }
        this.accurate = PERFORMANCE_MODE_ACCURATE.equals(performanceMode);
    }

    @NonNull
    public String getPath() {
        return path;
    }

    public boolean isAccurateModeEnabled() {
        return accurate;
    }
}
