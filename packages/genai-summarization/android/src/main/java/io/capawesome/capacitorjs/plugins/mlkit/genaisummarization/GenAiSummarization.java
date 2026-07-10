package io.capawesome.capacitorjs.plugins.mlkit.genaisummarization;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.genai.common.DownloadCallback;
import com.google.mlkit.genai.common.FeatureStatus;
import com.google.mlkit.genai.common.GenAiException;
import com.google.mlkit.genai.summarization.Summarization;
import com.google.mlkit.genai.summarization.SummarizationRequest;
import com.google.mlkit.genai.summarization.SummarizationResult;
import com.google.mlkit.genai.summarization.Summarizer;
import com.google.mlkit.genai.summarization.SummarizerOptions;
import io.capawesome.capacitorjs.plugins.mlkit.genaisummarization.classes.events.DownloadProgressEvent;
import io.capawesome.capacitorjs.plugins.mlkit.genaisummarization.classes.events.InferenceProgressEvent;
import io.capawesome.capacitorjs.plugins.mlkit.genaisummarization.classes.options.FeatureOptions;
import io.capawesome.capacitorjs.plugins.mlkit.genaisummarization.classes.options.SummarizeOptions;
import io.capawesome.capacitorjs.plugins.mlkit.genaisummarization.classes.results.CheckFeatureStatusResult;
import io.capawesome.capacitorjs.plugins.mlkit.genaisummarization.classes.results.SummarizeResult;
import io.capawesome.capacitorjs.plugins.mlkit.genaisummarization.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.mlkit.genaisummarization.interfaces.NonEmptyResultCallback;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GenAiSummarization {

    @NonNull
    private final Executor executor = Executors.newSingleThreadExecutor();

    @NonNull
    private final GenAiSummarizationPlugin plugin;

    @Nullable
    private Summarizer summarizer;

    @Nullable
    private FeatureOptions summarizerOptions;

    public GenAiSummarization(@NonNull GenAiSummarizationPlugin plugin) {
        this.plugin = plugin;
    }

    public void checkFeatureStatus(@NonNull FeatureOptions options, @NonNull NonEmptyResultCallback<CheckFeatureStatusResult> callback) {
        Summarizer summarizer = getSummarizer(options);
        ListenableFuture<Integer> future = summarizer.checkFeatureStatus();
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
        Summarizer summarizer = getSummarizer(options);
        ListenableFuture<Integer> future = summarizer.checkFeatureStatus();
        future.addListener(
            () -> {
                try {
                    int featureStatus = future.get();
                    if (featureStatus == FeatureStatus.AVAILABLE) {
                        callback.success();
                        return;
                    }
                    summarizer.downloadFeature(
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
        closeSummarizer();
    }

    public void summarize(@NonNull SummarizeOptions options, @NonNull NonEmptyResultCallback<SummarizeResult> callback) {
        Summarizer summarizer = getSummarizer(options);
        SummarizationRequest request = SummarizationRequest.builder(options.getText()).build();
        ListenableFuture<SummarizationResult> future = summarizer.runInference(request, additionalText ->
            plugin.notifyInferenceProgressListeners(new InferenceProgressEvent(additionalText))
        );
        future.addListener(
            () -> {
                try {
                    SummarizationResult result = future.get();
                    callback.success(new SummarizeResult(result.getSummary()));
                } catch (Exception exception) {
                    callback.error(unwrapException(exception));
                }
            },
            executor
        );
    }

    private void closeSummarizer() {
        if (summarizer != null) {
            summarizer.close();
            summarizer = null;
            summarizerOptions = null;
        }
    }

    @NonNull
    private Summarizer getSummarizer(@NonNull FeatureOptions options) {
        if (summarizer == null || !options.matches(summarizerOptions)) {
            closeSummarizer();
            SummarizerOptions sdkOptions = SummarizerOptions.builder(plugin.getContext())
                .setInputType(options.getInputType())
                .setLanguage(options.getLanguage())
                .setOutputType(options.getOutputType())
                .build();
            summarizer = Summarization.getClient(sdkOptions);
            summarizerOptions = options;
        }
        return summarizer;
    }

    @NonNull
    private static Exception unwrapException(@NonNull Exception exception) {
        if (exception instanceof ExecutionException && exception.getCause() instanceof Exception) {
            return (Exception) exception.getCause();
        }
        return exception;
    }
}
