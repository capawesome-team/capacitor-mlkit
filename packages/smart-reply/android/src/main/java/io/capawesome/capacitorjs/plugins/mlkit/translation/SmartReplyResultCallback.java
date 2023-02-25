package io.capawesome.capacitorjs.plugins.mlkit.translation;

public interface SmartReplyResultCallback {
    void success(String text);
    void error(Exception exception);
}
