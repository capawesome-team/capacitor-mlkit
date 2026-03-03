package io.capawesome.capacitorjs.plugins.mlkit.barcodescanning.classes.results;

import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.mlkit.barcodescanning.interfaces.Result;

public class GetMaxZoomRatioResult implements Result {

    private float zoomRatio;

    public GetMaxZoomRatioResult(float zoomRatio) {
        this.zoomRatio = zoomRatio;
    }

    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("zoomRatio", zoomRatio);
        return result;
    }
}
