package io.capawesome.capacitorjs.plugins.mlkit.selfiesegmentation.classes;

import com.google.mlkit.vision.common.InputImage;

public class ProcessImageOptions {

    private InputImage inputImage;

    private boolean enableRawSizeMask;

    public ProcessImageOptions(InputImage inputImage, boolean enableRawSizeMask) {
        this.inputImage = inputImage;
        this.enableRawSizeMask = enableRawSizeMask;
    }

    public InputImage getInputImage() {
        return inputImage;
    }

    public boolean isRawSizeMaskEnabled() {
        return enableRawSizeMask;
    }
}
