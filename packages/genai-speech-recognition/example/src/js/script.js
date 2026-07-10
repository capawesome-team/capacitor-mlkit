import { GenAiSpeechRecognition } from '@capacitor-mlkit/genai-speech-recognition';

const finalResultElement = document.getElementById('finalResult');
const outputElement = document.getElementById('output');
const partialResultElement = document.getElementById('partialResult');

const getFeatureOptions = () => ({
  locale: document.getElementById('localeInput').value,
  mode: document.getElementById('modeSelect').value,
});

const setOutput = text => {
  outputElement.textContent = text;
};

void GenAiSpeechRecognition.addListener('downloadProgress', event => {
  setOutput(`Total bytes downloaded: ${event.totalBytesDownloaded}`);
});

void GenAiSpeechRecognition.addListener('error', event => {
  setOutput(`Error: ${event.message}`);
});

void GenAiSpeechRecognition.addListener('finalResult', event => {
  finalResultElement.textContent += `${event.text}\n`;
});

void GenAiSpeechRecognition.addListener('partialResult', event => {
  partialResultElement.textContent = event.text;
});

window.checkFeatureStatus = async () => {
  try {
    const { featureStatus } =
      await GenAiSpeechRecognition.checkFeatureStatus(getFeatureOptions());
    setOutput(`Feature status: ${featureStatus}`);
  } catch (error) {
    setOutput(`Error: ${error.message}`);
  }
};

window.checkPermissions = async () => {
  try {
    const { microphone } = await GenAiSpeechRecognition.checkPermissions();
    setOutput(`Microphone permission: ${microphone}`);
  } catch (error) {
    setOutput(`Error: ${error.message}`);
  }
};

window.downloadFeature = async () => {
  try {
    setOutput('Downloading feature...');
    await GenAiSpeechRecognition.downloadFeature(getFeatureOptions());
    setOutput('Feature downloaded.');
  } catch (error) {
    setOutput(`Error: ${error.message}`);
  }
};

window.requestPermissions = async () => {
  try {
    const { microphone } = await GenAiSpeechRecognition.requestPermissions();
    setOutput(`Microphone permission: ${microphone}`);
  } catch (error) {
    setOutput(`Error: ${error.message}`);
  }
};

window.startRecognition = async () => {
  try {
    finalResultElement.textContent = '';
    partialResultElement.textContent = '';
    await GenAiSpeechRecognition.startRecognition(getFeatureOptions());
    setOutput('Recognition started.');
  } catch (error) {
    setOutput(`Error: ${error.message}`);
  }
};

window.stopRecognition = async () => {
  try {
    await GenAiSpeechRecognition.stopRecognition();
    setOutput('Recognition stopped.');
  } catch (error) {
    setOutput(`Error: ${error.message}`);
  }
};
