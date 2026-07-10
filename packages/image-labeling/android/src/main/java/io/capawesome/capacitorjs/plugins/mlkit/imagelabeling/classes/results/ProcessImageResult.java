package io.capawesome.capacitorjs.plugins.mlkit.imagelabeling.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.google.mlkit.vision.label.ImageLabel;
import io.capawesome.capacitorjs.plugins.mlkit.imagelabeling.interfaces.Result;
import java.util.List;

public class ProcessImageResult implements Result {

    @NonNull
    private final List<ImageLabel> labels;

    public ProcessImageResult(@NonNull List<ImageLabel> labels) {
        this.labels = labels;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSArray labelsResult = this.createLabelsResult();

        JSObject result = new JSObject();
        result.put("labels", labelsResult);
        return result;
    }

    private JSArray createLabelsResult() {
        JSArray result = new JSArray();
        for (ImageLabel label : labels) {
            JSObject labelResult = this.createLabelResult(label);
            result.put(labelResult);
        }
        return result;
    }

    private JSObject createLabelResult(@NonNull ImageLabel label) {
        JSObject result = new JSObject();
        result.put("index", label.getIndex());
        result.put("text", label.getText());
        result.put("confidence", label.getConfidence());
        return result;
    }
}
