import { registerPlugin } from '@capacitor/core';

import type { TranslationPlugin } from './definitions';

const Translation = registerPlugin<TranslationPlugin>('Translation', {
  web: () => import('./web').then(m => new m.TranslationWeb()),
});

export * from './definitions';
export { Translation };
