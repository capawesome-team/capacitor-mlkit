package io.capawesome.capacitorjs.plugins.mlkit.facemeshdetection;

import io.capawesome.capacitorjs.plugins.mlkit.facemeshdetection.classes.ProcessImageResult;

public interface ProcessImageResultCallback {
    void success(ProcessImageResult result);

    void cancel();

    void error(Exception exception);
}
