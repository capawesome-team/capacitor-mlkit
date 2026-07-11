import { TextRecognition } from '@capacitor-mlkit/text-recognition';
import { FilePicker } from '@capawesome/capacitor-file-picker';

let pickedPath;

const setResult = value => {
  document.querySelector('#result').textContent = `Result: ${value}`;
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

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#pick-image').addEventListener('click', () =>
    runWithResult(async () => {
      const { files } = await FilePicker.pickImages({ limit: 1 });
      pickedPath = files[0].path;
      setFile(files[0].name || pickedPath);
    }),
  );
  document.querySelector('#process-image').addEventListener('click', () =>
    runWithResult(async () => {
      if (!pickedPath) {
        setResult('Please pick an image first.');
        return;
      }
      const script = document.querySelector('#script').value;
      const { text, blocks } = await TextRecognition.processImage({
        path: pickedPath,
        script,
      });
      if (!text) {
        setResult('No text recognized.');
        return;
      }
      setResult(`${blocks.length} block(s): ${text}`);
    }),
  );
});
