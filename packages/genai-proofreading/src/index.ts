import { registerPlugin } from '@capacitor/core';

import type { GenAiProofreadingPlugin } from './definitions';

const GenAiProofreading = registerPlugin<GenAiProofreadingPlugin>(
  'GenAiProofreading',
  {
    web: () => import('./web').then(m => new m.GenAiProofreadingWeb()),
  },
);

export * from './definitions';
export { GenAiProofreading };
