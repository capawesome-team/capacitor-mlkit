import type { PluginListenerHandle } from '@capacitor/core';

export interface SubjectSegmentationPlugin {
  /**
   * Performs segmentation on an input image.
   *
   * Only available on Android and iOS.
   *
   * @since 7.2.0
   */
  processImage(options: ProcessImageOptions): Promise<ProcessImageResult>;

  /**
   * Check if the Google Subject Segmentation module is available.
   *
   * If the Google Subject Segmentation module is not available, you can install it by using `installGoogleSubjectSegmentationModule()`.
   *
   * Only available on Android.
   *
   * @since 7.2.0
   */
  isGoogleSubjectSegmentationModuleAvailable(): Promise<IsGoogleSubjectSegmentationModuleAvailableResult>;
  /**
   * Install the Google Subject Segmentation module.
   *
   * **Attention**: This only starts the installation.
   * The `googleSubjectSegmentationModuleInstallProgress` event listener will
   * notify you when the installation is complete.
   *
   * Only available on Android.
   *
   * @since 7.2.0
   */
  installGoogleSubjectSegmentationModule(): Promise<void>;

  /**
   * Called when the Google Subject Segmentation module is installed.
   *
   * Only available on Android.
   *
   * @since 7.2.0
   */
  addListener(
    eventName: 'googleSubjectSegmentationModuleInstallProgress',
    listenerFunc: (
      event: GoogleSubjectSegmentationModuleInstallProgressEvent,
    ) => void,
  ): Promise<PluginListenerHandle>;

  /**
   * Remove all listeners for this plugin.
   *
   * Only available on Android.
   *
   * @since 7.2.0
   */
  removeAllListeners(): Promise<void>;
}

/**
 * @since 7.2.0
 */
export interface ProcessImageOptions {
  /**
   * The local path to the image file.
   *
   * @since 7.2.0
   */
  path: string;

  /**
   * Scale the image to this width.
   * If no `height` is given, it will respect the aspect ratio.
   *
   * @since 7.2.0
   */
  width?: number;
  /**
   * Scale the image to this height.
   * If no `width` is given, it will respect the aspect ratio.
   *
   * @since 7.2.0
   */
  height?: number;

  /**
   * Sets the confidence threshold.
   *
   * @since 7.2.0
   * @default 0.9
   */
  confidence?: number;
}

/**
 * @since 7.2.0
 */
export interface ProcessImageResult {
  /**
   * The path to the segmented image file.
   *
   * @since 7.2.0
   */
  path: string;
  /**
   * Returns the width of the image file.
   *
   * @since 7.2.0
   */
  width: number;
  /**
   * Returns the height of the image file.
   *
   * @since 7.2.0
   */
  height: number;
}

/**
 * @since 7.2.0
 */
export interface IsGoogleSubjectSegmentationModuleAvailableResult {
  /**
   * Whether or not the Google Subject Segmentation module is available.
   *
   * @since 7.2.0
   */
  available: boolean;
}

/**
 * @since 7.2.0
 */
export interface GoogleSubjectSegmentationModuleInstallProgressEvent {
  /**
   * The current state of the installation.
   *
   * @since 7.2.0
   */
  state: GoogleSubjectSegmentationModuleInstallState;
  /**
   * The progress of the installation in percent between 0 and 100.
   *
   * @since 7.2.0
   */
  progress?: number;
}

/**
 * @since 7.2.0
 */
export enum GoogleSubjectSegmentationModuleInstallState {
  /**
   * @since 7.2.0
   */
  UNKNOWN = 0,
  /**
   * @since 7.2.0
   */
  PENDING = 1,
  /**
   * @since 7.2.0
   */
  DOWNLOADING = 2,
  /**
   * @since 7.2.0
   */
  CANCELED = 3,
  /**
   * @since 7.2.0
   */
  COMPLETED = 4,
  /**
   * @since 7.2.0
   */
  FAILED = 5,
  /**
   * @since 7.2.0
   */
  INSTALLING = 6,
  /**
   * @since 7.2.0
   */
  DOWNLOAD_PAUSED = 7,
}
