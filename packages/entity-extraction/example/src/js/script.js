import { EntityExtraction, Language } from '@capacitor-mlkit/entity-extraction';

const output = () => document.getElementById('output');
const getText = () => document.getElementById('textInput').value;

window.downloadModel = async () => {
  output().textContent = 'Downloading model...';
  try {
    await EntityExtraction.downloadModel({ language: Language.English });
    output().textContent = 'Model downloaded.';
  } catch (error) {
    output().textContent = `Error: ${error.message}`;
  }
};

window.getDownloadedModels = async () => {
  output().textContent = 'Loading...';
  try {
    const { languages } = await EntityExtraction.getDownloadedModels();
    output().textContent = JSON.stringify({ languages }, null, 2);
  } catch (error) {
    output().textContent = `Error: ${error.message}`;
  }
};

window.extractEntities = async () => {
  output().textContent = 'Extracting...';
  try {
    const { annotations } = await EntityExtraction.extractEntities({
      text: getText(),
      language: Language.English,
      referenceTime: Date.now(),
    });
    output().textContent = JSON.stringify({ annotations }, null, 2);
  } catch (error) {
    output().textContent = `Error: ${error.code ?? ''} ${error.message}`;
  }
};

window.deleteDownloadedModel = async () => {
  output().textContent = 'Deleting model...';
  try {
    await EntityExtraction.deleteDownloadedModel({
      language: Language.English,
    });
    output().textContent = 'Model deleted.';
  } catch (error) {
    output().textContent = `Error: ${error.message}`;
  }
};
