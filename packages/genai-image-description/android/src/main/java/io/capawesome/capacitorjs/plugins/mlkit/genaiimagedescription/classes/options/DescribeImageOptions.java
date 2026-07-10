package io.capawesome.capacitorjs.plugins.mlkit.genaiimagedescription.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.mlkit.genaiimagedescription.GenAiImageDescriptionPlugin;

public class DescribeImageOptions {

    @NonNull
    private final String path;

    public DescribeImageOptions(@NonNull PluginCall call) throws Exception {
        this.path = DescribeImageOptions.getPathFromCall(call);
    }

    @NonNull
    public String getPath() {
        return path;
    }

    @NonNull
    private static String getPathFromCall(@NonNull PluginCall call) throws Exception {
        String path = call.getString("path");
        if (path == null) {
            throw new Exception(GenAiImageDescriptionPlugin.ERROR_PATH_MISSING);
        }
        return path;
    }
}
