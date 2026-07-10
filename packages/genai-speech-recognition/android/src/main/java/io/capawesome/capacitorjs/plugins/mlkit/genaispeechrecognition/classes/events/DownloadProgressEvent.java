package io.capawesome.capacitorjs.plugins.mlkit.genaispeechrecognition.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.mlkit.genaispeechrecognition.interfaces.Result;

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
