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
 * @see https://developers.google.com/ml-kit/language/identification/langid-support
 */
export enum Language {
  /**
   * @since 0.0.2
   */
  Afrikaans = 'af',
  /**
   * @since 0.0.2
   */
  Amharic = 'am',
  /**
   * @since 0.0.2
   */
  Arabic = 'ar',
  /**
   * @since 0.0.2
   */
  ArabicLatin = 'ar-Latn',
  /**
   * @since 0.0.2
   */
  Azerbaijani = 'az',
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
  BulgarianLatin = 'bg-Latn',
  /**
   * @since 0.0.2
   */
  Bengali = 'bn',
  /**
   * @since 0.0.2
   */
  Bosnian = 'bs',
  /**
   * @since 0.0.2
   */
  Catalan = 'ca',
  /**
   * @since 0.0.2
   */
  Cebuano = 'ceb',
  /**
   * @since 0.0.2
   */
  Corsican = 'co',
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
  GreekLatin = 'el-Latn',
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
  Basque = 'eu',
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
  Filipino = 'fil',
  /**
   * @since 0.0.2
   */
  French = 'fr',
  /**
   * @since 0.0.2
   */
  WesternFrisian = 'fy',
  /**
   * @since 0.0.2
   */
  Irish = 'ga',
  /**
   * @since 0.0.2
   */
  ScotsGaelic = 'gd',
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
  Hausa = 'ha',
  /**
   * @since 0.0.2
   */
  Hawaiian = 'haw',
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
  HindiLatin = 'hi-Latn',
  /**
   * @since 0.0.2
   */
  Hmong = 'hmn',
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
  Armenian = 'hy',
  /**
   * @since 0.0.2
   */
  Indonesian = 'id',
  /**
   * @since 0.0.2
   */
  Igbo = 'ig',
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
  JapaneseLatin = 'ja-Latn',
  /**
   * @since 0.0.2
   */
  Javanese = 'jv',
  /**
   * @since 0.0.2
   */
  Georgian = 'ka',
  /**
   * @since 0.0.2
   */
  Kazakh = 'kk',
  /**
   * @since 0.0.2
   */
  Khmer = 'km',
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
  Kurdish = 'ku',
  /**
   * @since 0.0.2
   */
  Kyrgyz = 'ky',
  /**
   * @since 0.0.2
   */
  Latin = 'la',
  /**
   * @since 0.0.2
   */
  Luxembourgish = 'lb',
  /**
   * @since 0.0.2
   */
  Lao = 'lo',
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
  Malagasy = 'mg',
  /**
   * @since 0.0.2
   */
  Maori = 'mi',
  /**
   * @since 0.0.2
   */
  Macedonian = 'mk',
  /**
   * @since 0.0.2
   */
  Malayalam = 'ml',
  /**
   * @since 0.0.2
   */
  Mongolian = 'mn',
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
  Burmese = 'my',
  /**
   * @since 0.0.2
   */
  Nepali = 'ne',
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
  Nyanja = 'ny',
  /**
   * @since 0.0.2
   */
  Punjabi = 'pa',
  /**
   * @since 0.0.2
   */
  Polish = 'pl',
  /**
   * @since 0.0.2
   */
  Pashto = 'ps',
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
  RussianLatin = 'ru-Latn',
  /**
   * @since 0.0.2
   */
  Sindhi = 'sd',
  /**
   * @since 0.0.2
   */
  Sinhala = 'si',
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
  Samoan = 'sm',
  /**
   * @since 0.0.2
   */
  Shona = 'sn',
  /**
   * @since 0.0.2
   */
  Somali = 'so',
  /**
   * @since 0.0.2
   */
  Albanian = 'sq',
  /**
   * @since 0.0.2
   */
  Serbian = 'sr',
  /**
   * @since 0.0.2
   */
  Sesotho = 'st',
  /**
   * @since 0.0.2
   */
  Sundanese = 'su',
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
  Tajik = 'tg',
  /**
   * @since 0.0.2
   */
  Thai = 'th',
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
  Uzbek = 'uz',
  /**
   * @since 0.0.2
   */
  Vietnamese = 'vi',
  /**
   * @since 0.0.2
   */
  Xhosa = 'xh',
  /**
   * @since 0.0.2
   */
  Yiddish = 'yi',
  /**
   * @since 0.0.2
   */
  Yoruba = 'yo',
  /**
   * @since 0.0.2
   */
  Chinese = 'zh',
  /**
   * @since 0.0.2
   */
  ChineseLatin = 'zh-Latn',
  /**
   * @since 0.0.2
   */
  Zulu = 'zu',
}
