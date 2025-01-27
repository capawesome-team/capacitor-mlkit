package io.capawesome.capacitorjs.plugins.mlkit.translation;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.common.model.RemoteModelManager;
import com.google.mlkit.nl.translate.TranslateRemoteModel;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;
import java.util.Set;

public class Translation {

    private RemoteModelManager modelManager;

    public Translation() {
        this.modelManager = RemoteModelManager.getInstance();
    }

    public void deleteDownloadedModel(String language, final DeleteDownloadedModelResultCallback resultCallback) {
        TranslateRemoteModel frenchModel = new TranslateRemoteModel.Builder(language).build();
        modelManager
            .deleteDownloadedModel(frenchModel)
            .addOnSuccessListener(
                (OnSuccessListener) result -> {
                    resultCallback.success();
                }
            )
            .addOnFailureListener(exception -> {
                resultCallback.error(exception);
            });
    }

    public void downloadModel(String language, final DownloadModelResultCallback resultCallback) {
        TranslateRemoteModel frenchModel = new TranslateRemoteModel.Builder(language).build();
        DownloadConditions conditions = new DownloadConditions.Builder().requireWifi().build();
        modelManager
            .download(frenchModel, conditions)
            .addOnSuccessListener(
                (OnSuccessListener) result -> {
                    resultCallback.success();
                }
            )
            .addOnFailureListener(exception -> {
                resultCallback.error(exception);
            });
    }

    public void getDownloadedModels(final GetDownloadedModelsResultCallback resultCallback) {
        modelManager
            .getDownloadedModels(TranslateRemoteModel.class)
            .addOnSuccessListener(
                (OnSuccessListener<Set<TranslateRemoteModel>>) models -> {
                    resultCallback.success(models);
                }
            )
            .addOnFailureListener(exception -> {
                resultCallback.error(exception);
            });
    }

    public void translate(String text, String sourceLanguage, String targetLanguage, final TranslateResultCallback resultCallback) {
        TranslatorOptions options = new TranslatorOptions.Builder()
            .setSourceLanguage(sourceLanguage)
            .setTargetLanguage(targetLanguage)
            .build();
        final Translator translator = com.google.mlkit.nl.translate.Translation.getClient(options);
        DownloadConditions conditions = new DownloadConditions.Builder().requireWifi().build();
        translator
            .downloadModelIfNeeded(conditions)
            .addOnSuccessListener(
                (OnSuccessListener) downloadModelIfNeededResult -> {
                    translator
                        .translate(text)
                        .addOnSuccessListener(
                            (OnSuccessListener<String>) translateResult -> {
                                resultCallback.success(translateResult);
                                translator.close();
                            }
                        )
                        .addOnFailureListener(exception -> {
                            resultCallback.error(exception);
                            translator.close();
                        });
                }
            )
            .addOnFailureListener(exception -> {
                resultCallback.error(exception);
                translator.close();
            });
    }
}
