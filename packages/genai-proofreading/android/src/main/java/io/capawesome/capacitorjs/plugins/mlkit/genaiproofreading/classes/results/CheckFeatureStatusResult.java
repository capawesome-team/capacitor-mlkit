package io.capawesome.capacitorjs.plugins.mlkit.genaiproofreading.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import com.google.mlkit.genai.common.FeatureStatus;
import io.capawesome.capacitorjs.plugins.mlkit.genaiproofreading.interfaces.Result;

public class CheckFeatureStatusResult implements Result {

    private final int featureStatus;

    public CheckFeatureStatusResult(int featureStatus) {
        this.featureStatus = featureStatus;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("featureStatus", convertFeatureStatusToString(featureStatus));
        return result;
    }

    @NonNull
    private static String convertFeatureStatusToString(int featureStatus) {
        switch (featureStatus) {
            case FeatureStatus.AVAILABLE:
                return "AVAILABLE";
            case FeatureStatus.DOWNLOADABLE:
                return "DOWNLOADABLE";
            case FeatureStatus.DOWNLOADING:
                return "DOWNLOADING";
            default:
                return "UNAVAILABLE";
        }
    }
}
