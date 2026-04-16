import { CapacitorException, ExceptionCode, WebPlugin } from '@capacitor/core';

import type {
  FaceMeshDetectionPlugin,
  ProcessImageOptions,
  ProcessImageResult,
} from './definitions';

export class FaceMeshDetectionWeb
  extends WebPlugin
  implements FaceMeshDetectionPlugin
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
