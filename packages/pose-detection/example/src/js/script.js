import { PoseDetection } from '@capacitor-mlkit/pose-detection';

window.processImage = async () => {
  const path = document.getElementById('pathInput').value;
  const performanceMode = document.getElementById('performanceModeInput').value;
  const output = document.getElementById('output');
  try {
    const result = await PoseDetection.processImage({
      path,
      performanceMode,
    });
    output.textContent = JSON.stringify(result, null, 2);
  } catch (error) {
    output.textContent = `Error: ${error.message}`;
  }
};
