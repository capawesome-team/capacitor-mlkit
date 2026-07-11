import { EntityExtraction, Language } from '@capacitor-mlkit/entity-extraction';

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

const getLanguage = () => document.querySelector('#language').value;
const getText = () => document.querySelector('#text').value;

const populateLanguages = () => {
  const select = document.querySelector('#language');
  for (const language of Object.values(Language)) {
    const option = document.createElement('ion-select-option');
    option.value = language;
    option.textContent = language;
    select.appendChild(option);
  }
};

document.addEventListener('DOMContentLoaded', () => {
  populateLanguages();

  document.querySelector('#downloadModel').addEventListener('click', () =>
    runWithResult(async () => {
      await EntityExtraction.downloadModel({ language: getLanguage() });
      setResult('Model downloaded');
    }),
  );
  document
    .querySelector('#deleteDownloadedModel')
    .addEventListener('click', () =>
      runWithResult(async () => {
        await EntityExtraction.deleteDownloadedModel({
          language: getLanguage(),
        });
        setResult('Model deleted');
      }),
    );
  document.querySelector('#getDownloadedModels').addEventListener('click', () =>
    runWithResult(async () => {
      const { languages } = await EntityExtraction.getDownloadedModels();
      setResult(JSON.stringify({ languages }, null, 2));
    }),
  );
  document.querySelector('#extractEntities').addEventListener('click', () =>
    runWithResult(async () => {
      const { annotations } = await EntityExtraction.extractEntities({
        text: getText(),
        language: getLanguage(),
        referenceTime: Date.now(),
      });
      setResult(JSON.stringify({ annotations }, null, 2));
    }),
  );
});
