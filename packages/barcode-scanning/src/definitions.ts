import type { PermissionState, PluginListenerHandle } from '@capacitor/core';

export interface BarcodeScannerPlugin {
  /**
   * Start scanning for barcodes.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  startScan(options?: StartScanOptions): Promise<void>;
  /**
   * Stop scanning for barcodes.
   *
   * Only available on Android and iOS.
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
   * Available on Android and iOS.
   *
   * @since 0.0.1
   */
  isSupported(): Promise<IsSupportedResult>;
  /**
   * Enable camera's torch (flash) during a scan session.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  enableTorch(): Promise<void>;
  /**
   * Disable camera's torch (flash) during a scan session.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  disableTorch(): Promise<void>;
  /**
   * Toggle camera's torch (flash) during a scan session.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  toggleTorch(): Promise<void>;
  /**
   * Returns whether or not the camera's torch (flash) is enabled.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  isTorchEnabled(): Promise<IsTorchEnabledResult>;
  /**
   * Returns whether or not the camera's torch (flash) is available.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  isTorchAvailable(): Promise<IsTorchAvailableResult>;
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
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  checkPermissions(): Promise<PermissionStatus>;
  /**
   * Request camera permission.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  requestPermissions(): Promise<PermissionStatus>;
  /**
   * Called when a barcode is scanned.
   *
   * Available on Android and iOS.
   *
   * @since 0.0.1
   */
  addListener(
    eventName: 'barcodeScanned',
    listenerFunc: (event: BarcodeScannedEvent) => void,
  ): Promise<PluginListenerHandle> & PluginListenerHandle;
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
  ): Promise<PluginListenerHandle> & PluginListenerHandle;
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
  ): Promise<PluginListenerHandle> & PluginListenerHandle;
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
   * The HTML video element to use for the camera preview.
   *
   * Only available on Web.
   *
   * @since 5.1.0
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
   * The barcode format.
   *
   * @since 0.0.1
   * @example "QR_CODE"
   */
  format: BarcodeFormat;
  /**
   * The barcode value in a machine readable format.
   *
   * @since 0.0.1
   * @example "CapacitorJS"
   */
  rawValue: string;
  /**
   * The barcode value type.
   *
   * @since 0.0.1
   * @example "TEXT"
   */
  valueType: BarcodeValueType;
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
