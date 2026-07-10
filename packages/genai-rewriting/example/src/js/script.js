import { GenAiRewriting, Language } from '@capacitor-mlkit/genai-rewriting';

const outputElement = document.getElementById('output');

const setOutput = text => {
  outputElement.textContent = text;
};

const appendOutput = text => {
  outputElement.textContent += text;
};

const getFeatureOptions = () => {
  return {
    language: Language.English,
    outputType: document.getElementById('outputTypeSelect').value,
  };
};

void GenAiRewriting.addListener('downloadProgress', event => {
  setOutput(`Total bytes downloaded: ${event.totalBytesDownloaded}`);
});

void GenAiRewriting.addListener('inferenceProgress', event => {
  appendOutput(event.text);
});

window.checkFeatureStatus = async () => {
  try {
    const { featureStatus } =
      await GenAiRewriting.checkFeatureStatus(getFeatureOptions());
    setOutput(`Feature status: ${featureStatus}`);
  } catch (error) {
    setOutput(`Error: ${error.message}`);
  }
};

window.downloadFeature = async () => {
  try {
    setOutput('Downloading feature...');
    await GenAiRewriting.downloadFeature(getFeatureOptions());
    setOutput('Feature downloaded.');
  } catch (error) {
    setOutput(`Error: ${error.message}`);
  }
};

window.rewrite = async () => {
  try {
    setOutput('');
    const text = document.getElementById('textInput').value;
    const { results } = await GenAiRewriting.rewrite({
      ...getFeatureOptions(),
      text,
    });
    setOutput(results.join('\n\n---\n\n'));
  } catch (error) {
    setOutput(`Error: ${error.message}`);
  }
};
