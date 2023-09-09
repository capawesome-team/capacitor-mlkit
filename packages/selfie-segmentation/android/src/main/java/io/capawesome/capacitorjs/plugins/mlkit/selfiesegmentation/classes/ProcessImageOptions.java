package io.capawesome.capacitorjs.plugins.mlkit.selfiesegmentation.classes;

import android.graphics.Bitmap;
import com.google.mlkit.vision.common.InputImage;

public class ProcessImageOptions {

    private InputImage inputImage;
    private Float confidence;

    public ProcessImageOptions(InputImage inputImage, Integer width, Integer height, Float confidence) {
        this.inputImage = scaledImage(inputImage, width, height);

        this.confidence = confidence;
    }

    public InputImage getInputImage() {
        return inputImage;
    }
    public Float getConfidence() { return confidence; };

    private InputImage scaledImage(InputImage inputImage, Integer width, Integer height) {
        float scaleX = (width != null) ? width * 1f / inputImage.getWidth() : 0f;
        float scaleY = (height != null) ? height * 1f / inputImage.getHeight() : 0f;

        if (scaleX > 0f || scaleY > 0f) {
            if (scaleX > 0f && scaleY == 0f) {
                scaleY = scaleX;
            }
            if (scaleX == 0f && scaleY > 0f) {
                scaleX = scaleY;
            }

            return InputImage.fromBitmap(
                Bitmap.createScaledBitmap(
                    inputImage.getBitmapInternal(),
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
