package io.capawesome.capacitorjs.plugins.mlkit.entityextraction;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.common.model.RemoteModelManager;
import com.google.mlkit.nl.entityextraction.EntityExtractionParams;
import com.google.mlkit.nl.entityextraction.EntityExtractionRemoteModel;
import com.google.mlkit.nl.entityextraction.EntityExtractor;
import com.google.mlkit.nl.entityextraction.EntityExtractorOptions;
import io.capawesome.capacitorjs.plugins.mlkit.entityextraction.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.mlkit.entityextraction.classes.options.DeleteDownloadedModelOptions;
import io.capawesome.capacitorjs.plugins.mlkit.entityextraction.classes.options.DownloadModelOptions;
import io.capawesome.capacitorjs.plugins.mlkit.entityextraction.classes.options.ExtractEntitiesOptions;
import io.capawesome.capacitorjs.plugins.mlkit.entityextraction.classes.results.ExtractEntitiesResult;
import io.capawesome.capacitorjs.plugins.mlkit.entityextraction.classes.results.GetDownloadedModelsResult;
import io.capawesome.capacitorjs.plugins.mlkit.entityextraction.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.mlkit.entityextraction.interfaces.NonEmptyResultCallback;

public class EntityExtraction {

    @NonNull
    private final RemoteModelManager modelManager;

    @Nullable
    private EntityExtractor extractor;

    @Nullable
    private String extractorModelIdentifier;

    public EntityExtraction() {
        this.modelManager = RemoteModelManager.getInstance();
    }

    public void close() {
        if (extractor != null) {
            extractor.close();
            extractor = null;
            extractorModelIdentifier = null;
        }
    }

    public void deleteDownloadedModel(@NonNull DeleteDownloadedModelOptions options, @NonNull EmptyCallback callback) {
        EntityExtractionRemoteModel model = new EntityExtractionRemoteModel.Builder(options.getModelIdentifier()).build();
        modelManager.deleteDownloadedModel(model).addOnSuccessListener(unused -> callback.success()).addOnFailureListener(callback::error);
    }

    public void downloadModel(@NonNull DownloadModelOptions options, @NonNull EmptyCallback callback) {
        EntityExtractionRemoteModel model = new EntityExtractionRemoteModel.Builder(options.getModelIdentifier()).build();
        DownloadConditions conditions = new DownloadConditions.Builder().build();
        modelManager.download(model, conditions).addOnSuccessListener(unused -> callback.success()).addOnFailureListener(callback::error);
    }

    public void extractEntities(@NonNull ExtractEntitiesOptions options, @NonNull NonEmptyResultCallback<ExtractEntitiesResult> callback) {
        EntityExtractor extractor = getExtractor(options.getModelIdentifier());
        extractor
            .isModelDownloaded()
            .addOnSuccessListener(isModelDownloaded -> {
                if (!isModelDownloaded) {
                    callback.error(CustomExceptions.MODEL_NOT_DOWNLOADED);
                    return;
                }
                EntityExtractionParams params = createParams(options);
                extractor
                    .annotate(params)
                    .addOnSuccessListener(annotations -> callback.success(new ExtractEntitiesResult(annotations)))
                    .addOnFailureListener(callback::error);
            })
            .addOnFailureListener(callback::error);
    }

    public void getDownloadedModels(@NonNull NonEmptyResultCallback<GetDownloadedModelsResult> callback) {
        modelManager
            .getDownloadedModels(EntityExtractionRemoteModel.class)
            .addOnSuccessListener(models -> callback.success(new GetDownloadedModelsResult(models)))
            .addOnFailureListener(callback::error);
    }

    @NonNull
    private static EntityExtractionParams createParams(@NonNull ExtractEntitiesOptions options) {
        EntityExtractionParams.Builder builder = new EntityExtractionParams.Builder(options.getText());
        if (options.getReferenceTime() != null) {
            builder.setReferenceTime(options.getReferenceTime());
        }
        if (options.getReferenceTimeZone() != null) {
            builder.setReferenceTimeZone(options.getReferenceTimeZone());
        }
        if (options.getPreferredLocale() != null) {
            builder.setPreferredLocale(options.getPreferredLocale());
        }
        if (options.getEntityTypesFilter() != null) {
            builder.setEntityTypesFilter(options.getEntityTypesFilter());
        }
        return builder.build();
    }

    @NonNull
    private EntityExtractor getExtractor(@NonNull String modelIdentifier) {
        if (extractor != null && modelIdentifier.equals(extractorModelIdentifier)) {
            return extractor;
        }
        close();
        EntityExtractorOptions options = new EntityExtractorOptions.Builder(modelIdentifier).build();
        extractor = com.google.mlkit.nl.entityextraction.EntityExtraction.getClient(options);
        extractorModelIdentifier = modelIdentifier;
        return extractor;
    }
}
