import { WebPlugin } from '@capacitor/core';

import type { TranslationPlugin } from './definitions';

export class TranslationWeb extends WebPlugin implements TranslationPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
