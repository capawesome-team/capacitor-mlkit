import { registerPlugin } from '@capacitor/core';

import type { DocumentScannerPlugin } from './definitions';

const DocumentScanner = registerPlugin<DocumentScannerPlugin>(
  'DocumentScanner',
  {
    web: () => import('./web').then(m => new m.DocumentScannerWeb()),
  },
);

export * from './definitions';
export { DocumentScanner };
