export interface FaceDetectionPlugin {
  /**
   * Detects human faces from the supplied image.
   *
   * Only available on Android and iOS.
   *
   * @since 5.1.0
   */
  processImage(options: ProcessImageOptions): Promise<ProcessImageResult>;
}

/**
 * @since 5.1.0
 */
export type ProcessImageOptions = (
  | {
      /**
       * The local path to the image file.
       *
       * @since 5.1.0
       */
      path: string;
    }
  | {
      /**
       * Represents an image object (Base64 encoded).
       *
       * @since 5.1.0
       */
      image: string;
    }
) & {
  /**
   * The options for the face detector.
   */
  options?: FaceDetectorOptions;
};

/**
 * @since 5.1.0
 */
export interface ProcessImageResult {
  /**
   * The detected faces.
   *
   * @since 5.1.0
   */
  faces: Face[];
}

/**
 * https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceDetectorOptions
 *
 * @since 5.1.0
 */
export interface FaceDetectorOptions {
  performanceMode?: PerformanceMode;

  landmarkMode?: LandmarkMode;
  contourMode?: ContourMode;

  classificationMode?: ClassificationMode;

  // Sets the smallest desired face size, expressed as a proportion of the width of the head to the image width.
  // https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceDetectorOptions.Builder#public-facedetectoroptions.builder-setminfacesize-float-minfacesize
  minFaceSize?: number;

  // Enables face tracking, which will maintain a consistent ID for each face when processing consecutive frames.
  // Tracking should be disabled for handling a series of non-consecutive still images.
  // https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceDetectorOptions.Builder#public-facedetectoroptions.builder-enabletracking
  enableTracking?: boolean;
}

/**
 * Defines options to control accuracy / speed trade-offs in performing face detection.
 * In general, choosing the more accurate mode will generally result in longer runtime, whereas choosing the faster mode will generally result in detecting fewer faces.
 * https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceDetectorOptions.PerformanceMode
 *
 * @since 5.1.0
 */
export enum PerformanceMode {
  // Indicates a preference for speed in the options that may make an accuracy vs. speed trade-off.
  // This will tend to detect fewer faces and may be less precise in determining values such as position, but will run faster.
  // https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceDetectorOptions#PERFORMANCE_MODE_FAST
  FAST = 1,
  // Indicates a preference for accuracy in the options that may make an accuracy vs. speed trade-off.
  // This will tend to detect more faces and may be more precise in determining values such as position, at the cost of speed.
  // https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceDetectorOptions#public-static-final-int-performance_mode_accurate
  ACCURATE = 2,
}

/**
 * Defines options to enable face landmarks or not.
 * Processing time increases as the extra face landmark to search.
 * https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceDetectorOptions.LandmarkMode
 *
 * @since 5.1.0
 */
export enum LandmarkMode {
  // Does not perform landmark detection.
  // https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceDetectorOptions#public-static-final-int-landmark_mode_none
  NONE = 1,
  // Detects FaceLandmark for a given face.
  // https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceDetectorOptions#public-static-final-int-landmark_mode_all
  ALL = 2,
}

/**
 * Defines options to enable face contours or not.
 * Processing time increases as the number of contours to search.
 * https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceDetectorOptions.ContourMode
 *
 * @since 5.1.0
 */
export enum ContourMode {
  // Does not perform contour detection.
  // https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceDetectorOptions#public-static-final-int-contour_mode_none
  NONE = 1,
  // Detects FaceContour for a given face.
  // Note that it would return contours for up to 5 faces
  // https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceDetectorOptions#public-static-final-int-contour_mode_all
  ALL = 2,
}

/**
 * Defines options for characterizing attributes such as "smiling" and "eyes open".
 * Processing time increases as extra classification to search.
 * https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceDetectorOptions.ClassificationMode
 *
 * @since 5.1.0
 */
export enum ClassificationMode {
  // Does not perform classification.
  // https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceDetectorOptions#public-static-final-int-classification_mode_none
  NONE = 1,
  // Performs "eyes open" and "smiling" classification.
  // https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceDetectorOptions#public-static-final-int-classification_mode_all
  ALL = 2,
}

/**
 * Represents a face detected by `FaceDetector`.
 * https://developers.google.com/android/reference/com/google/mlkit/vision/face/Face
 *
 * @since 5.1.0
 */
export interface Face {
  // Returns the axis-aligned bounding rectangle of the detected face.
  bounds: Rect;

  // Returns a list of face landmarks.
  landmarks?: FaceLandmark[];
  // Returns a list of face contours.
  contours?: FaceContour[];

  // Returns the tracking ID if the tracking is enabled.
  trackingId?: number;

  // Returns the rotation of the face about the horizontal axis of the image.
  // Positive euler X is the face is looking up.
  headEulerAngleX: number;
  // Returns the rotation of the face about the vertical axis of the image.
  // Positive euler y is when the face turns toward the right side of the image that is being processed.
  headEulerAngleY: number;
  // Returns the rotation of the face about the axis pointing out of the image.
  // Positive euler z is a counter-clockwise rotation within the image plane.
  headEulerAngleZ: number;

