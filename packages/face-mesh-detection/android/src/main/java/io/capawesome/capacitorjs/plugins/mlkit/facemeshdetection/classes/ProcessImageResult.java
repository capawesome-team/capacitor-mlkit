package io.capawesome.capacitorjs.plugins.mlkit.facemeshdetection.classes;

import android.graphics.Rect;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.google.mlkit.vision.common.PointF3D;
import com.google.mlkit.vision.common.Triangle;
import com.google.mlkit.vision.facemesh.FaceMesh;
import com.google.mlkit.vision.facemesh.FaceMeshPoint;
import java.util.List;

public class ProcessImageResult {

    private final List<FaceMesh> faceMeshs;

    @FaceMesh.ContourType
    private final int[] contourTypes = {
        FaceMesh.FACE_OVAL,
        FaceMesh.LEFT_EYEBROW_TOP,
        FaceMesh.LEFT_EYEBROW_BOTTOM,
        FaceMesh.RIGHT_EYEBROW_TOP,
        FaceMesh.RIGHT_EYEBROW_BOTTOM,
        FaceMesh.LEFT_EYE,
        FaceMesh.RIGHT_EYE,
        FaceMesh.UPPER_LIP_TOP,
        FaceMesh.UPPER_LIP_BOTTOM,
        FaceMesh.LOWER_LIP_TOP,
        FaceMesh.LOWER_LIP_BOTTOM,
        FaceMesh.NOSE_BRIDGE
    };

    public ProcessImageResult(List<FaceMesh> faceMeshs) {
        this.faceMeshs = faceMeshs;
    }

    public JSObject toJSObject() {
        JSArray faceMeshsResult = this.createFaceMeshsResult();

        JSObject result = new JSObject();
        result.put("faceMeshs", faceMeshsResult);
        return result;
    }

    private JSArray createFaceMeshsResult() {
        JSArray result = new JSArray();
        for (FaceMesh faceMesh : faceMeshs) {
            JSObject faceMeshResult = this.createFaceMeshResult(faceMesh);
            result.put(faceMeshResult);
        }
        return result;
    }

    private JSObject createFaceMeshResult(FaceMesh faceMesh) {
        JSObject result = new JSObject();

        Rect boundingBox = faceMesh.getBoundingBox();
        JSObject boundsResult = this.createBoundsResult(boundingBox);
        result.put("bounds", boundsResult);

        JSObject contoursResult = new JSObject();
        for (int contourType : contourTypes) {
            JSArray faceMeshPointsResult = new JSArray();
            List<FaceMeshPoint> faceMeshPoints = faceMesh.getPoints(contourType);
            for (FaceMeshPoint faceMeshPoint : faceMeshPoints) {
                int index = faceMeshPoint.getIndex();
                PointF3D position = faceMeshPoint.getPosition();

                JSObject faceMeshPointResult = this.createFaceMeshPointResult(index, position);
                faceMeshPointsResult.put(faceMeshPointResult);
            }

            if (faceMeshPointsResult.length() > 0) {
                contoursResult.put(contourType(contourType), faceMeshPointsResult);
            }
        }
        if (contoursResult.length() > 0) {
            result.put("contours", contoursResult);
        }

        JSArray faceMeshPointsResult = new JSArray();
        List<FaceMeshPoint> faceMeshPoints = faceMesh.getAllPoints();
        for (FaceMeshPoint faceMeshPoint : faceMeshPoints) {
            int index = faceMeshPoint.getIndex();
            PointF3D position = faceMeshPoint.getPosition();

            JSObject faceMeshPointResult = this.createFaceMeshPointResult(index, position);
            faceMeshPointsResult.put(faceMeshPointResult);
        }
        if (faceMeshPointsResult.length() > 0) {
            result.put("faceMeshPoints", faceMeshPointsResult);
        }

        JSArray trianglesResult = new JSArray();
        List<Triangle<FaceMeshPoint>> triangles = faceMesh.getAllTriangles();
        for (Triangle<FaceMeshPoint> triangle : triangles) {
            JSArray triangleResult = new JSArray();

            // 3 Points connecting to each other and representing a triangle area.
            List<FaceMeshPoint> connectedPoints = triangle.getAllPoints();

            for (FaceMeshPoint faceMeshPoint : connectedPoints) {
                int index = faceMeshPoint.getIndex();
                PointF3D position = faceMeshPoint.getPosition();

                JSObject faceMeshPointResult = this.createFaceMeshPointResult(index, position);
                triangleResult.put(faceMeshPointResult);
            }

            if (triangleResult.length() > 0) {
                trianglesResult.put(triangleResult);
            }
        }
        if (trianglesResult.length() > 0) {
            result.put("triangles", trianglesResult);
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

    private JSObject createFaceMeshPointResult(int index, PointF3D point) {
        JSObject result = new JSObject();
        result.put("index", index);
        result.put("point", createPointResult(point));
        return result;
    }

    private JSObject createPointResult(PointF3D point) {
        JSObject result = new JSObject();
        result.put("x", point.getX());
        result.put("y", point.getY());
        result.put("z", point.getZ());
        return result;
    }

    private String contourType(@FaceMesh.ContourType int contourType) {
        switch (contourType) {
            case FaceMesh.FACE_OVAL:
                return "faceOval";
            case FaceMesh.LEFT_EYEBROW_TOP:
                return "leftEyebrowTop";
            case FaceMesh.LEFT_EYEBROW_BOTTOM:
                return "leftEyebrowBottom";
            case FaceMesh.RIGHT_EYEBROW_TOP:
                return "rightEyebrowTop";
            case FaceMesh.RIGHT_EYEBROW_BOTTOM:
                return "rightEyebrowBottom";
            case FaceMesh.LEFT_EYE:
                return "leftEye";
            case FaceMesh.RIGHT_EYE:
                return "rightEye";
            case FaceMesh.UPPER_LIP_TOP:
                return "upperLipTop";
            case FaceMesh.UPPER_LIP_BOTTOM:
                return "upperLipBottom";
            case FaceMesh.LOWER_LIP_TOP:
                return "lowerLipTop";
            case FaceMesh.LOWER_LIP_BOTTOM:
                return "lowerLipBottom";
            case FaceMesh.NOSE_BRIDGE:
                return "noseBridge";
            default:
                return "";
        }
    }
}
