import { WebPlugin } from '@capacitor/core';
import type { BarcodeScannerPlugin, GetMaxZoomRatioResult, GetMinZoomRatioResult, GetZoomRatioResult, IsGoogleBarcodeScannerModuleAvailableResult, IsSupportedResult, PermissionStatus, ReadBarcodesFromImageOptions, ReadBarcodesFromImageResult, ScanResult, SetZoomRatioOptions, StartScanOptions, IsTorchEnabledResult, IsTorchAvailableResult } from './definitions';
declare global {
    const BarcodeDetector: typeof import('./barcode-detector').BarcodeDetector;
}
export declare class BarcodeScannerWeb extends WebPlugin implements BarcodeScannerPlugin {
    private readonly _isSupported;
    private readonly errorVideoElementMissing;
    private readonly eventBarcodesScanned;
    private intervalId;
    private stream;
    private videoElement;
    startScan(options?: StartScanOptions): Promise<void>;
    stopScan(): Promise<void>;
    pauseScan(): Promise<void>;
    resumeScan(): Promise<void>;
    readBarcodesFromImage(_options: ReadBarcodesFromImageOptions): Promise<ReadBarcodesFromImageResult>;
    scan(): Promise<ScanResult>;
    isSupported(): Promise<IsSupportedResult>;
    enableTorch(): Promise<void>;
    disableTorch(): Promise<void>;
    toggleTorch(): Promise<void>;
    isTorchEnabled(): Promise<IsTorchEnabledResult>;
    isTorchAvailable(): Promise<IsTorchAvailableResult>;
    setZoomRatio(_options: SetZoomRatioOptions): Promise<void>;
    getZoomRatio(): Promise<GetZoomRatioResult>;
    getMinZoomRatio(): Promise<GetMinZoomRatioResult>;
    getMaxZoomRatio(): Promise<GetMaxZoomRatioResult>;
    openSettings(): Promise<void>;
    isGoogleBarcodeScannerModuleAvailable(): Promise<IsGoogleBarcodeScannerModuleAvailableResult>;
    installGoogleBarcodeScannerModule(): Promise<void>;
    checkPermissions(): Promise<PermissionStatus>;
    requestPermissions(): Promise<PermissionStatus>;
    private createUnavailableException;
    private handleScannedBarcodes;
}
