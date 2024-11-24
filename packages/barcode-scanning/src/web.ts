import { CapacitorException, ExceptionCode, WebPlugin } from '@capacitor/core';

import type {
  BarcodeFormat as PluginBarcodeFormat,
  BarcodesScannedEvent,
  BarcodeScannerPlugin,
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

import type { DetectedBarcode, BarcodeFormat as WebBarcodeFormat } from './barcode-detector';

export class BarcodeScannerWeb
  extends WebPlugin
  implements BarcodeScannerPlugin
{
  private readonly _isSupported = 'BarcodeDetector' in window;
  private intervalId: number | undefined;
  private stream: MediaStream | undefined;
  private videoElement: HTMLVideoElement | undefined;

  async startScan(options?: StartScanOptions): Promise<void> {
    if (!window.BarcodeDetector) {
      this.throwUnsupportedError();
    }

    this.videoElement = document.createElement('video');
    this.videoElement.style.position = 'absolute';
    this.videoElement.style.top = '0';
    this.videoElement.style.left = '0';
    this.videoElement.style.right = '0';
    this.videoElement.style.bottom = '0';
    this.videoElement.style.width = '100%';
    this.videoElement.style.height = '100%';
    this.videoElement.style.zIndex = '-9999';
    this.videoElement.style.objectFit = 'cover';
    if (options?.videoElementId) this.videoElement.id = options.videoElementId;
    document.body.appendChild(this.videoElement);

    this.stream = await navigator.mediaDevices.getUserMedia({
      video: {
        facingMode: {
          ideal: options?.lensFacing === LensFacing.Back ? 'environment' : 'user',
        },
      },
      audio: false,
    });
    this.videoElement.srcObject = this.stream;
    await this.videoElement.play();
    const barcodeDetector = new BarcodeDetector({
      formats: options?.formats?.map(format => format.toLowerCase() as WebBarcodeFormat),
    });
    this.intervalId = window.setInterval(async () => {
      const barcodes = await barcodeDetector.detect(this.videoElement!);
      if (barcodes.length === 0) {
        return;
      } else {
        this.handleScannedBarcodes(barcodes);
      }
    }, 1000); // Most use-cases don't need to scan faster than once per second, so it does not make sense to waste resources by scanning more frequently.
  }

  async stopScan(): Promise<void> {
    if (!this._isSupported) {
      this.throwUnsupportedError();
    }
    if (this.intervalId) {
      window.clearInterval(this.intervalId);
      this.intervalId = undefined;
    }
    if (this.stream) {
      this.stream.getTracks().forEach(track => {
        track.stop();
      });
      this.stream = undefined;
    }
    if (this.videoElement) {
      this.videoElement.remove();
      this.videoElement = undefined;
    }
  }

  async readBarcodesFromImage(
    _options: ReadBarcodesFromImageOptions,
  ): Promise<ReadBarcodesFromImageResult> {
    throw this.createUnimplementedException();
  }

  async scan(): Promise<ScanResult> {
    throw this.createUnimplementedException();
  }

  async isSupported(): Promise<IsSupportedResult> {
    return {
      supported: this._isSupported,
    };
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
    throw this.createUnavailableException();
  }

  async isTorchAvailable(): Promise<IsTorchAvailableResult> {
    throw this.createUnavailableException();
  }

  async setZoomRatio(_options: SetZoomRatioOptions): Promise<void> {
    throw this.createUnimplementedException();
  }

  async getZoomRatio(): Promise<GetZoomRatioResult> {
    throw this.createUnimplementedException();
  }

  async getMinZoomRatio(): Promise<GetMinZoomRatioResult> {
    throw this.createUnimplementedException();
  }

  async getMaxZoomRatio(): Promise<GetMaxZoomRatioResult> {
    throw this.createUnimplementedException();
  }

  async openSettings(): Promise<void> {
    throw this.createUnimplementedException();
  }

  async isGoogleBarcodeScannerModuleAvailable(): Promise<IsGoogleBarcodeScannerModuleAvailableResult> {
    throw this.createUnavailableException();
  }

  async installGoogleBarcodeScannerModule(): Promise<void> {
    throw this.createUnavailableException();
  }

  async checkPermissions(): Promise<PermissionStatus> {
    throw this.createUnavailableException();
  }

  async requestPermissions(): Promise<PermissionStatus> {
    throw this.createUnavailableException();
  }

  private createUnimplementedException(): CapacitorException {
    return new CapacitorException(
      'This Barcode Scanner plugin method is not implemented yet on this platform.',
      ExceptionCode.Unimplemented,
    );
  }

  private createUnavailableException(): CapacitorException {
    return new CapacitorException(
      'This Barcode Scanner plugin method is not available on this platform.',
      ExceptionCode.Unavailable,
    );
  }

  private throwUnsupportedError(): never {
    throw this.unavailable(
      'Barcode Detector API not available in this browser. You can install polyfill, check README.md for more information.',
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
        rawValue: barcode.rawValue,
        format: barcode.format.toUpperCase() as PluginBarcodeFormat,
        valueType: BarcodeValueType.Unknown,
      })),
    };
    this.notifyListeners('barcodesScanned', result);
  }
}

declare global {
  var BarcodeDetector: typeof import("./barcode-detector").BarcodeDetector;
}
