import { CapacitorException, ExceptionCode, WebPlugin } from '@capacitor/core';

import type {
  SubjectSegmentationPlugin,
  ProcessImageOptions,
  ProcessImageResult,
  IsGoogleSubjectSegmentationModuleAvailableResult,
} from './definitions';

export class SubjectSegmentationWeb
  extends WebPlugin
  implements SubjectSegmentationPlugin {
  public async processImage(
    _options: ProcessImageOptions,
  ): Promise<ProcessImageResult> {
    throw this.createUnavailableException();
  }

  private createUnavailableException(): CapacitorException {
    return new CapacitorException(
      'This plugin method is not available on this platform.',
      ExceptionCode.Unavailable,
    );
  }

  async isGoogleSubjectSegmentationModuleAvailable(): Promise<IsGoogleSubjectSegmentationModuleAvailableResult> {
    throw this.createUnavailableException();
  }

  async installGoogleSubjectSegmentationModule(): Promise<void> {
    throw this.createUnavailableException();
  }
}
