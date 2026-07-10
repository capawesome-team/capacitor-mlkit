export interface EntityExtractionPlugin {
  /**
   * Delete the model for the given language.
   *
   * Only available on Android and iOS.
   *
   * @since 8.2.0
   */
  deleteDownloadedModel(options: DeleteDownloadedModelOptions): Promise<void>;
  /**
   * Download the model for the given language.
   *
   * The model must be downloaded before entities can be extracted for a language.
   *
   * Only available on Android and iOS.
   *
   * @since 8.2.0
   */
  downloadModel(options: DownloadModelOptions): Promise<void>;
  /**
   * Extract entities (e.g. addresses, date-times or phone numbers) from the given text.
   *
   * The model for the given language must be downloaded first using the `downloadModel(...)` method.
   * If the model is not downloaded, the method rejects with the `MODEL_NOT_DOWNLOADED` error code.
   *
   * Only available on Android and iOS.
   *
   * @since 8.2.0
   */
  extractEntities(
    options: ExtractEntitiesOptions,
  ): Promise<ExtractEntitiesResult>;
  /**
   * Get the languages for which a model has been downloaded.
   *
   * Only available on Android and iOS.
   *
   * @since 8.2.0
   */
  getDownloadedModels(): Promise<GetDownloadedModelsResult>;
}

/**
 * @since 8.2.0
 */
export interface DeleteDownloadedModelOptions {
  /**
   * The language for which to delete the model.
   *
   * @since 8.2.0
   */
  language: Language;
}

/**
 * @since 8.2.0
 */
export interface DownloadModelOptions {
  /**
   * The language for which to download the model.
   *
   * @since 8.2.0
   */
  language: Language;
}

/**
 * @since 8.2.0
 */
export interface ExtractEntitiesOptions {
  /**
   * The text to extract entities from.
   *
   * @since 8.2.0
   */
  text: string;
  /**
   * The language of the model to use.
   *
   * The model for this language must be downloaded first using the `downloadModel(...)` method.
   *
   * @since 8.2.0
   */
  language: Language;
  /**
   * The reference time (in milliseconds since epoch) used to resolve relative date-times (e.g. "tomorrow").
   *
   * Defaults to the current time.
   *
   * @since 8.2.0
   * @example 1751990400000
   */
  referenceTime?: number;
  /**
   * The reference time zone (as an IANA time zone identifier) used to resolve relative date-times.
   *
   * Defaults to the device's time zone.
   *
   * @since 8.2.0
   * @example "America/New_York"
   */
  referenceTimeZone?: string;
  /**
   * The preferred locale (as a BCP-47 language tag) used to disambiguate entities.
   *
   * Defaults to the device's locale.
   *
   * @since 8.2.0
   * @example "en-US"
   */
  preferredLocale?: string;
  /**
   * The entity types to extract.
   *
   * If not set, all entity types are extracted.
   *
   * @since 8.2.0
   */
  entityTypes?: EntityType[];
}

/**
 * @since 8.2.0
 */
export interface ExtractEntitiesResult {
  /**
   * The extracted annotations.
   *
   * @since 8.2.0
   */
  annotations: EntityAnnotation[];
}

/**
 * @since 8.2.0
 */
export interface EntityAnnotation {
  /**
   * The text segment within the original text that this annotation refers to.
   *
   * @since 8.2.0
   */
  text: string;
  /**
   * The start index (inclusive) of the annotation in the original text.
   *
   * @since 8.2.0
   */
  start: number;
  /**
   * The end index (exclusive) of the annotation in the original text.
   *
   * @since 8.2.0
   */
  end: number;
  /**
   * The entities that this annotation could refer to.
   *
   * @since 8.2.0
   */
  entities: Entity[];
}

/**
 * @since 8.2.0
 */
