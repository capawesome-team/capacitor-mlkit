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
export interface ProcessImageOptions {
  /**
   * The local path to the image file.
   *
   * @since 5.1.0
   */
  path: string;
  /**
   * Defines options to control accuracy / speed trade-offs in performing face detection.
   *
   * @since 5.1.0
   * @default PerformanceMode.Fast
   * @see https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceDetectorOptions.PerformanceMode
   */
  performanceMode?: PerformanceMode;
  /**
   * Defines options to enable face landmarks or not.
   *
   * @since 5.1.0
   * @default LandmarkMode.None
   * @see https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceDetectorOptions.LandmarkMode
   */
  landmarkMode?: LandmarkMode;
  /**
   * Defines options to enable face contours or not.
   *
   * @since 5.1.0
   * @default ContourMode.None
   * @see https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceDetectorOptions.ContourMode
   */
  contourMode?: ContourMode;
  /**
   * Defines options for characterizing attributes such as "smiling" * and "eyes open".
   *
   * @since 5.1.0
   * @default ClassificationMode.None
   * @see https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceDetectorOptions.ClassificationMode
   */
  classificationMode?: ClassificationMode;
  /**
   * Sets the smallest desired face size, expressed as a proportion of the width of the head to the image width.
   *
   * @since 5.1.0
   * @default 0.1
   * @see https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceDetectorOptions.Builder#public-facedetectoroptions.builder-setminfacesize-float-minfacesize
   */
  minFaceSize?: number;
  /**
   * Enables face tracking, which will maintain a consistent ID for each face when processing consecutive frames.
   * Tracking should be disabled for handling a series of non-consecutive still images.
   *
   * @since 5.1.0
   * @default false
   * @see https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceDetectorOptions.Builder#public-facedetectoroptions.builder-enabletracking
   */
  enableTracking?: boolean;
}

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
 * Defines options to control accuracy / speed trade-offs in performing face detection.
 * In general, choosing the more accurate mode will generally result in longer runtime, whereas choosing the faster mode will generally result in detecting fewer faces.
 *
 * @since 5.1.0
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceDetectorOptions.PerformanceMode
 */
export enum PerformanceMode {
  /**
   * Indicates a preference for speed in the options that may make an accuracy vs. speed trade-off.
   * This will tend to detect fewer faces and may be less precise in determining values such as position, but will run faster.
   *
   * @since 5.1.0
   * @see https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceDetectorOptions#PERFORMANCE_MODE_FAST
   */
  Fast = 1,
  /**
   * Indicates a preference for accuracy in the options that may make an accuracy vs. speed trade-off.
   * This will tend to detect more faces and may be more precise in determining values such as position, at the cost of speed.
   *
   * @since 5.1.0
   * @see https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceDetectorOptions#public-static-final-int-performance_mode_accurate
   */
  Accurate = 2,
}

/**
 * Defines options to enable face landmarks or not.
 * Processing time increases as the extra face landmark to search.
 *
 * @since 5.1.0
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceDetectorOptions.LandmarkMode
 */
export enum LandmarkMode {
  /**
   * Does not perform landmark detection.
   *
   * @since 5.1.0
   * @see https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceDetectorOptions#public-static-final-int-landmark_mode_none
   */
  None = 1,
  /**
   * Detects FaceLandmark for a given face.
   *
   * @since 5.1.0
   * @see https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceDetectorOptions#public-static-final-int-landmark_mode_all
   */
  All = 2,
}

/**
 * Defines options to enable face contours or not.
 * Processing time increases as the number of contours to search.
 *
 * @since 5.1.0
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceDetectorOptions.ContourMode
 */
export enum ContourMode {
  /**
   * Does not perform contour detection.
   *
   * @since 5.1.0
   * @see https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceDetectorOptions#public-static-final-int-contour_mode_none
   */
  None = 1,
  /**
   * Detects FaceContour for a given face.
   * Note that it would return contours for up to 5 faces
   *
   * @since 5.1.0
   * @see https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceDetectorOptions#public-static-final-int-contour_mode_all
   */
  All = 2,
}

