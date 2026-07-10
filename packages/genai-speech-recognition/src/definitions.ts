import type { PermissionState, PluginListenerHandle } from '@capacitor/core';

/**
 * @since 8.2.0
 */
export interface GenAiSpeechRecognitionPlugin {
  /**
   * Check the current availability status of the speech recognition feature.
   *
   * The feature availability depends on the configured mode and locale.
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
   * Check the status of the microphone permission.
   *
   * Only available on Android.
   *
   * @since 8.2.0
   */
  checkPermissions(): Promise<PermissionStatus>;
  /**
   * Download the speech recognition feature.
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
   * Request the microphone permission.
   *
   * Only available on Android.
   *
   * @since 8.2.0
   */
  requestPermissions(): Promise<PermissionStatus>;
  /**
   * Start a speech recognition session.
   *
   * The audio is captured from the device microphone. If the microphone
   * permission has not been granted yet, it will be requested automatically.
   * The `partialResult` and `finalResult` event listeners will notify you
   * about the recognition results. The `error` event listener will notify
   * you about errors that occur during the session.
   *
   * Rejects with the `RECOGNITION_ALREADY_RUNNING` error code if a
   * recognition session is already running.
   *
   * Only available on Android.
   *
   * @since 8.2.0
   */
  startRecognition(options?: StartRecognitionOptions): Promise<void>;
  /**
   * Stop the current speech recognition session.
   *
   * Only available on Android.
   *
   * @since 8.2.0
   */
  stopRecognition(): Promise<void>;
  /**
   * Called while the speech recognition feature is being downloaded.
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
   * Called when an error occurs during a recognition session.
   *
   * Only available on Android.
   *
   * @since 8.2.0
   */
  addListener(
    eventName: 'error',
    listenerFunc: (event: ErrorEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when a final recognition result is available.
   *
   * Only available on Android.
   *
   * @since 8.2.0
   */
  addListener(
    eventName: 'finalResult',
    listenerFunc: (event: RecognitionResultEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when a partial recognition result is available.
   *
   * Only available on Android.
   *
   * @since 8.2.0
   */
  addListener(
    eventName: 'partialResult',
    listenerFunc: (event: RecognitionResultEvent) => void,
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
   * The current availability status of the speech recognition feature.
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
export interface ErrorEvent {
  /**
   * The error message.
   *
   * @since 8.2.0
   * @example 'An unknown error occurred.'
   */
  message: string;
}

/**
 * The feature availability depends on the configured mode and locale.
 * Therefore, `checkFeatureStatus(...)` and `downloadFeature(...)` take
 * the same base options as `startRecognition(...)`.
 *
 * @since 8.2.0
 */
export interface FeatureOptions {
  /**
   * The locale of the speech to recognize as a BCP-47 language tag.
   *
   * The supported locales depend on the configured mode.
   *
   * @since 8.2.0
   * @default The device locale.
   * @example 'en-US'
   */
  locale?: string;
  /**
   * The speech recognition mode.
   *
   * @since 8.2.0
   * @default RecognitionMode.Basic
   */
  mode?: RecognitionMode;
}

/**
 * @since 8.2.0
 */
export interface PermissionStatus {
  /**
   * The status of the microphone permission.
   *
   * @since 8.2.0
   * @example 'granted'
   */
  microphone: PermissionState;
}

/**
 * @since 8.2.0
 */
export interface RecognitionResultEvent {
  /**
   * The recognized text.
   *
   * @since 8.2.0
   * @example 'Hello World'
   */
  text: string;
}

/**
 * @since 8.2.0
 */
export type StartRecognitionOptions = FeatureOptions;

/**
 * @since 8.2.0
 */
export enum ErrorCode {
  /**
   * The microphone permission was denied.
   *
   * @since 8.2.0
   */
  MicrophonePermissionDenied = 'MICROPHONE_PERMISSION_DENIED',
  /**
   * A recognition session is already running.
   *
   * @since 8.2.0
   */
  RecognitionAlreadyRunning = 'RECOGNITION_ALREADY_RUNNING',
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
export enum RecognitionMode {
  /**
   * Speech recognition using a GenAI model.
   *
   * This mode provides the best quality but is currently only
   * available on a limited set of devices (e.g. Pixel 10).
   *
   * @since 8.2.0
   */
  Advanced = 'ADVANCED',
  /**
   * Speech recognition using a traditional speech model.
   *
   * This mode requires Android API level 31 or higher.
   *
   * @since 8.2.0
   */
  Basic = 'BASIC',
}
