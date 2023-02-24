package io.capawesome.capacitorjs.plugins.mlkit.translation;

public interface DownloadModelResultCallback {
    void success();
    void error(Exception exception);
}
