import { registerPlugin } from '@capacitor/core';

import type { EntityExtractionPlugin } from './definitions';

const EntityExtraction = registerPlugin<EntityExtractionPlugin>(
  'EntityExtraction',
  {
    web: () => import('./web').then(m => new m.EntityExtractionWeb()),
  },
);

export * from './definitions';
export { EntityExtraction };
