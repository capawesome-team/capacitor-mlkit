package io.capawesome.capacitorjs.plugins.mlkit.facedetection.classes;

import android.graphics.PointF;
import android.graphics.Rect;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceContour;
import com.google.mlkit.vision.face.FaceLandmark;
import java.util.List;

public class ProcessImageResult {

    private List<Face> faces;

    public ProcessImageResult(List<Face> faces) {
        this.faces = faces;
    }

    public JSObject toJSObject() {
        JSArray facesResult = this.createFacesResult();

        JSObject result = new JSObject();
        result.put("faces", facesResult);
        return result;
    }

    private JSArray createFacesResult() {
        JSArray result = new JSArray();
        for (Face face : faces) {
            JSObject faceResult = this.createFaceResult(face);
            result.put(faceResult);
        }
        return result;
    }

    private JSObject createFaceResult(Face face) {
        JSObject result = new JSObject();

        Rect boundingBox = face.getBoundingBox();
        JSObject boundsResult = this.createBoundsResult(boundingBox);
        result.put("bounds", boundsResult);

        JSArray landmarksResult = new JSArray();
        for (FaceLandmark landmark : face.getAllLandmarks()) {
            JSObject landmarkResult = this.createLandmarkResult(landmark);
            landmarksResult.put(landmarkResult);
        }
        if (landmarksResult.length() > 0) {
            result.put("landmarks", landmarksResult);
        }

        JSArray contoursResult = new JSArray();
        for (FaceContour contour : face.getAllContours()) {
            List<PointF> points = contour.getPoints();
            JSArray pointsResult = new JSArray();
            for (PointF point : points) {
                JSObject positionResult = this.createPositionResult(point);
                pointsResult.put(positionResult);
            }
            if (pointsResult.length() > 0) {
                JSObject contourResult = this.createContourResult(contour, pointsResult);
                contoursResult.put(contourResult);
            }
        }
        if (contoursResult.length() > 0) {
            result.put("contours", contoursResult);
        }

        Integer trackingId = face.getTrackingId();
        if (trackingId != null) {
            result.put("trackingId", trackingId);
        }

        result.put("headEulerAngleX", face.getHeadEulerAngleX());
        result.put("headEulerAngleY", face.getHeadEulerAngleY());
        result.put("headEulerAngleZ", face.getHeadEulerAngleZ());

        Float smilingProbability = face.getSmilingProbability();
        if (smilingProbability != null) {
            result.put("smilingProbability", smilingProbability);
        }

        Float leftEyeOpenProbability = face.getLeftEyeOpenProbability();
        if (leftEyeOpenProbability != null) {
            result.put("leftEyeOpenProbability", leftEyeOpenProbability);
        }

        Float rightEyeOpenProbability = face.getRightEyeOpenProbability();
        if (rightEyeOpenProbability != null) {
            result.put("rightEyeOpenProbability", rightEyeOpenProbability);
        }

        return result;
    }

    private JSObject createBoundsResult(Rect boundingBox) {
        JSObject result = new JSObject();
        result.put("left", boundingBox.left);
        result.put("top", boundingBox.top);
        result.put("right", boundingBox.right);
        result.put("bottom", boundingBox.bottom);
        return result;
    }

    private JSObject createLandmarkResult(FaceLandmark landmark) {
        JSObject positionResult = this.createPositionResult(landmark.getPosition());
        JSObject result = new JSObject();
        result.put("type", landmark.getLandmarkType());
        result.put("position", positionResult);
        return result;
    }

    private JSObject createPositionResult(PointF point) {
        JSObject result = new JSObject();
        result.put("x", point.x);
        result.put("y", point.y);
        return result;
    }

    private JSObject createContourResult(FaceContour contour, JSArray pointsResult) {
        JSObject result = new JSObject();
        result.put("type", contour.getFaceContourType());
        result.put("points", pointsResult);
        return result;
    }
}
