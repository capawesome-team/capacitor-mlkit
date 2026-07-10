import { registerPlugin } from '@capacitor/core';

import type { GenAiPromptPlugin } from './definitions';

const GenAiPrompt = registerPlugin<GenAiPromptPlugin>('GenAiPrompt', {
  web: () => import('./web').then(m => new m.GenAiPromptWeb()),
});

export * from './definitions';
export { GenAiPrompt };
