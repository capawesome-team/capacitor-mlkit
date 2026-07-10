import { CapacitorException, ExceptionCode, WebPlugin } from '@capacitor/core';

import type {
  CheckFeatureStatusResult,
  DescribeImageOptions,
  DescribeImageResult,
  GenAiImageDescriptionPlugin,
} from './definitions';

export class GenAiImageDescriptionWeb
  extends WebPlugin
  implements GenAiImageDescriptionPlugin
{
  public async checkFeatureStatus(): Promise<CheckFeatureStatusResult> {
    throw this.createUnimplementedException();
  }

  public async describeImage(
    _options: DescribeImageOptions,
  ): Promise<DescribeImageResult> {
    throw this.createUnimplementedException();
  }

  public async downloadFeature(): Promise<void> {
    throw this.createUnimplementedException();
  }

  private createUnimplementedException(): CapacitorException {
    return new CapacitorException(
      'This plugin method is not implemented on this platform.',
      ExceptionCode.Unimplemented,
    );
  }
}
