import { CapacitorException, ExceptionCode, WebPlugin } from '@capacitor/core';

import type {
  FaceDetectionPlugin,
  ProcessImageOptions,
  ProcessImageResult,
} from './definitions';

export class FaceDetectionWeb extends WebPlugin implements FaceDetectionPlugin {
  public async processImage(
    _options: ProcessImageOptions,
  ): Promise<ProcessImageResult> {
    throw this.createUnavailableException();
  }

  private createUnavailableException(): CapacitorException {
    return new CapacitorException(
      'This Face Detection plugin method is not available on this platform.',
      ExceptionCode.Unavailable,
    );
  }
}
