import { WebPlugin } from '@capacitor/core';

import type { FaceMeshDetectionPlugin } from './definitions';

export class FaceMeshDetectionWeb
  extends WebPlugin
  implements FaceMeshDetectionPlugin
{
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
