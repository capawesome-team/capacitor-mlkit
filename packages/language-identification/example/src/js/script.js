import { LanguageIdentification } from '@capacitor-mlkit/language-identification';

const setResult = value => {
  document.querySelector('#result').textContent = `Result: ${value}`;
};

const runWithResult = async callback => {
  try {
    await callback();
  } catch (error) {
    setResult(error.message || error);
  }
};

const getText = () => document.querySelector('#text').value;

const getConfidenceThreshold = () => {
  const value = document.querySelector('#confidenceThreshold').value;
  return value === '' ? undefined : Number(value);
};

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#identifyLanguage').addEventListener('click', () =>
    runWithResult(async () => {
      const result = await LanguageIdentification.identifyLanguage({
        text: getText(),
        confidenceThreshold: getConfidenceThreshold(),
      });
      setResult(JSON.stringify(result, null, 2));
    }),
  );
  document
    .querySelector('#identifyPossibleLanguages')
    .addEventListener('click', () =>
      runWithResult(async () => {
        const result = await LanguageIdentification.identifyPossibleLanguages({
          text: getText(),
          confidenceThreshold: getConfidenceThreshold(),
        });
        setResult(JSON.stringify(result, null, 2));
      }),
    );
});
