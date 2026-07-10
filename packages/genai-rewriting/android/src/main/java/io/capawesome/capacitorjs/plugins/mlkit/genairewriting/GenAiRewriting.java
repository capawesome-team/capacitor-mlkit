package io.capawesome.capacitorjs.plugins.mlkit.genairewriting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.genai.common.DownloadCallback;
import com.google.mlkit.genai.common.FeatureStatus;
import com.google.mlkit.genai.common.GenAiException;
import com.google.mlkit.genai.rewriting.Rewriter;
import com.google.mlkit.genai.rewriting.RewriterOptions;
import com.google.mlkit.genai.rewriting.Rewriting;
import com.google.mlkit.genai.rewriting.RewritingRequest;
import com.google.mlkit.genai.rewriting.RewritingResult;
import com.google.mlkit.genai.rewriting.RewritingSuggestion;
import io.capawesome.capacitorjs.plugins.mlkit.genairewriting.classes.events.DownloadProgressEvent;
import io.capawesome.capacitorjs.plugins.mlkit.genairewriting.classes.events.InferenceProgressEvent;
import io.capawesome.capacitorjs.plugins.mlkit.genairewriting.classes.options.FeatureOptions;
import io.capawesome.capacitorjs.plugins.mlkit.genairewriting.classes.options.RewriteOptions;
import io.capawesome.capacitorjs.plugins.mlkit.genairewriting.classes.results.CheckFeatureStatusResult;
import io.capawesome.capacitorjs.plugins.mlkit.genairewriting.classes.results.RewriteResult;
import io.capawesome.capacitorjs.plugins.mlkit.genairewriting.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.mlkit.genairewriting.interfaces.NonEmptyResultCallback;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GenAiRewriting {

    @NonNull
    private final Executor executor = Executors.newSingleThreadExecutor();

    @NonNull
    private final GenAiRewritingPlugin plugin;

    @Nullable
    private Rewriter rewriter;

    @Nullable
    private FeatureOptions rewriterOptions;

    public GenAiRewriting(@NonNull GenAiRewritingPlugin plugin) {
        this.plugin = plugin;
    }

    public void checkFeatureStatus(@NonNull FeatureOptions options, @NonNull NonEmptyResultCallback<CheckFeatureStatusResult> callback) {
        Rewriter rewriter = getRewriter(options);
        ListenableFuture<Integer> future = rewriter.checkFeatureStatus();
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
        Rewriter rewriter = getRewriter(options);
        ListenableFuture<Integer> future = rewriter.checkFeatureStatus();
        future.addListener(
            () -> {
                try {
                    int featureStatus = future.get();
                    if (featureStatus == FeatureStatus.AVAILABLE) {
                        callback.success();
                        return;
                    }
                    rewriter.downloadFeature(
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
        closeRewriter();
    }

    public void rewrite(@NonNull RewriteOptions options, @NonNull NonEmptyResultCallback<RewriteResult> callback) {
        Rewriter rewriter = getRewriter(options);
        RewritingRequest request = RewritingRequest.builder(options.getText()).build();
        ListenableFuture<RewritingResult> future = rewriter.runInference(request, additionalText ->
            plugin.notifyInferenceProgressListeners(new InferenceProgressEvent(additionalText))
        );
        future.addListener(
            () -> {
                try {
                    RewritingResult result = future.get();
                    List<String> results = new ArrayList<>();
                    for (RewritingSuggestion suggestion : result.getResults()) {
                        results.add(suggestion.getText());
                    }
                    callback.success(new RewriteResult(results));
                } catch (Exception exception) {
                    callback.error(unwrapException(exception));
                }
            },
            executor
        );
    }

    private void closeRewriter() {
        if (rewriter != null) {
            rewriter.close();
            rewriter = null;
            rewriterOptions = null;
        }
    }

    @NonNull
    private Rewriter getRewriter(@NonNull FeatureOptions options) {
        if (rewriter == null || !options.matches(rewriterOptions)) {
            closeRewriter();
            RewriterOptions sdkOptions = RewriterOptions.builder(plugin.getContext())
                .setLanguage(options.getLanguage())
                .setOutputType(options.getOutputType())
                .build();
            rewriter = Rewriting.getClient(sdkOptions);
            rewriterOptions = options;
        }
        return rewriter;
    }

    @NonNull
    private static Exception unwrapException(@NonNull Exception exception) {
        if (exception instanceof ExecutionException && exception.getCause() instanceof Exception) {
            return (Exception) exception.getCause();
        }
        return exception;
    }
}
