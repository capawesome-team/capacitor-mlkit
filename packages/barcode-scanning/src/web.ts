import { CapacitorException, ExceptionCode, WebPlugin } from '@capacitor/core';

import type {
  BarcodeScannerPlugin,
  IsSupportedResult,
  IsTorchAvailableResult,
  IsTorchEnabledResult,
  PermissionStatus,
  ReadBarcodesFromImageOptions,
  ReadBarcodesFromImageResult,
  ScanResult,
  StartScanOptions,
} from './definitions';

export class BarcodeScannerWeb
  extends WebPlugin
  implements BarcodeScannerPlugin
{
  async startScan(_options?: StartScanOptions): Promise<void> {
    throw this.createUnavailableException();
  }

  async stopScan(): Promise<void> {
    throw this.createUnavailableException();
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
    throw this.createUnavailableException();
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

  async openSettings(): Promise<void> {
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
}
