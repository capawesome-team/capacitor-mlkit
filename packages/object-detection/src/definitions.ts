export interface ObjectDetectionPlugin {
  /**
   * Detects objects in the supplied image.
   *
   * Each detected object is localized with a bounding box and, if classification
   * is enabled, labeled with one of five coarse categories.
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
   * Whether to classify the detected objects into coarse categories.
   *
   * Classification returns only five coarse categories (`Fashion good`, `Food`,
   * `Home good`, `Place` and `Plant`).
   *
   * @since 8.2.0
   * @default false
   */
  shouldEnableClassification?: boolean;
  /**
   * Whether to detect multiple objects instead of only the most prominent one.
   *
   * When enabled, up to five objects are detected.
   *
   * @since 8.2.0
   * @default false
   */
  shouldEnableMultipleObjects?: boolean;
}

/**
 * @since 8.2.0
 */
export interface ProcessImageResult {
  /**
   * The detected objects.
   *
   * @since 8.2.0
   */
  detectedObjects: DetectedObject[];
}

/**
 * Represents an object detected in an image.
 *
 * @since 8.2.0
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/objects/DetectedObject
 */
export interface DetectedObject {
  /**
   * The bounding box of the detected object.
   *
   * @since 8.2.0
   */
  boundingBox: Rect;
  /**
   * The tracking identifier of the detected object.
   *
   * Only set when the object is detected in stream mode.
   *
   * @since 8.2.0
   */
  trackingId?: number;
  /**
   * The labels of the detected object.
   *
   * The list is empty if classification is disabled.
   *
   * @since 8.2.0
   */
  labels: ObjectLabel[];
}

/**
 * Represents a label of a detected object.
 *
 * @since 8.2.0
 */
export interface ObjectLabel {
  /**
   * The index of the label.
   *
   * @since 8.2.0
   */
  index: number;
  /**
   * The text of the label (e.g. `Food`).
   *
   * The text is always in English.
   *
   * @since 8.2.0
   */
  text: string;
  /**
   * The confidence of the label.
   *
   * The value is between `0.0` and `1.0`.
   *
   * @since 8.2.0
   */
  confidence: number;
}

/**
 * Represents a rectangle.
 *
 * @since 8.2.0
 */
export interface Rect {
  /**
   * The left coordinate of the rectangle in pixels.
   *
   * @since 8.2.0
   */
  left: number;
  /**
   * The top coordinate of the rectangle in pixels.
   *
   * @since 8.2.0
   */
  top: number;
  /**
   * The right coordinate of the rectangle in pixels.
   *
   * @since 8.2.0
   */
  right: number;
  /**
   * The bottom coordinate of the rectangle in pixels.
   *
   * @since 8.2.0
   */
  bottom: number;
}
