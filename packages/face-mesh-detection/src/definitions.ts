export interface FaceMeshDetectionPlugin {
  /**
   * Detects face mesh from the supplied image.
   *
   * Only available on Android.
   *
   * @since 5.3.0
   */
  processImage(options: ProcessImageOptions): Promise<ProcessImageResult>;
}

/**
 * @since 5.3.0
 */
export interface ProcessImageOptions {
  /**
   * The local path to the image file.
   *
   * @since 5.3.0
   */
  path: string;

  /**
   * Sets the use case.
   *
   * When `BoundingBoxOnly` is selected, the returned `FaceMesh` only contains bounding box.
   *
   * When `FaceMesh` is selected, the returned `FaceMesh` contains bounding box as well as 468 `FaceMeshPoint` and triangle information.
   * It detects at most 2 faces in this case and it is slower than `BoundingBoxOnly`.
   *
   * @since 5.3.0
   * @default UseCase.FaceMesh
   * @see https://developers.google.com/android/reference/com/google/mlkit/vision/facemesh/FaceMeshDetectorOptions.UseCase
   */
  useCase?: UseCase;
}

/**
 * @since 5.3.0
 */
export interface ProcessImageResult {
  /**
   * The detected face meshs.
   *
   * @since 5.3.0
   */
  faceMeshs: FaceMesh[];
}

/**
 * Defines options for main use case.
 *
 * @since 5.3.0
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/facemesh/FaceMeshDetectorOptions.UseCase
 */
export enum UseCase {
  /**
   * Return bounding box for detected face.
   *
   * @since 5.3.0
   * @see https://developers.google.com/android/reference/com/google/mlkit/vision/facemesh/FaceMeshDetectorOptions#public-static-final-int-bounding_box_only
   */
  BoundingBoxOnly = 0,
  /**
   * Return face mesh info for detected face.
   * It returns at most 2 `FaceMesh`s in this use case.
   *
   * @since 5.3.0
   * @see https://developers.google.com/android/reference/com/google/mlkit/vision/facemesh/FaceMeshDetectorOptions#public-static-final-int-face_mesh
   */
  FaceMesh = 1,
}

/**
 * Represents a face mesh detected by `FaceMeshDetector`.
 *
 * When `BoundingBoxOnly` is selected, `FaceMesh` only contains valid bounding box.
 *
 * When `FaceMesh` is selected, `FaceMesh` also contains a group of 468 3D face mesh points and related triangle information.
 * Each point is represented by `FaceMeshPoint` describing a specific position in detected face.
 * The triangle information is a group of 3 `FaceMeshPoint`s representing a valid surface on Face (e.g. a valid small surface on nose tip).
 *
 * @since 5.3.0
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/face/Face
 */
export interface FaceMesh {
  /**
   * Returns the axis-aligned bounding rectangle of the detected face mesh.
   *
   * @since 5.3.0
   */
  bounds: Rect;

  /**
   * Returns a list of `FaceMeshPoint` representing the whole detected face.
   *
   * @since 5.3.0
   */
  faceMeshPoints?: FaceMeshPoint[];
  /**
   * Returns a list of `Triangle` representing logical triangle surfaces of detected face.
   *
   * Each `Triangle` contains 3 `FaceMeshPoint`, representing 3 points of the triangle surface.
   * The sequence of the 3 points are constant and always counter clockwise in face mesh.
   *
   * @since 5.3.0
   */
  triangles?: Triangle[];
}

/**
 * Rect holds four integer coordinates for a rectangle.
 *
 * @since 5.3.0
 * @see https://developer.android.com/reference/android/graphics/Rect.html
 */
export interface Rect {
  /**
   * The X coordinate of the left side of the rectangle
   *
   * @since 5.3.0
   */
  left: number;
  /**
   * The Y coordinate of the top of the rectangle
   *
   * @since 5.3.0
   */
  top: number;
  /**
   * The X coordinate of the right side of the rectangle
   *
   * @since 5.3.0
   */
  right: number;
  /**
   * The Y coordinate of the bottom of the rectangle
   *
   * @since 5.3.0
   */
  bottom: number;
}

/**
 * Represents a 3D point in face mesh.
 *
 * The index is an unique ID meaning a fixed position on face, ranging from 0 to 467.
 *
 * In `Point3D`, `x` and `y` are pixel location of detected face in `InputImage`.
 * `z` is also scaled to image size, while the origin will be somewhere in the center of all 468 face mesh points.
 *
 * @since 5.3.0
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/facemesh/FaceMeshPoint
 */
export interface FaceMeshPoint {
  /**
   * Gets the index of the face mesh point, ranging from 0 to 467.
   * For each specific point, the index is a constant value.
   *
   * @since 5.3.0
   */
  index: number;
  /**
   * Gets a 3D point in face mesh.
   *
   * Inside `Point3D`, `X` and `Y` means a 2D position in original image.
   *
   * More information on the `Z` value:
   * - The unit of measure for the `Z` value is the same as `X` and `Y`.
   * - The smaller the `Z` value, the closer that landmark is to the camera.
   * - The `Z` origin is approximately at the center of all 468 face mesh points.
   *   `Z` value will be negative if the point is close to camera and will be positive if the point is away from the camera.
   *
   * @since 5.3.0
   */
  position: Point3D[];
}

/**
 * Represents a 3D point.
 *
 * @since 5.3.0
 */
export interface Point3D {
  /**
   * Returns the X value of the point.
   *
   * @since 5.3.0
   */
  x: number;
  /**
   * Returns the Y value of the point.
   *
   * @since 5.3.0
   */
  y: number;
  /**
   * Returns the Z value of the point.
   *
   * @since 5.3.0
   */
  z: number;
}

/**
 * Represents a triangle with 3 generic points.
 *
 * @since 5.3.0
 */
export interface Triangle {
  /**
   * Returns all points inside the `Triangle`.
   *
   * @since 5.3.0
   */
  points: FaceMeshPoint[];
}

/**
 * Type of face contour.
 *
 * @since 5.3.0
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/facemesh/FaceMesh.ContourType
 */
export enum ContourType {
  /**
   * The outline of the subject's face.
   *
   * @since 5.3.0
   */
  FaceOval = 1,
  /**
   * The outline of the subject's left eye cavity.
   *
   * @since 5.3.0
   */
  LeftEye = 6,
  /**
   * The bottom outline of the subject's left eyebrow.
   *
   * @since 5.3.0
   */
  LeftEyebrowBottom = 3,
  /**
   * The top outline of the subject's left eyebrow.
   *
   * @since 5.3.0
   */
  LeftEyebrowTop = 2,
  /**
   * The bottom outline of the subject's lower lip.
   *
   * @since 5.3.0
   */
  LowerLipBottom = 11,
  /**
   * The top outline of the subject's lower lip.
   *
   * @since 5.3.0
   */
  LowerLipTop = 10,
  /**
   * The outline of the subject's nose bridge.
   *
   * @since 5.3.0
   */
  NoseBridge = 12,
  /**
   * The outline of the subject's right eye cavity.
   *
   * @since 5.3.0
   */
  RightEye = 7,
  /**
   * The bottom outline of the subject's right eyebrow.
   *
   * @since 5.3.0
   */
  RightEyebrowBottom = 5,
  /**
   * The top outline of the subject's right eyebrow.
   *
   * @since 5.3.0
   */
  RightEyebrowTop = 4,
  /**
   * The bottom outline of the subject's upper lip.
   *
   * @since 5.3.0
   */
  UpperLipBottom = 9,
  /**
   * The top outline of the subject's upper lip.
   *
   * @since 5.3.0
   */
  UpperLipTop = 8,
}
