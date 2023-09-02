package io.capawesome.capacitorjs.plugins.mlkit.selfiesegmentation;

import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.segmentation.Segmentation;
import com.google.mlkit.vision.segmentation.Segmenter;
import com.google.mlkit.vision.segmentation.selfie.SelfieSegmenterOptions;
import io.capawesome.capacitorjs.plugins.mlkit.selfiesegmentation.classes.ProcessImageOptions;
import io.capawesome.capacitorjs.plugins.mlkit.selfiesegmentation.classes.ProcessImageResult;

public class SelfieSegmentation {

    @NonNull
    private final SelfieSegmentationPlugin plugin;

    public SelfieSegmentation(@NonNull SelfieSegmentationPlugin plugin) {
        this.plugin = plugin;
    }

    @Nullable
    public InputImage createInputImageFromFilePath(@NonNull String path) {
        try {
            return InputImage.fromFilePath(this.plugin.getContext(), Uri.parse(path));
        } catch (Exception exception) {
            return null;
        }
    }

    public void processImage(ProcessImageOptions options, ProcessImageResultCallback callback) {
        InputImage inputImage = options.getInputImage();
        boolean enableRawSizeMask = options.isRawSizeMaskEnabled();

        SelfieSegmenterOptions.Builder builder = new SelfieSegmenterOptions.Builder();
        builder.setDetectorMode(SelfieSegmenterOptions.SINGLE_IMAGE_MODE);
        if (enableRawSizeMask) {
            builder.enableRawSizeMask();
        }
        SelfieSegmenterOptions selfieSegmenterOptions = builder.build();

        final Segmenter segmenter = Segmentation.getClient(selfieSegmenterOptions);
        plugin
            .getActivity()
            .runOnUiThread(
                () -> {
                    segmenter
                        .process(inputImage)
                        .addOnSuccessListener(
                            mask -> {
                                segmenter.close();
                                ProcessImageResult result = new ProcessImageResult(mask);
                                callback.success(result);
                            }
                        )
                        .addOnCanceledListener(
                            () -> {
                                segmenter.close();
                                callback.cancel();
                            }
                        )
                        .addOnFailureListener(
                            exception -> {
                                segmenter.close();
                                callback.error(exception);
                            }
                        );
                }
            );
    }
}
