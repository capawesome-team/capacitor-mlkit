package io.capawesome.capacitorjs.plugins.mlkit.genaiimagedescription.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.mlkit.genaiimagedescription.interfaces.Result;

public class DescribeImageResult implements Result {

    @NonNull
    private final String description;

    public DescribeImageResult(@NonNull String description) {
        this.description = description;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("description", description);
        return result;
    }
}
