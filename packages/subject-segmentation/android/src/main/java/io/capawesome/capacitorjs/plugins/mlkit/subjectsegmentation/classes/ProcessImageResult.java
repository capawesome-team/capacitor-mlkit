package io.capawesome.capacitorjs.plugins.mlkit.subjectsegmentation.classes;

import com.getcapacitor.JSObject;

public class ProcessImageResult {

    private final String imagePath;

    private final int width;
    private final int height;

    public ProcessImageResult(String imagePath, int width, int height) {
        this.imagePath = imagePath;

        this.width = width;
        this.height = height;
    }

    public JSObject toJSObject() {
        JSObject result = new JSObject();

        result.put("path", imagePath);

        result.put("width", width);
        result.put("height", height);

        return result;
    }
}
