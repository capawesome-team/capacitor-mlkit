import {
  GenAiProofreading,
  InputType,
  Language,
} from '@capacitor-mlkit/genai-proofreading';

const setResult = value => {
  document.querySelector('#result').textContent = `Result: ${value}`;
};

const appendResult = value => {
  document.querySelector('#result').textContent += value;
};

const getFeatureOptions = () => ({
  inputType: document.querySelector('#inputType').value,
  language: document.querySelector('#language').value,
});

const runWithResult = async callback => {
  try {
    await callback();
  } catch (error) {
    setResult(error.message || error);
  }
};

void GenAiProofreading.addListener('downloadProgress', event => {
  setResult(`Downloaded ${event.totalBytesDownloaded} bytes`);
});

void GenAiProofreading.addListener('inferenceProgress', event => {
  appendResult(event.text);
});

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#inputType').value = InputType.Keyboard;
  document.querySelector('#language').value = Language.English;

  document
    .querySelector('#check-feature-status')
    .addEventListener('click', () =>
      runWithResult(async () => {
        const { featureStatus } =
          await GenAiProofreading.checkFeatureStatus(getFeatureOptions());
        setResult(featureStatus);
      }),
    );
  document.querySelector('#download-feature').addEventListener('click', () =>
    runWithResult(async () => {
      setResult('Downloading feature...');
      await GenAiProofreading.downloadFeature(getFeatureOptions());
      setResult('Feature downloaded');
    }),
  );
  document.querySelector('#proofread').addEventListener('click', () =>
    runWithResult(async () => {
      setResult('');
      const text = document.querySelector('#text').value;
      const { results } = await GenAiProofreading.proofread({
        ...getFeatureOptions(),
        text,
      });
      setResult(results.join('\n\n'));
    }),
  );
});
