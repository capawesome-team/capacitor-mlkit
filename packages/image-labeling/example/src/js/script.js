import { ImageLabeling } from '@capacitor-mlkit/image-labeling';

window.processImage = async () => {
  const path = document.getElementById('pathInput').value;
  const confidenceThreshold = parseFloat(
    document.getElementById('confidenceInput').value,
  );
  const output = document.getElementById('output');
  try {
    const result = await ImageLabeling.processImage({
      path,
      confidenceThreshold,
    });
    output.textContent = JSON.stringify(result, null, 2);
  } catch (error) {
    output.textContent = `Error: ${error.message}`;
  }
};
