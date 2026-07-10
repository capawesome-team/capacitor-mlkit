import { registerPlugin } from '@capacitor/core';

import type { ImageLabelingPlugin } from './definitions';

const ImageLabeling = registerPlugin<ImageLabelingPlugin>('ImageLabeling', {
  web: () => import('./web').then(m => new m.ImageLabelingWeb()),
});

export * from './definitions';
export { ImageLabeling };
