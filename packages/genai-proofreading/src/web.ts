import { CapacitorException, ExceptionCode, WebPlugin } from '@capacitor/core';

import type {
  CheckFeatureStatusResult,
  FeatureOptions,
  GenAiProofreadingPlugin,
  ProofreadOptions,
  ProofreadResult,
} from './definitions';

export class GenAiProofreadingWeb
  extends WebPlugin
  implements GenAiProofreadingPlugin
{
  public async checkFeatureStatus(
    _options?: FeatureOptions,
  ): Promise<CheckFeatureStatusResult> {
    throw this.createUnimplementedException();
  }

  public async downloadFeature(_options?: FeatureOptions): Promise<void> {
    throw this.createUnimplementedException();
  }

  public async proofread(_options: ProofreadOptions): Promise<ProofreadResult> {
    throw this.createUnimplementedException();
  }

  private createUnimplementedException(): CapacitorException {
    return new CapacitorException(
      'This plugin method is not implemented on this platform.',
      ExceptionCode.Unimplemented,
    );
  }
}
