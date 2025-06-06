import type { PluginListenerHandle } from '@capacitor/core';

export interface DocumentScannerPlugin {
  /**
   * Starts the document scanning process.
   *
   * Only available on Android.
   *
   * @since 7.2.1
   * @param options Configuration options for the scanner.
   * @returns A promise that resolves with the scan result.
   */
  scanDocument(options: ScanOptions): Promise<ScanResult>;

  /**
   * Check if the Google Document Scanner module is available.
   *
   * If the Google Document Scanner module is not available, you can install it by using `installGoogleDocumentScannerModule()`.
   *
   * Only available on Android.
   *
   * @since 7.2.1
   */
  isGoogleDocumentScannerModuleAvailable(): Promise<IsGoogleDocumentScannerModuleAvailableResult>;
  /**
   * Install the Google Document Scanner module.
   *
   * **Attention**: This only starts the installation.
   * The `googleDocumentScannerModuleInstallProgress` event listener will
   * notify you when the installation is complete.
   *
   * Only available on Android.
   *
   * @since 7.2.1
   */
  installGoogleDocumentScannerModule(): Promise<void>;

  /**
   * Called when the Google Document Scanner module is installed.
   *
   * Only available on Android.
   *
   * @since 7.2.1
   */
  addListener(
    eventName: 'googleDocumentScannerModuleInstallProgress',
    listenerFunc: (
      event: GoogleDocumentScannerModuleInstallProgressEvent,
    ) => void,
  ): Promise<PluginListenerHandle>;

  /**
   * Remove all listeners for this plugin.
   *
   * Only available on Android.
   *
   * @since 7.2.1
   */
  removeAllListeners(): Promise<void>;
}

/**
 * Options for the document scanner.
 * @since 7.2.1
 */
export interface ScanOptions {
  /**
   * Whether to allow importing from the photo gallery.
   * @default false
   * @since 7.2.1
   */
  galleryImportAllowed?: boolean;
  /**
   * The maximum number of pages that can be scanned.
   * @default 10
   * @since 7.2.1
   */
  pageLimit?: number;
  /**
   * The desired result formats.
   * Can be 'JPEG', 'PDF', or 'JPEG_PDF'.
   * @default 'JPEG_PDF'
   * @since 7.2.1
   */
  resultFormats?: 'JPEG' | 'PDF' | 'JPEG_PDF';
  /**
   * The scanner mode.
   * BASE: Basic editing capabilities (crop, rotate, reorder pages, etc.).
   * BASE_WITH_FILTER: Adds image filters (grayscale, auto image enhancement, etc.) to the BASE mode.
   * FULL: Adds ML-enabled image cleaning capabilities (erase stains, fingers, etc.) to the BASE_WITH_FILTER mode. This mode will also allow future major features to be automatically added along with Google Play services updates, while the other two modes will maintain their current feature sets and only receive minor refinements.
   * @default "FULL"
   * @since 7.2.1
   */
  scannerMode?: 'FULL' | 'BASE' | 'BASE_WITH_FILTER';
}

/**
 * Information about a generated PDF document.
 * @since 7.2.1
 */
export interface PdfInfo {
  /**
   * The URI of the generated PDF file.
   * @since 7.2.1
   */
  uri: string;
  /**
   * The number of pages in the PDF.
   * @since 7.2.1
   */
  pageCount: number;
}

/**
 * Result of a document scan operation.
 * @since 7.2.1
 */
export interface ScanResult {
  /**
   * An array of URIs for the scanned image pages (JPEG).
   * Present if 'JPEG' or 'JPEG_PDF' was requested in resultFormats.
   * @since 7.2.1
   */
  scannedImages?: string[];
  /**
   * Information about the generated PDF.
   * Present if 'PDF' or 'JPEG_PDF' was requested in resultFormats.
   * @since 7.2.1
   */
  pdf?: PdfInfo;
}

/**
 * @since 7.2.1
 */
export interface IsGoogleDocumentScannerModuleAvailableResult {
  /**
   * Whether or not the Google Document Scanner module is available.
   *
   * @since 7.2.1
   */
  available: boolean;
}

/**
 * @since 7.2.1
 */
export interface GoogleDocumentScannerModuleInstallProgressEvent {
  /**
   * The current state of the installation.
   *
   * @since 7.2.1
   */
  state: GoogleDocumentScannerModuleInstallState;
  /**
   * The progress of the installation in percent between 0 and 100.
   *
   * @since 7.2.1
   */
  progress?: number;
}

/**
 * @since 7.2.1
 */
export enum GoogleDocumentScannerModuleInstallState {
  /**
   * @since 7.2.1
   */
  UNKNOWN = 0,
  /**
   * @since 7.2.1
   */
  PENDING = 1,
  /**
   * @since 7.2.1
   */
  DOWNLOADING = 2,
  /**
   * @since 7.2.1
   */
  CANCELED = 3,
  /**
   * @since 7.2.1
   */
  COMPLETED = 4,
  /**
   * @since 7.2.1
   */
  FAILED = 5,
  /**
   * @since 7.2.1
   */
  INSTALLING = 6,
  /**
   * @since 7.2.1
   */
  DOWNLOAD_PAUSED = 7,
}
