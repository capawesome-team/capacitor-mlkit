import { WebPlugin } from '@capacitor/core';

import type {
  DeleteDownloadedModelOptions,
  DownloadModel,
  GetDownloadedModelsResult,
  TranslateOptions,
  TranslateResult,
  LanguageIdentificationPlugin,
} from './definitions';

export class LanguageIdentificationWeb extends WebPlugin implements LanguageIdentificationPlugin {
  public async deleteDownloadedModel(
    _options: DeleteDownloadedModelOptions,
  ): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  public async downloadModel(_options: DownloadModel): Promise<void> {
    throw this.unimplemented('Not implemented on web.');
  }

  public async getDownloadedModels(): Promise<GetDownloadedModelsResult> {
    throw this.unimplemented('Not implemented on web.');
  }

  public async translate(_options: TranslateOptions): Promise<TranslateResult> {
    throw this.unimplemented('Not implemented on web.');
  }
}
