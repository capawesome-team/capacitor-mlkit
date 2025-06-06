package io.capawesome.capacitorjs.plugins.mlkit.documentscanner;

import static com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate.InstallState.STATE_CANCELED;
import static com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate.InstallState.STATE_COMPLETED;
import static com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate.InstallState.STATE_FAILED;

import androidx.annotation.NonNull;
import com.google.android.gms.common.moduleinstall.InstallStatusListener;
import com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate;

public class ModuleInstallProgressListener implements InstallStatusListener {

    @NonNull
    private final DocumentScanner implementation;

    public ModuleInstallProgressListener(@NonNull DocumentScanner implementation) {
        this.implementation = implementation;
    }

    public static boolean isTerminateState(@ModuleInstallStatusUpdate.InstallState int state) {
        return state == STATE_CANCELED || state == STATE_COMPLETED || state == STATE_FAILED;
    }

    @Override
    public void onInstallStatusUpdated(ModuleInstallStatusUpdate update) {
        ModuleInstallStatusUpdate.ProgressInfo progressInfo = update.getProgressInfo();
        @ModuleInstallStatusUpdate.InstallState
        int state = update.getInstallState();
        // Progress info is only set when modules are in the progress of downloading.
        Integer progress = null;
        if (progressInfo != null) {
            if (progressInfo.getTotalBytesToDownload() > 0) {
                progress = (int) ((100.0 * progressInfo.getBytesDownloaded()) / progressInfo.getTotalBytesToDownload());
            } else {
                // avoid division by zero if total bytes is 0, though unlikely for actual progress
                progress = 0;
            }
        }
        implementation.handleGoogleDocumentScannerModuleInstallProgress(state, progress);
    }
}