  // Returns a value between 0.0 and 1.0 giving a probability that the face is smiling.
  smilingProbability?: number;
  // Returns a value between 0.0 and 1.0 giving a probability that the face's left eye is open.
  leftEyeOpenProbability?: number;
  // Returns a value between 0.0 and 1.0 giving a probability that the face's right eye is open.
  rightEyeOpenProbability?: number;
}

/**
 * Rect holds four integer coordinates for a rectangle.
 * https://developer.android.com/reference/android/graphics/Rect.html
 *
 * @since 5.1.0
 */
export interface Rect {
  // The X coordinate of the left side of the rectangle
  left: number;
  // The Y coordinate of the top of the rectangle
  top: number;
  // The X coordinate of the right side of the rectangle
  right: number;
  // The Y coordinate of the bottom of the rectangle
  bottom: number;

  // The X coordinate of the left side of the rectangle
  x: number;
  // The Y coordinate of the top of the rectangle
  y: number;
  // The rectangle's width
  width: number;
  // The rectangle's height
  height: number;
}

/**
 * Represent a face landmark.
 * A landmark is a point on a detected face, such as an eye, nose, or mouth.
 * https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceLandmark
 *
 * @since 5.1.0
 */
export interface FaceLandmark {
  // Gets the FaceLandmark.LandmarkType type.
  type: LandmarkType;
  // Gets a 2D point for landmark position, where (0, 0) is the upper-left corner of the image.
  position: PointF;
}

/**
 * Represent a face contour.
 * A contour is a list of points on a detected face, such as the mouth.
 * https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceContour
 *
 * @since 5.1.0
 */
export interface FaceContour {
  // Gets the FaceContour.ContourType type.
  type: ContourType;
  // Gets a list of 2D points for this face contour, where (0, 0) is the upper-left corner of the image.
  points: PointF[];
}

/**
 * PointF holds two float coordinates
 * https://developer.android.com/reference/android/graphics/PointF.html
 *
 * @since 5.1.0
 */
export interface PointF {
  x: number;
  y: number;
}

/**
 * Landmark types for face.
 * https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceLandmark.LandmarkType
 *
 * @since 5.1.0
 */
export enum LandmarkType {
  // The center of the subject's bottom lip.
  MOUTH_BOTTOM = 0,
  // The midpoint between the subject's left mouth corner and the outer corner of the subject's left eye.
  // For full profile faces, this becomes the centroid of the nose base, nose tip, left ear lobe and left ear tip.
  LEFT_CHEEK = 1,
  // The midpoint of the subject's left ear tip and left ear lobe.
  LEFT_EAR = 3,
  // The center of the subject's left eye cavity.
  LEFT_EYE = 4,
  // The subject's left mouth corner where the lips meet.
  MOUTH_LEFT = 5,
  // The midpoint between the subject's nostrils where the nose meets the face.
  NOSE_BASE = 6,
  // The midpoint between the subject's right mouth corner and the outer corner of the subject's right eye.
  // For full profile faces, this becomes the centroid of the nose base, nose tip, right ear lobe and right ear tip.
  RIGHT_CHEEK = 7,
  // The midpoint of the subject's right ear tip and right ear lobe.
  RIGHT_EAR = 9,
  // The center of the subject's right eye cavity.
  RIGHT_EYE = 10,
  // The subject's right mouth corner where the lips meet.
  MOUTH_RIGHT = 11,
}

/**
 * Contour types for face.
 * https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceContour.ContourType
 *
 * @since 5.1.0
 */
export enum ContourType {
  // The outline of the subject's face.
  FACE = 1,
  // The top outline of the subject's left eyebrow.
  LEFT_EYEBROW_TOP = 2,
  // The bottom outline of the subject's left eyebrow.
  LEFT_EYEBROW_BOTTOM = 3,
  // The top outline of the subject's right eyebrow.
  RIGHT_EYEBROW_TOP = 4,
  // The bottom outline of the subject's right eyebrow.
  RIGHT_EYEBROW_BOTTOM = 5,
  // The outline of the subject's left eye cavity.
  LEFT_EYE = 6,
  // The outline of the subject's right eye cavity.
  RIGHT_EYE = 7,
  // The top outline of the subject's upper lip.
  UPPER_LIP_TOP = 8,
  // The bottom outline of the subject's upper lip.
  UPPER_LIP_BOTTOM = 9,
  // The top outline of the subject's lower lip.
  LOWER_LIP_TOP = 10,
  // The bottom outline of the subject's lower lip.
  LOWER_LIP_BOTTOM = 11,
  // The outline of the subject's nose bridge.
  NOSE_BRIDGE = 12,
  // The outline of the subject's nose bridge.
  NOSE_BOTTOM = 13,
  // The center of the left cheek.
  LEFT_CHEEK = 14,
  // The center of the right cheek.
  RIGHT_CHEEK = 15,
}
