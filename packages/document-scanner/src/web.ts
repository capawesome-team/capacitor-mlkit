import { WebPlugin } from '@capacitor/core';

import type { DocumentScannerPlugin, ScanResult } from './definitions';

export class DocumentScannerWeb
  extends WebPlugin
  implements DocumentScannerPlugin
{
  async scanDocument(): Promise<ScanResult> {
    console.warn('Document scanning is not available on the web.');
    throw this.unavailable('Document scanning is not available on the web.');
  }
}