export interface Entity {
  /**
   * The type of the entity.
   *
   * @since 8.2.0
   */
  type: EntityType;
  /**
   * The granularity of the extracted date-time.
   *
   * Only available if `type` is `DATE_TIME`.
   *
   * @since 8.2.0
   */
  dateTimeGranularity?: DateTimeGranularity;
  /**
   * The extracted date-time in milliseconds since epoch.
   *
   * Only available if `type` is `DATE_TIME`.
   *
   * @since 8.2.0
   */
  timestamp?: number;
  /**
   * The currency of the money as it appears in the text (not canonicalized).
   *
   * Only available if `type` is `MONEY`.
   *
   * @since 8.2.0
   */
  unnormalizedCurrency?: string;
  /**
   * The integer part of the money amount.
   *
   * Only available if `type` is `MONEY`.
   *
   * @since 8.2.0
   */
  integerPart?: number;
  /**
   * The fractional part of the money amount.
   *
   * Only available if `type` is `MONEY`.
   *
   * @since 8.2.0
   */
  fractionalPart?: number;
  /**
   * The network of the payment card.
   *
   * Only available if `type` is `PAYMENT_CARD`.
   *
   * @since 8.2.0
   */
  paymentCardNetwork?: PaymentCardNetwork;
  /**
   * The number of the payment card in canonical format.
   *
   * Only available if `type` is `PAYMENT_CARD`.
   *
   * @since 8.2.0
   */
  paymentCardNumber?: string;
  /**
   * The IATA airline designator (two or three letters).
   *
   * Only available if `type` is `FLIGHT_NUMBER`.
   *
   * @since 8.2.0
   */
  airlineCode?: string;
  /**
   * The flight number (1 to 4 digits).
   *
   * Only available if `type` is `FLIGHT_NUMBER`.
   *
   * @since 8.2.0
   */
  flightNumber?: string;
  /**
   * The full ISBN in canonical format.
   *
   * Only available if `type` is `ISBN`.
   *
   * @since 8.2.0
   */
  isbn?: string;
  /**
   * The full IBAN in canonical format.
   *
   * Only available if `type` is `IBAN`.
   *
   * @since 8.2.0
   */
  iban?: string;
  /**
   * The ISO 3166-1 alpha-2 country code of the IBAN.
   *
   * Only available if `type` is `IBAN`.
   *
   * @since 8.2.0
   */
  ibanCountryCode?: string;
  /**
   * The parcel tracking carrier.
   *
   * Only available if `type` is `TRACKING_NUMBER`.
   *
   * @since 8.2.0
   */
  parcelCarrier?: ParcelTrackingCarrier;
  /**
   * The parcel tracking number in canonical format.
   *
   * Only available if `type` is `TRACKING_NUMBER`.
   *
   * @since 8.2.0
   */
  parcelTrackingNumber?: string;
}

/**
 * @since 8.2.0
 */
export interface GetDownloadedModelsResult {
  /**
   * The languages for which a model has been downloaded.
   *
   * @since 8.2.0
   */
  languages: Language[];
}

/**
 * @since 8.2.0
 */
export enum EntityType {
  /**
   * A physical address.
   *
   * @since 8.2.0
   */
  Address = 'ADDRESS',
  /**
   * A date and time reference.
   *
   * @since 8.2.0
   */
  DateTime = 'DATE_TIME',
  /**
   * An e-mail address.
   *
   * @since 8.2.0
   */
  Email = 'EMAIL',
  /**
   * A flight number in IATA format.
   *
   * @since 8.2.0
   */
  FlightNumber = 'FLIGHT_NUMBER',
  /**
   * An International Bank Account Number (IBAN).
   *
   * @since 8.2.0
   */
  Iban = 'IBAN',
  /**
   * An International Standard Book Number (ISBN).
   *
   * @since 8.2.0
   */
  Isbn = 'ISBN',
  /**
   * An amount of money.
   *
   * @since 8.2.0
   */
  Money = 'MONEY',
  /**
   * A payment card.
   *
   * @since 8.2.0
   */
  PaymentCard = 'PAYMENT_CARD',
  /**
   * A phone number.
   *
   * @since 8.2.0
   */
  Phone = 'PHONE',
  /**
   * A shipment tracking number.
   *
   * @since 8.2.0
   */
  TrackingNumber = 'TRACKING_NUMBER',
  /**
   * A URL.
   *
   * @since 8.2.0
   */
  Url = 'URL',
}

