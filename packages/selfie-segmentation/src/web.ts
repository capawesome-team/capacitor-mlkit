import { CapacitorException, ExceptionCode, WebPlugin } from '@capacitor/core';

import type {
  SelfieSegmentationPlugin,
  ProcessImageOptions,
  ProcessImageResult,
} from './definitions';

export class SelfieSegmentationWeb
  extends WebPlugin
  implements SelfieSegmentationPlugin
{
  public async processImage(
    _options: ProcessImageOptions,
  ): Promise<ProcessImageResult> {
    throw this.createUnavailableException();
  }

  private createUnavailableException(): CapacitorException {
    return new CapacitorException(
      'This Selfie Segmentation plugin method is not available on this platform.',
      ExceptionCode.Unavailable,
    );
  }
}
