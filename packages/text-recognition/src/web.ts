import { CapacitorException, ExceptionCode, WebPlugin } from '@capacitor/core';

import type {
  TextRecognitionPlugin,
  ProcessImageOptions,
  ProcessImageResult,
} from './definitions';

export class TextRecognitionWeb
  extends WebPlugin
  implements TextRecognitionPlugin
{
  public async processImage(
    _options: ProcessImageOptions,
  ): Promise<ProcessImageResult> {
    throw this.createUnimplementedException();
  }

  private createUnimplementedException(): CapacitorException {
    return new CapacitorException(
      'This method is not implemented on web.',
      ExceptionCode.Unimplemented,
    );
  }
}
