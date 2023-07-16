package io.capawesome.capacitorjs.plugins.mlkit.facedetection;

import android.graphics.PointF;
import com.getcapacitor.JSObject;

public class FaceDetectionHelper {

    public static JSObject convertPoint(PointF point) {
        JSObject pointObject = new JSObject();

        // Gets x coordinate.
        pointObject.put("x", point.x);
        // Gets y coordinate.
        pointObject.put("y", point.y);

        return pointObject;
    }
}
