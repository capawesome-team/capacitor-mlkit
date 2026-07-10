package io.capawesome.capacitorjs.plugins.mlkit.languageidentification;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.mlkit.languageidentification.classes.options.IdentifyLanguageOptions;
import io.capawesome.capacitorjs.plugins.mlkit.languageidentification.classes.options.IdentifyPossibleLanguagesOptions;
import io.capawesome.capacitorjs.plugins.mlkit.languageidentification.classes.results.IdentifyLanguageResult;
import io.capawesome.capacitorjs.plugins.mlkit.languageidentification.classes.results.IdentifyPossibleLanguagesResult;
import io.capawesome.capacitorjs.plugins.mlkit.languageidentification.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.mlkit.languageidentification.interfaces.Result;

@CapacitorPlugin(name = "LanguageIdentification")
public class LanguageIdentificationPlugin extends Plugin {

    public static final String TAG = "LanguageIdentification";

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error occurred.";

    private LanguageIdentification implementation;

    @Override
    public void load() {
        implementation = new LanguageIdentification();
    }

    @PluginMethod
    public void identifyLanguage(PluginCall call) {
        try {
            IdentifyLanguageOptions options = new IdentifyLanguageOptions(call);
            NonEmptyResultCallback<IdentifyLanguageResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull IdentifyLanguageResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.identifyLanguage(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void identifyPossibleLanguages(PluginCall call) {
        try {
            IdentifyPossibleLanguagesOptions options = new IdentifyPossibleLanguagesOptions(call);
            NonEmptyResultCallback<IdentifyPossibleLanguagesResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull IdentifyPossibleLanguagesResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.identifyPossibleLanguages(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    private void rejectCall(@NonNull PluginCall call, @NonNull Exception exception) {
        String message = exception.getMessage();
        if (message == null) {
            message = ERROR_UNKNOWN_ERROR;
        }
        Logger.error(TAG, message, exception);
        call.reject(message);
    }

    private void resolveCall(@NonNull PluginCall call, @Nullable Result result) {
        if (result == null) {
            call.resolve();
        } else {
            call.resolve(result.toJSObject());
        }
    }
}
