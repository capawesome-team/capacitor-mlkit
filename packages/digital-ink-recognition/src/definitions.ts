export interface DigitalInkRecognitionPlugin {
  /**
   * Delete the downloaded model for the given language.
   *
   * Only available on Android and iOS.
   *
   * @since 8.2.0
   */
  deleteDownloadedModel(options: DeleteDownloadedModelOptions): Promise<void>;
  /**
   * Download a model for the given language.
   *
   * Only available on Android and iOS.
   *
   * @since 8.2.0
   */
  downloadModel(options: DownloadModelOptions): Promise<void>;
  /**
   * Get the languages for which a model has been downloaded.
   *
   * Only available on Android and iOS.
   *
   * @since 8.2.0
   */
  getDownloadedModels(): Promise<GetDownloadedModelsResult>;
  /**
   * Recognize the handwritten text or hand-drawn shapes in the given strokes.
   *
   * The model for the given language must be downloaded before calling this method,
   * otherwise the call is rejected with the error code `MODEL_NOT_DOWNLOADED`.
   *
   * Only available on Android and iOS.
   *
   * @since 8.2.0
   */
  recognize(options: RecognizeOptions): Promise<RecognizeResult>;
}

/**
 * @since 8.2.0
 */
export interface DeleteDownloadedModelOptions {
  /**
   * The BCP-47 language tag of the model to delete.
   *
   * @since 8.2.0
   * @example 'en-US'
   */
  languageTag: string;
}

/**
 * @since 8.2.0
 */
export interface DownloadModelOptions {
  /**
   * The BCP-47 language tag of the model to download.
   *
   * See the [supported languages](https://developers.google.com/ml-kit/vision/digital-ink-recognition/base-models)
   * for a list of available models. The special tag `zxx-Zsym-x-autodraw` identifies
   * the model for recognizing hand-drawn shapes.
   *
   * @since 8.2.0
   * @example 'en-US'
   */
  languageTag: string;
}

/**
 * @since 8.2.0
 */
export interface GetDownloadedModelsResult {
  /**
   * The BCP-47 language tags of the downloaded models.
   *
   * @since 8.2.0
   * @example ['en-US']
   */
  languageTags: string[];
}

/**
 * @since 8.2.0
 */
export interface RecognizeOptions {
  /**
   * The BCP-47 language tag of the model to use for recognition.
   *
   * @since 8.2.0
   * @example 'en-US'
   */
  languageTag: string;
  /**
   * The strokes to recognize.
   *
   * @since 8.2.0
   */
  strokes: Stroke[];
  /**
   * The maximum number of recognition candidates to return.
   *
   * @since 8.2.0
   * @default 5
   */
  maxResultCount?: number;
  /**
   * The characters immediately preceding the strokes to improve recognition accuracy.
   *
   * A good rule of thumb is to provide as many characters as possible,
   * up to around 20 characters (including spaces).
   *
   * @since 8.2.0
   * @example 'Hello '
   */
  preContext?: string;
  /**
   * The size of the writing area to improve recognition accuracy.
   *
   * @since 8.2.0
   */
  writingArea?: WritingArea;
}

/**
 * @since 8.2.0
 */
export interface Stroke {
  /**
   * The points of the stroke in the order in which they were captured.
   *
   * @since 8.2.0
   */
  points: StrokePoint[];
}

/**
 * @since 8.2.0
 */
export interface StrokePoint {
  /**
   * The horizontal coordinate of the point. Increases to the right.
   *
   * @since 8.2.0
   * @example 100
   */
  x: number;
  /**
   * The vertical coordinate of the point. Increases going downward.
   *
   * @since 8.2.0
   * @example 50
   */
  y: number;
  /**
   * The timestamp of the point in milliseconds.
   *
   * Providing a timestamp improves recognition accuracy.
   *
   * @since 8.2.0
   * @example 1719327600000
   */
  t?: number;
}

/**
 * @since 8.2.0
 */
export interface WritingArea {
  /**
   * The width of the writing area.
   *
   * The unit must be the same as the one used for the stroke coordinates.
   *
   * @since 8.2.0
   * @example 300
   */
  width: number;
  /**
   * The height of the writing area.
   *
   * The unit must be the same as the one used for the stroke coordinates.
   *
   * @since 8.2.0
   * @example 100
   */
  height: number;
}

/**
 * @since 8.2.0
 */
export interface RecognizeResult {
  /**
   * The recognition candidates ordered by descending quality.
   *
   * @since 8.2.0
   */
  candidates: RecognitionCandidate[];
}

/**
 * @since 8.2.0
 */
export interface RecognitionCandidate {
  /**
   * The recognized text.
   *
   * @since 8.2.0
   * @example 'Hello'
   */
  text: string;
  /**
   * The score of the candidate. Lower scores indicate better results.
   *
   * The score is not set for all models.
   *
   * @since 8.2.0
   * @example 0.5
   */
  score?: number;
}

/**
 * @since 8.2.0
 */
export enum ErrorCode {
  /**
   * The model for the given language has not been downloaded yet.
   *
   * @since 8.2.0
   */
  ModelNotDownloaded = 'MODEL_NOT_DOWNLOADED',
  /**
   * The given language tag is not supported.
   *
   * @since 8.2.0
   */
  UnsupportedLanguageTag = 'UNSUPPORTED_LANGUAGE_TAG',
}
