package io.capawesome.capacitorjs.plugins.mlkit.facedetection;

import io.capawesome.capacitorjs.plugins.mlkit.facedetection.classes.ProcessImageResult;

public interface ProcessImageResultCallback {
    void success(ProcessImageResult result);
    void cancel();
    void error(Exception exception);
}
