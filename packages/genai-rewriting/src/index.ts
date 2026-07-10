import { registerPlugin } from '@capacitor/core';

import type { GenAiRewritingPlugin } from './definitions';

const GenAiRewriting = registerPlugin<GenAiRewritingPlugin>('GenAiRewriting', {
  web: () => import('./web').then(m => new m.GenAiRewritingWeb()),
});

export * from './definitions';
export { GenAiRewriting };
