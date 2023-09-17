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
    throw this.createUnavailableException();
  }

  private createUnavailableException(): CapacitorException {
    return new CapacitorException(
      'This Face Mesh Detection plugin method is not available on this platform.',
      ExceptionCode.Unavailable,
    );
  }
}
