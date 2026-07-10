package io.capawesome.capacitorjs.plugins.mlkit.genaispeechrecognition.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import com.google.mlkit.genai.speechrecognition.SpeechRecognizerOptions;
import io.capawesome.capacitorjs.plugins.mlkit.genaispeechrecognition.GenAiSpeechRecognitionPlugin;
import java.util.Locale;

public class FeatureOptions {

    @NonNull
    private final Locale locale;

    private final int mode;

    public FeatureOptions(@NonNull PluginCall call) throws Exception {
        this.locale = FeatureOptions.getLocaleFromCall(call);
        this.mode = FeatureOptions.getModeFromCall(call);
    }

    @NonNull
    public Locale getLocale() {
        return locale;
    }

    public int getMode() {
        return mode;
    }

    public boolean matches(@Nullable FeatureOptions other) {
        return other != null && locale.equals(other.locale) && mode == other.mode;
    }

    @NonNull
    private static Locale getLocaleFromCall(@NonNull PluginCall call) throws Exception {
        String locale = call.getString("locale");
        if (locale == null) {
            return Locale.getDefault();
        }
        Locale parsedLocale = Locale.forLanguageTag(locale);
        if (parsedLocale.getLanguage().isEmpty()) {
            throw new Exception(GenAiSpeechRecognitionPlugin.ERROR_LOCALE_INVALID);
        }
        return parsedLocale;
    }

    private static int getModeFromCall(@NonNull PluginCall call) throws Exception {
        String mode = call.getString("mode", "BASIC");
        switch (mode) {
            case "BASIC":
                return SpeechRecognizerOptions.Mode.MODE_BASIC;
            case "ADVANCED":
                return SpeechRecognizerOptions.Mode.MODE_ADVANCED;
            default:
                throw new Exception(GenAiSpeechRecognitionPlugin.ERROR_MODE_INVALID);
        }
    }
}
