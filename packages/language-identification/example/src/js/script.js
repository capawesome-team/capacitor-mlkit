import { LanguageIdentification } from '@capacitor-mlkit/language-identification';

const getText = () => document.getElementById('textInput').value;

const render = value => {
  document.getElementById('output').textContent = JSON.stringify(
    value,
    null,
    2,
  );
};

window.identifyLanguage = async () => {
  render('Loading...');
  try {
    const result = await LanguageIdentification.identifyLanguage({
      text: getText(),
    });
    render(result);
  } catch (error) {
    render(`Error: ${error.message}`);
  }
};

window.identifyPossibleLanguages = async () => {
  render('Loading...');
  try {
    const result = await LanguageIdentification.identifyPossibleLanguages({
      text: getText(),
    });
    render(result);
  } catch (error) {
    render(`Error: ${error.message}`);
  }
};
