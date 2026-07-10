import { GenAiPrompt } from '@capacitor-mlkit/genai-prompt';

const outputElement = document.getElementById('output');

const setOutput = text => {
  outputElement.textContent = text;
};

const appendOutput = text => {
  outputElement.textContent += text;
};

void GenAiPrompt.addListener('downloadProgress', event => {
  setOutput(`Total bytes downloaded: ${event.totalBytesDownloaded}`);
});

void GenAiPrompt.addListener('inferenceProgress', event => {
  appendOutput(event.text);
});

window.checkFeatureStatus = async () => {
  try {
    const { featureStatus } = await GenAiPrompt.checkFeatureStatus();
    setOutput(`Feature status: ${featureStatus}`);
  } catch (error) {
    setOutput(`Error: ${error.message}`);
  }
};

window.downloadFeature = async () => {
  try {
    setOutput('Downloading feature...');
    await GenAiPrompt.downloadFeature();
    setOutput('Feature downloaded.');
  } catch (error) {
    setOutput(`Error: ${error.message}`);
  }
};

window.generateContent = async () => {
  try {
    setOutput('');
    const prompt = document.getElementById('promptInput').value;
    const imagePath = document.getElementById('imagePathInput').value;
    const { text } = await GenAiPrompt.generateContent({
      prompt,
      imagePath: imagePath || undefined,
    });
    setOutput(text);
  } catch (error) {
    setOutput(`Error: ${error.message}`);
  }
};
