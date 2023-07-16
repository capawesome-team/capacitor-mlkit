package io.capawesome.capacitorjs.plugins.mlkit.facedetection;

import com.google.mlkit.vision.face.Face;
import java.util.List;

public interface ProcessImageResultCallback {
    void success(List<Face> faces);
    void cancel();
    void error(Exception exception);
}
