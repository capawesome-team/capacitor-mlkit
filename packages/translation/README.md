# @capacitor-mlkit/translation

Unofficial Capacitor plugin for [ML Kit Translation](https://developers.google.com/ml-kit/language/translation).[^1]

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-deploy-real-time-app-updates.png?t=1" />
  </a>
</div>

## Installation

```bash
npm install @capacitor-mlkit/translation
npx cap sync
```

### Android

#### Variables

If needed, you can define the following project variable in your app’s `variables.gradle` file to change the default version of the dependency:

- `$mlkitTranslateVersion` version of `com.google.mlkit:translate` (default: `17.0.3`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

### iOS

#### Minimum Deployment Target

Make sure to set the deployment target in your `ios/App/Podfile` to at least `15.5`:

```ruby
platform :ios, '15.5'
```

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-mlkit-plugin-demo](https://github.com/robingenz/capacitor-mlkit-plugin-demo)

## Usage

```typescript
import { Translation, Language } from '@capacitor-mlkit/translation';

const deleteDownloadedModel = async () => {
  await Translation.deleteDownloadedModel({
    language: Language.English,
  });
};

const downloadModel = async () => {
  await Translation.downloadModel({
    language: Language.English,
  });
};

const getDownloadedModels = async () => {
  const { languages } = await Translation.getDownloadedModels();
  return languages;
};

const translate = async () => {
  const { text } = await Translation.translate({
    text: 'Good morning!',
    sourceLanguage: Language.English,
    targetLanguage: Language.German,
  });
  return text;
};
```

## API

<docgen-index>

* [`deleteDownloadedModel(...)`](#deletedownloadedmodel)
* [`downloadModel(...)`](#downloadmodel)
* [`getDownloadedModels()`](#getdownloadedmodels)
* [`translate(...)`](#translate)
* [Interfaces](#interfaces)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### deleteDownloadedModel(...)

```typescript
deleteDownloadedModel(options: DeleteDownloadedModelOptions) => Promise<void>
```

Delete the language model for the given language.

Only available on Android and iOS.

| Param         | Type                                                                                  |
| ------------- | ------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#deletedownloadedmodeloptions">DeleteDownloadedModelOptions</a></code> |

**Since:** 0.0.1

--------------------


### downloadModel(...)

```typescript
downloadModel(options: DownloadModel) => Promise<void>
```

Download a language model for offline translation.

Language models are around 30MB in size, so be sure to only download the models you need
and only download them using a WiFi connection unless the user has specified otherwise.

Only available on Android and iOS.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#downloadmodel">DownloadModel</a></code> |

**Since:** 0.0.1

--------------------


### getDownloadedModels()

```typescript
getDownloadedModels() => Promise<GetDownloadedModelsResult>
```

Get the languages for which a model has been downloaded.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getdownloadedmodelsresult">GetDownloadedModelsResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### translate(...)

```typescript
translate(options: TranslateOptions) => Promise<TranslateResult>
```

Translate the given text.

If the language model for the given source and target languages is not downloaded,
it will be downloaded automatically which may take some time.
If you want to avoid this, use the `downloadModel(...)` method to download the model first.

Only available on Android and iOS.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#translateoptions">TranslateOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#translateresult">TranslateResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### Interfaces


#### DeleteDownloadedModelOptions

| Prop           | Type                                          | Description                                 | Since |
| -------------- | --------------------------------------------- | ------------------------------------------- | ----- |
| **`language`** | <code><a href="#language">Language</a></code> | The language for which to delete the model. | 0.0.1 |


#### DownloadModel

| Prop           | Type                                          | Description                             | Since |
| -------------- | --------------------------------------------- | --------------------------------------- | ----- |
| **`language`** | <code><a href="#language">Language</a></code> | The language to download the model for. | 0.0.1 |


#### GetDownloadedModelsResult

| Prop            | Type                    | Description                                          |
| --------------- | ----------------------- | ---------------------------------------------------- |
| **`languages`** | <code>Language[]</code> | The languages for which a model has been downloaded. |


#### TranslateResult

| Prop       | Type                | Description          | Since |
| ---------- | ------------------- | -------------------- | ----- |
| **`text`** | <code>string</code> | The translated text. | 0.0.1 |


#### TranslateOptions

| Prop                 | Type                                          | Description                                                                                                                                                    | Since |
| -------------------- | --------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`text`**           | <code>string</code>                           | The text to translate.                                                                                                                                         | 0.0.1 |
| **`sourceLanguage`** | <code><a href="#language">Language</a></code> | The source language of the text. If you don't know the source language, you can use the <a href="#language">`Language</a> Identification` plugin to detect it. | 0.0.1 |
| **`targetLanguage`** | <code><a href="#language">Language</a></code> | The target language to translate the text to.                                                                                                                  | 0.0.1 |


### Enums


#### Language

| Members          | Value             | Since |
| ---------------- | ----------------- | ----- |
| **`Afrikaans`**  | <code>'af'</code> | 0.0.1 |
| **`Arabic`**     | <code>'ar'</code> | 0.0.1 |
| **`Belarusian`** | <code>'be'</code> | 0.0.1 |
| **`Bulgarian`**  | <code>'bg'</code> | 0.0.1 |
| **`Bengali`**    | <code>'bn'</code> | 0.0.1 |
| **`Catalan`**    | <code>'ca'</code> | 0.0.1 |
| **`Czech`**      | <code>'cs'</code> | 0.0.1 |
| **`Welsh`**      | <code>'cy'</code> | 0.0.1 |
| **`Danish`**     | <code>'da'</code> | 0.0.1 |
| **`German`**     | <code>'de'</code> | 0.0.1 |
| **`Greek`**      | <code>'el'</code> | 0.0.1 |
| **`English`**    | <code>'en'</code> | 0.0.1 |
| **`Esperanto`**  | <code>'eo'</code> | 0.0.1 |
| **`Spanish`**    | <code>'es'</code> | 0.0.1 |
| **`Estonian`**   | <code>'et'</code> | 0.0.1 |
| **`Persian`**    | <code>'fa'</code> | 0.0.1 |
| **`Finnish`**    | <code>'fi'</code> | 0.0.1 |
| **`French`**     | <code>'fr'</code> | 0.0.1 |
| **`Irish`**      | <code>'ga'</code> | 0.0.1 |
| **`Galician`**   | <code>'gl'</code> | 0.0.1 |
| **`Gujarati`**   | <code>'gu'</code> | 0.0.1 |
| **`Hebrew`**     | <code>'he'</code> | 0.0.1 |
| **`Hindi`**      | <code>'hi'</code> | 0.0.1 |
| **`Croatian`**   | <code>'hr'</code> | 0.0.1 |
| **`Haitian`**    | <code>'ht'</code> | 0.0.1 |
| **`Hungarian`**  | <code>'hu'</code> | 0.0.1 |
| **`Indonesian`** | <code>'id'</code> | 0.0.1 |
| **`Icelandic`**  | <code>'is'</code> | 0.0.1 |
| **`Italian`**    | <code>'it'</code> | 0.0.1 |
| **`Japanese`**   | <code>'ja'</code> | 0.0.1 |
| **`Georgian`**   | <code>'ka'</code> | 0.0.1 |
| **`Kannada`**    | <code>'kn'</code> | 0.0.1 |
| **`Korean`**     | <code>'ko'</code> | 0.0.1 |
| **`Lithuanian`** | <code>'lt'</code> | 0.0.1 |
| **`Latvian`**    | <code>'lv'</code> | 0.0.1 |
| **`Macedonian`** | <code>'mk'</code> | 0.0.1 |
| **`Marathi`**    | <code>'mr'</code> | 0.0.1 |
| **`Malay`**      | <code>'ms'</code> | 0.0.1 |
| **`Maltese`**    | <code>'mt'</code> | 0.0.1 |
| **`Dutch`**      | <code>'nl'</code> | 0.0.1 |
| **`Norwegian`**  | <code>'no'</code> | 0.0.1 |
| **`Polish`**     | <code>'pl'</code> | 0.0.1 |
| **`Portuguese`** | <code>'pt'</code> | 0.0.1 |
| **`Romanian`**   | <code>'ro'</code> | 0.0.1 |
| **`Russian`**    | <code>'ru'</code> | 0.0.1 |
| **`Slovak`**     | <code>'sk'</code> | 0.0.1 |
| **`Slovenian`**  | <code>'sl'</code> | 0.0.1 |
| **`Albanian`**   | <code>'sq'</code> | 0.0.1 |
| **`Swedish`**    | <code>'sv'</code> | 0.0.1 |
| **`Swahili`**    | <code>'sw'</code> | 0.0.1 |
| **`Tamil`**      | <code>'ta'</code> | 0.0.1 |
| **`Telugu`**     | <code>'te'</code> | 0.0.1 |
| **`Thai`**       | <code>'th'</code> | 0.0.1 |
| **`Tagalog`**    | <code>'tl'</code> | 0.0.1 |
| **`Turkish`**    | <code>'tr'</code> | 0.0.1 |
| **`Ukrainian`**  | <code>'uk'</code> | 0.0.1 |
| **`Urdu`**       | <code>'ur'</code> | 0.0.1 |
| **`Vietnamese`** | <code>'vi'</code> | 0.0.1 |
| **`Chinese`**    | <code>'zh'</code> | 0.0.1 |

</docgen-api>

## Terms & Privacy

This plugin uses the [Google ML Kit](https://developers.google.com/ml-kit):

- [Terms & Privacy](https://developers.google.com/ml-kit/terms)
- [Android Data Disclosure](https://developers.google.com/ml-kit/android-data-disclosure)
- [iOS Data Disclosure](https://developers.google.com/ml-kit/ios-data-disclosure)

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/translation/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/translation/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.
