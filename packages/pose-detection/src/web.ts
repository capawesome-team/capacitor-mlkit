import { CapacitorException, ExceptionCode, WebPlugin } from '@capacitor/core';

import type {
  PoseDetectionPlugin,
  ProcessImageOptions,
  ProcessImageResult,
} from './definitions';

export class PoseDetectionWeb extends WebPlugin implements PoseDetectionPlugin {
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
