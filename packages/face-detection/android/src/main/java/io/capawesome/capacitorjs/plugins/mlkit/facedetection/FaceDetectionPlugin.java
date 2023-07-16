package io.capawesome.capacitorjs.plugins.mlkit.facedetection;

import android.graphics.PointF;
import android.graphics.Rect;
import android.net.Uri;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceContour;
import com.google.mlkit.vision.face.FaceDetectorOptions;
import com.google.mlkit.vision.face.FaceLandmark;
import java.util.List;

@CapacitorPlugin(name = "FaceDetection")
public class FaceDetectionPlugin extends Plugin {

    public static final String ERROR_PROCESS_IMAGE_CANCELED = "processImage canceled.";

    private FaceDetection implementation;

    @Override
    public void load() {
        try {
            implementation = new FaceDetection(this);
        } catch (Exception exception) {
            Logger.error(exception.getMessage(), exception);
        }
    }

    @PluginMethod
    public void processImage(PluginCall call) {
        try {
            final InputImage image;
            final FaceDetectorOptions options;

            String path = call.getString("path", null);
            if (path != null) {
                image = InputImage.fromFilePath(getContext(), Uri.parse(path));
            } else {
                call.reject("Must provide a path");
                return;
            }

            // Creates a new builder to build FaceDetectorOptions.
            // https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceDetectorOptions.Builder#FaceDetectorOptions.Builder()
            FaceDetectorOptions.Builder builder = new FaceDetectorOptions.Builder();

            Integer performanceMode = call.getInt("performanceMode", null);
            if (performanceMode != null) {
                // Extended option for controlling additional accuracy / speed trade-offs in performing face detection.
                builder.setPerformanceMode(performanceMode);
            }

            Integer landmarkMode = call.getInt("landmarkMode", null);
            if (landmarkMode != null) {
                // Sets whether to detect no landmarks or all landmarks.
                builder.setLandmarkMode(landmarkMode);
            }
            Integer contourMode = call.getInt("contourMode", null);
            if (contourMode != null) {
                // Sets whether to detect no contours or all contours.
                builder.setContourMode(contourMode);
            }

            Integer classificationMode = call.getInt("classificationMode", null);
            if (classificationMode != null) {
                // Indicates whether to run additional classifiers for characterizing attributes such as "smiling" and "eyes open".
                builder.setClassificationMode(classificationMode);
            }

            Float minFaceSize = call.getFloat("minFaceSize", null);
            if (minFaceSize != null) {
                // Sets the smallest desired face size, expressed as a proportion of the width of the head to the image width.
                builder.setMinFaceSize(minFaceSize);
            }

            Boolean enableTracking = call.getBoolean("enableTracking", null);
            if (enableTracking != null) {
                // Enables face tracking, which will maintain a consistent ID for each face when processing consecutive frames.
                builder.enableTracking();
            }

            // Builds a face detector instance.
            options = builder.build();

            implementation.processImage(
                image,
                options,
                new ProcessImageResultCallback() {
                    @Override
                    public void success(List<Face> faces) {
                        JSArray facesArray = new JSArray();

                        // Represents a face detected by FaceDetector.
                        // https://developers.google.com/android/reference/com/google/mlkit/vision/face/Face
                        for (Face face : faces) {
                            JSObject faceObject = new JSObject();

                            { // Bounds
                                // Returns the axis-aligned bounding rectangle of the detected face.
                                Rect bounds = face.getBoundingBox();

                                JSObject boundsObject = new JSObject();

                                boundsObject.put("left", bounds.left);
                                boundsObject.put("top", bounds.top);
                                boundsObject.put("right", bounds.right);
                                boundsObject.put("bottom", bounds.bottom);

                                faceObject.put("bounds", boundsObject);
                            }

                            { // Landmarks
                                JSArray landmarksArray = new JSArray();

                                // Gets a list of all available FaceLandmarks.
                                // https://developers.google.com/android/reference/com/google/mlkit/vision/face/Face#getAllLandmarks()
                                List<FaceLandmark> landmarks = face.getAllLandmarks();

                                for (FaceLandmark landmark : landmarks) {
                                    JSObject pointObject = FaceDetectionHelper.convertPoint(landmark.getPosition());

                                    JSObject landmarkObject = new JSObject();

                                    // Gets the FaceLandmark.LandmarkType type.
                                    landmarkObject.put("type", landmark.getLandmarkType());
                                    // Gets a 2D point for landmark position, where (0, 0) is the upper-left corner of the image.
                                    landmarkObject.put("position", pointObject);

                                    landmarksArray.put(landmarkObject);
                                }

                                if (landmarksArray.length() > 0) {
                                    faceObject.put("landmarks", landmarksArray);
                                }
                            }

                            { // Contours
                                JSArray contoursArray = new JSArray();

                                // Gets a list of all available FaceContours.
                                // https://developers.google.com/android/reference/com/google/mlkit/vision/face/Face#getAllContours()
                                List<FaceContour> contours = face.getAllContours();

                                for (FaceContour contour : contours) {
                                    // Gets a list of 2D points for this face contour, where (0, 0) is the upper-left corner of the image.
                                    // https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceContour#getPoints()
                                    List<PointF> points = contour.getPoints();

                                    JSArray pointsArray = new JSArray();

                                    for (PointF point : points) {
                                        pointsArray.put(FaceDetectionHelper.convertPoint(point));
                                    }

                                    if (pointsArray.length() > 0) {
                                        JSObject contourObject = new JSObject();

                                        // Gets the FaceContour.ContourType type.
                                        contourObject.put("type", contour.getFaceContourType());
                                        // Gets a list of 2D points for this face contour, where (0, 0) is the upper-left corner of the image.
                                        contourObject.put("points", pointsArray);

                                        contoursArray.put(contourObject);
                                    }
                                }

                                if (contoursArray.length() > 0) {
                                    faceObject.put("contours", contoursArray);
                                }
                            }

                            // Returns the tracking ID if the tracking is enabled.
                            // Otherwise, returns null;
                            // https://developers.google.com/android/reference/com/google/mlkit/vision/face/Face#getTrackingId()
                            Integer trackingId = face.getTrackingId();
                            if (trackingId != null) {
                                faceObject.put("trackingId", trackingId);
                            }

                            // Returns the rotation of the face about the horizontal axis of the image.
                            // Positive euler X is the face is looking up.
                            // https://developers.google.com/android/reference/com/google/mlkit/vision/face/Face#getHeadEulerAngleX()
                            faceObject.put("headEulerAngleX", face.getHeadEulerAngleX());
                            // Returns the rotation of the face about the vertical axis of the image.
                            // Positive euler y is when the face turns toward the right side of the image that is being processed.
                            // https://developers.google.com/android/reference/com/google/mlkit/vision/face/Face#getHeadEulerAngleY()
                            faceObject.put("headEulerAngleY", face.getHeadEulerAngleY());
                            // Returns the rotation of the face about the axis pointing out of the image.
                            // Positive euler z is a counter-clockwise rotation within the image plane.
                            // https://developers.google.com/android/reference/com/google/mlkit/vision/face/Face#getHeadEulerAngleZ()
                            faceObject.put("headEulerAngleZ", face.getHeadEulerAngleZ());

                            // Returns a value between 0.0 and 1.0 giving a probability that the face is smiling.
                            // This returns null if the probability was not computed.
                            // https://developers.google.com/android/reference/com/google/mlkit/vision/face/Face#getSmilingProbability()
                            Float smilingProbability = face.getSmilingProbability();
                            if (smilingProbability != null) {
                                faceObject.put("smilingProbability", smilingProbability);
                            }
                            // Returns a value between 0.0 and 1.0 giving a probability that the face's left eye is open.
                            // This returns null if the probability was not computed.
                            // https://developers.google.com/android/reference/com/google/mlkit/vision/face/Face#getLeftEyeOpenProbability()
                            Float leftEyeOpenProbability = face.getLeftEyeOpenProbability();
                            if (leftEyeOpenProbability != null) {
                                faceObject.put("leftEyeOpenProbability", leftEyeOpenProbability);
                            }
                            // Returns a value between 0.0 and 1.0 giving a probability that the face's right eye is open.
                            // This returns null if the probability was not computed.
                            // https://developers.google.com/android/reference/com/google/mlkit/vision/face/Face#getRightEyeOpenProbability()
                            Float rightEyeOpenProbability = face.getRightEyeOpenProbability();
                            if (rightEyeOpenProbability != null) {
                                faceObject.put("rightEyeOpenProbability", rightEyeOpenProbability);
                            }

                            facesArray.put(faceObject);
                        }

                        JSObject result = new JSObject();
                        result.put("faces", facesArray);
                        call.resolve(result);
                    }

                    @Override
                    public void cancel() {
                        call.reject(ERROR_PROCESS_IMAGE_CANCELED);
                    }

                    @Override
                    public void error(Exception exception) {
                        String message = exception.getMessage();
                        Logger.error(message, exception);
                        call.reject(message);
                    }
                }
            );
        } catch (Exception exception) {
            String message = exception.getMessage();
            Logger.error(message, exception);
            call.reject(message);
        }
    }
}
