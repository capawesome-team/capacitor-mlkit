package io.capawesome.capacitorjs.plugins.mlkit.genaispeechrecognition.classes;

public class CustomExceptions {

    public static final CustomException MICROPHONE_PERMISSION_DENIED = new CustomException(
        "MICROPHONE_PERMISSION_DENIED",
        "The microphone permission was denied."
    );
    public static final CustomException RECOGNITION_ALREADY_RUNNING = new CustomException(
        "RECOGNITION_ALREADY_RUNNING",
        "A recognition session is already running."
    );
}
