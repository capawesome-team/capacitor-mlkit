import { GenAiImageDescription } from '@capacitor-mlkit/genai-image-description';
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

void GenAiImageDescription.addListener('downloadProgress', event => {
  setResult(`Downloading... ${event.totalBytesDownloaded} bytes`);
});

void GenAiImageDescription.addListener('inferenceProgress', event => {
  appendResult(event.text);
});

document.addEventListener('DOMContentLoaded', () => {
  document
    .querySelector('#check-feature-status')
    .addEventListener('click', () =>
      runWithResult(async () => {
        const { featureStatus } =
          await GenAiImageDescription.checkFeatureStatus();
        setResult(`Feature status: ${featureStatus}`);
      }),
    );
  document.querySelector('#download-feature').addEventListener('click', () =>
    runWithResult(async () => {
      setResult('Downloading...');
      await GenAiImageDescription.downloadFeature();
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
  document.querySelector('#describe-image').addEventListener('click', () =>
    runWithResult(async () => {
      if (!pickedPath) {
        setResult('Please pick an image first.');
        return;
      }
      setResult('');
      const { description } = await GenAiImageDescription.describeImage({
        path: pickedPath,
      });
      setResult(description);
    }),
  );
});
