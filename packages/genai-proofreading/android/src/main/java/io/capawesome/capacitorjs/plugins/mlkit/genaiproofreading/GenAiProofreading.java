package io.capawesome.capacitorjs.plugins.mlkit.genaiproofreading;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.genai.common.DownloadCallback;
import com.google.mlkit.genai.common.FeatureStatus;
import com.google.mlkit.genai.common.GenAiException;
import com.google.mlkit.genai.proofreading.Proofreader;
import com.google.mlkit.genai.proofreading.ProofreaderOptions;
import com.google.mlkit.genai.proofreading.Proofreading;
import com.google.mlkit.genai.proofreading.ProofreadingRequest;
import com.google.mlkit.genai.proofreading.ProofreadingResult;
import com.google.mlkit.genai.proofreading.ProofreadingSuggestion;
import io.capawesome.capacitorjs.plugins.mlkit.genaiproofreading.classes.events.DownloadProgressEvent;
import io.capawesome.capacitorjs.plugins.mlkit.genaiproofreading.classes.events.InferenceProgressEvent;
import io.capawesome.capacitorjs.plugins.mlkit.genaiproofreading.classes.options.FeatureOptions;
import io.capawesome.capacitorjs.plugins.mlkit.genaiproofreading.classes.options.ProofreadOptions;
import io.capawesome.capacitorjs.plugins.mlkit.genaiproofreading.classes.results.CheckFeatureStatusResult;
import io.capawesome.capacitorjs.plugins.mlkit.genaiproofreading.classes.results.ProofreadResult;
import io.capawesome.capacitorjs.plugins.mlkit.genaiproofreading.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.mlkit.genaiproofreading.interfaces.NonEmptyResultCallback;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GenAiProofreading {

    @NonNull
    private final Executor executor = Executors.newSingleThreadExecutor();

    @NonNull
    private final GenAiProofreadingPlugin plugin;

    @Nullable
    private Proofreader proofreader;

    @Nullable
    private FeatureOptions proofreaderOptions;

    public GenAiProofreading(@NonNull GenAiProofreadingPlugin plugin) {
        this.plugin = plugin;
    }

    public void checkFeatureStatus(@NonNull FeatureOptions options, @NonNull NonEmptyResultCallback<CheckFeatureStatusResult> callback) {
        Proofreader proofreader = getProofreader(options);
        ListenableFuture<Integer> future = proofreader.checkFeatureStatus();
        future.addListener(
            () -> {
                try {
                    int featureStatus = future.get();
                    callback.success(new CheckFeatureStatusResult(featureStatus));
                } catch (Exception exception) {
                    callback.error(unwrapException(exception));
                }
            },
            executor
        );
    }

    public void downloadFeature(@NonNull FeatureOptions options, @NonNull EmptyCallback callback) {
        Proofreader proofreader = getProofreader(options);
        ListenableFuture<Integer> future = proofreader.checkFeatureStatus();
        future.addListener(
            () -> {
                try {
                    int featureStatus = future.get();
                    if (featureStatus == FeatureStatus.AVAILABLE) {
                        callback.success();
                        return;
                    }
                    proofreader.downloadFeature(
                        new DownloadCallback() {
                            @Override
                            public void onDownloadStarted(long bytesToDownload) {}

                            @Override
                            public void onDownloadProgress(long totalBytesDownloaded) {
                                plugin.notifyDownloadProgressListeners(new DownloadProgressEvent(totalBytesDownloaded));
                            }

                            @Override
                            public void onDownloadCompleted() {
                                callback.success();
                            }

                            @Override
                            public void onDownloadFailed(@NonNull GenAiException exception) {
                                callback.error(exception);
                            }
                        }
                    );
                } catch (Exception exception) {
                    callback.error(unwrapException(exception));
                }
            },
            executor
        );
    }

    public void handleOnDestroy() {
        closeProofreader();
    }

    public void proofread(@NonNull ProofreadOptions options, @NonNull NonEmptyResultCallback<ProofreadResult> callback) {
        Proofreader proofreader = getProofreader(options);
        ProofreadingRequest request = ProofreadingRequest.builder(options.getText()).build();
        ListenableFuture<ProofreadingResult> future = proofreader.runInference(request, additionalText ->
            plugin.notifyInferenceProgressListeners(new InferenceProgressEvent(additionalText))
        );
        future.addListener(
            () -> {
                try {
                    ProofreadingResult result = future.get();
                    List<String> results = new ArrayList<>();
                    for (ProofreadingSuggestion suggestion : result.getResults()) {
                        results.add(suggestion.getText());
                    }
                    callback.success(new ProofreadResult(results));
                } catch (Exception exception) {
                    callback.error(unwrapException(exception));
                }
            },
            executor
        );
    }

    private void closeProofreader() {
        if (proofreader != null) {
            proofreader.close();
            proofreader = null;
            proofreaderOptions = null;
        }
    }

    @NonNull
    private Proofreader getProofreader(@NonNull FeatureOptions options) {
        if (proofreader == null || !options.matches(proofreaderOptions)) {
            closeProofreader();
            ProofreaderOptions sdkOptions = ProofreaderOptions.builder(plugin.getContext())
                .setInputType(options.getInputType())
                .setLanguage(options.getLanguage())
                .build();
            proofreader = Proofreading.getClient(sdkOptions);
            proofreaderOptions = options;
        }
        return proofreader;
    }

    @NonNull
    private static Exception unwrapException(@NonNull Exception exception) {
        if (exception instanceof ExecutionException && exception.getCause() instanceof Exception) {
            return (Exception) exception.getCause();
        }
        return exception;
    }
}
