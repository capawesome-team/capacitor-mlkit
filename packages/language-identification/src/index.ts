import { registerPlugin } from '@capacitor/core';

import type { LanguageIdentificationPlugin } from './definitions';

const LanguageIdentification = registerPlugin<LanguageIdentificationPlugin>('LanguageIdentification', {
  web: () => import('./web').then(m => new m.LanguageIdentificationWeb()),
});

export * from './definitions';
export { LanguageIdentification };
