package io.capawesome.capacitorjs.plugins.mlkit.subjectsegmentation;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.segmentation.subject.SubjectSegmentation;
import com.google.mlkit.vision.segmentation.subject.SubjectSegmenter;
import com.google.mlkit.vision.segmentation.subject.SubjectSegmenterOptions;

import io.capawesome.capacitorjs.plugins.mlkit.subjectsegmentation.classes.ProcessImageOptions;
import io.capawesome.capacitorjs.plugins.mlkit.subjectsegmentation.classes.ProcessImageResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class SubjectSegmentationCapacitor {

    @NonNull
    private final SubjectSegmentationPlugin plugin;

    public SubjectSegmentationCapacitor(@NonNull SubjectSegmentationPlugin plugin) {
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
        Float threshold = options.getConfidence();

        SubjectSegmenterOptions.Builder builder = new SubjectSegmenterOptions.Builder();
        SubjectSegmenterOptions subjectSegmenterOptions = builder.build();

        final SubjectSegmenter segmenter = SubjectSegmentation.getClient(subjectSegmenterOptions);

        plugin
                .getActivity()
                .runOnUiThread(() ->
                        segmenter
                                .process(inputImage)
                                .addOnSuccessListener((segmentationResult) -> {
                                    segmenter.close();

                                    FloatBuffer mask = segmentationResult.getForegroundConfidenceMask();

                                    Bitmap bitmap = inputImage.getBitmapInternal();
                                    Objects.requireNonNull(bitmap).setHasAlpha(true);

                                    ByteBuffer pixels = ByteBuffer.allocateDirect(bitmap.getAllocationByteCount());
                                    bitmap.copyPixelsToBuffer(pixels);

                                    final boolean bigEndian = pixels.order() == ByteOrder.BIG_ENDIAN;
                                    final int ALPHA = bigEndian ? 3 : 0;
                                    final int RED = bigEndian ? 2 : 1;
                                    final int GREEN = bigEndian ? 1 : 2;
                                    final int BLUE = bigEndian ? 0 : 3;

                                    for (int i = 0; i < pixels.capacity() >> 2; i++) {
                                        assert mask != null;
                                        float confidence = mask.get();

                                        if (confidence >= threshold) {
                                            byte red = pixels.get((i << 2) + RED);
                                            byte green = pixels.get((i << 2) + GREEN);
                                            byte blue = pixels.get((i << 2) + BLUE);

                                            pixels.put((i << 2) + ALPHA, (byte) (0xff));
                                            pixels.put((i << 2) + RED, (byte) (red * confidence));
                                            pixels.put((i << 2) + GREEN, (byte) (green * confidence));
                                            pixels.put((i << 2) + BLUE, (byte) (blue * confidence));
                                        } else {
                                            pixels.putInt(i << 2, 0x00000000); // transparent
                                        }
                                    }

                                    bitmap.copyPixelsFromBuffer(pixels.rewind());

                                    // Create an image file name
                                    @SuppressLint("SimpleDateFormat")
                                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                                    String imageFileName = "PNG_" + timeStamp + "_";

                                    try {
                                        File image = File.createTempFile(imageFileName, ".png");

                                        OutputStream stream = new FileOutputStream(image);
                                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                        stream.close();

                                        ProcessImageResult result = new ProcessImageResult(
                                                image.getAbsolutePath(),
                                                bitmap.getWidth(),
                                                bitmap.getHeight()
                                        );

                                        callback.success(result);
                                    } catch (Exception exception) {
                                        callback.error(exception);
                                    }
                                })
                                .addOnCanceledListener(() -> {
                                    segmenter.close();
                                    callback.cancel();
                                })
                                .addOnFailureListener(exception -> {
                                    segmenter.close();
                                    callback.error(exception);
                                })
                );
    }
}
