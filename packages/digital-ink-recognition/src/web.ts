import { CapacitorException, ExceptionCode, WebPlugin } from '@capacitor/core';

import type {
  DeleteDownloadedModelOptions,
  DigitalInkRecognitionPlugin,
  DownloadModelOptions,
  GetDownloadedModelsResult,
  RecognizeOptions,
  RecognizeResult,
} from './definitions';

export class DigitalInkRecognitionWeb
  extends WebPlugin
  implements DigitalInkRecognitionPlugin
{
  public async deleteDownloadedModel(
    _options: DeleteDownloadedModelOptions,
  ): Promise<void> {
    throw this.createUnimplementedException();
  }

  public async downloadModel(_options: DownloadModelOptions): Promise<void> {
    throw this.createUnimplementedException();
  }

  public async getDownloadedModels(): Promise<GetDownloadedModelsResult> {
    throw this.createUnimplementedException();
  }

  public async recognize(_options: RecognizeOptions): Promise<RecognizeResult> {
    throw this.createUnimplementedException();
  }

  private createUnimplementedException(): CapacitorException {
    return new CapacitorException(
      'This method is not implemented on web.',
      ExceptionCode.Unimplemented,
    );
  }
}
