import { GenAiImageDescription } from '@capacitor-mlkit/genai-image-description';

const outputElement = document.getElementById('output');

const setOutput = text => {
  outputElement.textContent = text;
};

const appendOutput = text => {
  outputElement.textContent += text;
};

void GenAiImageDescription.addListener('downloadProgress', event => {
  setOutput(`Total bytes downloaded: ${event.totalBytesDownloaded}`);
});

void GenAiImageDescription.addListener('inferenceProgress', event => {
  appendOutput(event.text);
});

window.checkFeatureStatus = async () => {
  try {
    const { featureStatus } = await GenAiImageDescription.checkFeatureStatus();
    setOutput(`Feature status: ${featureStatus}`);
  } catch (error) {
    setOutput(`Error: ${error.message}`);
  }
};

window.downloadFeature = async () => {
  try {
    setOutput('Downloading feature...');
    await GenAiImageDescription.downloadFeature();
    setOutput('Feature downloaded.');
  } catch (error) {
    setOutput(`Error: ${error.message}`);
  }
};

window.describeImage = async () => {
  try {
    setOutput('');
    const path = document.getElementById('pathInput').value;
    const { description } = await GenAiImageDescription.describeImage({ path });
    setOutput(description);
  } catch (error) {
    setOutput(`Error: ${error.message}`);
  }
};
