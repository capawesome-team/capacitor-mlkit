package io.capawesome.capacitorjs.plugins.mlkit.posedetection.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.google.mlkit.vision.common.PointF3D;
import com.google.mlkit.vision.pose.Pose;
import com.google.mlkit.vision.pose.PoseLandmark;
import io.capawesome.capacitorjs.plugins.mlkit.posedetection.interfaces.Result;
import java.util.List;

public class ProcessImageResult implements Result {

    @NonNull
    private final Pose pose;

    public ProcessImageResult(@NonNull Pose pose) {
        this.pose = pose;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("poses", this.createPosesResult(pose));
        return result;
    }

    private JSArray createPosesResult(@NonNull Pose pose) {
        JSArray result = new JSArray();
        List<PoseLandmark> landmarks = pose.getAllPoseLandmarks();
        if (!landmarks.isEmpty()) {
            JSObject poseResult = new JSObject();
            poseResult.put("landmarks", this.createLandmarksResult(landmarks));
            result.put(poseResult);
        }
        return result;
    }

    private JSArray createLandmarksResult(@NonNull List<PoseLandmark> landmarks) {
        JSArray result = new JSArray();
        for (PoseLandmark landmark : landmarks) {
            result.put(this.createLandmarkResult(landmark));
        }
        return result;
    }

    private JSObject createLandmarkResult(@NonNull PoseLandmark landmark) {
        PointF3D position = landmark.getPosition3D();
        JSObject result = new JSObject();
        result.put("type", this.convertLandmarkTypeToString(landmark.getLandmarkType()));
        result.put("x", position.getX());
        result.put("y", position.getY());
        result.put("z", position.getZ());
        result.put("inFrameLikelihood", landmark.getInFrameLikelihood());
        return result;
    }

    @NonNull
    private String convertLandmarkTypeToString(int type) {
        switch (type) {
            case PoseLandmark.NOSE:
                return "NOSE";
            case PoseLandmark.LEFT_EYE_INNER:
                return "LEFT_EYE_INNER";
            case PoseLandmark.LEFT_EYE:
                return "LEFT_EYE";
            case PoseLandmark.LEFT_EYE_OUTER:
                return "LEFT_EYE_OUTER";
            case PoseLandmark.RIGHT_EYE_INNER:
                return "RIGHT_EYE_INNER";
            case PoseLandmark.RIGHT_EYE:
                return "RIGHT_EYE";
            case PoseLandmark.RIGHT_EYE_OUTER:
                return "RIGHT_EYE_OUTER";
            case PoseLandmark.LEFT_EAR:
                return "LEFT_EAR";
            case PoseLandmark.RIGHT_EAR:
                return "RIGHT_EAR";
            case PoseLandmark.LEFT_MOUTH:
                return "LEFT_MOUTH";
            case PoseLandmark.RIGHT_MOUTH:
                return "RIGHT_MOUTH";
            case PoseLandmark.LEFT_SHOULDER:
                return "LEFT_SHOULDER";
            case PoseLandmark.RIGHT_SHOULDER:
                return "RIGHT_SHOULDER";
            case PoseLandmark.LEFT_ELBOW:
                return "LEFT_ELBOW";
            case PoseLandmark.RIGHT_ELBOW:
                return "RIGHT_ELBOW";
            case PoseLandmark.LEFT_WRIST:
                return "LEFT_WRIST";
            case PoseLandmark.RIGHT_WRIST:
                return "RIGHT_WRIST";
            case PoseLandmark.LEFT_PINKY:
                return "LEFT_PINKY";
            case PoseLandmark.RIGHT_PINKY:
                return "RIGHT_PINKY";
            case PoseLandmark.LEFT_INDEX:
                return "LEFT_INDEX";
            case PoseLandmark.RIGHT_INDEX:
                return "RIGHT_INDEX";
            case PoseLandmark.LEFT_THUMB:
                return "LEFT_THUMB";
            case PoseLandmark.RIGHT_THUMB:
                return "RIGHT_THUMB";
            case PoseLandmark.LEFT_HIP:
                return "LEFT_HIP";
            case PoseLandmark.RIGHT_HIP:
                return "RIGHT_HIP";
            case PoseLandmark.LEFT_KNEE:
                return "LEFT_KNEE";
            case PoseLandmark.RIGHT_KNEE:
                return "RIGHT_KNEE";
            case PoseLandmark.LEFT_ANKLE:
                return "LEFT_ANKLE";
            case PoseLandmark.RIGHT_ANKLE:
                return "RIGHT_ANKLE";
            case PoseLandmark.LEFT_HEEL:
                return "LEFT_HEEL";
            case PoseLandmark.RIGHT_HEEL:
                return "RIGHT_HEEL";
            case PoseLandmark.LEFT_FOOT_INDEX:
                return "LEFT_FOOT_INDEX";
            case PoseLandmark.RIGHT_FOOT_INDEX:
                return "RIGHT_FOOT_INDEX";
            default:
                return "";
        }
    }
}
