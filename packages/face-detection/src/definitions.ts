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
   * The classification mode for face detection.
   */
  classificationMode: ClassificationMode;
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
  faces: unknown[];
}

/**
 * @since 5.1.0
 */
export enum ClassificationMode {
  /**
   * Performs no classification.
   *
   * @since 5.1.0
   */
  None = 1,
  /**
   * Performs "eyes open" and "smiling" classification.
   *
   * @since 5.1.0
   */
  All = 2,
}
