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
   * If no `height` is given, it will respect the aspect ratio.
   *
   * @since 5.2.0
   */
  width?: number;
  /**
   * Scale the image to this height.
   * If no `width` is given, it will respect the aspect ratio.
   *
   * @since 5.2.0
   */
  height?: number;

  /**
   * Sets the confidence threshold.
   *
   * @since 5.2.0
   * @default 0.9
   */
  confidence?: number;
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
