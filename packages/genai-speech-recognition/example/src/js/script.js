import {
  GenAiSpeechRecognition,
  RecognitionMode,
} from '@capacitor-mlkit/genai-speech-recognition';

let finalText = '';

const setResult = value => {
  document.querySelector('#result').textContent = `Result: ${value}`;
};

const getFeatureOptions = () => ({
  locale: document.querySelector('#locale').value,
  mode: document.querySelector('#mode').value,
});

const runWithResult = async callback => {
  try {
    await callback();
  } catch (error) {
    setResult(error.message || error);
  }
};

void GenAiSpeechRecognition.addListener('downloadProgress', event => {
  setResult(`Downloaded ${event.totalBytesDownloaded} bytes`);
});

void GenAiSpeechRecognition.addListener('error', event => {
  setResult(event.message);
});

void GenAiSpeechRecognition.addListener('finalResult', event => {
  finalText += `${event.text} `;
  setResult(finalText);
});

void GenAiSpeechRecognition.addListener('partialResult', event => {
  setResult(`${finalText}${event.text}`);
});

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#mode').value = RecognitionMode.Basic;

  document.querySelector('#check-permissions').addEventListener('click', () =>
    runWithResult(async () => {
      const { microphone } = await GenAiSpeechRecognition.checkPermissions();
      setResult(`Microphone permission: ${microphone}`);
    }),
  );
  document.querySelector('#request-permissions').addEventListener('click', () =>
    runWithResult(async () => {
      const { microphone } = await GenAiSpeechRecognition.requestPermissions();
      setResult(`Microphone permission: ${microphone}`);
    }),
  );
  document
    .querySelector('#check-feature-status')
    .addEventListener('click', () =>
      runWithResult(async () => {
        const { featureStatus } =
          await GenAiSpeechRecognition.checkFeatureStatus(getFeatureOptions());
        setResult(featureStatus);
      }),
    );
  document.querySelector('#download-feature').addEventListener('click', () =>
    runWithResult(async () => {
      setResult('Downloading feature...');
      await GenAiSpeechRecognition.downloadFeature(getFeatureOptions());
      setResult('Feature downloaded');
    }),
  );
  document.querySelector('#start-recognition').addEventListener('click', () =>
    runWithResult(async () => {
      finalText = '';
      setResult('');
      await GenAiSpeechRecognition.startRecognition(getFeatureOptions());
    }),
  );
  document.querySelector('#stop-recognition').addEventListener('click', () =>
    runWithResult(async () => {
      await GenAiSpeechRecognition.stopRecognition();
    }),
  );
});
