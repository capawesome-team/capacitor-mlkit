package io.capawesome.capacitorjs.plugins.mlkit.facedetection.classes;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.FaceDetectorOptions;

public class ProcessImageOptions {

    private InputImage inputImage;

    @FaceDetectorOptions.PerformanceMode
    private int performanceMode;

    @FaceDetectorOptions.LandmarkMode
    private int landmarkMode;

    @FaceDetectorOptions.ContourMode
    private int contourMode;

    @FaceDetectorOptions.ClassificationMode
    private int classificationMode;

    private float minFaceSize;
    private boolean enableTracking;

    public ProcessImageOptions(
        InputImage inputImage,
        @FaceDetectorOptions.PerformanceMode int performanceMode,
        @FaceDetectorOptions.LandmarkMode int landmarkMode,
        @FaceDetectorOptions.ContourMode int contourMode,
        @FaceDetectorOptions.ClassificationMode int classificationMode,
        float minFaceSize,
        boolean enableTracking
    ) {
        this.inputImage = inputImage;
        this.performanceMode = performanceMode;
        this.landmarkMode = landmarkMode;
        this.contourMode = contourMode;
        this.classificationMode = classificationMode;
        this.minFaceSize = minFaceSize;
        this.enableTracking = enableTracking;
    }

    public InputImage getInputImage() {
        return inputImage;
    }

    public int getPerformanceMode() {
        return performanceMode;
    }

    public int getLandmarkMode() {
        return landmarkMode;
    }

    public int getContourMode() {
        return contourMode;
    }

    public int getClassificationMode() {
        return classificationMode;
    }

    public float getMinFaceSize() {
        return minFaceSize;
    }

    public boolean isTrackingEnabled() {
        return enableTracking;
    }
}
