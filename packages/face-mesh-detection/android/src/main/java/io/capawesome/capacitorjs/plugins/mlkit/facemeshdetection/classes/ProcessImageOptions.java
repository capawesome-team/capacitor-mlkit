package io.capawesome.capacitorjs.plugins.mlkit.facemeshdetection.classes;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.facemesh.FaceMeshDetectorOptions;

public class ProcessImageOptions {

    private final InputImage inputImage;

    @FaceMeshDetectorOptions.UseCase
    private int useCase;

    public ProcessImageOptions(InputImage inputImage, @FaceMeshDetectorOptions.UseCase int useCase) {
        this.inputImage = inputImage;

        this.useCase = useCase;
    }

    public InputImage getInputImage() {
        return inputImage;
    }

    public int getUseCase() {
        return useCase;
    }
}
