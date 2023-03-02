package io.capawesome.capacitorjs.plugins.mlkit.translation;

import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.google.mlkit.nl.translate.TranslateRemoteModel;
import java.util.Set;

@CapacitorPlugin(name = "LanguageIdentification")
public class LanguageIdentificationPlugin extends Plugin {

    private LanguageIdentification implementation = new LanguageIdentification();

    @PluginMethod
    public void deleteDownloadedModel(PluginCall call) {
        try {
            String language = call.getString("language");
            if (language == null) {
                call.reject(ERROR_LANGUAGE_MISSING);
                return;
            }

            implementation.deleteDownloadedModel(
                language,
                new DeleteDownloadedModelResultCallback() {
                    @Override
                    public void success() {
                        call.resolve();
                    }

                    @Override
                    public void error(Exception exception) {
                        String message = exception.getMessage();
                        Logger.error(message, exception);
                        call.reject(message);
                    }
                }
            );
        } catch (Exception exception) {
            String message = exception.getMessage();
            Logger.error(message, exception);
            call.reject(message);
        }
    }
}
