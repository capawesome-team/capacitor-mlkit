import {
  GenAiSummarization,
  InputType,
  Language,
  OutputType,
} from '@capacitor-mlkit/genai-summarization';

const featureOptions = {
  inputType: InputType.Article,
  language: Language.English,
  outputType: OutputType.ThreeBullets,
};

const outputElement = document.getElementById('output');

const setOutput = text => {
  outputElement.textContent = text;
};

const appendOutput = text => {
  outputElement.textContent += text;
};

void GenAiSummarization.addListener('downloadProgress', event => {
  setOutput(`Total bytes downloaded: ${event.totalBytesDownloaded}`);
});

void GenAiSummarization.addListener('inferenceProgress', event => {
  appendOutput(event.text);
});

window.checkFeatureStatus = async () => {
  try {
    const { featureStatus } =
      await GenAiSummarization.checkFeatureStatus(featureOptions);
    setOutput(`Feature status: ${featureStatus}`);
  } catch (error) {
    setOutput(`Error: ${error.message}`);
  }
};

window.downloadFeature = async () => {
  try {
    setOutput('Downloading feature...');
    await GenAiSummarization.downloadFeature(featureOptions);
    setOutput('Feature downloaded.');
  } catch (error) {
    setOutput(`Error: ${error.message}`);
  }
};

window.summarize = async () => {
  try {
    setOutput('');
    const text = document.getElementById('textInput').value;
    const { summary } = await GenAiSummarization.summarize({
      ...featureOptions,
      text,
    });
    setOutput(summary);
  } catch (error) {
    setOutput(`Error: ${error.message}`);
  }
};
