package io.capawesome.capacitorjs.plugins.mlkit.facedetection;

import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "FaceDetection")
public class FaceDetectionPlugin extends Plugin {

    private FaceDetection implementation = new FaceDetection();

    @PluginMethod
    public void processImage(PluginCall call) {
        try {
            implementation.processImage();
            call.resolve();
        } catch (Exception exception) {
            String message = exception.getMessage();
            Logger.error(message, exception);
            call.reject(message);
        }
    }
}
