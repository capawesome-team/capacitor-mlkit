import { CapacitorException, ExceptionCode, WebPlugin } from '@capacitor/core';

import type {
  DeleteDownloadedModelOptions,
  DigitalInkRecognitionPlugin,
  DownloadModel,
  GetDownloadedModelsResult,
  IsModelDownloadedOptions,
  IsModelDownloadedResult,
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
    throw this.createUnavailableException();
  }

  public async downloadModel(_options: DownloadModel): Promise<void> {
    throw this.createUnavailableException();
  }

  public async isModelDownloaded(
    _options: IsModelDownloadedOptions,
  ): Promise<IsModelDownloadedResult> {
    throw this.createUnavailableException();
  }

  public async getDownloadedModels(): Promise<GetDownloadedModelsResult> {
    throw this.createUnavailableException();
  }

  public async recognize(_options: RecognizeOptions): Promise<RecognizeResult> {
    throw this.createUnavailableException();
  }

  private createUnavailableException(): CapacitorException {
    return new CapacitorException(
      'This Digital Ink Recognition plugin method is not available on this platform.',
      ExceptionCode.Unavailable,
    );
  }
}
