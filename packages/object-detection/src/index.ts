import { registerPlugin } from '@capacitor/core';

import type { ObjectDetectionPlugin } from './definitions';

const ObjectDetection = registerPlugin<ObjectDetectionPlugin>(
  'ObjectDetection',
  {
    web: () => import('./web').then(m => new m.ObjectDetectionWeb()),
  },
);

export * from './definitions';
export { ObjectDetection };
