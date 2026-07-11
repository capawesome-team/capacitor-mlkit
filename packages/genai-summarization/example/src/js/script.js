import {
  GenAiSummarization,
  InputType,
  Language,
  OutputType,
} from '@capacitor-mlkit/genai-summarization';

const setResult = value => {
  document.querySelector('#result').textContent = `Result: ${value}`;
};

const appendResult = value => {
  document.querySelector('#result').textContent += value;
};

const getFeatureOptions = () => ({
  inputType: document.querySelector('#inputType').value,
  outputType: document.querySelector('#outputType').value,
  language: document.querySelector('#language').value,
});

const runWithResult = async callback => {
  try {
    await callback();
  } catch (error) {
    setResult(error.message || error);
  }
};

void GenAiSummarization.addListener('downloadProgress', event => {
  setResult(`Downloaded ${event.totalBytesDownloaded} bytes`);
});

void GenAiSummarization.addListener('inferenceProgress', event => {
  appendResult(event.text);
});

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#inputType').value = InputType.Article;
  document.querySelector('#outputType').value = OutputType.ThreeBullets;
  document.querySelector('#language').value = Language.English;

  document
    .querySelector('#check-feature-status')
    .addEventListener('click', () =>
      runWithResult(async () => {
        const { featureStatus } =
          await GenAiSummarization.checkFeatureStatus(getFeatureOptions());
        setResult(featureStatus);
      }),
    );
  document.querySelector('#download-feature').addEventListener('click', () =>
    runWithResult(async () => {
      setResult('Downloading feature...');
      await GenAiSummarization.downloadFeature(getFeatureOptions());
      setResult('Feature downloaded');
    }),
  );
  document.querySelector('#summarize').addEventListener('click', () =>
    runWithResult(async () => {
      setResult('');
      const text = document.querySelector('#text').value;
      await GenAiSummarization.summarize({ ...getFeatureOptions(), text });
    }),
  );
});
