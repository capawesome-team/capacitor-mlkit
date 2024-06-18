import { CapacitorException, ExceptionCode, WebPlugin } from '@capacitor/core';

import type {
  BarcodeScannedEvent,
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
import { BarcodeValueType } from './definitions';

export class BarcodeScannerWeb
  extends WebPlugin
  implements BarcodeScannerPlugin
{
  public static readonly ERROR_VIDEO_ELEMENT_MISSING =
    'videoElement must be provided.';
  private readonly _isSupported = 'BarcodeDetector' in window;
  private intervalId: number | undefined;
  private stream: MediaStream | undefined;

  async startScan(options?: StartScanOptions): Promise<void> {
    if (!this._isSupported) {
      this.throwUnsupportedError();
    }
    if (!options?.videoElement) {
      throw new Error(BarcodeScannerWeb.ERROR_VIDEO_ELEMENT_MISSING);
    }
    this.stream = await navigator.mediaDevices.getUserMedia({
      video: {
        facingMode: {
          ideal: 'environment',
        },
      },
      audio: false,
    });
    options.videoElement.srcObject = this.stream;
    await options.videoElement.play();
    const barcodeDetector = new window.BarcodeDetector({
      formats: ['qr_code'],
    });
    this.intervalId = window.setInterval(async () => {
      const barcodes = await barcodeDetector.detect(options.videoElement);
      if (barcodes.length === 0) {
        return;
      } else {
        for (const barcode of barcodes) {
          this.handleScannedBarcode(barcode);
        }
      }
    }, 1000);
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
    throw this.createUnavailableException();
  }

  async requestPermissions(): Promise<PermissionStatus> {
    throw this.createUnavailableException();
  }

  private createUnavailableException(): CapacitorException {
    return new CapacitorException(
      'This Barcode Scanner plugin method is not available on this platform.',
      ExceptionCode.Unavailable,
    );
  }

  private throwUnsupportedError(): never {
    throw this.unavailable(
      'Barcode Detector API not available in this browser.',
    );
  }

  private handleScannedBarcode(barcode: any): void {
    const result: BarcodeScannedEvent = {
      barcode: {
        displayValue: barcode.rawValue,
        rawValue: barcode.rawValue,
        format: barcode.format,
        valueType: BarcodeValueType.Unknown,
      },
    };
    this.notifyListeners('barcodeScanned', result);
  }
}

declare global {
  interface Window {
    BarcodeDetector: any;
  }
}
