import {
  GenAiProofreading,
  InputType,
  Language,
} from '@capacitor-mlkit/genai-proofreading';

const featureOptions = {
  inputType: InputType.Keyboard,
  language: Language.English,
};

const outputElement = document.getElementById('output');

const setOutput = text => {
  outputElement.textContent = text;
};

const appendOutput = text => {
  outputElement.textContent += text;
};

void GenAiProofreading.addListener('downloadProgress', event => {
  setOutput(`Total bytes downloaded: ${event.totalBytesDownloaded}`);
});

void GenAiProofreading.addListener('inferenceProgress', event => {
  appendOutput(event.text);
});

window.checkFeatureStatus = async () => {
  try {
    const { featureStatus } =
      await GenAiProofreading.checkFeatureStatus(featureOptions);
    setOutput(`Feature status: ${featureStatus}`);
  } catch (error) {
    setOutput(`Error: ${error.message}`);
  }
};

window.downloadFeature = async () => {
  try {
    setOutput('Downloading feature...');
    await GenAiProofreading.downloadFeature(featureOptions);
    setOutput('Feature downloaded.');
  } catch (error) {
    setOutput(`Error: ${error.message}`);
  }
};

window.proofread = async () => {
  try {
    setOutput('');
    const text = document.getElementById('textInput').value;
    const { results } = await GenAiProofreading.proofread({
      ...featureOptions,
      text,
    });
    setOutput(results.join('\n\n'));
  } catch (error) {
    setOutput(`Error: ${error.message}`);
  }
};
