package io.capawesome.capacitorjs.plugins.mlkit.selfiesegmentation.classes;

import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.google.mlkit.vision.segmentation.SegmentationMask;
import java.nio.ByteBuffer;
import org.json.JSONException;

public class ProcessImageResult {

    private SegmentationMask segmentationMask;

    public ProcessImageResult(SegmentationMask segmentationMask) {
        this.segmentationMask = segmentationMask;
    }

    public JSObject toJSObject() throws JSONException {
        JSArray maskResult = this.createMaskResult();

        JSObject result = new JSObject();
        result.put("mask", maskResult);
        result.put("width", segmentationMask.getWidth());
        result.put("height", segmentationMask.getHeight());
        return result;
    }

    private JSArray createMaskResult() throws JSONException {
        JSArray result = new JSArray();

        ByteBuffer mask = segmentationMask.getBuffer();
        int maskWidth = segmentationMask.getWidth();
        int maskHeight = segmentationMask.getHeight();

        for (int y = 0; y < maskHeight; y++) {
            for (int x = 0; x < maskWidth; x++) {
                result.put(mask.getFloat());
            }
        }
        return result;
    }
}
