import { CapacitorException, ExceptionCode, WebPlugin } from '@capacitor/core';

import type {
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
}
