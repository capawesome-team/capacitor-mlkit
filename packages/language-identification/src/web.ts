import { CapacitorException, ExceptionCode, WebPlugin } from '@capacitor/core';

import type {
  IdentifyLanguageOptions,
  IdentifyLanguageResult,
  IdentifyPossibleLanguagesOptions,
  IdentifyPossibleLanguagesResult,
  LanguageIdentificationPlugin,
} from './definitions';

export class LanguageIdentificationWeb
  extends WebPlugin
  implements LanguageIdentificationPlugin
{
  public async identifyLanguage(
    _options: IdentifyLanguageOptions,
  ): Promise<IdentifyLanguageResult> {
    throw this.createUnimplementedException();
  }

  public async identifyPossibleLanguages(
    _options: IdentifyPossibleLanguagesOptions,
  ): Promise<IdentifyPossibleLanguagesResult> {
    throw this.createUnimplementedException();
  }

  private createUnimplementedException(): CapacitorException {
    return new CapacitorException(
      'This method is not implemented on web.',
      ExceptionCode.Unimplemented,
    );
  }
}
