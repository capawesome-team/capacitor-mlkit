import { registerPlugin } from '@capacitor/core';

import type { GenAiImageDescriptionPlugin } from './definitions';

const GenAiImageDescription = registerPlugin<GenAiImageDescriptionPlugin>(
  'GenAiImageDescription',
  {
    web: () => import('./web').then(m => new m.GenAiImageDescriptionWeb()),
  },
);

export * from './definitions';
export { GenAiImageDescription };
