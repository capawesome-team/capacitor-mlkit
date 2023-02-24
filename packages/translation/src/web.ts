import { WebPlugin } from '@capacitor/core';

import type {
  DeleteDownloadedModelOptions,
  DownloadModel,
  GetDownloadedModelsResult,
  TranslateOptions,
  TranslateResult,
  TranslationPlugin,
} from './definitions';

export class TranslationWeb extends WebPlugin implements TranslationPlugin {
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
