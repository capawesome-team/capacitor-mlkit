export interface LanguageIdentificationPlugin {
  /**
   * Identify the language of the given text.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.2
   */
  identifyLanguage(
    options: IdentifyLanguageOptions,
  ): Promise<IdentifyLanguageResult>;
}

/**
 * @since 0.0.2
 */
export interface IdentifyLanguageOptions {
  /**
   * The minimum confidence threshold for the language identification.
   *
   * If the confidence of the identified language is below this threshold, an error will be thrown.
   *
   * The confidence is a value between 0 and 1.
   *
   * @since 0.0.2
   * @default 0.5
   */
  confidenceThreshold?: number;
  /**
   * The text to identify the language of.
   *
   * @since 0.0.2
   */
  text: string;
}

/**
 * @since 0.0.2
 */
export interface IdentifyLanguageResult {
  /**
   * The identified language.
   *
   * @since 0.0.2
   */
  language: Language;
}

/**
 * The language to translate to or from.
 *
 * @since 0.0.2
 */
export enum Language {
  /**
   * @since 0.0.2
   */
  Afrikaans = 'af',
  /**
   * @since 0.0.2
   */
  Arabic = 'ar',
  /**
   * @since 0.0.2
   */
  Belarusian = 'be',
  /**
   * @since 0.0.2
   */
  Bulgarian = 'bg',
  /**
   * @since 0.0.2
   */
  Bengali = 'bn',
  /**
   * @since 0.0.2
   */
  Catalan = 'ca',
  /**
   * @since 0.0.2
   */
  Czech = 'cs',
  /**
   * @since 0.0.2
   */
  Welsh = 'cy',
  /**
   * @since 0.0.2
   */
  Danish = 'da',
  /**
   * @since 0.0.2
   */
  German = 'de',
  /**
   * @since 0.0.2
   */
  Greek = 'el',
  /**
   * @since 0.0.2
   */
  English = 'en',
  /**
   * @since 0.0.2
   */
  Esperanto = 'eo',
  /**
   * @since 0.0.2
   */
  Spanish = 'es',
  /**
   * @since 0.0.2
   */
  Estonian = 'et',
  /**
   * @since 0.0.2
   */
  Persian = 'fa',
  /**
   * @since 0.0.2
   */
  Finnish = 'fi',
  /**
   * @since 0.0.2
   */
  French = 'fr',
  /**
   * @since 0.0.2
   */
  Irish = 'ga',
  /**
   * @since 0.0.2
   */
  Galician = 'gl',
  /**
   * @since 0.0.2
   */
  Gujarati = 'gu',
  /**
   * @since 0.0.2
   */
  Hebrew = 'he',
  /**
   * @since 0.0.2
   */
  Hindi = 'hi',
  /**
   * @since 0.0.2
   */
  Croatian = 'hr',
  /**
   * @since 0.0.2
   */
  Haitian = 'ht',
  /**
   * @since 0.0.2
   */
  Hungarian = 'hu',
  /**
   * @since 0.0.2
   */
  Indonesian = 'id',
  /**
   * @since 0.0.2
   */
  Icelandic = 'is',
  /**
   * @since 0.0.2
   */
  Italian = 'it',
  /**
   * @since 0.0.2
   */
  Japanese = 'ja',
  /**
   * @since 0.0.2
   */
  Georgian = 'ka',
  /**
   * @since 0.0.2
   */
  Kannada = 'kn',
  /**
   * @since 0.0.2
   */
  Korean = 'ko',
  /**
   * @since 0.0.2
   */
  Lithuanian = 'lt',
  /**
   * @since 0.0.2
   */
  Latvian = 'lv',
  /**
   * @since 0.0.2
   */
  Macedonian = 'mk',
  /**
   * @since 0.0.2
   */
  Marathi = 'mr',
  /**
   * @since 0.0.2
   */
  Malay = 'ms',
  /**
   * @since 0.0.2
   */
  Maltese = 'mt',
  /**
   * @since 0.0.2
   */
  Dutch = 'nl',
  /**
   * @since 0.0.2
   */
  Norwegian = 'no',
  /**
   * @since 0.0.2
   */
  Polish = 'pl',
  /**
   * @since 0.0.2
   */
  Portuguese = 'pt',
  /**
   * @since 0.0.2
   */
  Romanian = 'ro',
  /**
   * @since 0.0.2
   */
  Russian = 'ru',
  /**
   * @since 0.0.2
   */
  Slovak = 'sk',
  /**
   * @since 0.0.2
   */
  Slovenian = 'sl',
  /**
   * @since 0.0.2
   */
  Albanian = 'sq',
  /**
   * @since 0.0.2
   */
  Swedish = 'sv',
  /**
   * @since 0.0.2
   */
  Swahili = 'sw',
  /**
   * @since 0.0.2
   */
  Tamil = 'ta',
  /**
   * @since 0.0.2
   */
  Telugu = 'te',
  /**
   * @since 0.0.2
   */
  Thai = 'th',
  /**
   * @since 0.0.2
   */
  Tagalog = 'tl',
  /**
   * @since 0.0.2
   */
  Turkish = 'tr',
  /**
   * @since 0.0.2
   */
  Ukrainian = 'uk',
  /**
   * @since 0.0.2
   */
  Urdu = 'ur',
  /**
   * @since 0.0.2
   */
  Vietnamese = 'vi',
  /**
   * @since 0.0.2
   */
  Chinese = 'zh',
}
