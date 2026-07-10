export interface ImageLabelingPlugin {
  /**
   * Detects labels in the supplied image.
   *
   * Labels can describe objects, locations, activities, animal species, products and more.
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
   * The confidence threshold for the labels.
   *
   * Only labels with a confidence greater than or equal to this value are returned.
   *
   * The value must be between `0.0` and `1.0`.
   *
   * @since 8.2.0
   * @default 0.5
   */
  confidenceThreshold?: number;
}

/**
 * @since 8.2.0
 */
export interface ProcessImageResult {
  /**
   * The detected labels.
   *
   * @since 8.2.0
   */
  labels: ImageLabel[];
}

/**
 * Represents a label detected in an image.
 *
 * @since 8.2.0
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/label/ImageLabel
 */
export interface ImageLabel {
  /**
   * The index of the label within the model.
   *
   * @since 8.2.0
   */
  index: number;
  /**
   * The text of the label (e.g. `Bicycle`).
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
