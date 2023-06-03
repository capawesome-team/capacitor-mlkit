package io.capawesome.capacitorjs.plugins.mlkit.barcodescanning;

import static com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate.InstallState.STATE_CANCELED;
import static com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate.InstallState.STATE_COMPLETED;
import static com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate.InstallState.STATE_FAILED;

import com.google.android.gms.common.moduleinstall.InstallStatusListener;
import com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate;

public class ModuleInstallProgressListener implements InstallStatusListener {
    @Override
    public void onInstallStatusUpdated(ModuleInstallStatusUpdate update) {
        ModuleInstallStatusUpdate.ProgressInfo progressInfo = update.getProgressInfo();
        // Progress info is only set when modules are in the progress of downloading.
        if (progressInfo != null) {
            int progress =
                    (int)
                            (progressInfo.getBytesDownloaded() * 100 / progressInfo.getTotalBytesToDownload());
            // Set the progress for the progress bar.
            progressBar.setProgress(progress);
        }
        // Handle failure status maybe…

        // Unregister listener when there are no more install status updates.
        if (isTerminateState(update.getInstallState())) {
            moduleInstallClient.unregisterListener(this);
        }
    }

    public boolean isTerminateState(@ModuleInstallStatusUpdate.InstallState int state) {
        return state == STATE_CANCELED || state == STATE_COMPLETED || state == STATE_FAILED;
    }
}
