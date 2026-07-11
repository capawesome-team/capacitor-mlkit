import { ImageLabeling } from '@capacitor-mlkit/image-labeling';
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
      const confidenceThreshold = parseFloat(
        document.querySelector('#confidence-threshold').value,
      );
      const { labels } = await ImageLabeling.processImage({
        path: pickedPath,
        confidenceThreshold,
      });
      if (labels.length === 0) {
        setResult('No labels detected.');
        return;
      }
      const text = labels
        .map(label => `${label.text} (${label.confidence.toFixed(2)})`)
        .join(', ');
      setResult(`${labels.length} label(s): ${text}`);
    }),
  );
});
