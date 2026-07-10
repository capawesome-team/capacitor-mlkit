import { CapacitorException, ExceptionCode, WebPlugin } from '@capacitor/core';

import type {
  SmartReplyPlugin,
  SuggestRepliesOptions,
  SuggestRepliesResult,
} from './definitions';

export class SmartReplyWeb extends WebPlugin implements SmartReplyPlugin {
  public async suggestReplies(
    _options: SuggestRepliesOptions,
  ): Promise<SuggestRepliesResult> {
    throw this.createUnimplementedException();
  }

  private createUnimplementedException(): CapacitorException {
    return new CapacitorException(
      'This method is not implemented on web.',
      ExceptionCode.Unimplemented,
    );
  }
}
