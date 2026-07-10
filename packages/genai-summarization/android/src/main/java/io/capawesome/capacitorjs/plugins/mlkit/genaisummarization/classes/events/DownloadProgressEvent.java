package io.capawesome.capacitorjs.plugins.mlkit.genaisummarization.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.mlkit.genaisummarization.interfaces.Result;

public class DownloadProgressEvent implements Result {

    private final long totalBytesDownloaded;

    public DownloadProgressEvent(long totalBytesDownloaded) {
        this.totalBytesDownloaded = totalBytesDownloaded;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("totalBytesDownloaded", totalBytesDownloaded);
        return result;
    }
}
