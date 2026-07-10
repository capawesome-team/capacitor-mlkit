export interface LanguageIdentificationPlugin {
  /**
   * Identify the most likely language of the given text.
   *
   * Only available on Android and iOS.
   *
   * @since 8.2.0
   */
  identifyLanguage(
    options: IdentifyLanguageOptions,
  ): Promise<IdentifyLanguageResult>;
  /**
   * Identify all possible languages of the given text with their confidence.
   *
   * Only available on Android and iOS.
   *
   * @since 8.2.0
   */
  identifyPossibleLanguages(
    options: IdentifyPossibleLanguagesOptions,
  ): Promise<IdentifyPossibleLanguagesResult>;
}

/**
 * @since 8.2.0
 */
export interface IdentifyLanguageOptions {
  /**
   * The text to identify the language for.
   *
   * @since 8.2.0
   */
  text: string;
  /**
   * The confidence threshold (`0.0` to `1.0`) a language must meet to be returned.
   *
   * If no language meets the threshold, the `language` will be `'und'` (undetermined).
   *
   * @since 8.2.0
   * @default 0.5
   */
  confidenceThreshold?: number;
}

/**
 * @since 8.2.0
 */
export interface IdentifyLanguageResult {
  /**
   * The BCP-47 language tag of the most likely language.
   *
   * The value is `'und'` (undetermined) if no language met the confidence threshold.
   *
   * @since 8.2.0
   * @example 'en'
   */
  language: string;
}

/**
 * @since 8.2.0
 */
export interface IdentifyPossibleLanguagesOptions {
  /**
   * The text to identify the languages for.
   *
   * @since 8.2.0
   */
  text: string;
  /**
   * The confidence threshold (`0.0` to `1.0`) a language must meet to be returned.
   *
   * @since 8.2.0
   * @default 0.01
   */
  confidenceThreshold?: number;
}

/**
 * @since 8.2.0
 */
export interface IdentifyPossibleLanguagesResult {
  /**
   * The identified languages ordered by descending confidence.
   *
   * The array contains a single entry with the language `'und'` (undetermined)
   * if no language met the confidence threshold.
   *
   * @since 8.2.0
   */
  identifiedLanguages: IdentifiedLanguage[];
}

/**
 * @since 8.2.0
 */
export interface IdentifiedLanguage {
  /**
   * The BCP-47 language tag of the identified language.
   *
   * @since 8.2.0
   * @example 'en'
   */
  language: string;
  /**
   * The confidence (`0.0` to `1.0`) of the identified language.
   *
   * @since 8.2.0
   * @example 0.99
   */
  confidence: number;
}
