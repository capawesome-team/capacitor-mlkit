export interface TranslationPlugin {
  /**
   * Delete the language model for the given language.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  deleteDownloadedModel(options: DeleteDownloadedModelOptions): Promise<void>;
  /**
   * Download a language model for offline translation.
   *
   * Language models are around 30MB in size, so be sure to only download the models you need
   * and only download them using a WiFi connection unless the user has specified otherwise.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  downloadModel(options: DownloadModel): Promise<void>;
  /**
   * Get the languages for which a model has been downloaded.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  getDownloadedModels(): Promise<GetDownloadedModelsResult>;
  /**
   * Translate the given text.
   *
   * If the language model for the given source and target languages is not downloaded,
   * it will be downloaded automatically which may take some time.
   * If you want to avoid this, use the `downloadModel(...)` method to download the model first.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  translate(options: TranslateOptions): Promise<TranslateResult>;
}

/**
 * @since 0.0.1
 */
export interface DeleteDownloadedModelOptions {
  /**
   * The language for which to delete the model.
   *
   * @since 0.0.1
   */
  language: Language;
}

/**
 * @since 0.0.1
 */
export interface DownloadModel {
  /**
   * The language to download the model for.
   *
   * @since 0.0.1
   * @example "en"
   */
  language: Language;
}

/**
 * @since 0.0.1
 */
export interface GetDownloadedModelsResult {
  /**
   * The languages for which a model has been downloaded.
   */
  languages: Language[];
}

/**
 * @since 0.0.1
 */
export interface TranslateOptions {
  /**
   * The text to translate.
   *
   * @since 0.0.1
   */
  text: string;
  /**
   * The source language of the text.
   *
   * If you don't know the source language, you can use the `Language Identification` plugin to detect it.
   *
   * @since 0.0.1
   */
  sourceLanguage: Language;
  /**
   * The target language to translate the text to.
   *
   * @since 0.0.1
   */
  targetLanguage: Language;
}

/**
 * @since 0.0.1
 */
export interface TranslateResult {
  /**
   * The translated text.
   *
   * @since 0.0.1
   */
  text: string;
}

/**
 * The language to translate to or from.
 *
 * @since 0.0.1
 */
export enum Language {
  /**
   * @since 0.0.1
   */
  Afrikaans = 'af',
  /**
   * @since 0.0.1
   */
  Arabic = 'ar',
  /**
   * @since 0.0.1
   */
  Belarusian = 'be',
  /**
   * @since 0.0.1
   */
  Bulgarian = 'bg',
  /**
   * @since 0.0.1
   */
  Bengali = 'bn',
  /**
   * @since 0.0.1
   */
  Catalan = 'ca',
  /**
   * @since 0.0.1
   */
  Czech = 'cs',
  /**
   * @since 0.0.1
   */
  Welsh = 'cy',
  /**
   * @since 0.0.1
   */
  Danish = 'da',
  /**
   * @since 0.0.1
   */
  German = 'de',
  /**
   * @since 0.0.1
   */
  Greek = 'el',
  /**
   * @since 0.0.1
   */
  English = 'en',
  /**
   * @since 0.0.1
   */
  Esperanto = 'eo',
  /**
   * @since 0.0.1
   */
  Spanish = 'es',
  /**
   * @since 0.0.1
   */
  Estonian = 'et',
  /**
   * @since 0.0.1
   */
  Persian = 'fa',
  /**
   * @since 0.0.1
   */
  Finnish = 'fi',
  /**
   * @since 0.0.1
   */
  French = 'fr',
  /**
   * @since 0.0.1
   */
  Irish = 'ga',
  /**
   * @since 0.0.1
   */
  Galician = 'gl',
  /**
   * @since 0.0.1
   */
  Gujarati = 'gu',
  /**
   * @since 0.0.1
   */
  Hebrew = 'he',
  /**
   * @since 0.0.1
   */
  Hindi = 'hi',
  /**
   * @since 0.0.1
   */
  Croatian = 'hr',
  /**
   * @since 0.0.1
   */
  Haitian = 'ht',
  /**
   * @since 0.0.1
   */
  Hungarian = 'hu',
  /**
   * @since 0.0.1
   */
  Indonesian = 'id',
  /**
   * @since 0.0.1
   */
  Icelandic = 'is',
  /**
   * @since 0.0.1
   */
  Italian = 'it',
  /**
   * @since 0.0.1
   */
  Japanese = 'ja',
  /**
   * @since 0.0.1
   */
  Georgian = 'ka',
  /**
   * @since 0.0.1
   */
  Kannada = 'kn',
  /**
   * @since 0.0.1
   */
  Korean = 'ko',
  /**
   * @since 0.0.1
   */
  Lithuanian = 'lt',
  /**
   * @since 0.0.1
   */
  Latvian = 'lv',
  /**
   * @since 0.0.1
   */
  Macedonian = 'mk',
  /**
   * @since 0.0.1
   */
  Marathi = 'mr',
  /**
   * @since 0.0.1
   */
  Malay = 'ms',
  /**
   * @since 0.0.1
   */
  Maltese = 'mt',
  /**
   * @since 0.0.1
   */
  Dutch = 'nl',
  /**
   * @since 0.0.1
   */
  Norwegian = 'no',
  /**
   * @since 0.0.1
   */
  Polish = 'pl',
  /**
   * @since 0.0.1
   */
  Portuguese = 'pt',
  /**
   * @since 0.0.1
   */
  Romanian = 'ro',
  /**
   * @since 0.0.1
   */
  Russian = 'ru',
  /**
   * @since 0.0.1
   */
  Slovak = 'sk',
  /**
   * @since 0.0.1
   */
  Slovenian = 'sl',
  /**
   * @since 0.0.1
   */
  Albanian = 'sq',
  /**
   * @since 0.0.1
   */
  Swedish = 'sv',
  /**
   * @since 0.0.1
   */
  Swahili = 'sw',
  /**
   * @since 0.0.1
   */
  Tamil = 'ta',
  /**
   * @since 0.0.1
   */
  Telugu = 'te',
  /**
   * @since 0.0.1
   */
  Thai = 'th',
  /**
   * @since 0.0.1
   */
  Tagalog = 'tl',
  /**
   * @since 0.0.1
   */
  Turkish = 'tr',
  /**
   * @since 0.0.1
   */
  Ukrainian = 'uk',
  /**
   * @since 0.0.1
   */
  Urdu = 'ur',
  /**
   * @since 0.0.1
   */
  Vietnamese = 'vi',
  /**
   * @since 0.0.1
   */
  Chinese = 'zh',
}