/**
 * Defines options for characterizing attributes such as "smiling" and "eyes open".
 * Processing time increases as extra classification to search.
 *
 * @since 5.1.0
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceDetectorOptions.ClassificationMode
 */
export enum ClassificationMode {
  /**
   * Does not perform classification.
   *
   * @since 5.1.0
   * @see https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceDetectorOptions#public-static-final-int-classification_mode_none
   */
  None = 1,
  /**
   * Performs "eyes open" and "smiling" classification.
   *
   * @since 5.1.0
   * @see https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceDetectorOptions#public-static-final-int-classification_mode_all
   */
  All = 2,
}

/**
 * Represents a face detected by `FaceDetector`.
 *
 * @since 5.1.0
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/face/Face
 */
export interface Face {
  /**
   * Returns the axis-aligned bounding rectangle of the detected face.
   *
   * @since 5.1.0
   */
  bounds: Rect;

  /**
   * Returns a list of face landmarks.
   *
   * @since 5.1.0
   */
  landmarks?: FaceLandmark[];
  /**
   * Returns a list of face contours.
   *
   * @since 5.1.0
   */
  contours?: FaceContour[];

  /**
   * Returns the tracking ID if the tracking is enabled.
   *
   * @since 5.1.0
   */
  trackingId?: number;

  /**
   * Returns the rotation of the face about the horizontal axis of the image.
   * Positive euler X is the face is looking up.
   *
   * @since 5.1.0
   */
  headEulerAngleX: number;
  /**
   * Returns the rotation of the face about the vertical axis of the image.
   * Positive euler y is when the face turns toward the right side of the image that is being processed.
   *
   * @since 5.1.0
   */
  headEulerAngleY: number;
  /**
   * Returns the rotation of the face about the axis pointing out of the image.
   * Positive euler z is a counter-clockwise rotation within the image plane.
   *
   * @since 5.1.0
   */
  headEulerAngleZ: number;

  /**
   * Returns a value between 0.0 and 1.0 giving a probability that the face is smiling.
   *
   * @since 5.1.0
   */
  smilingProbability?: number;
  /**
   * Returns a value between 0.0 and 1.0 giving a probability that the face's left eye is open.
   *
   * @since 5.1.0
   */
  leftEyeOpenProbability?: number;
  /**
   * Returns a value between 0.0 and 1.0 giving a probability that the face's right eye is open.
   *
   * @since 5.1.0
   */
  rightEyeOpenProbability?: number;
}

/**
 * Rect holds four integer coordinates for a rectangle.
 *
 * @since 5.1.0
 * @see https://developer.android.com/reference/android/graphics/Rect.html
 */
export interface Rect {
  /**
   * The X coordinate of the left side of the rectangle.
   *
   * @since 5.1.0
   */
  left: number;
  /**
   * The Y coordinate of the top of the rectangle.
   *
   * @since 5.1.0
   */
  top: number;
  /**
   * The X coordinate of the right side of the rectangle.
   *
   * @since 5.1.0
   */
  right: number;
  /**
   * The Y coordinate of the bottom of the rectangle.
   *
   * @since 5.1.0
   */
  bottom: number;
}

/**
 * Represent a face landmark.
 * A landmark is a point on a detected face, such as an eye, nose, or mouth.
 *
 * @since 5.1.0
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceLandmark
 */
export interface FaceLandmark {
  /**
   * Gets the FaceLandmark.LandmarkType type.
   *
   * @since 5.1.0
   */
  type: LandmarkType;
  /**
   * Gets a 2D point for landmark position, where (0, 0) is the upper-left corner of the image.
   *
   * @since 5.1.0
   */
  position: Point;
}

/**
 * Represent a face contour.
 * A contour is a list of points on a detected face, such as the mouth.
 *
 * @since 5.1.0
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceContour
 */
export interface FaceContour {
  /**
   * Gets the FaceContour.ContourType type.
   *
   * @since 5.1.0
   */
  type: ContourType;
  /**
   * Gets a list of 2D points for this face contour, where (0, 0) is the upper-left corner of the image.
   *
   * @since 5.1.0
   */
  points: Point[];
}

