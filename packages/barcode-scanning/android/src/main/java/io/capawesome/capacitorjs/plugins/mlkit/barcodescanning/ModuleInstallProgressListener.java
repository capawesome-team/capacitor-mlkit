package io.capawesome.capacitorjs.plugins.mlkit.barcodescanning;

import static com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate.InstallState.STATE_CANCELED;
import static com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate.InstallState.STATE_COMPLETED;
import static com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate.InstallState.STATE_FAILED;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.moduleinstall.InstallStatusListener;
import com.google.android.gms.common.moduleinstall.ModuleInstall;
import com.google.android.gms.common.moduleinstall.ModuleInstallClient;
import com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate;

public class ModuleInstallProgressListener implements InstallStatusListener {

    @NonNull
    private final BarcodeScanner implementation;

    public ModuleInstallProgressListener(BarcodeScanner implementation) {
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
            progress = (int) (100 * (progressInfo.getBytesDownloaded() / progressInfo.getTotalBytesToDownload()));
        }
        implementation.handleGoogleBarcodeScannerModuleInstallProgress(state, progress);
    }
}
