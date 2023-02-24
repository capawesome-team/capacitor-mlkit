package io.capawesome.capacitorjs.plugins.mlkit.translation;

import java.util.Set;

public interface GetDownloadedModelsResultCallback {
    void success(Set models);
    void error(Exception exception);
}
