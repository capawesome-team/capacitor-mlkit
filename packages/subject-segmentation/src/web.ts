import { CapacitorException, ExceptionCode, WebPlugin } from '@capacitor/core';

import type {
  SubjectSegmentationPlugin,
  ProcessImageOptions,
  ProcessImageResult,
  IsGoogleSubjectSegmentationModuleAvailableResult,
} from './definitions';

export class SubjectSegmentationWeb
  extends WebPlugin
  implements SubjectSegmentationPlugin
{
  public async processImage(
    _options: ProcessImageOptions,
  ): Promise<ProcessImageResult> {
    throw this.createUnimplementedException();
  }

  private createUnimplementedException(): CapacitorException {
    return new CapacitorException(
      'This plugin method is not implemented on this platform.',
      ExceptionCode.Unimplemented,
    );
  }

  async isGoogleSubjectSegmentationModuleAvailable(): Promise<IsGoogleSubjectSegmentationModuleAvailableResult> {
    throw this.createUnimplementedException();
  }

  async installGoogleSubjectSegmentationModule(): Promise<void> {
    throw this.createUnimplementedException();
  }
}
