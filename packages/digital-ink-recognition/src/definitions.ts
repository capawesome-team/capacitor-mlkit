export interface DigitalInkRecognitionPlugin {
  /**
   * Delete the language model for the given language.
   *
   * Only available on Android and iOS.
   *
   * @since 5.1.0
   */
  deleteDownloadedModel(options: DeleteDownloadedModelOptions): Promise<void>;
  /**
   * Download a language model for the given language.
   *
   * Language models are around 20MB in size, so be sure to only download the models you need
   * and only download them using a WiFi connection unless the user has specified otherwise.
   *
   * Only available on Android and iOS.
   *
   * @since 5.1.0
   */
  downloadModel(options: DownloadModel): Promise<void>;
  /**
   * Returns whether or not the model for the given language is downloaded.
   *
   * Only available on Android and iOS.
   *
   * @since 5.1.0
   */
  isModelDownloaded(
    options: IsModelDownloadedOptions,
  ): Promise<IsModelDownloadedResult>;
  /**
   * Get the languages for which a model has been downloaded.
   *
   * Only available on Android and iOS.
   *
   * @since 5.1.0
   */
  getDownloadedModels(): Promise<GetDownloadedModelsResult>;
  /**
   * Process an image and return the recognized text.
   *
   * @since 5.1.0
   */
  recognize(options: RecognizeOptions): Promise<RecognizeResult>;
}

/**
 * @since 5.1.0
 */
export interface DeleteDownloadedModelOptions {
  /**
   * The language as BCP-47 tag for which to delete the model.
   *
   * @since 5.1.0
   */
  language: string;
}

/**
 * @since 5.1.0
 */
export interface DownloadModel {
  /**
   * The language as BCP-47 tag to download the model for.
   *
   * @since 5.1.0
   * @example "en-US"
   */
  language: string;
}

/**
 * @since 5.1.0
 */
export interface IsModelDownloadedOptions {
  /**
   * The language as BCP-47 tag to check if the model is downloaded for.
   *
   * @since 5.1.0
   * @example "en-US"
   */
  language: string;
}

/**
 * @since 5.1.0
 */
export interface IsModelDownloadedResult {
  /**
   * Whether or not the model is downloaded.
   *
   * @since 5.1.0
   */
  isModelDownloaded: boolean;
}

/**
 * @since 5.1.0
 */
export interface GetDownloadedModelsResult {
  /**
   * The languages as BCP-47 tags for which a model has been downloaded.
   */
  languages: string[];
}

/**
 * @since 5.1.0
 */
export interface RecognizeOptions {
  /**
   * The language as BCP-47 tag to recognize the text in.
   *
   * @since 5.1.0
   */
  language: string;
}

/**
 * @since 5.1.0
 */
export interface RecognizeResult {
  /**
   * The recognized candidates.
   *
   * @since 5.1.0
   */
  candidates: Candidate[];
}

/**
 * @since 5.1.0
 */
export interface Candidate {
  /**
   * The textual representation of the recognition.
   *
   * @since 5.1.0
   */
  text: string;
  /**
   * The candidate's score.
   *
   * @since 5.1.0
   */
  score?: number;
}
