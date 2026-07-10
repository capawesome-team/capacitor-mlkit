import { CapacitorException, ExceptionCode, WebPlugin } from '@capacitor/core';

import type {
  CheckFeatureStatusResult,
  GenAiPromptPlugin,
  GenerateContentOptions,
  GenerateContentResult,
} from './definitions';

export class GenAiPromptWeb extends WebPlugin implements GenAiPromptPlugin {
  public async checkFeatureStatus(): Promise<CheckFeatureStatusResult> {
    throw this.createUnimplementedException();
  }

  public async downloadFeature(): Promise<void> {
    throw this.createUnimplementedException();
  }

  public async generateContent(
    _options: GenerateContentOptions,
  ): Promise<GenerateContentResult> {
    throw this.createUnimplementedException();
  }

  private createUnimplementedException(): CapacitorException {
    return new CapacitorException(
      'This plugin method is not implemented on this platform.',
      ExceptionCode.Unimplemented,
    );
  }
}
