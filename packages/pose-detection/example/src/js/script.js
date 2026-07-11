import { PoseDetection } from '@capacitor-mlkit/pose-detection';
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
      const performanceMode = document.querySelector('#performance-mode').value;
      const { poses } = await PoseDetection.processImage({
        path: pickedPath,
        performanceMode,
      });
      if (poses.length === 0) {
        setResult('No poses detected.');
        return;
      }
      const { landmarks } = poses[0];
      const preview = landmarks
        .slice(0, 3)
        .map(
          landmark =>
            `${landmark.type} (${Math.round(landmark.x)}, ${Math.round(
              landmark.y,
            )})`,
        )
        .join(', ');
      setResult(
        `${poses.length} pose(s), ${landmarks.length} landmarks. First: ${preview}`,
      );
    }),
  );
});
