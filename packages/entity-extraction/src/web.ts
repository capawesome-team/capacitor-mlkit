import { CapacitorException, ExceptionCode, WebPlugin } from '@capacitor/core';

import type {
  DeleteDownloadedModelOptions,
  DownloadModelOptions,
  EntityExtractionPlugin,
  ExtractEntitiesOptions,
  ExtractEntitiesResult,
  GetDownloadedModelsResult,
} from './definitions';

export class EntityExtractionWeb
  extends WebPlugin
  implements EntityExtractionPlugin
{
  public async deleteDownloadedModel(
    _options: DeleteDownloadedModelOptions,
  ): Promise<void> {
    throw this.createUnimplementedException();
  }

  public async downloadModel(_options: DownloadModelOptions): Promise<void> {
    throw this.createUnimplementedException();
  }

  public async extractEntities(
    _options: ExtractEntitiesOptions,
  ): Promise<ExtractEntitiesResult> {
    throw this.createUnimplementedException();
  }

  public async getDownloadedModels(): Promise<GetDownloadedModelsResult> {
    throw this.createUnimplementedException();
  }

  private createUnimplementedException(): CapacitorException {
    return new CapacitorException(
      'This method is not implemented on web.',
      ExceptionCode.Unimplemented,
    );
  }
}
