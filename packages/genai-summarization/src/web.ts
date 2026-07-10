import { CapacitorException, ExceptionCode, WebPlugin } from '@capacitor/core';

import type {
  CheckFeatureStatusResult,
  FeatureOptions,
  GenAiSummarizationPlugin,
  SummarizeOptions,
  SummarizeResult,
} from './definitions';

export class GenAiSummarizationWeb
  extends WebPlugin
  implements GenAiSummarizationPlugin
{
  public async checkFeatureStatus(
    _options?: FeatureOptions,
  ): Promise<CheckFeatureStatusResult> {
    throw this.createUnimplementedException();
  }

  public async downloadFeature(_options?: FeatureOptions): Promise<void> {
    throw this.createUnimplementedException();
  }

  public async summarize(_options: SummarizeOptions): Promise<SummarizeResult> {
    throw this.createUnimplementedException();
  }

  private createUnimplementedException(): CapacitorException {
    return new CapacitorException(
      'This plugin method is not implemented on this platform.',
      ExceptionCode.Unimplemented,
    );
  }
}
