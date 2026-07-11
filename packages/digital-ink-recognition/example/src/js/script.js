import { DigitalInkRecognition } from '@capacitor-mlkit/digital-ink-recognition';

const setResult = value => {
  document.querySelector('#result').textContent = `Result: ${value}`;
};

const runWithResult = async callback => {
  try {
    await callback();
  } catch (error) {
    setResult(error.message || error);
  }
};

const getLanguageTag = () => document.querySelector('#languageTag').value;

let strokes = [];
let currentPoints = undefined;

document.addEventListener('DOMContentLoaded', () => {
  const canvas = document.querySelector('#canvas');
  const context = canvas.getContext('2d');

  const getPointFromEvent = event => {
    const rect = canvas.getBoundingClientRect();
    return {
      x: ((event.clientX - rect.left) / rect.width) * canvas.width,
      y: ((event.clientY - rect.top) / rect.height) * canvas.height,
      t: Date.now(),
    };
  };

  canvas.addEventListener('pointerdown', event => {
    const point = getPointFromEvent(event);
    currentPoints = [point];
    context.beginPath();
    context.moveTo(point.x, point.y);
  });

  canvas.addEventListener('pointermove', event => {
    if (!currentPoints) {
      return;
    }
    const point = getPointFromEvent(event);
    currentPoints.push(point);
    context.lineTo(point.x, point.y);
    context.stroke();
  });

  canvas.addEventListener('pointerup', event => {
    if (!currentPoints) {
      return;
    }
    const point = getPointFromEvent(event);
    currentPoints.push(point);
    context.lineTo(point.x, point.y);
    context.stroke();
    strokes.push({ points: currentPoints });
    currentPoints = undefined;
  });

  document.querySelector('#downloadModel').addEventListener('click', () =>
    runWithResult(async () => {
      await DigitalInkRecognition.downloadModel({
        languageTag: getLanguageTag(),
      });
      setResult('Model downloaded');
    }),
  );
  document
    .querySelector('#deleteDownloadedModel')
    .addEventListener('click', () =>
      runWithResult(async () => {
        await DigitalInkRecognition.deleteDownloadedModel({
          languageTag: getLanguageTag(),
        });
        setResult('Model deleted');
      }),
    );
  document.querySelector('#getDownloadedModels').addEventListener('click', () =>
    runWithResult(async () => {
      const { languageTags } =
        await DigitalInkRecognition.getDownloadedModels();
      setResult(JSON.stringify({ languageTags }, null, 2));
    }),
  );
  document.querySelector('#recognize').addEventListener('click', () =>
    runWithResult(async () => {
      const { candidates } = await DigitalInkRecognition.recognize({
        languageTag: getLanguageTag(),
        strokes,
        writingArea: {
          width: canvas.width,
          height: canvas.height,
        },
      });
      setResult(JSON.stringify({ candidates }, null, 2));
    }),
  );
  document.querySelector('#clear').addEventListener('click', () => {
    strokes = [];
    currentPoints = undefined;
    context.clearRect(0, 0, canvas.width, canvas.height);
    setResult('-');
  });
});
