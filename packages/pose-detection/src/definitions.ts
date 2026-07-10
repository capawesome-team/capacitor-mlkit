export interface PoseDetectionPlugin {
  /**
   * Detects the pose of a person in the supplied image.
   *
   * The API detects the pose of a single (most prominent) person and returns
   * all 33 skeletal landmarks with their positions.
   *
   * Only available on Android and iOS.
   *
   * @since 8.2.0
   */
  processImage(options: ProcessImageOptions): Promise<ProcessImageResult>;
}

/**
 * @since 8.2.0
 */
export interface ProcessImageOptions {
  /**
   * The local path to the image file.
   *
   * @since 8.2.0
   */
  path: string;
  /**
   * The performance mode of the pose detector.
   *
   * @since 8.2.0
   * @default PerformanceMode.Base
   */
  performanceMode?: PerformanceMode;
}

/**
 * @since 8.2.0
 */
export enum PerformanceMode {
  /**
   * The base model is faster but less accurate.
   *
   * @since 8.2.0
   */
  Base = 'BASE',
  /**
   * The accurate model is slower but more accurate.
   *
   * @since 8.2.0
   */
  Accurate = 'ACCURATE',
}

/**
 * @since 8.2.0
 */
export interface ProcessImageResult {
  /**
   * The detected poses.
   *
   * The array contains either zero or one pose, because the API only detects
   * the pose of a single person.
   *
   * @since 8.2.0
   */
  poses: Pose[];
}

/**
 * Represents a pose detected in an image.
 *
 * @since 8.2.0
 */
export interface Pose {
  /**
   * The detected landmarks of the pose.
   *
   * The array always contains all 33 landmarks.
   *
   * @since 8.2.0
   */
  landmarks: PoseLandmark[];
}

/**
 * Represents a landmark of a detected pose.
 *
 * @since 8.2.0
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/pose/PoseLandmark
 */
export interface PoseLandmark {
  /**
   * The type of the landmark (e.g. `NOSE`).
   *
   * @since 8.2.0
   */
  type: PoseLandmarkType;
  /**
   * The x coordinate of the landmark in the image in pixels.
   *
   * @since 8.2.0
   */
  x: number;
  /**
   * The y coordinate of the landmark in the image in pixels.
   *
   * @since 8.2.0
   */
  y: number;
  /**
   * The z coordinate of the landmark.
   *
   * The value represents the depth of the landmark. The smaller the value,
   * the closer the landmark is to the camera. The origin is the midpoint
   * between the hips.
   *
   * @since 8.2.0
   */
  z: number;
  /**
   * The likelihood that the landmark is within the image frame.
   *
   * The value is between `0.0` and `1.0`.
   *
   * @since 8.2.0
   */
  inFrameLikelihood: number;
}

/**
 * The type of a pose landmark.
 *
 * @since 8.2.0
 */
export enum PoseLandmarkType {
  Nose = 'NOSE',
  LeftEyeInner = 'LEFT_EYE_INNER',
  LeftEye = 'LEFT_EYE',
  LeftEyeOuter = 'LEFT_EYE_OUTER',
  RightEyeInner = 'RIGHT_EYE_INNER',
  RightEye = 'RIGHT_EYE',
  RightEyeOuter = 'RIGHT_EYE_OUTER',
  LeftEar = 'LEFT_EAR',
  RightEar = 'RIGHT_EAR',
  LeftMouth = 'LEFT_MOUTH',
  RightMouth = 'RIGHT_MOUTH',
  LeftShoulder = 'LEFT_SHOULDER',
  RightShoulder = 'RIGHT_SHOULDER',
  LeftElbow = 'LEFT_ELBOW',
  RightElbow = 'RIGHT_ELBOW',
  LeftWrist = 'LEFT_WRIST',
  RightWrist = 'RIGHT_WRIST',
  LeftPinky = 'LEFT_PINKY',
  RightPinky = 'RIGHT_PINKY',
  LeftIndex = 'LEFT_INDEX',
  RightIndex = 'RIGHT_INDEX',
  LeftThumb = 'LEFT_THUMB',
  RightThumb = 'RIGHT_THUMB',
  LeftHip = 'LEFT_HIP',
  RightHip = 'RIGHT_HIP',
  LeftKnee = 'LEFT_KNEE',
  RightKnee = 'RIGHT_KNEE',
  LeftAnkle = 'LEFT_ANKLE',
  RightAnkle = 'RIGHT_ANKLE',
  LeftHeel = 'LEFT_HEEL',
  RightHeel = 'RIGHT_HEEL',
  LeftFootIndex = 'LEFT_FOOT_INDEX',
  RightFootIndex = 'RIGHT_FOOT_INDEX',
}
