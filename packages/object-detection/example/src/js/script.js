import { ObjectDetection } from '@capacitor-mlkit/object-detection';
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

const formatObject = detectedObject => {
  const { left, top, right, bottom } = detectedObject.boundingBox;
  const box = `[${left}, ${top}, ${right}, ${bottom}]`;
  const labels =
    detectedObject.labels.length === 0
      ? 'no labels'
      : detectedObject.labels
          .map(label => `${label.text} (${label.confidence.toFixed(2)})`)
          .join(', ');
  return `${box}: ${labels}`;
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
      const shouldEnableClassification = document.querySelector(
        '#enable-classification',
      ).checked;
      const shouldEnableMultipleObjects = document.querySelector(
        '#enable-multiple-objects',
      ).checked;
      const { detectedObjects } = await ObjectDetection.processImage({
        path: pickedPath,
        shouldEnableClassification,
        shouldEnableMultipleObjects,
      });
      if (detectedObjects.length === 0) {
        setResult('No objects detected.');
        return;
      }
      const text = detectedObjects.map(formatObject).join(' | ');
      setResult(`${detectedObjects.length} object(s): ${text}`);
    }),
  );
});
