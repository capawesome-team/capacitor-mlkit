import { registerPlugin } from '@capacitor/core';

import type { PoseDetectionPlugin } from './definitions';

const PoseDetection = registerPlugin<PoseDetectionPlugin>('PoseDetection', {
  web: () => import('./web').then(m => new m.PoseDetectionWeb()),
});

export * from './definitions';
export { PoseDetection };