/**
 * Point holds two coordinates
 *
 * @since 5.1.0
 */
export interface Point {
  x: number;
  y: number;
}

/**
 * Landmark types for face.
 *
 * @since 5.1.0
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceLandmark.LandmarkType
 */
export enum LandmarkType {
  /**
   * The center of the subject's bottom lip.
   *
   * @since 5.1.0
   */
  MouthBottom = 0,
  /**
   * The midpoint between the subject's left mouth corner and the outer corner of the subject's left eye.
   * For full profile faces, this becomes the centroid of the nose base, nose tip, left ear lobe and left ear tip.
   *
   * @since 5.1.0
   */
  LeftCheek = 1,
  /**
   * The midpoint of the subject's left ear tip and left ear lobe.
   *
   * @since 5.1.0
   */
  LeftEar = 3,
  /**
   * The center of the subject's left eye cavity.
   *
   * @since 5.1.0
   */
  LeftEye = 4,
  /**
   * The subject's left mouth corner where the lips meet.
   *
   * @since 5.1.0
   */
  MouthLeft = 5,
  /**
   * The midpoint between the subject's nostrils where the nose meets the face.
   *
   * @since 5.1.0
   */
  NoseBase = 6,
  /**
   * The midpoint between the subject's right mouth corner and the outer corner of the subject's right eye.
   * For full profile faces, this becomes the centroid of the nose base, nose tip, right ear lobe and right ear tip.
   *
   * @since 5.1.0
   */
  RightCheek = 7,
  /**
   * The midpoint of the subject's right ear tip and right ear lobe.
   *
   * @since 5.1.0
   */
  RightEar = 9,
  /**
   * The center of the subject's right eye cavity.
   *
   * @since 5.1.0
   */
  RightEye = 10,
  /**
   * The subject's right mouth corner where the lips meet.
   *
   * @since 5.1.0
   */
  MouthRight = 11,
}

/**
 * Contour types for face.
 *
 * @since 5.1.0
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceContour.ContourType
 */
export enum ContourType {
  /**
   * The outline of the subject's face.
   *
   * @since 5.1.0
   */
  Face = 1,
  /**
   * The top outline of the subject's left eyebrow.
   *
   * @since 5.1.0
   */
  LeftEyebrowTop = 2,
  /**
   * The bottom outline of the subject's left eyebrow.
   *
   * @since 5.1.0
   */
  LeftEyebrowBottom = 3,
  /**
   * The top outline of the subject's right eyebrow.
   *
   * @since 5.1.0
   */
  RightEyebrowTop = 4,
  /**
   * The bottom outline of the subject's right eyebrow.
   *
   * @since 5.1.0
   */
  RightEyebrowBottom = 5,
  /**
   * The outline of the subject's left eye cavity.
   *
   * @since 5.1.0
   */
  LeftEye = 6,
  /**
   * The outline of the subject's right eye cavity.
   *
   * @since 5.1.0
   */
  RightEye = 7,
  /**
   * The top outline of the subject's upper lip.
   *
   * @since 5.1.0
   */
  UpperLipTop = 8,
  /**
   * The bottom outline of the subject's upper lip.
   *
   * @since 5.1.0
   */
  UpperLipBottom = 9,
  /**
   * The top outline of the subject's lower lip.
   *
   * @since 5.1.0
   */
  LowerLipTop = 10,
  /**
   * The bottom outline of the subject's lower lip.
   *
   * @since 5.1.0
   */
  LowerLipBottom = 11,
  /**
   * The outline of the subject's nose bridge.
   *
   * @since 5.1.0
   */
  NoseBridge = 12,
  /**
   * The outline of the subject's nose bridge.
   *
   * @since 5.1.0
   */
  NoseBottom = 13,
  /**
   * The center of the left cheek.
   *
   * @since 5.1.0
   */
  LeftCheek = 14,
  /**
   * The center of the right cheek.
   *
   * @since 5.1.0
   */
  RightCheek = 15,
}
