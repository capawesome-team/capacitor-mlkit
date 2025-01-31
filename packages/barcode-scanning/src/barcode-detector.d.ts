/**
 * The possible types of barcode format that can be detected using the
 * Barcode Detection API. This list may change in the future.
 * Adapted from: https://developer.mozilla.org/en-US/docs/Web/API/Barcode_Detection_API
 */
export type BarcodeDetectorFormat =
  | 'aztec'
  | 'code_128'
  | 'code_39'
  | 'code_93'
  | 'codabar'
  | 'data_matrix'
  | 'ean_13'
  | 'ean_8'
  | 'itf'
  | 'pdf417'
  | 'qr_code'
  | 'upc_a'
  | 'upc_e'
  | 'unknown';

/**
 * The return type of the Barcode Detect API `detect` function that
 * describes a barcode that has been recognized by the API.
 */
export interface DetectedBarcode {
  /**
   * A DOMRectReadOnly, which returns the dimensions of a rectangle
   * representing the extent of a detected barcode, aligned with the
   * image
   */
  boundingBox: DOMRectReadOnly;
  /**
   * The x and y co-ordinates of the four corner points of the detected
   * barcode relative to the image, starting with the top left and working
   * clockwise. This may not be square due to perspective distortions
   * within the image.
   */
  cornerPoints: [
    { x: number; y: number },
    { x: number; y: number },
    { x: number; y: number },
    { x: number; y: number },
  ];
  /**
   * The detected barcode format
   */
  format: BarcodeDetectorFormat;

  /**
   * A string decoded from the barcode data
   */
  rawValue: string;
}

/**
 * Options for describing how a BarcodeDetector should be initialised
 */
export interface BarcodeDetectorOptions {
  /**
   * Which formats the barcode detector should detect
   */
  formats?: BarcodeDetectorFormat[];
}

/**
 * The BarcodeDetector interface of the Barcode Detection API allows
 * detection of linear and two dimensional barcodes in images.
 */
export class BarcodeDetector {
  /**
   * Initialize a Barcode Detector instance
   */
  constructor(options?: BarcodeDetectorOptions);

  /**
   * Retrieve the formats that are supported by the detector
   */
  static getSupportedFormats(): Promise<BarcodeDetectorFormat[]>;

  /**
   * Attempt to detect barcodes from an image source
   */
  public detect(source: ImageBitmapSource): Promise<DetectedBarcode[]>;
}
