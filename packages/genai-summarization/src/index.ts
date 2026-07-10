import { registerPlugin } from '@capacitor/core';

import type { GenAiSummarizationPlugin } from './definitions';

const GenAiSummarization = registerPlugin<GenAiSummarizationPlugin>(
  'GenAiSummarization',
  {
    web: () => import('./web').then(m => new m.GenAiSummarizationWeb()),
  },
);

export * from './definitions';
export { GenAiSummarization };
