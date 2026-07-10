import { TextRecognition } from '@capacitor-mlkit/text-recognition';

window.processImage = async () => {
  const path = document.getElementById('pathInput').value;
  const script = document.getElementById('scriptInput').value;
  const output = document.getElementById('output');
  try {
    const result = await TextRecognition.processImage({
      path,
      script,
    });
    output.textContent = JSON.stringify(result, null, 2);
  } catch (error) {
    output.textContent = `Error: ${error.message}`;
  }
};
