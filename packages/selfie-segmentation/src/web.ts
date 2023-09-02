import { WebPlugin } from '@capacitor/core';

import type { SelfieSegmentationPlugin } from './definitions';

export class SelfieSegmentationWeb
  extends WebPlugin
  implements SelfieSegmentationPlugin
{
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
