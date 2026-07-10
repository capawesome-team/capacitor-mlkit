import { DigitalInkRecognition } from '@capacitor-mlkit/digital-ink-recognition';

const canvas = document.getElementById('canvas');
const context = canvas.getContext('2d');

let strokes = [];
let currentPoints = undefined;

const getLanguageTag = () => document.getElementById('languageTagInput').value;

const render = value => {
  document.getElementById('output').textContent = JSON.stringify(
    value,
    null,
    2,
  );
};

const getPointFromEvent = event => {
  const rect = canvas.getBoundingClientRect();
  return {
    x: event.clientX - rect.left,
    y: event.clientY - rect.top,
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

window.clearCanvas = () => {
  strokes = [];
  currentPoints = undefined;
  context.clearRect(0, 0, canvas.width, canvas.height);
  render('');
};

window.deleteDownloadedModel = async () => {
  render('Loading...');
  try {
    await DigitalInkRecognition.deleteDownloadedModel({
      languageTag: getLanguageTag(),
    });
    render('Model deleted.');
  } catch (error) {
    render(`Error: ${error.message}`);
  }
};

window.downloadModel = async () => {
  render('Loading...');
  try {
    await DigitalInkRecognition.downloadModel({
      languageTag: getLanguageTag(),
    });
    render('Model downloaded.');
  } catch (error) {
    render(`Error: ${error.message}`);
  }
};

window.getDownloadedModels = async () => {
  render('Loading...');
  try {
    const result = await DigitalInkRecognition.getDownloadedModels();
    render(result);
  } catch (error) {
    render(`Error: ${error.message}`);
  }
};

window.recognize = async () => {
  render('Loading...');
  try {
    const result = await DigitalInkRecognition.recognize({
      languageTag: getLanguageTag(),
      strokes,
      writingArea: {
        width: canvas.width,
        height: canvas.height,
      },
    });
    render(result);
  } catch (error) {
    render(`Error: ${error.message}`);
  }
};
