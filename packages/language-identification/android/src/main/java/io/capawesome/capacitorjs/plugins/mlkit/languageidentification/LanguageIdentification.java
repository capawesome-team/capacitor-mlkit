package io.capawesome.capacitorjs.plugins.mlkit.languageidentification;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.mlkit.nl.languageid.LanguageIdentificationOptions;
import com.google.mlkit.nl.languageid.LanguageIdentifier;
import io.capawesome.capacitorjs.plugins.mlkit.languageidentification.classes.options.IdentifyLanguageOptions;
import io.capawesome.capacitorjs.plugins.mlkit.languageidentification.classes.options.IdentifyPossibleLanguagesOptions;
import io.capawesome.capacitorjs.plugins.mlkit.languageidentification.classes.results.IdentifyLanguageResult;
import io.capawesome.capacitorjs.plugins.mlkit.languageidentification.classes.results.IdentifyPossibleLanguagesResult;
import io.capawesome.capacitorjs.plugins.mlkit.languageidentification.interfaces.NonEmptyResultCallback;

public class LanguageIdentification {

    public void identifyLanguage(
        @NonNull IdentifyLanguageOptions options,
        @NonNull NonEmptyResultCallback<IdentifyLanguageResult> callback
    ) {
        LanguageIdentifier client = createClient(options.getConfidenceThreshold());
        client
            .identifyLanguage(options.getText())
            .addOnSuccessListener(languageTag -> {
                client.close();
                callback.success(new IdentifyLanguageResult(languageTag));
            })
            .addOnFailureListener(exception -> {
                client.close();
                callback.error(exception);
            });
    }

    public void identifyPossibleLanguages(
        @NonNull IdentifyPossibleLanguagesOptions options,
        @NonNull NonEmptyResultCallback<IdentifyPossibleLanguagesResult> callback
    ) {
        LanguageIdentifier client = createClient(options.getConfidenceThreshold());
        client
            .identifyPossibleLanguages(options.getText())
            .addOnSuccessListener(identifiedLanguages -> {
                client.close();
                callback.success(new IdentifyPossibleLanguagesResult(identifiedLanguages));
            })
            .addOnFailureListener(exception -> {
                client.close();
                callback.error(exception);
            });
    }

    @NonNull
    private LanguageIdentifier createClient(@Nullable Float confidenceThreshold) {
        if (confidenceThreshold == null) {
            return com.google.mlkit.nl.languageid.LanguageIdentification.getClient();
        }
        LanguageIdentificationOptions options = new LanguageIdentificationOptions.Builder()
            .setConfidenceThreshold(confidenceThreshold)
            .build();
        return com.google.mlkit.nl.languageid.LanguageIdentification.getClient(options);
    }
}
