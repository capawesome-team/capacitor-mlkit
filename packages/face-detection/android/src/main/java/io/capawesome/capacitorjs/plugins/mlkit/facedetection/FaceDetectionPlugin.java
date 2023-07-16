package io.capawesome.capacitorjs.plugins.mlkit.facedetection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.graphics.Rect;
import android.net.Uri;
import android.util.Base64;
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
                String content = call.getString("image", null);
                if (content != null) {
                    final byte[] data = Base64.decode(content, Base64.DEFAULT);

                    // Decode an immutable bitmap from the specified byte array.
                    // https://developer.android.com/reference/android/graphics/BitmapFactory#decodeByteArray(byte[],%20int,%20int)
                    Bitmap bitmap = BitmapFactory.decodeByteArray(
                        // Byte array of compressed image data.
                        data,
                        // Offset into imageData for where the decoder should begin parsing.
                        0,
                        // The number of bytes, beginning at offset, to parse.
                        data.length
                    );

                    // Creates an InputImage from a Bitmap.
                    // https://developers.google.com/android/reference/com/google/mlkit/vision/common/InputImage#fromBitmap(android.graphics.Bitmap,%20int)
                    image =
                        InputImage.fromBitmap(
                            // The input Bitmap.
                            bitmap,
                            // The image's counter-clockwise orientation degrees.
                            0
                        );
                } else {
                    call.reject("Must provide either a path or an image");
                    return;
                }
            }

            // Creates a new builder to build FaceDetectorOptions.
            // https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceDetectorOptions.Builder#FaceDetectorOptions.Builder()
            FaceDetectorOptions.Builder builder = new FaceDetectorOptions.Builder();

            JSObject optionsObject = call.getObject("options", null);
            if (optionsObject != null) {
                if (optionsObject.has("performanceMode")) {
                    Integer performanceMode = optionsObject.getInteger("performanceMode");

                    // Extended option for controlling additional accuracy / speed trade-offs in performing face detection.
                    builder.setPerformanceMode(performanceMode);
                }

                if (optionsObject.has("landmarkMode")) {
                    Integer landmarkMode = optionsObject.getInteger("landmarkMode");

                    // Sets whether to detect no landmarks or all landmarks.
                    builder.setLandmarkMode(landmarkMode);
                }
                if (optionsObject.has("contourMode")) {
                    Integer contourMode = optionsObject.getInteger("contourMode");

                    // Sets whether to detect no contours or all contours.
                    builder.setContourMode(contourMode);
                }

                if (optionsObject.has("classificationMode")) {
                    Integer classificationMode = optionsObject.getInteger("classificationMode");

                    // Indicates whether to run additional classifiers for characterizing attributes such as "smiling" and "eyes open".
                    builder.setClassificationMode(classificationMode);
                }

                if (optionsObject.has("minFaceSize")) {
                    Double minFaceSize = optionsObject.getDouble("minFaceSize");

                    // Sets the smallest desired face size, expressed as a proportion of the width of the head to the image width.
                    builder.setMinFaceSize(minFaceSize.floatValue());
                }

                Boolean enableTracking = optionsObject.getBoolean("enableTracking", false);
                if (enableTracking) {
                    // Enables face tracking, which will maintain a consistent ID for each face when processing consecutive frames.
                    builder.enableTracking();
                }
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

                                // The X coordinate of the left side of the rectangle
                                boundsObject.put("x", bounds.left);
                                // The Y coordinate of the top of the rectangle
                                boundsObject.put("y", bounds.top);
                                // The rectangle's width.
                                boundsObject.put("width", bounds.width());
                                // The rectangle's height.
                                boundsObject.put("height", bounds.height());

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
