import { registerPlugin } from '@capacitor/core';

import type { TextRecognitionPlugin } from './definitions';

const TextRecognition = registerPlugin<TextRecognitionPlugin>(
  'TextRecognition',
  {
    web: () => import('./web').then(m => new m.TextRecognitionWeb()),
  },
);

export * from './definitions';
export { TextRecognition };
