import type { PluginListenerHandle } from '@capacitor/core';

/**
 * @since 8.2.0
 */
export interface GenAiPromptPlugin {
  /**
   * Check the current availability status of the prompt feature.
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
   * Download the prompt feature.
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
   * Generate content from a custom text-only or multimodal
   * (image and text) prompt.
   *
   * The `inferenceProgress` event listener will notify you about
   * partial results as they are generated. The returned promise
   * resolves with the full result.
   *
   * The output is open-ended generation from an on-device model
   * and is not moderated. The prompt is currently only validated
   * for English and Korean.
   *
   * Only available on Android.
   *
   * @since 8.2.0
   */
  generateContent(
    options: GenerateContentOptions,
  ): Promise<GenerateContentResult>;
  /**
   * Called while the prompt feature is being downloaded.
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
   * The current availability status of the prompt feature.
   *
   * @since 8.2.0
   * @example 'AVAILABLE'
   */
  featureStatus: FeatureStatus;
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
export interface GenerateContentOptions {
  /**
   * The local file path of an image to include in the prompt
   * for a multimodal (image and text) request.
   *
   * @since 8.2.0
   * @example 'file:///path/to/image.jpg'
   */
  imagePath?: string;
  /**
   * The maximum number of tokens that can be generated in the response.
   *
   * Use cases that require a long output (more than 256 tokens)
   * should be avoided.
   *
   * If not set, the default value of the model is used.
   *
   * @since 8.2.0
   * @example 256
   */
  maxOutputTokens?: number;
  /**
   * The prompt to generate content from.
   *
   * The input must be under 4,000 tokens (or about 3,000 English words).
   *
   * @since 8.2.0
   * @example 'Write a short poem about the sea.'
   */
  prompt: string;
  /**
   * The random seed used during inference. Using the same seed
   * with the same options produces deterministic results.
   *
   * If not set, the default value of the model is used.
   *
   * @since 8.2.0
   * @example 0
   */
  seed?: number;
  /**
   * Controls the randomness of the output. Lower values produce
   * more deterministic output, higher values produce more
   * creative output.
   *
   * If not set, the default value of the model is used.
   *
   * @since 8.2.0
   * @example 0.2
   */
  temperature?: number;
  /**
   * The number of highest-probability tokens that are considered
   * when sampling the next token.
   *
   * If not set, the default value of the model is used.
   *
   * @since 8.2.0
   * @example 16
   */
  topK?: number;
}

/**
 * @since 8.2.0
 */
export interface GenerateContentResult {
  /**
   * The generated text.
   *
   * @since 8.2.0
   * @example 'The sea is a vast expanse of blue...'
   */
  text: string;
}

/**
 * @since 8.2.0
 */
export interface InferenceProgressEvent {
  /**
   * The new text of the partial result.
   *
   * @since 8.2.0
   * @example 'The sea is a vast expanse of blue...'
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
