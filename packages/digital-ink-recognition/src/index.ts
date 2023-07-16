import { registerPlugin } from '@capacitor/core';

import type { DigitalInkRecognitionPlugin } from './definitions';

const DigitalInkRecognition = registerPlugin<DigitalInkRecognitionPlugin>(
  'DigitalInkRecognition',
  {
    web: () => import('./web').then(m => new m.DigitalInkRecognitionWeb()),
  },
);

export * from './definitions';
export { DigitalInkRecognition };
