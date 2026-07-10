import type { PluginListenerHandle } from '@capacitor/core';

/**
 * @since 8.2.0
 */
export interface GenAiProofreadingPlugin {
  /**
   * Check the current availability status of the proofreading feature.
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
   * Download the proofreading feature.
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
   * Proofread the given text.
   *
   * The `inferenceProgress` event listener will notify you about
   * partial results as they are generated. The returned promise
   * resolves with the full result.
   *
   * Only available on Android.
   *
   * @since 8.2.0
   */
  proofread(options: ProofreadOptions): Promise<ProofreadResult>;
  /**
   * Called while the proofreading feature is being downloaded.
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
   * The current availability status of the proofreading feature.
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
 * The feature availability depends on the configured input type
 * and language. Therefore, `checkFeatureStatus(...)` and
 * `downloadFeature(...)` take the same base options as `proofread(...)`.
 *
 * @since 8.2.0
 */
export interface FeatureOptions {
  /**
   * The type of the input text.
   *
   * @since 8.2.0
   * @default InputType.Keyboard
   */
  inputType?: InputType;
  /**
   * The language of the input text.
   *
   * @since 8.2.0
   * @default Language.English
   */
  language?: Language;
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
export interface ProofreadOptions extends FeatureOptions {
  /**
   * The text to proofread.
   *
   * @since 8.2.0
   * @example 'Capacitor is an open source natvie runtime.'
   */
  text: string;
}

/**
 * @since 8.2.0
 */
export interface ProofreadResult {
  /**
   * The proofread suggestions for the input text.
   *
   * More than one result may be returned. If multiple suggestions are
   * returned, they are sorted in descending order of confidence.
   *
   * @since 8.2.0
   * @example ['Capacitor is an open source native runtime.']
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
export enum InputType {
  /**
   * The input text was typed on a keyboard.
   *
   * @since 8.2.0
   */
  Keyboard = 'KEYBOARD',
  /**
   * The input text was produced by converting voice to text.
   *
   * @since 8.2.0
   */
  Voice = 'VOICE',
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
