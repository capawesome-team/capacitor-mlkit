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
    throw this.createUnimplementedException();
  }

  private createUnimplementedException(): CapacitorException {
    return new CapacitorException(
      'This method is not implemented on web.',
      ExceptionCode.Unimplemented,
    );
  }
}
