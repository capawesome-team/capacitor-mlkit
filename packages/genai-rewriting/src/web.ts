import { CapacitorException, ExceptionCode, WebPlugin } from '@capacitor/core';

import type {
  CheckFeatureStatusResult,
  FeatureOptions,
  GenAiRewritingPlugin,
  RewriteOptions,
  RewriteResult,
} from './definitions';

export class GenAiRewritingWeb
  extends WebPlugin
  implements GenAiRewritingPlugin
{
  public async checkFeatureStatus(
    _options?: FeatureOptions,
  ): Promise<CheckFeatureStatusResult> {
    throw this.createUnimplementedException();
  }

  public async downloadFeature(_options?: FeatureOptions): Promise<void> {
    throw this.createUnimplementedException();
  }

  public async rewrite(_options: RewriteOptions): Promise<RewriteResult> {
    throw this.createUnimplementedException();
  }

  private createUnimplementedException(): CapacitorException {
    return new CapacitorException(
      'This plugin method is not implemented on this platform.',
      ExceptionCode.Unimplemented,
    );
  }
}
