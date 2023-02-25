import { WebPlugin } from '@capacitor/core';

import type {
  SmartReplyPlugin,
  TextMessage,
  SmartReplySuggestion,
} from './definitions';

export class SmartReplyWeb extends WebPlugin implements SmartReplyPlugin {
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  public async smartReply(_messages: [TextMessage]): Promise<{ value: [SmartReplySuggestion] }> {
    throw this.unimplemented('Not implemented on web.');
  }
}
