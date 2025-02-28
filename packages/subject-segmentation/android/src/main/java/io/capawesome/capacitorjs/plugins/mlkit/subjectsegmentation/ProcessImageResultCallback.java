package io.capawesome.capacitorjs.plugins.mlkit.subjectsegmentation;

import io.capawesome.capacitorjs.plugins.mlkit.subjectsegmentation.classes.ProcessImageResult;

public interface ProcessImageResultCallback {
    void success(ProcessImageResult result);

    void cancel();

    void error(Exception exception);
}
