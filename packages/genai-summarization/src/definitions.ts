import type { PluginListenerHandle } from '@capacitor/core';

/**
 * @since 8.2.0
 */
export interface GenAiSummarizationPlugin {
  /**
   * Check the current availability status of the summarization feature.
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
   * Download the summarization feature.
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
   * Summarize the given text.
   *
   * The `inferenceProgress` event listener will notify you about
   * partial results as they are generated. The returned promise
   * resolves with the full result.
   *
   * Only available on Android.
   *
   * @since 8.2.0
   */
  summarize(options: SummarizeOptions): Promise<SummarizeResult>;
  /**
   * Called while the summarization feature is being downloaded.
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
   * The current availability status of the summarization feature.
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
 * The feature availability depends on the configured input type,
 * output type and language. Therefore, `checkFeatureStatus(...)` and
 * `downloadFeature(...)` take the same base options as `summarize(...)`.
 *
 * @since 8.2.0
 */
export interface FeatureOptions {
  /**
   * The type of the input text.
   *
   * @since 8.2.0
   * @default InputType.Article
   */
  inputType?: InputType;
  /**
   * The language of the input text.
   *
   * @since 8.2.0
   * @default Language.English
   */
  language?: Language;
  /**
   * The type of the output summary.
   *
   * @since 8.2.0
   * @default OutputType.OneBullet
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
   * @example '* The article is about the Capacitor framework.'
   */
  text: string;
}

/**
 * @since 8.2.0
 */
export interface SummarizeOptions extends FeatureOptions {
  /**
   * The text to summarize.
   *
   * The input must be under 4,000 tokens (or about 3,000 English words).
   *
   * @since 8.2.0
   * @example 'Capacitor is an open source native runtime for building Web Native apps.'
   */
  text: string;
}

/**
 * @since 8.2.0
 */
export interface SummarizeResult {
  /**
   * The summary of the input text.
   *
   * @since 8.2.0
   * @example '* The article is about the Capacitor framework.'
   */
  summary: string;
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
   * The input text is an article.
   *
   * @since 8.2.0
   */
  Article = 'ARTICLE',
  /**
   * The input text is a conversation where each line
   * uses the format `<name>: <message>`.
   *
   * @since 8.2.0
   */
  Conversation = 'CONVERSATION',
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
  Japanese = 'JAPANESE',
  /**
   * @since 8.2.0
   */
  Korean = 'KOREAN',
}

/**
 * @since 8.2.0
 */
export enum OutputType {
  /**
   * The summary consists of one bullet point.
   *
   * @since 8.2.0
   */
  OneBullet = 'ONE_BULLET',
  /**
   * The summary consists of up to two bullet points.
   *
   * @since 8.2.0
   */
  TwoBullets = 'TWO_BULLETS',
  /**
   * The summary consists of up to three bullet points.
   *
   * @since 8.2.0
   */
  ThreeBullets = 'THREE_BULLETS',
}
