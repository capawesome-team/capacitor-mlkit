import { SmartReply } from '@capacitor-mlkit/smart-reply';

const setResult = value => {
  document.querySelector('#result').textContent = `Result: ${value}`;
};

const runWithResult = async callback => {
  try {
    await callback();
  } catch (error) {
    setResult(error.message || error);
  }
};

const parseConversation = () => {
  const raw = document.querySelector('#conversation').value;
  const lines = raw.split('\n').map(line => line.trim());
  const messages = [];
  let timestamp = Date.now() - lines.length * 60_000;
  for (const line of lines) {
    if (!line) {
      continue;
    }
    const isLocalUser = line.toLowerCase().startsWith('local:');
    const text = line.replace(/^(local|remote):/i, '').trim();
    const message = { text, timestamp, isLocalUser };
    if (!isLocalUser) {
      message.userId = 'user-1';
    }
    messages.push(message);
    timestamp += 60_000;
  }
  return messages;
};

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#suggestReplies').addEventListener('click', () =>
    runWithResult(async () => {
      const messages = parseConversation();
      const { status, suggestions } = await SmartReply.suggestReplies({
        messages,
      });
      setResult(JSON.stringify({ status, suggestions }, null, 2));
    }),
  );
});
