package io.capawesome.capacitorjs.plugins.mlkit.textrecognition.interfaces;

import androidx.annotation.NonNull;

public interface NonEmptyResultCallback<T extends Result> extends Callback {
    void success(@NonNull T result);
}
