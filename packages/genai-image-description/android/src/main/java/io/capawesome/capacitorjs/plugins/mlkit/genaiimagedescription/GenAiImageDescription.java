package io.capawesome.capacitorjs.plugins.mlkit.genaiimagedescription;

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
import com.google.mlkit.genai.imagedescription.ImageDescriber;
import com.google.mlkit.genai.imagedescription.ImageDescriberOptions;
import com.google.mlkit.genai.imagedescription.ImageDescription;
import com.google.mlkit.genai.imagedescription.ImageDescriptionRequest;
import com.google.mlkit.genai.imagedescription.ImageDescriptionResult;
import io.capawesome.capacitorjs.plugins.mlkit.genaiimagedescription.classes.events.DownloadProgressEvent;
import io.capawesome.capacitorjs.plugins.mlkit.genaiimagedescription.classes.events.InferenceProgressEvent;
import io.capawesome.capacitorjs.plugins.mlkit.genaiimagedescription.classes.options.DescribeImageOptions;
import io.capawesome.capacitorjs.plugins.mlkit.genaiimagedescription.classes.results.CheckFeatureStatusResult;
import io.capawesome.capacitorjs.plugins.mlkit.genaiimagedescription.classes.results.DescribeImageResult;
import io.capawesome.capacitorjs.plugins.mlkit.genaiimagedescription.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.mlkit.genaiimagedescription.interfaces.NonEmptyResultCallback;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GenAiImageDescription {

    @NonNull
    private final Executor executor = Executors.newSingleThreadExecutor();

    @NonNull
    private final GenAiImageDescriptionPlugin plugin;

    @Nullable
    private ImageDescriber imageDescriber;

    public GenAiImageDescription(@NonNull GenAiImageDescriptionPlugin plugin) {
        this.plugin = plugin;
    }

    public void checkFeatureStatus(@NonNull NonEmptyResultCallback<CheckFeatureStatusResult> callback) {
        ImageDescriber imageDescriber = getImageDescriber();
        ListenableFuture<Integer> future = imageDescriber.checkFeatureStatus();
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

    public void describeImage(@NonNull DescribeImageOptions options, @NonNull NonEmptyResultCallback<DescribeImageResult> callback) {
        Bitmap bitmap;
        try {
            bitmap = getBitmapFromPath(options.getPath());
        } catch (Exception exception) {
            callback.error(new Exception(GenAiImageDescriptionPlugin.ERROR_IMAGE_LOAD_FAILED));
            return;
        }
        ImageDescriber imageDescriber = getImageDescriber();
        ImageDescriptionRequest request = ImageDescriptionRequest.builder(bitmap).build();
        ListenableFuture<ImageDescriptionResult> future = imageDescriber.runInference(request, additionalText ->
            plugin.notifyInferenceProgressListeners(new InferenceProgressEvent(additionalText))
        );
        future.addListener(
            () -> {
                try {
                    ImageDescriptionResult result = future.get();
                    callback.success(new DescribeImageResult(result.getDescription()));
                } catch (Exception exception) {
                    callback.error(unwrapException(exception));
                }
            },
            executor
        );
    }

    public void downloadFeature(@NonNull EmptyCallback callback) {
        ImageDescriber imageDescriber = getImageDescriber();
        ListenableFuture<Integer> future = imageDescriber.checkFeatureStatus();
        future.addListener(
            () -> {
                try {
                    int featureStatus = future.get();
                    if (featureStatus == FeatureStatus.AVAILABLE) {
                        callback.success();
                        return;
                    }
                    imageDescriber.downloadFeature(
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
        closeImageDescriber();
    }

    private void closeImageDescriber() {
        if (imageDescriber != null) {
            imageDescriber.close();
            imageDescriber = null;
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
            throw new Exception(GenAiImageDescriptionPlugin.ERROR_IMAGE_LOAD_FAILED);
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
    private ImageDescriber getImageDescriber() {
        if (imageDescriber == null) {
            ImageDescriberOptions sdkOptions = ImageDescriberOptions.builder(plugin.getContext()).build();
            imageDescriber = ImageDescription.getClient(sdkOptions);
        }
        return imageDescriber;
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
