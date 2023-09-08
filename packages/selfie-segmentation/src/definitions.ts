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
   * Scale the image to this width.
   *
   * @since 5.2.0
   */
  width?: number;

  /**
   * Scale the image to this height.
   *
   * @since 5.2.0
   */
  height?: number;
}

/**
 * @since 5.2.0
 */
export interface ProcessImageResult {
  /**
   * The path to the segmented image file.
   *
   * @since 5.2.0
   */
  path: string;

  /**
   * Returns the width of the image file.
   *
   * @since 5.2.0
   */
  width: number;
  /**
   * Returns the height of the image file.
   *
   * @since 5.2.0
   */
  height: number;
}
