import type { PluginListenerHandle } from '@capacitor/core';

/**
 * @since 8.2.0
 */
export interface GenAiImageDescriptionPlugin {
  /**
   * Check the current availability status of the image description feature.
   *
   * If the status is `DOWNLOADABLE`, you can download the feature
   * using `downloadFeature(...)`.
   *
   * Only available on Android.
   *
   * @since 8.2.0
   */
  checkFeatureStatus(): Promise<CheckFeatureStatusResult>;
  /**
   * Generate a natural-language description of the given image.
   *
   * The `inferenceProgress` event listener will notify you about
   * partial results as they are generated. The returned promise
   * resolves with the full result.
   *
   * The description is currently only available in English.
   *
   * Only available on Android.
   *
   * @since 8.2.0
   */
  describeImage(options: DescribeImageOptions): Promise<DescribeImageResult>;
  /**
   * Download the image description feature.
   *
   * The `downloadProgress` event listener will notify you about
   * the download progress. The returned promise resolves when
   * the download is complete or the feature is already available.
   *
   * Only available on Android.
   *
   * @since 8.2.0
   */
  downloadFeature(): Promise<void>;
  /**
   * Called while the image description feature is being downloaded.
   *
   * Only available on Android.
   *
   * @since 8.2.0
   */
  addListener(
    eventName: 'downloadProgress',
    listenerFunc: (event: DownloadProgressEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when a new partial result is available during inference.
   *
   * Only available on Android.
   *
   * @since 8.2.0
   */
  addListener(
    eventName: 'inferenceProgress',
    listenerFunc: (event: InferenceProgressEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Remove all listeners for this plugin.
   *
   * @since 8.2.0
   */
  removeAllListeners(): Promise<void>;
}

/**
 * @since 8.2.0
 */
export interface CheckFeatureStatusResult {
  /**
   * The current availability status of the image description feature.
   *
   * @since 8.2.0
   * @example 'AVAILABLE'
   */
  featureStatus: FeatureStatus;
}

/**
 * @since 8.2.0
 */
export interface DescribeImageOptions {
  /**
   * The local file path of the image to describe.
   *
   * @since 8.2.0
   * @example 'file:///path/to/image.jpg'
   */
  path: string;
}

/**
 * @since 8.2.0
 */
export interface DescribeImageResult {
  /**
   * The natural-language description of the image.
   *
   * @since 8.2.0
   * @example 'A dog running on a beach.'
   */
  description: string;
}

/**
 * @since 8.2.0
 */
export interface DownloadProgressEvent {
  /**
   * The total number of bytes downloaded so far.
   *
   * @since 8.2.0
   * @example 1024
   */
  totalBytesDownloaded: number;
}

/**
 * @since 8.2.0
 */
export interface InferenceProgressEvent {
  /**
   * The new text of the partial result.
   *
   * @since 8.2.0
   * @example 'A dog running on a beach.'
   */
  text: string;
}

/**
 * @since 8.2.0
 */
export enum FeatureStatus {
  /**
   * The feature is available to use.
   *
   * @since 8.2.0
   */
  Available = 'AVAILABLE',
  /**
   * The feature can be downloaded using `downloadFeature(...)`.
   *
   * @since 8.2.0
   */
  Downloadable = 'DOWNLOADABLE',
  /**
   * The feature is currently being downloaded.
   *
   * @since 8.2.0
   */
  Downloading = 'DOWNLOADING',
  /**
   * The feature is not available on this device.
   *
   * @since 8.2.0
   */
  Unavailable = 'UNAVAILABLE',
}
