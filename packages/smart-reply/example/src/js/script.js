import { SmartReply } from '@capacitor-mlkit/smart-reply';

const conversation = [
  {
    text: 'Are you free for lunch today?',
    timestamp: Date.now() - 60_000,
    isLocalUser: false,
    userId: 'user-1',
  },
  {
    text: 'Sure, where do you want to go?',
    timestamp: Date.now() - 30_000,
    isLocalUser: true,
  },
  {
    text: 'How about the new place downtown?',
    timestamp: Date.now(),
    isLocalUser: false,
    userId: 'user-1',
  },
];

window.suggestReplies = async () => {
  const output = document.getElementById('output');
  output.textContent = 'Loading...';
  try {
    const { status, suggestions } = await SmartReply.suggestReplies({
      messages: conversation,
    });
    output.textContent = JSON.stringify({ status, suggestions }, null, 2);
  } catch (error) {
    output.textContent = `Error: ${error.message}`;
  }
};
