import {
  GenAiRewriting,
  Language,
  OutputType,
} from '@capacitor-mlkit/genai-rewriting';

const setResult = value => {
  document.querySelector('#result').textContent = `Result: ${value}`;
};

const appendResult = value => {
  document.querySelector('#result').textContent += value;
};

const getFeatureOptions = () => ({
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

void GenAiRewriting.addListener('downloadProgress', event => {
  setResult(`Downloaded ${event.totalBytesDownloaded} bytes`);
});

void GenAiRewriting.addListener('inferenceProgress', event => {
  appendResult(event.text);
});

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#outputType').value = OutputType.Elaborate;
  document.querySelector('#language').value = Language.English;

  document
    .querySelector('#check-feature-status')
    .addEventListener('click', () =>
      runWithResult(async () => {
        const { featureStatus } =
          await GenAiRewriting.checkFeatureStatus(getFeatureOptions());
        setResult(featureStatus);
      }),
    );
  document.querySelector('#download-feature').addEventListener('click', () =>
    runWithResult(async () => {
      setResult('Downloading feature...');
      await GenAiRewriting.downloadFeature(getFeatureOptions());
      setResult('Feature downloaded');
    }),
  );
  document.querySelector('#rewrite').addEventListener('click', () =>
    runWithResult(async () => {
      setResult('');
      const text = document.querySelector('#text').value;
      const { results } = await GenAiRewriting.rewrite({
        ...getFeatureOptions(),
        text,
      });
      setResult(results.join('\n\n---\n\n'));
    }),
  );
});
