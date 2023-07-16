package io.capawesome.capacitorjs.plugins.mlkit.digitalinkrecognition;

import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "DigitalInkRecognition")
public class DigitalInkRecognitionPlugin extends Plugin {

    private DigitalInkRecognition implementation = new DigitalInkRecognition();

    @PluginMethod
    public void echo(PluginCall call) {
        try {
            implementation.echo();
            call.resolve();
        } catch (Exception exception) {
            String message = exception.getMessage();
            Logger.error(message, exception);
            call.reject(message);
        }
    }
}
