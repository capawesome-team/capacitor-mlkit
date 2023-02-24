package io.capawesome.capacitorjs.plugins.mlkit.translation;

import com.google.mlkit.nl.translate.TranslateRemoteModel;
import java.util.Set;

public interface GetDownloadedModelsResultCallback {
    void success(Set<TranslateRemoteModel> models);
    void error(Exception exception);
}
