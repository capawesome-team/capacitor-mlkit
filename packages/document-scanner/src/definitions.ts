import type { PluginListenerHandle } from '@capacitor/core';

export interface DocumentScannerPlugin {
  /**
   * Starts the document scanning process.
   *
   * Only available on Android.
   *
   * @since 7.3.0
   */
  scanDocument(options: ScanOptions): Promise<ScanResult>;
  /**
   * Check if the Google Document Scanner module is available.
   *
   * If the Google Document Scanner module is not available, you can install it by using `installGoogleDocumentScannerModule()`.
   *
   * Only available on Android.
   *
   * @since 7.3.0
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
   * @since 7.3.0
   */
  installGoogleDocumentScannerModule(): Promise<void>;
  /**
   * Called when the Google Document Scanner module is installed.
   *
   * Only available on Android.
   *
   * @since 7.3.0
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
   * @since 7.3.0
   */
  removeAllListeners(): Promise<void>;
}

/**
 * @since 7.3.0
 */
export interface ScanOptions {
  /**
   * Whether to allow importing from the photo gallery.
   *
   * @default false
   * @since 7.3.0
   */
  galleryImportAllowed?: boolean;
  /**
   * The maximum number of pages that can be scanned.
   *
   * @default 10
   * @since 7.3.0
   */
  pageLimit?: number;
  /**
   * The desired result formats.
   * Can be 'JPEG', 'PDF', or 'JPEG_PDF'.
   *
   * @default 'JPEG_PDF'
   * @since 7.3.0
   */
  resultFormats?: 'JPEG' | 'PDF' | 'JPEG_PDF';
  /**
   * The scanner mode.
   * BASE: Basic editing capabilities (crop, rotate, reorder pages, etc.).
   * BASE_WITH_FILTER: Adds image filters (grayscale, auto image enhancement, etc.) to the BASE mode.
   * FULL: Adds ML-enabled image cleaning capabilities (erase stains, fingers, etc.) to the BASE_WITH_FILTER mode. This mode will also allow future major features to be automatically added along with Google Play services updates, while the other two modes will maintain their current feature sets and only receive minor refinements.
   *
   * @default "FULL"
   * @since 7.3.0
   */
  scannerMode?: 'FULL' | 'BASE' | 'BASE_WITH_FILTER';
}

/**
 * @since 7.3.0
 */
export interface PdfInfo {
  /**
   * The URI of the generated PDF file.
   *
   * @since 7.3.0
   */
  uri: string;
  /**
   * The number of pages in the PDF.
   *
   * @since 7.3.0
   */
  pageCount: number;
}

/**
 * Result of a document scan operation.
 * @since 7.3.0
 */
export interface ScanResult {
  /**
   * An array of URIs for the scanned image pages (JPEG).
   * Present if 'JPEG' or 'JPEG_PDF' was requested in resultFormats.
   *
   * @since 7.3.0
   */
  scannedImages?: string[];
  /**
   * Information about the generated PDF.
   * Present if 'PDF' or 'JPEG_PDF' was requested in resultFormats.
   *
   * @since 7.3.0
   */
  pdf?: PdfInfo;
}

/**
 * @since 7.3.0
 */
export interface IsGoogleDocumentScannerModuleAvailableResult {
  /**
   * Whether or not the Google Document Scanner module is available.
   *
   * @since 7.3.0
   */
  available: boolean;
}

/**
 * @since 7.3.0
 */
export interface GoogleDocumentScannerModuleInstallProgressEvent {
  /**
   * The current state of the installation.
   *
   * @since 7.3.0
   */
  state: GoogleDocumentScannerModuleInstallState;
  /**
   * The progress of the installation in percent between 0 and 100.
   *
   * @since 7.3.0
   */
  progress?: number;
}

/**
 * @since 7.3.0
 */
export enum GoogleDocumentScannerModuleInstallState {
  /**
   * @since 7.3.0
   */
  UNKNOWN = 0,
  /**
   * @since 7.3.0
   */
  PENDING = 1,
  /**
   * @since 7.3.0
   */
  DOWNLOADING = 2,
  /**
   * @since 7.3.0
   */
  CANCELED = 3,
  /**
   * @since 7.3.0
   */
  COMPLETED = 4,
  /**
   * @since 7.3.0
   */
  FAILED = 5,
  /**
   * @since 7.3.0
   */
  INSTALLING = 6,
  /**
   * @since 7.3.0
   */
  DOWNLOAD_PAUSED = 7,
}
