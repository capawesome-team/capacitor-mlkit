export interface SelfieSegmentationPlugin {
  /**
   * Performs segmentation on an input image.
   *
   * Only available on Android and iOS.
   *
   * @since 5.2.0
   */
  processImage(options: ProcessImageOptions): Promise<ProcessImageResult>;
}

/**
 * @since 5.2.0
 */
export interface ProcessImageOptions {
  /**
   * The local path to the image file.
   *
   * @since 5.2.0
   */
  path: string;

  /**
   * Asks the segmenter to return the raw size mask which matches the model output size.
   *
   * @since 5.2.0
   */
  rawSizeMask?: boolean;
}

/**
 * @since 5.2.0
 * @see https://developers.google.com/android/reference/com/google/mlkit/vision/segmentation/SegmentationMask
 */
export interface ProcessImageResult {
  /**
   * Returns a mask that indicates the foreground and background segmentation.
   *
   * This mask’s dimensions could vary, depending on whether a raw size mask is requested via options.
   *
   * @since 5.2.0
   */
  mask: number[];

  /**
   * Returns the width of the mask.
   *
   * @since 5.2.0
   */
  width: number;
  /**
   * Returns the height of the mask.
   *
   * @since 5.2.0
   */
  height: number;
}
