import { ObjectDetection } from '@capacitor-mlkit/object-detection';

window.processImage = async () => {
  const path = document.getElementById('pathInput').value;
  const shouldEnableClassification = document.getElementById(
    'classificationInput',
  ).checked;
  const shouldEnableMultipleObjects = document.getElementById(
    'multipleObjectsInput',
  ).checked;
  const output = document.getElementById('output');
  try {
    const result = await ObjectDetection.processImage({
      path,
      shouldEnableClassification,
      shouldEnableMultipleObjects,
    });
    output.textContent = JSON.stringify(result, null, 2);
  } catch (error) {
    output.textContent = `Error: ${error.message}`;
  }
};
