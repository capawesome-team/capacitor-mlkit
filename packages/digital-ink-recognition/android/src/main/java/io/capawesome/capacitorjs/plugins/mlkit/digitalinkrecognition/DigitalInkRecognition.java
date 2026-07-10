package io.capawesome.capacitorjs.plugins.mlkit.digitalinkrecognition;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.common.model.RemoteModelManager;
import com.google.mlkit.vision.digitalink.common.RecognitionResult;
import com.google.mlkit.vision.digitalink.recognition.DigitalInkRecognitionModel;
import com.google.mlkit.vision.digitalink.recognition.DigitalInkRecognitionModelIdentifier;
import com.google.mlkit.vision.digitalink.recognition.DigitalInkRecognizer;
import com.google.mlkit.vision.digitalink.recognition.DigitalInkRecognizerOptions;
import com.google.mlkit.vision.digitalink.recognition.RecognitionContext;
import com.google.mlkit.vision.digitalink.recognition.WritingArea;
import io.capawesome.capacitorjs.plugins.mlkit.digitalinkrecognition.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.mlkit.digitalinkrecognition.classes.options.DeleteDownloadedModelOptions;
import io.capawesome.capacitorjs.plugins.mlkit.digitalinkrecognition.classes.options.DownloadModelOptions;
import io.capawesome.capacitorjs.plugins.mlkit.digitalinkrecognition.classes.options.RecognizeOptions;
import io.capawesome.capacitorjs.plugins.mlkit.digitalinkrecognition.classes.results.GetDownloadedModelsResult;
import io.capawesome.capacitorjs.plugins.mlkit.digitalinkrecognition.classes.results.RecognizeResult;
import io.capawesome.capacitorjs.plugins.mlkit.digitalinkrecognition.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.mlkit.digitalinkrecognition.interfaces.NonEmptyResultCallback;

public class DigitalInkRecognition {

    @NonNull
    private final RemoteModelManager modelManager;

    public DigitalInkRecognition() {
        this.modelManager = RemoteModelManager.getInstance();
    }

    public void deleteDownloadedModel(@NonNull DeleteDownloadedModelOptions options, @NonNull EmptyCallback callback) throws Exception {
        DigitalInkRecognitionModel model = createModel(options.getLanguageTag());
        modelManager.deleteDownloadedModel(model).addOnSuccessListener(result -> callback.success()).addOnFailureListener(callback::error);
    }

    public void downloadModel(@NonNull DownloadModelOptions options, @NonNull EmptyCallback callback) throws Exception {
        DigitalInkRecognitionModel model = createModel(options.getLanguageTag());
        DownloadConditions conditions = new DownloadConditions.Builder().build();
        modelManager.download(model, conditions).addOnSuccessListener(result -> callback.success()).addOnFailureListener(callback::error);
    }

    public void getDownloadedModels(@NonNull NonEmptyResultCallback<GetDownloadedModelsResult> callback) {
        modelManager
            .getDownloadedModels(DigitalInkRecognitionModel.class)
            .addOnSuccessListener(models -> callback.success(new GetDownloadedModelsResult(models)))
            .addOnFailureListener(callback::error);
    }

    public void recognize(@NonNull RecognizeOptions options, @NonNull NonEmptyResultCallback<RecognizeResult> callback) throws Exception {
        DigitalInkRecognitionModel model = createModel(options.getLanguageTag());
        modelManager
            .isModelDownloaded(model)
            .addOnSuccessListener(isModelDownloaded -> {
                if (!isModelDownloaded) {
                    callback.error(CustomExceptions.MODEL_NOT_DOWNLOADED);
                    return;
                }
                DigitalInkRecognizerOptions recognizerOptions = DigitalInkRecognizerOptions.builder(model)
                    .setMaxResultCount(options.getMaxResultCount())
                    .build();
                DigitalInkRecognizer recognizer = com.google.mlkit.vision.digitalink.recognition.DigitalInkRecognition.getClient(
                    recognizerOptions
                );
                RecognitionContext recognitionContext = createRecognitionContext(options.getPreContext(), options.getWritingArea());
                Task<RecognitionResult> task = recognitionContext == null
                    ? recognizer.recognize(options.getInk())
                    : recognizer.recognize(options.getInk(), recognitionContext);
                task
                    .addOnSuccessListener(recognitionResult -> {
                        recognizer.close();
                        callback.success(new RecognizeResult(recognitionResult));
                    })
                    .addOnFailureListener(exception -> {
                        recognizer.close();
                        callback.error(exception);
                    });
            })
            .addOnFailureListener(callback::error);
    }

    @NonNull
    private DigitalInkRecognitionModel createModel(@NonNull String languageTag) throws Exception {
        DigitalInkRecognitionModelIdentifier modelIdentifier;
        try {
            modelIdentifier = DigitalInkRecognitionModelIdentifier.fromLanguageTag(languageTag);
        } catch (Exception exception) {
            throw CustomExceptions.UNSUPPORTED_LANGUAGE_TAG;
        }
        if (modelIdentifier == null) {
            throw CustomExceptions.UNSUPPORTED_LANGUAGE_TAG;
        }
        return DigitalInkRecognitionModel.builder(modelIdentifier).build();
    }

    @Nullable
    private RecognitionContext createRecognitionContext(@Nullable String preContext, @Nullable WritingArea writingArea) {
        if (preContext == null && writingArea == null) {
            return null;
        }
        RecognitionContext.Builder builder = RecognitionContext.builder().setPreContext(preContext == null ? "" : preContext);
        if (writingArea != null) {
            builder.setWritingArea(writingArea);
        }
        return builder.build();
    }
}
