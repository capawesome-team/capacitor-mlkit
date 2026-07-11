import { GenAiPrompt } from '@capacitor-mlkit/genai-prompt';
import { FilePicker } from '@capawesome/capacitor-file-picker';

let pickedPath;

const setResult = value => {
  document.querySelector('#result').textContent = `Result: ${value}`;
};

const appendResult = value => {
  document.querySelector('#result').textContent += value;
};

const setFile = value => {
  document.querySelector('#file').textContent = `File: ${value}`;
};

const runWithResult = async callback => {
  try {
    await callback();
  } catch (error) {
    setResult(error.message || error);
  }
};

void GenAiPrompt.addListener('downloadProgress', event => {
  setResult(`Downloading... ${event.totalBytesDownloaded} bytes`);
});

void GenAiPrompt.addListener('inferenceProgress', event => {
  appendResult(event.text);
});

document.addEventListener('DOMContentLoaded', () => {
  document
    .querySelector('#check-feature-status')
    .addEventListener('click', () =>
      runWithResult(async () => {
        const { featureStatus } = await GenAiPrompt.checkFeatureStatus();
        setResult(`Feature status: ${featureStatus}`);
      }),
    );
  document.querySelector('#download-feature').addEventListener('click', () =>
    runWithResult(async () => {
      setResult('Downloading...');
      await GenAiPrompt.downloadFeature();
      setResult('Feature downloaded.');
    }),
  );
  document.querySelector('#pick-image').addEventListener('click', () =>
    runWithResult(async () => {
      const { files } = await FilePicker.pickImages({ limit: 1 });
      pickedPath = files[0].path;
      setFile(files[0].name || pickedPath);
    }),
  );
  document.querySelector('#generate-content').addEventListener('click', () =>
    runWithResult(async () => {
      const prompt = document.querySelector('#prompt').value;
      if (!prompt) {
        setResult('Please enter a prompt first.');
        return;
      }
      setResult('');
      const { text } = await GenAiPrompt.generateContent({
        prompt,
        imagePath: pickedPath || undefined,
      });
      setResult(text);
    }),
  );
});
