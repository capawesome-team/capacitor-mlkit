package io.capawesome.capacitorjs.plugins.mlkit.digitalinkrecognition.classes;

public class CustomExceptions {

    public static final CustomException MODEL_NOT_DOWNLOADED = new CustomException("MODEL_NOT_DOWNLOADED", "model must be downloaded.");
    public static final CustomException UNSUPPORTED_LANGUAGE_TAG = new CustomException(
        "UNSUPPORTED_LANGUAGE_TAG",
        "languageTag is not supported."
    );
}
