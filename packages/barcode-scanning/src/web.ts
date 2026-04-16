import { CapacitorException, ExceptionCode, WebPlugin } from '@capacitor/core';

import type {
  BarcodeDetectorFormat,
  DetectedBarcode,
} from './barcode-detector';
import type {
  Barcode,
  BarcodeFormat,
  BarcodeScannerPlugin,
  BarcodesScannedEvent,
  GetMaxZoomRatioResult,
  GetMinZoomRatioResult,
  GetZoomRatioResult,
  IsGoogleBarcodeScannerModuleAvailableResult,
  IsSupportedResult,
  IsTorchAvailableResult,
  IsTorchEnabledResult,
  PermissionStatus,
  ReadBarcodesFromImageOptions,
  ReadBarcodesFromImageResult,
  ScanResult,
  SetZoomRatioOptions,
  StartScanOptions,
} from './definitions';
import { BarcodeValueType, LensFacing } from './definitions';

declare global {
  // eslint-disable-next-line @typescript-eslint/consistent-type-imports
  const BarcodeDetector: typeof import('./barcode-detector').BarcodeDetector;
}

export class BarcodeScannerWeb
  extends WebPlugin
  implements BarcodeScannerPlugin
{
  private readonly _isSupported = 'BarcodeDetector' in window;
  private readonly errorVideoElementMissing = 'videoElement must be provided.';
  private readonly errorPathMissing = 'path must be provided.';
  private readonly eventBarcodesScanned = 'barcodesScanned';

  private intervalId: number | undefined;
  private stream: MediaStream | undefined;
  private videoElement: HTMLVideoElement | undefined;

  async startScan(options?: StartScanOptions): Promise<void> {
    if (!this._isSupported) {
      throw this.createUnavailableException();
    }
    if (!options?.videoElement) {
      throw new Error(this.errorVideoElementMissing);
    }
    this.videoElement = options.videoElement;
    this.stream = await navigator.mediaDevices.getUserMedia({
      video: {
        facingMode: {
          ideal:
            options?.lensFacing === LensFacing.Front ? 'user' : 'environment',
        },
      },
      audio: false,
    });
    options.videoElement.srcObject = this.stream;
    await options.videoElement.play();
    const barcodeDetector = new BarcodeDetector();
    this.intervalId = window.setInterval(async () => {
      if (!options.videoElement) {
        return;
      }
      const barcodes = await barcodeDetector.detect(options.videoElement);
      if (barcodes.length === 0) {
        return;
      } else {
        this.handleScannedBarcodes(barcodes);
      }
    }, 500);
  }

  async stopScan(): Promise<void> {
    if (!this._isSupported) {
      throw this.createUnavailableException();
    }
    if (this.intervalId) {
      clearInterval(this.intervalId);
      this.intervalId = undefined;
    }
    if (this.stream) {
      this.stream.getTracks().forEach(track => track.stop());
      this.stream = undefined;
    }
    if (this.videoElement) {
      this.videoElement.srcObject = null;
      this.videoElement = undefined;
    }
  }

  async readBarcodesFromImage(
    options: ReadBarcodesFromImageOptions,
  ): Promise<ReadBarcodesFromImageResult> {
    if (!this._isSupported) {
      throw this.createUnavailableException();
    }
    if (!options.path) {
      throw new Error(this.errorPathMissing);
    }
    const formats = options.formats?.map(
      format => format.toLowerCase() as BarcodeDetectorFormat,
    );
    const barcodeDetector = new BarcodeDetector(
      formats?.length ? { formats } : undefined,
    );
    const fetchResponse = await fetch(options.path);
    const imageBlob = await fetchResponse.blob();
    const imageBitmap = await createImageBitmap(imageBlob);
    const detectedBarcodes = await barcodeDetector.detect(imageBitmap);
    return {
      barcodes: this.convertDetectedBarcodesToBarcodes(detectedBarcodes),
    };
  }

  async scan(): Promise<ScanResult> {
    throw this.createUnavailableException();
  }

  async isSupported(): Promise<IsSupportedResult> {
    return { supported: this._isSupported };
  }

  async enableTorch(): Promise<void> {
    throw this.createUnavailableException();
  }

  async disableTorch(): Promise<void> {
    throw this.createUnavailableException();
  }

  async toggleTorch(): Promise<void> {
    throw this.createUnavailableException();
  }

  async isTorchEnabled(): Promise<IsTorchEnabledResult> {
    throw this.createUnavailableException;
  }

  async isTorchAvailable(): Promise<IsTorchAvailableResult> {
    return { available: false };
  }

  async setZoomRatio(_options: SetZoomRatioOptions): Promise<void> {
    throw this.createUnavailableException();
  }

  async getZoomRatio(): Promise<GetZoomRatioResult> {
    throw this.createUnavailableException();
  }

  async getMinZoomRatio(): Promise<GetMinZoomRatioResult> {
    throw this.createUnavailableException();
  }

  async getMaxZoomRatio(): Promise<GetMaxZoomRatioResult> {
    throw this.createUnavailableException();
  }

  async openSettings(): Promise<void> {
    throw this.createUnavailableException();
  }

  async isGoogleBarcodeScannerModuleAvailable(): Promise<IsGoogleBarcodeScannerModuleAvailableResult> {
    throw this.createUnavailableException();
  }

  async installGoogleBarcodeScannerModule(): Promise<void> {
    throw this.createUnavailableException();
  }

  async checkPermissions(): Promise<PermissionStatus> {
    try {
      const result = await navigator.permissions.query({
        name: 'camera' as any,
      });
      return {
        camera: result.state,
      };
    } catch (error) {
      return {
        camera: 'prompt',
      };
    }
  }

  async requestPermissions(): Promise<PermissionStatus> {
    try {
      const stream = await navigator.mediaDevices.getUserMedia({ video: true });
      stream.getTracks().forEach(track => track.stop());
      return {
        camera: 'granted',
      };
    } catch (error) {
      return {
        camera: 'denied',
      };
    }
  }

  private createUnavailableException(): CapacitorException {
    return new CapacitorException(
      'This plugin method is not available on this platform.',
      ExceptionCode.Unavailable,
    );
  }

  private handleScannedBarcodes(barcodes: DetectedBarcode[]): void {
    const result: BarcodesScannedEvent = {
      barcodes: this.convertDetectedBarcodesToBarcodes(barcodes),
    };
    this.notifyListeners(this.eventBarcodesScanned, result);
  }

  private convertDetectedBarcodesToBarcodes(
    barcodes: DetectedBarcode[],
  ): Barcode[] {
    return barcodes.map(barcode => ({
      cornerPoints: [
        [barcode.cornerPoints[0].x, barcode.cornerPoints[0].y],
        [barcode.cornerPoints[1].x, barcode.cornerPoints[1].y],
        [barcode.cornerPoints[2].x, barcode.cornerPoints[2].y],
        [barcode.cornerPoints[3].x, barcode.cornerPoints[3].y],
      ],
      displayValue: barcode.rawValue,
      rawValue: barcode.rawValue,
      format: barcode.format.toUpperCase() as BarcodeFormat,
      valueType: BarcodeValueType.Unknown,
    }));
  }
}
