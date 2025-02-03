import type { PermissionState, PluginListenerHandle } from '@capacitor/core';

export interface BarcodeScannerPlugin {
  /**
   * Start scanning for barcodes.
   *
   * @since 0.0.1
   */
  startScan(options?: StartScanOptions): Promise<void>;
  /**
   * Stop scanning for barcodes.
   *
   * @since 0.0.1
   */
  stopScan(): Promise<void>;
  /**
   * Read barcodes from an image.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  readBarcodesFromImage(
    options: ReadBarcodesFromImageOptions,
  ): Promise<ReadBarcodesFromImageResult>;
  /**
   * Scan a barcode with a ready-to-use interface without WebView customization.
   *
   * On **Android**, this method is only available on devices with Google Play Services
   * installed. Therefore, no camera permission is required.
   *
   * **Attention:** Before using this method on *Android*, first check if the Google Barcode Scanner module is available
   * by using `isGoogleBarcodeScannerModuleAvailable()`.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   * @experimental This method is experimental and may change in the future.
   */
  scan(options?: ScanOptions): Promise<ScanResult>;
  /**
   * Returns whether or not the barcode scanner is supported.
   *
   * @since 0.0.1
   */
  isSupported(): Promise<IsSupportedResult>;
  /**
   * Set the zoom ratio of the camera.
   *
   * Only available on Android and iOS.
   *
   * @since 5.4.0
   */
  setZoomRatio(options: SetZoomRatioOptions): Promise<void>;
  /**
   * Get the zoom ratio of the camera.
   *
   * Only available on Android and iOS.
   *
   * @since 5.4.0
   */
  getZoomRatio(): Promise<GetZoomRatioResult>;
  /**
   * Get the minimum zoom ratio of the camera.
   *
   * Only available on Android and iOS.
   *
   * @since 5.4.0
   */
  getMinZoomRatio(): Promise<GetMinZoomRatioResult>;
  /**
   * Get the maximum zoom ratio of the camera.
   *
   * Only available on Android and iOS.
   *
   * @since 5.4.0
   */
  getMaxZoomRatio(): Promise<GetMaxZoomRatioResult>;
  /**
   * Open the settings of the app so that the user can grant the camera permission.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  openSettings(): Promise<void>;
  /**
   * Check if the Google Barcode Scanner module is available.
   *
   * If the Google Barcode Scanner module is not available, you can install it by using `installGoogleBarcodeScannerModule()`.
   *
   * Only available on Android.
   *
   * @since 5.1.0
   */
  isGoogleBarcodeScannerModuleAvailable(): Promise<IsGoogleBarcodeScannerModuleAvailableResult>;
  /**
   * Install the Google Barcode Scanner module.
   *
   * **Attention**: This only starts the installation.
   * The `googleBarcodeScannerModuleInstallProgress` event listener will
   * notify you when the installation is complete.
   *
   * Only available on Android.
   *
   * @since 5.1.0
   */
  installGoogleBarcodeScannerModule(): Promise<void>;
  /**
   * Check camera permission.
   *
   * @since 0.0.1
   */
  checkPermissions(): Promise<PermissionStatus>;
  /**
   * Request camera permission.
   *
   * @since 0.0.1
   */
  requestPermissions(): Promise<PermissionStatus>;
  /**
   * Called when barcodes are scanned.
   *
   * @since 6.2.0
   */
  addListener(
    eventName: 'barcodesScanned',
    listenerFunc: (event: BarcodesScannedEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when an error occurs during the scan.
   *
   * Available on Android and iOS.
   *
   * @since 0.0.1
   */
  addListener(
    eventName: 'scanError',
    listenerFunc: (event: ScanErrorEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when the Google Barcode Scanner module is installed.
   *
   * Available on Android.
   *
   * @since 5.1.0
   */
  addListener(
    eventName: 'googleBarcodeScannerModuleInstallProgress',
    listenerFunc: (
      event: GoogleBarcodeScannerModuleInstallProgressEvent,
    ) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Remove all listeners for this plugin.
   *
   * @since 0.0.1
   */
  removeAllListeners(): Promise<void>;
}

/**
 * @since 0.0.1
 */
export interface StartScanOptions {
  /**
   * Improve the speed of the barcode scanner by configuring
   * the barcode formats to scan for.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  formats?: BarcodeFormat[];
  /**
   * Configure the camera (front or back) to use.
   *
   * @since 0.0.1
   */
  lensFacing?: LensFacing;
  /**
   * Configure the resolution of the captured image that is used for barcode scanning.
   *
   * Only available on Android and iOS.
   *
   * @since 7.0.0
   * @default Resolution['1280x720']
   */
  resolution?: Resolution;
  /**
   * The HTML video element to use for the camera preview.
   *
   * Only available on web.
   *
   * @since 7.1.0
   */
  videoElement?: HTMLVideoElement;
}

/**
 * @since 0.0.1
 */
export interface ReadBarcodesFromImageOptions {
  /**
   * Improve the speed of the barcode scanner by configuring
   * the barcode formats to scan for.
   *
   * @since 0.0.1
   */
  formats?: BarcodeFormat[];
  /**
   * The local path to the image file.
   *
   * @since 0.0.1
   */
  path: string;
}

/**
 * @since 0.0.1
 */
export interface ReadBarcodesFromImageResult {
  /**
   * The detected barcodes.
   *
   * @since 0.0.1
   */
  barcodes: Barcode[];
}

/**
 * @since 0.0.1
 */
export interface ScanOptions {
  /**
   * Improve the speed of the barcode scanner by configuring
   * the barcode formats to scan for.
   *
   * @since 0.0.1
   */
  formats?: BarcodeFormat[];
}

/**
 * @since 0.0.1
 */
export interface ScanResult {
  /**
   * The detected barcodes.
   *
   * @since 0.0.1
   */
  barcodes: Barcode[];
}

/**
 * @since 0.0.1
 */
export interface IsSupportedResult {
  /**
   * Whether or not the barcode scanner is supported by checking if the device has a camera.
   *
   * @since 0.0.1
   * @example true
   */
  supported: boolean;
}

/**
 * @since 0.0.1
 */
export interface IsTorchEnabledResult {
  /**
   * Whether or not the torch is enabled.
   *
   * @since 0.0.1
   * @example true
   */
  enabled: boolean;
}

/**
 * @since 0.0.1
 */
export interface IsTorchAvailableResult {
  /**
   * Whether or not the torch is available.
   *
   * @since 0.0.1
   * @example true
   */
  available: boolean;
}

/**
 * @since 5.4.0
 */
export interface SetZoomRatioOptions {
  /**
   * The zoom ratio to set.
   *
   * @since 5.4.0
   */
  zoomRatio: number;
}

/**
 * @since 5.4.0
 */
export interface GetZoomRatioResult {
  /**
   * The zoom ratio.
   *
   * @since 5.4.0
   */
  zoomRatio: number;
}

/**
 * @since 5.4.0
 */
export interface GetMinZoomRatioResult {
  /**
   * The minimum zoom ratio.
   *
   * @since 5.4.0
   */
  zoomRatio: number;
}

/**
 * @since 5.4.0
 */
export interface GetMaxZoomRatioResult {
  /**
   * The maximum zoom ratio.
   *
   * @since 5.4.0
   */
  zoomRatio: number;
}

/**
 * @since 5.1.0
 */
export interface IsGoogleBarcodeScannerModuleAvailableResult {
  /**
   * Whether or not the Google Barcode Scanner module is available.
   *
   * @since 5.1.0
   */
  available: boolean;
}

/**
 * @since 0.0.1
 */
export type CameraPermissionState = PermissionState | 'limited';

/**
 * @since 0.0.1
 */
export interface PermissionStatus {
  /**
   * @since 0.0.1
   */
  camera: CameraPermissionState;
}

/**
 * @since 0.0.1
 */
export interface BarcodeScannedEvent {
  /**
   * A detected barcode.
   *
   * @since 0.0.1
   */
  barcode: Barcode;
}

/**
 * @since 6.2.0
 */
export interface BarcodesScannedEvent {
  /**
   * The detected barcodes.
   *
   * @since 6.2.0
   */
  barcodes: Barcode[];
}

/**
 * @since 0.0.1
 */
export interface ScanErrorEvent {
  /**
   * The error message.
   *
   * @since 0.0.1
   */
  message: string;
}

/**
 * @since 5.1.0
 */
export interface GoogleBarcodeScannerModuleInstallProgressEvent {
  /**
   * The current state of the installation.
   *
   * @since 5.1.0
   */
  state: GoogleBarcodeScannerModuleInstallState;
  /**
   * The progress of the installation in percent between 0 and 100.
   *
   * @since 5.1.0
   */
  progress?: number;
}

/**
 * @since 0.0.1
 */
export interface Barcode {
  /**
   * Raw bytes as it was encoded in the barcode.
   *
   * @since 0.0.1
   * @example [67, 97, 112, 97, 99, 105, 116, 111, 114, 74, 83]
   */
  bytes?: number[];
  /**
   * Calendar event info.
   *
   * @since 7.0.0
   */
  calendarEvent?: BarcodeCalendarEvent;
  /**
   * Person's or organization's business card.
   *
   * @since 7.0.0
   */
  contactInfo?: BarcodeContactInfo;
  /**
   * The four corner points of the barcode in clockwise
   * order starting with top-left.
   *
   * This property is currently only supported by the `startScan(...)` method.
   *
   * @since 0.0.1
   * @example [[34, 33], [264, 32], [263, 263], [33, 263]]
   * @experimental This property is experimental and may change in the future.
   */
  cornerPoints?: [
    [number, number],
    [number, number],
    [number, number],
    [number, number],
  ];
  /**
   * The barcode value in a human readable format.
   *
   * @since 0.0.1
   * @example "CapacitorJS"
   */
  displayValue: string;
  /**
   * Driver license or ID card.
   *
   * @since 7.0.0
   */
  driverLicense?: BarcodeDriverLicense;
  /**
   * An email message from a 'MAILTO:'.
   *
   * @since 7.0.0
   */
  email?: BarcodeEmail;
  /**
   * The barcode format.
   *
   * @since 0.0.1
   * @example "QR_CODE"
   */
  format: BarcodeFormat;
  /**
   * GPS coordinates from a 'GEO:'.
   *
   * @since 7.0.0
   */
  geoPoint?: BarcodeGeoPoint;
  /**
   * Phone number info.
   *
   * @since 7.0.0
   */
  phone?: BarcodePhone;
  /**
   * The raw bytes of the barcode.
   *
   * @since 7.1.0
   * @example [67, 97, 112, 97, 99, 105, 116, 111, 114, 74, 83]
   */
  rawBytes?: number[];
  /**
   * The barcode value in a machine readable format.
   *
   * This value is only available when the barcode is encoded
   * in the UTF-8 character set. Otherwise, the `rawBytes` property
   * should be used and this property will be an empty string.
   *
   * @since 0.0.1
   * @example "CapacitorJS"
   */
  rawValue: string;
  /**
   * A sms message from a 'SMS:'.
   *
   * @since 7.0.0
   */
  sms?: BarcodeSms;
  /**
   * A URL and title from a 'MEBKM:'.
   *
   * @since 7.0.0
   */
  urlBookmark?: BarcodeUrlBookmark;
  /**
   * The barcode value type.
   *
   * @since 0.0.1
   * @example "TEXT"
   */
  valueType: BarcodeValueType;
  /**
   * A wifi network parameters from a 'WIFI:'.
   *
   * @since 7.0.0
   */
  wifi?: BarcodeWifi;
}

/**
 * @since 7.0.0
 */
export interface BarcodeCalendarEvent {
  /**
   * The event description.
   *
   * @since 7.0.0
   */
  description?: string;
  /**
   * The event end date as ISO 8601 string.
   *
   * @since 7.0.0
   * @example "2022-12-31T23:59:59.999Z"
   */
  end?: string;
  /**
   * The event location.
   *
   * @since 7.0.0
   */
  location?: string;
  /**
   * The event organizer.
   *
   * @since 7.0.0
   */
  organizer?: string;
  /**
   * The event start date as ISO 8601 string.
   *
   * @since 7.0.0
   * @example "2022-01-01T00:00:00.000Z"
   */
  start?: string;
  /**
   * The event status.
   *
   * @since 7.0.0
   */
  status?: string;
  /**
   * The event summary.
   *
   * @since 7.0.0
   */
  summary?: string;
}

/**
 * @since 7.0.0
 */
export interface BarcodeContactInfo {
  /**
   * The contact's addresses.
   *
   * @since 7.0.0
   */
  addresses?: Address[];
  /**
   * The contact's emails.
   *
   * @since 7.0.0
   */
  emails?: BarcodeEmail[];
  /**
   * The contact's name.
   *
   * @since 7.0.0
   */
  personName?: PersonName;
  /**
   * The contact's organization.
   *
   * @since 7.0.0
   */
  organization?: string;
  /**
   * The contact's phones.
   *
   * @since 7.0.0
   */
  phones?: BarcodePhone[];
  /**
   * The contact's title.
   *
   * @since 7.0.0
   */
  title?: string;
  /**
   * The contact's urls.
   *
   * @since 7.0.0
   */
  urls?: string[];
}

/**
 * @since 7.0.0
 */
export interface Address {
  /**
   * Formatted address, multiple lines when appropriate.
   *
   * @since 7.0.0
   */
  addressLines?: string[];
  /**
   * Address type.
   *
   * @since 7.0.0
   */
  type?: AddressType;
}

/**
 * @since 7.0.0
 */
export interface BarcodeDriverLicense {
  /**
   * City of holder's address.
   *
   * @since 7.0.0
   */
  addressCity?: string;
  /**
   * State of holder's address.
   *
   * @since 7.0.0
   */
  addressState?: string;
  /**
   * Street of holder's address.
   *
   * @since 7.0.0
   */
  addressStreet?: string;
  /**
   * Postal code of holder's address.
   *
   * @since 7.0.0
   */
  addressZip?: string;
  /**
   * Birthdate of the holder.
   *
   * @since 7.0.0
   */
  birthDate?: string;
  /**
   * "DL" for driver's licenses, "ID" for ID cards.
   *
   * @since 7.0.0
   */
  documentType?: string;
  /**
   * Expiration date of the license.
   *
   * @since 7.0.0
   */
  expiryDate?: string;
  /**
   * Holder's first name.
   *
   * @since 7.0.0
   */
  firstName?: string;
  /**
   * Holder's gender.
   *
   * @since 7.0.0
   */
  gender?: string;

  /**
   * Issue date of the license.
   *
   * @since 7.0.0
   */
  issueDate?: string;
  /**
   * ISO 3166-1 alpha-3 code in which DL/ID was issued.
   *
   * @since 7.0.0
   */
  issuingCountry?: string;
  /**
   * Holder's last name.
   *
   * @since 7.0.0
   */
  lastName?: string;
  /**
   * Driver license ID number.
   *
   * @since 7.0.0
   */
  licenseNumber?: string;
  /**
   * Holder's middle name.
   *
   * @since 7.0.0
   */
  middleName?: string;
}

/**
 * @since 7.0.0
 */
export interface BarcodeEmail {
  /**
   * The email address.
   *
   * @since 7.0.0
   */
  address?: string;
  /**
   * The email body.
   *
   * @since 7.0.0
   */
  body?: string;
  /**
   * The email subject.
   *
   * @since 7.0.0
   */
  subject?: string;
  /**
   * The email address type.
   *
   * @since 7.0.0
   */
  type: EmailFormatType;
}

/**
 * @since 7.0.0
 */
export interface BarcodeGeoPoint {
  /**
   * Latitude.
   *
   * @since 7.0.0
   */
  latitude?: number;
  /**
   * Longitude.
   *
   * @since 7.0.0
   */
  longitude?: number;
}

/**
 * @since 7.0.0
 */
export interface BarcodePhone {
  /**
   * The phone number.
   *
   * @since 7.0.0
   */
  number?: string;
  /**
   * The phone number type.
   *
   * @since 7.0.0
   */
  type?: PhoneFormatType;
}

/**
 * @since 7.0.0
 */
export interface PersonName {
  /**
   * First name.
   *
   * @since 7.0.0
   */
  first?: string;
  /**
   * The formatted name.
   *
   * @since 7.0.0
   */
  formattedName?: string;
  /**
   * Last name.
   *
   * @since 7.0.0
   */
  last?: string;
  /**
   * Middle name.
   *
   * @since 7.0.0
   */
  middle?: string;
  /**
   * Name prefix.
   *
   * @since 7.0.0
   */
  prefix?: string;
  /**
   * Text string to be set as the kana name in the phonebook. Used for Japanese contacts.
   *
   * @since 7.0.0
   */
  pronunciation?: string;
  /**
   * Name suffix.
   *
   * @since 7.0.0
   */
  suffix?: string;
}

/**
 * @since 7.0.0
 */
export interface BarcodeSms {
  /**
   * The phone number of the sms.
   *
   * @since 7.0.0
   */
  phoneNumber?: string;
  /**
   * The message content of the sms.
   *
   * @since 7.0.0
   */
  message?: string;
}

/**
 * @since 7.0.0
 */
export interface BarcodeUrlBookmark {
  /**
   * The URL of the bookmark.
   *
   * @since 7.0.0
   */
  url?: string;
  /**
   * The title of the bookmark.
   *
   * @since 7.0.0
   */
  title?: string;
}

/**
 * @since 7.0.0
 */
export interface BarcodeWifi {
  /**
   * Encryption type of the WI-FI.
   *
   * @since 7.0.0
   */
  encryptionType: WifiEncryptionType;
  /**
   * Password of the WI-FI.
   *
   * @since 7.0.0
   */
  password?: string;
  /**
   * SSID of the WI-FI.
   *
   * @since 7.0.0
   */
  ssid?: string;
}

/**
 * @since 0.0.1
 */
export enum BarcodeFormat {
  /**
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  Aztec = 'AZTEC',
  /**
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  Codabar = 'CODABAR',
  /**
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  Code39 = 'CODE_39',
  /**
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  Code93 = 'CODE_93',
  /**
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  Code128 = 'CODE_128',
  /**
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  DataMatrix = 'DATA_MATRIX',
  /**
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  Ean8 = 'EAN_8',
  /**
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  Ean13 = 'EAN_13',
  /**
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  Itf = 'ITF',
  /**
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  Pdf417 = 'PDF_417',
  /**
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  QrCode = 'QR_CODE',
  /**
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  UpcA = 'UPC_A',
  /**
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  UpcE = 'UPC_E',
}

/**
 * @since 0.0.1
 */
export enum BarcodeValueType {
  /**
   * @since 0.0.1
   */
  CalendarEvent = 'CALENDAR_EVENT',
  /**
   * @since 0.0.1
   */
  ContactInfo = 'CONTACT_INFO',
  /**
   * @since 0.0.1
   */
  DriversLicense = 'DRIVERS_LICENSE',
  /**
   * @since 0.0.1
   */
  Email = 'EMAIL',
  /**
   * @since 0.0.1
   */
  Geo = 'GEO',
  /**
   * @since 0.0.1
   */
  Isbn = 'ISBN',
  /**
   * @since 0.0.1
   */
  Phone = 'PHONE',
  /**
   * @since 0.0.1
   */
  Product = 'PRODUCT',
  /**
   * @since 0.0.1
   */
  Sms = 'SMS',
  /**
   * @since 0.0.1
   */
  Text = 'TEXT',
  /**
   * @since 0.0.1
   */
  Url = 'URL',
  /**
   * @since 0.0.1
   */
  Wifi = 'WIFI',
  /**
   * @since 0.0.1
   */
  Unknown = 'UNKNOWN',
}

/**
 * @since 7.0.0
 */
export enum Resolution {
  /**
   * @since 7.0.0
   */
  '640x480' = 0,
  /**
   * @since 7.0.0
   */
  '1280x720' = 1,
  /**
   * @since 7.0.0
   */
  '1920x1080' = 2,
}

/**
 * @since 0.0.1
 */
export enum LensFacing {
  /**
   * @since 0.0.1
   */
  Front = 'FRONT',
  /**
   * @since 0.0.1
   */
  Back = 'BACK',
}

/**
 * @since 5.1.0
 */
export enum GoogleBarcodeScannerModuleInstallState {
  /**
   * @since 5.1.0
   */
  UNKNOWN = 0,
  /**
   * @since 5.1.0
   */
  PENDING = 1,
  /**
   * @since 5.1.0
   */
  DOWNLOADING = 2,
  /**
   * @since 5.1.0
   */
  CANCELED = 3,
  /**
   * @since 5.1.0
   */
  COMPLETED = 4,
  /**
   * @since 5.1.0
   */
  FAILED = 5,
  /**
   * @since 5.1.0
   */
  INSTALLING = 6,
  /**
   * @since 5.1.0
   */
  DOWNLOAD_PAUSED = 7,
}

/**
 * @since 7.0.0
 */
export enum AddressType {
  /**
   * @since 7.0.0
   */
  HOME = 0,
  /**
   * @since 7.0.0
   */
  UNKNOWN = 1,
  /**
   * @since 7.0.0
   */
  WORK = 2,
}

/**
 * @since 7.0.0
 */
export enum EmailFormatType {
  /**
   * @since 7.0.0
   */
  HOME = 0,
  /**
   * @since 7.0.0
   */
  UNKNOWN = 1,
  /**
   * @since 7.0.0
   */
  WORK = 2,
}

/**
 * @since 7.0.0
 */
export enum PhoneFormatType {
  /**
   * @since 7.0.0
   */
  FAX = 0,
  /**
   * @since 7.0.0
   */
  HOME = 1,
  /**
   * @since 7.0.0
   */
  MOBILE = 2,
  /**
   * @since 7.0.0
   */
  UNKNOWN = 3,
  /**
   * @since 7.0.0
   */
  WORK = 4,
}

/**
 * @since 7.0.0
 */
export enum WifiEncryptionType {
  /**
   * @since 7.0.0
   */
  OPEN = 1,
  /**
   * @since 7.0.0
   */
  WEP = 2,
  /**
   * @since 7.0.0
   */
  WPA = 3,
}
