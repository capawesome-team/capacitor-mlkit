package io.capawesome.capacitorjs.plugins.mlkit.objectdetection.classes.results;

import android.graphics.Rect;
import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.google.mlkit.vision.objects.DetectedObject;
import io.capawesome.capacitorjs.plugins.mlkit.objectdetection.interfaces.Result;
import java.util.List;

public class ProcessImageResult implements Result {

    @NonNull
    private final List<DetectedObject> detectedObjects;

    public ProcessImageResult(@NonNull List<DetectedObject> detectedObjects) {
        this.detectedObjects = detectedObjects;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSArray detectedObjectsResult = this.createDetectedObjectsResult();

        JSObject result = new JSObject();
        result.put("detectedObjects", detectedObjectsResult);
        return result;
    }

    private JSArray createDetectedObjectsResult() {
        JSArray result = new JSArray();
        for (DetectedObject detectedObject : detectedObjects) {
            JSObject detectedObjectResult = this.createDetectedObjectResult(detectedObject);
            result.put(detectedObjectResult);
        }
        return result;
    }

    private JSObject createDetectedObjectResult(@NonNull DetectedObject detectedObject) {
        JSObject result = new JSObject();
        result.put("boundingBox", this.createBoundingBoxResult(detectedObject.getBoundingBox()));
        Integer trackingId = detectedObject.getTrackingId();
        if (trackingId != null) {
            result.put("trackingId", trackingId);
        }
        result.put("labels", this.createLabelsResult(detectedObject.getLabels()));
        return result;
    }

    private JSObject createBoundingBoxResult(@NonNull Rect boundingBox) {
        JSObject result = new JSObject();
        result.put("left", boundingBox.left);
        result.put("top", boundingBox.top);
        result.put("right", boundingBox.right);
        result.put("bottom", boundingBox.bottom);
        return result;
    }

    private JSArray createLabelsResult(@NonNull List<DetectedObject.Label> labels) {
        JSArray result = new JSArray();
        for (DetectedObject.Label label : labels) {
            JSObject labelResult = new JSObject();
            labelResult.put("index", label.getIndex());
            labelResult.put("text", label.getText());
            labelResult.put("confidence", label.getConfidence());
            result.put(labelResult);
        }
        return result;
    }
}
