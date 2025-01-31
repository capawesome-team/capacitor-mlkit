import { CapacitorException, ExceptionCode, WebPlugin } from '@capacitor/core';

import type { DetectedBarcode } from './barcode-detector';
import { BarcodeValueType, LensFacing } from './definitions';
import type {
  BarcodeFormat,
  BarcodesScannedEvent,
  BarcodeScannerPlugin,
  GetMaxZoomRatioResult,
  GetMinZoomRatioResult,
  GetZoomRatioResult,
  IsGoogleBarcodeScannerModuleAvailableResult,
  IsSupportedResult,
  PermissionStatus,
  ReadBarcodesFromImageOptions,
  ReadBarcodesFromImageResult,
  ScanResult,
  SetZoomRatioOptions,
  StartScanOptions,
} from './definitions';

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
    _options: ReadBarcodesFromImageOptions,
  ): Promise<ReadBarcodesFromImageResult> {
    throw this.createUnavailableException();
  }

  async scan(): Promise<ScanResult> {
    throw this.createUnavailableException();
  }

  async isSupported(): Promise<IsSupportedResult> {
    return { supported: this._isSupported };
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
      barcodes: barcodes.map(barcode => ({
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
      })),
    };
    this.notifyListeners(this.eventBarcodesScanned, result);
  }
}
