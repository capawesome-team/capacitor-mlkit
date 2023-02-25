import { registerPlugin } from '@capacitor/core';

import type { SmartReplyPlugin } from './definitions';

const SmartReply = registerPlugin<SmartReplyPlugin>('SmartReply', {
  web: () => import('./web').then(m => new m.SmartReplyWeb()),
});

export * from './definitions';
export { SmartReply };
