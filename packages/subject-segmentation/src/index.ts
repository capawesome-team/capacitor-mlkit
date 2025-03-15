import { registerPlugin } from '@capacitor/core';

import type { SubjectSegmentationPlugin } from './definitions';

const SubjectSegmentation = registerPlugin<SubjectSegmentationPlugin>(
  'SubjectSegmentation',
  {
    web: () => import('./web').then(m => new m.SubjectSegmentationWeb()),
  },
);

export * from './definitions';
export { SubjectSegmentation };
