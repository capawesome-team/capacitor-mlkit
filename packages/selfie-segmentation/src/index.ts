import { registerPlugin } from '@capacitor/core';

import type { SelfieSegmentationPlugin } from './definitions';

const SelfieSegmentation = registerPlugin<SelfieSegmentationPlugin>(
  'SelfieSegmentation',
  {
    web: () => import('./web').then(m => new m.SelfieSegmentationWeb()),
  },
);

export * from './definitions';
export { SelfieSegmentation };
