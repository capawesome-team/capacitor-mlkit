import type { PluginListenerHandle } from '@capacitor/core';

/**
 * @since 8.2.0
 */
export interface GenAiRewritingPlugin {
  /**
   * Check the current availability status of the rewriting feature.
   *
   * If the status is `DOWNLOADABLE`, you can download the feature
   * using `downloadFeature(...)`.
   *
   * Only available on Android.
   *
   * @since 8.2.0
   */
  checkFeatureStatus(
    options?: FeatureOptions,
  ): Promise<CheckFeatureStatusResult>;
  /**
   * Download the rewriting feature.
   *
   * The `downloadProgress` event listener will notify you about
   * the download progress. The returned promise resolves when
   * the download is complete or the feature is already available.
   *
   * Only available on Android.
   *
   * @since 8.2.0
   */
  downloadFeature(options?: FeatureOptions): Promise<void>;
  /**
   * Rewrite the given text in the configured style.
   *
   * The `inferenceProgress` event listener will notify you about
   * partial results as they are generated. The returned promise
   * resolves with the full result.
   *
   * Only available on Android.
   *
   * @since 8.2.0
   */
  rewrite(options: RewriteOptions): Promise<RewriteResult>;
  /**
   * Called while the rewriting feature is being downloaded.
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
   * The current availability status of the rewriting feature.
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
 * The feature availability depends on the configured output type
 * and language. Therefore, `checkFeatureStatus(...)` and
 * `downloadFeature(...)` take the same base options as `rewrite(...)`.
 *
 * @since 8.2.0
 */
export interface FeatureOptions {
  /**
   * The language of the input text.
   *
   * @since 8.2.0
   * @default Language.English
   */
  language?: Language;
  /**
   * The style in which the input text should be rewritten.
   *
   * @since 8.2.0
   * @default OutputType.Elaborate
   */
  outputType?: OutputType;
}

/**
 * @since 8.2.0
 */
export interface InferenceProgressEvent {
  /**
   * The new text of the partial result.
   *
   * @since 8.2.0
   * @example 'Capacitor is an open source native runtime.'
   */
  text: string;
}

/**
 * @since 8.2.0
 */
export interface RewriteOptions extends FeatureOptions {
  /**
   * The text to rewrite.
   *
   * The input must be under 256 tokens.
   *
   * @since 8.2.0
   * @example 'Capacitor is an open source native runtime for building Web Native apps.'
   */
  text: string;
}

/**
 * @since 8.2.0
 */
export interface RewriteResult {
  /**
   * The rewritten text suggestions, sorted in descending order of confidence.
   *
   * @since 8.2.0
   * @example ['Capacitor is an open source native runtime for building Web Native apps.']
   */
  results: string[];
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

/**
 * @since 8.2.0
 */
export enum Language {
  /**
   * @since 8.2.0
   */
  English = 'ENGLISH',
  /**
   * @since 8.2.0
   */
  French = 'FRENCH',
  /**
   * @since 8.2.0
   */
  German = 'GERMAN',
  /**
   * @since 8.2.0
   */
  Italian = 'ITALIAN',
  /**
   * @since 8.2.0
   */
  Japanese = 'JAPANESE',
  /**
   * @since 8.2.0
   */
  Korean = 'KOREAN',
  /**
   * @since 8.2.0
   */
  Spanish = 'SPANISH',
}

/**
 * @since 8.2.0
 */
export enum OutputType {
  /**
   * Elaborate the input text.
   *
   * @since 8.2.0
   */
  Elaborate = 'ELABORATE',
  /**
   * Insert appropriate emojis into the input text.
   *
   * @since 8.2.0
   */
  Emojify = 'EMOJIFY',
  /**
   * Shorten the input text.
   *
   * @since 8.2.0
   */
  Shorten = 'SHORTEN',
  /**
   * Make the input text more friendly.
   *
   * @since 8.2.0
   */
  Friendly = 'FRIENDLY',
  /**
   * Make the input text more professional.
   *
   * @since 8.2.0
   */
  Professional = 'PROFESSIONAL',
  /**
   * Rephrase the input text.
   *
   * @since 8.2.0
   */
  Rephrase = 'REPHRASE',
}
