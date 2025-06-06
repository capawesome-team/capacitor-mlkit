import { CapacitorException, ExceptionCode, WebPlugin } from '@capacitor/core';

import type {
  DocumentScannerPlugin,
  ScanResult,
  IsGoogleDocumentScannerModuleAvailableResult,
} from './definitions';

export class DocumentScannerWeb
  extends WebPlugin
  implements DocumentScannerPlugin
{
  private createUnimplementedException(): CapacitorException {
    return new CapacitorException(
      'This plugin method is not implemented on this platform.',
      ExceptionCode.Unimplemented,
    );
  }

  async scanDocument(): Promise<ScanResult> {
    throw this.createUnimplementedException();
  }

  async isGoogleDocumentScannerModuleAvailable(): Promise<IsGoogleDocumentScannerModuleAvailableResult> {
    throw this.createUnimplementedException();
  }

  async installGoogleDocumentScannerModule(): Promise<void> {
    throw this.createUnimplementedException();
  }
}
