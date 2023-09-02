package io.capawesome.capacitorjs.plugins.mlkit.selfiesegmentation;

import io.capawesome.capacitorjs.plugins.mlkit.selfiesegmentation.classes.ProcessImageResult;

public interface ProcessImageResultCallback {
    void success(ProcessImageResult result);

    void cancel();

    void error(Exception exception);
}
