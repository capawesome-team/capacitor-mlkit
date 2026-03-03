package io.capawesome.capacitorjs.plugins.mlkit.subjectsegmentation;

public interface IsGoogleSubjectSegmentationModuleAvailableResultCallback {
    void success(boolean isAvailable);
    void error(Exception exception);
}