/**
 * The language of the model.
 *
 * @since 8.2.0
 */
export enum Language {
  /**
   * @since 8.2.0
   */
  Arabic = 'ARABIC',
  /**
   * @since 8.2.0
   */
  Chinese = 'CHINESE',
  /**
   * @since 8.2.0
   */
  Dutch = 'DUTCH',
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
  Polish = 'POLISH',
  /**
   * @since 8.2.0
   */
  Portuguese = 'PORTUGUESE',
  /**
   * @since 8.2.0
   */
  Russian = 'RUSSIAN',
  /**
   * @since 8.2.0
   */
  Spanish = 'SPANISH',
  /**
   * @since 8.2.0
   */
  Thai = 'THAI',
  /**
   * @since 8.2.0
   */
  Turkish = 'TURKISH',
}

/**
 * The granularity of an extracted date-time.
 *
 * @since 8.2.0
 */
export enum DateTimeGranularity {
  /**
   * @since 8.2.0
   */
  Unknown = 'UNKNOWN',
  /**
   * @since 8.2.0
   */
  Year = 'YEAR',
  /**
   * @since 8.2.0
   */
  Month = 'MONTH',
  /**
   * @since 8.2.0
   */
  Week = 'WEEK',
  /**
   * @since 8.2.0
   */
  Day = 'DAY',
  /**
   * @since 8.2.0
   */
  Hour = 'HOUR',
  /**
   * @since 8.2.0
   */
  Minute = 'MINUTE',
  /**
   * @since 8.2.0
   */
  Second = 'SECOND',
}

/**
 * The network of a payment card.
 *
 * @since 8.2.0
 */
export enum PaymentCardNetwork {
  /**
   * @since 8.2.0
   */
  Unknown = 'UNKNOWN',
  /**
   * @since 8.2.0
   */
  Amex = 'AMEX',
  /**
   * @since 8.2.0
   */
  DinersClub = 'DINERS_CLUB',
  /**
   * @since 8.2.0
   */
  Discover = 'DISCOVER',
  /**
   * @since 8.2.0
   */
  InterPayment = 'INTER_PAYMENT',
  /**
   * @since 8.2.0
   */
  Jcb = 'JCB',
  /**
   * @since 8.2.0
   */
  Maestro = 'MAESTRO',
  /**
   * @since 8.2.0
   */
  Mastercard = 'MASTERCARD',
  /**
   * @since 8.2.0
   */
  Mir = 'MIR',
  /**
   * @since 8.2.0
   */
  Troy = 'TROY',
  /**
   * @since 8.2.0
   */
  Unionpay = 'UNIONPAY',
  /**
   * @since 8.2.0
   */
  Visa = 'VISA',
}

/**
 * The carrier of a parcel tracking number.
 *
 * @since 8.2.0
 */
export enum ParcelTrackingCarrier {
  /**
   * @since 8.2.0
   */
  Unknown = 'UNKNOWN',
  /**
   * @since 8.2.0
   */
  Fedex = 'FEDEX',
  /**
   * @since 8.2.0
   */
  Ups = 'UPS',
  /**
   * @since 8.2.0
   */
  Dhl = 'DHL',
  /**
   * @since 8.2.0
   */
  Usps = 'USPS',
  /**
   * @since 8.2.0
   */
  Ontrac = 'ONTRAC',
  /**
   * @since 8.2.0
   */
  Lasership = 'LASERSHIP',
  /**
   * @since 8.2.0
   */
  IsraelPost = 'ISRAEL_POST',
  /**
   * @since 8.2.0
   */
  SwissPost = 'SWISS_POST',
  /**
   * @since 8.2.0
   */
  Msc = 'MSC',
  /**
   * @since 8.2.0
   */
  Amazon = 'AMAZON',
  /**
   * @since 8.2.0
   */
  IParcel = 'I_PARCEL',
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
}
