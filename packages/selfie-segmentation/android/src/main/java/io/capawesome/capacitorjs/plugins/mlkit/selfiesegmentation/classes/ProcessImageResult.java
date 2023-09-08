package io.capawesome.capacitorjs.plugins.mlkit.selfiesegmentation.classes;

import com.getcapacitor.JSObject;
import org.json.JSONException;

public class ProcessImageResult {

    // private SegmentationMask segmentationMask;
    private String imagePath;
    private int width;
    private int height;

    public ProcessImageResult(String imagePath, int width, int height) {
        // this.segmentationMask = segmentationMask;
        this.imagePath = imagePath;
        this.width = width;
        this.height = height;
    }

    public JSObject toJSObject() throws JSONException {
        // JSArray maskResult = this.createMaskResult();

        JSObject result = new JSObject();
        // result.put("mask", maskResult);
        // result.put("width", segmentationMask.getWidth());
        // result.put("height", segmentationMask.getHeight());
        result.put("path", imagePath);
        result.put("width", width);
        result.put("height", height);
        return result;
    }
    // private JSArray createMaskResult() throws JSONException {
    //     JSArray result = new JSArray();
    //
    //     ByteBuffer mask = segmentationMask.getBuffer();
    //     int maskWidth = segmentationMask.getWidth();
    //     int maskHeight = segmentationMask.getHeight();
    //
    //     for (int y = 0; y < maskHeight; y++) {
    //         for (int x = 0; x < maskWidth; x++) {
    //             result.put(mask.getFloat());
    //         }
    //     }
    //     return result;
    // }
}
