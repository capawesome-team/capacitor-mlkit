import { CapacitorException, ExceptionCode, WebPlugin } from '@capacitor/core';

import type {
  CheckFeatureStatusResult,
  FeatureOptions,
  GenAiSpeechRecognitionPlugin,
  PermissionStatus,
  StartRecognitionOptions,
} from './definitions';

export class GenAiSpeechRecognitionWeb
  extends WebPlugin
  implements GenAiSpeechRecognitionPlugin
{
  public async checkFeatureStatus(
    _options?: FeatureOptions,
  ): Promise<CheckFeatureStatusResult> {
    throw this.createUnimplementedException();
  }

  public async checkPermissions(): Promise<PermissionStatus> {
    throw this.createUnimplementedException();
  }

  public async downloadFeature(_options?: FeatureOptions): Promise<void> {
    throw this.createUnimplementedException();
  }

  public async requestPermissions(): Promise<PermissionStatus> {
    throw this.createUnimplementedException();
  }

  public async startRecognition(
    _options?: StartRecognitionOptions,
  ): Promise<void> {
    throw this.createUnimplementedException();
  }

  public async stopRecognition(): Promise<void> {
    throw this.createUnimplementedException();
  }

  private createUnimplementedException(): CapacitorException {
    return new CapacitorException(
      'This plugin method is not implemented on this platform.',
      ExceptionCode.Unimplemented,
    );
  }
}
