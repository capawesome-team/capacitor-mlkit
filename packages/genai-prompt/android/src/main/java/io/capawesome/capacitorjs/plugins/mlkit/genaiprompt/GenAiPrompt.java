package io.capawesome.capacitorjs.plugins.mlkit.genaiprompt;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.genai.common.DownloadCallback;
import com.google.mlkit.genai.common.FeatureStatus;
import com.google.mlkit.genai.common.GenAiException;
import com.google.mlkit.genai.prompt.GenerateContentRequest;
import com.google.mlkit.genai.prompt.GenerateContentResponse;
import com.google.mlkit.genai.prompt.Generation;
import com.google.mlkit.genai.prompt.ImagePart;
import com.google.mlkit.genai.prompt.TextPart;
import com.google.mlkit.genai.prompt.java.GenerativeModelFutures;
import io.capawesome.capacitorjs.plugins.mlkit.genaiprompt.classes.events.DownloadProgressEvent;
import io.capawesome.capacitorjs.plugins.mlkit.genaiprompt.classes.events.InferenceProgressEvent;
import io.capawesome.capacitorjs.plugins.mlkit.genaiprompt.classes.options.GenerateContentOptions;
import io.capawesome.capacitorjs.plugins.mlkit.genaiprompt.classes.results.CheckFeatureStatusResult;
import io.capawesome.capacitorjs.plugins.mlkit.genaiprompt.classes.results.GenerateContentResult;
import io.capawesome.capacitorjs.plugins.mlkit.genaiprompt.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.mlkit.genaiprompt.interfaces.NonEmptyResultCallback;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GenAiPrompt {

    @NonNull
    private final Executor executor = Executors.newSingleThreadExecutor();

    @NonNull
    private final GenAiPromptPlugin plugin;

    @Nullable
    private GenerativeModelFutures generativeModel;

    public GenAiPrompt(@NonNull GenAiPromptPlugin plugin) {
        this.plugin = plugin;
    }

    public void checkFeatureStatus(@NonNull NonEmptyResultCallback<CheckFeatureStatusResult> callback) {
        GenerativeModelFutures generativeModel = getGenerativeModel();
        ListenableFuture<Integer> future = generativeModel.checkStatus();
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

    public void downloadFeature(@NonNull EmptyCallback callback) {
        GenerativeModelFutures generativeModel = getGenerativeModel();
        ListenableFuture<Integer> future = generativeModel.checkStatus();
        future.addListener(
            () -> {
                try {
                    int featureStatus = future.get();
                    if (featureStatus == FeatureStatus.AVAILABLE) {
                        callback.success();
                        return;
                    }
                    generativeModel.download(
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

    public void generateContent(@NonNull GenerateContentOptions options, @NonNull NonEmptyResultCallback<GenerateContentResult> callback) {
        Bitmap bitmap = null;
        String imagePath = options.getImagePath();
        if (imagePath != null) {
            try {
                bitmap = getBitmapFromPath(imagePath);
            } catch (Exception exception) {
                callback.error(new Exception(GenAiPromptPlugin.ERROR_IMAGE_LOAD_FAILED));
                return;
            }
        }
        GenerativeModelFutures generativeModel = getGenerativeModel();
        GenerateContentRequest.Builder requestBuilder;
        if (bitmap == null) {
            requestBuilder = new GenerateContentRequest.Builder(new TextPart(options.getPrompt()));
        } else {
            requestBuilder = new GenerateContentRequest.Builder(new ImagePart(bitmap), new TextPart(options.getPrompt()));
        }
        requestBuilder.setMaxOutputTokens(options.getMaxOutputTokens());
        requestBuilder.setSeed(options.getSeed());
        requestBuilder.setTemperature(options.getTemperature());
        requestBuilder.setTopK(options.getTopK());
        ListenableFuture<GenerateContentResponse> future = generativeModel.generateContent(requestBuilder.build(), additionalText ->
            plugin.notifyInferenceProgressListeners(new InferenceProgressEvent(additionalText))
        );
        future.addListener(
            () -> {
                try {
                    GenerateContentResponse response = future.get();
                    callback.success(new GenerateContentResult(response.getCandidates().get(0).getText()));
                } catch (Exception exception) {
                    callback.error(unwrapException(exception));
                }
            },
            executor
        );
    }

    public void handleOnDestroy() {
        closeGenerativeModel();
    }

    private void closeGenerativeModel() {
        if (generativeModel != null) {
            generativeModel.getGenerativeModel().close();
            generativeModel = null;
        }
    }

    @NonNull
    private Bitmap getBitmapFromPath(@NonNull String path) throws Exception {
        Uri uri = Uri.parse(path);
        Bitmap bitmap;
        try (InputStream inputStream = plugin.getContext().getContentResolver().openInputStream(uri)) {
            bitmap = BitmapFactory.decodeStream(inputStream);
        }
        if (bitmap == null) {
            throw new Exception(GenAiPromptPlugin.ERROR_IMAGE_LOAD_FAILED);
        }
        int orientation = ExifInterface.ORIENTATION_NORMAL;
        try (InputStream inputStream = plugin.getContext().getContentResolver().openInputStream(uri)) {
            if (inputStream != null) {
                ExifInterface exifInterface = new ExifInterface(inputStream);
                orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            }
        }
        return rotateBitmap(bitmap, orientation);
    }

    @NonNull
    private GenerativeModelFutures getGenerativeModel() {
        if (generativeModel == null) {
            generativeModel = GenerativeModelFutures.from(Generation.INSTANCE.getClient());
        }
        return generativeModel;
    }

    @NonNull
    private static Bitmap rotateBitmap(@NonNull Bitmap bitmap, int orientation) {
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.postRotate(90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.postRotate(180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.postRotate(270);
                break;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.postScale(1, -1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.postRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.postRotate(270);
                matrix.postScale(-1, 1);
                break;
            default:
                return bitmap;
        }
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (rotatedBitmap != bitmap) {
            bitmap.recycle();
        }
        return rotatedBitmap;
    }

    @NonNull
    private static Exception unwrapException(@NonNull Exception exception) {
        if (exception instanceof ExecutionException && exception.getCause() instanceof Exception) {
            return (Exception) exception.getCause();
        }
        return exception;
    }
}
