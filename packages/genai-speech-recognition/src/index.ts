import { registerPlugin } from '@capacitor/core';

import type { GenAiSpeechRecognitionPlugin } from './definitions';

const GenAiSpeechRecognition = registerPlugin<GenAiSpeechRecognitionPlugin>(
  'GenAiSpeechRecognition',
  {
    web: () => import('./web').then(m => new m.GenAiSpeechRecognitionWeb()),
  },
);

export * from './definitions';
export { GenAiSpeechRecognition };
