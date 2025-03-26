package io.capawesome.capacitorjs.plugins.mlkit.subjectsegmentation.classes;

import android.graphics.Bitmap;
import com.google.mlkit.vision.common.InputImage;
import java.util.Objects;

public class ProcessImageOptions {

    private final InputImage inputImage;

    private final Float confidence;

    public ProcessImageOptions(InputImage inputImage, Integer width, Integer height, Float confidence) {
        this.inputImage = scaledImage(inputImage, width, height);

        this.confidence = confidence;
    }

    public InputImage getInputImage() {
        return inputImage;
    }

    public Float getConfidence() {
        return confidence;
    }

    private InputImage scaledImage(InputImage inputImage, Integer width, Integer height) {
        float scaleX = (width != null) ? (width * 1f) / inputImage.getWidth() : 0f;
        float scaleY = (height != null) ? (height * 1f) / inputImage.getHeight() : 0f;

        if (scaleX > 0f || scaleY > 0f) {
            if (scaleX > 0f && scaleY == 0f) scaleY = scaleX;
            else if (scaleY > 0f && scaleX == 0f) scaleX = scaleY;

            return InputImage.fromBitmap(
                Bitmap.createScaledBitmap(
                    Objects.requireNonNull(inputImage.getBitmapInternal()),
                    (int) (inputImage.getWidth() * scaleX),
                    (int) (inputImage.getHeight() * scaleY),
                    false
                ),
                inputImage.getRotationDegrees()
            );
        }

        return inputImage;
    }
}
