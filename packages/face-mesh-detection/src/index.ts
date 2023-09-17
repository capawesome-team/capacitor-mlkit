import { registerPlugin } from '@capacitor/core';

import type { FaceMeshDetectionPlugin } from './definitions';

const FaceMeshDetection = registerPlugin<FaceMeshDetectionPlugin>(
  'FaceMeshDetection',
  {
    web: () => import('./web').then(m => new m.FaceMeshDetectionWeb()),
  },
);

export * from './definitions';
export { FaceMeshDetection };
