package io.capawesome.capacitorjs.plugins.mlkit.translation;

public interface TranslateResultCallback {
    void success(String text);
    void error(Exception exception);
}
