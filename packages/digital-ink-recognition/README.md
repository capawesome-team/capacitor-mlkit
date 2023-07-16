# @capacitor-mlkit/digital-ink-recognition

Unofficial Capacitor plugin for [ML Kit Digital Ink Recognition](https://developers.google.com/ml-kit/vision/digital-ink-recognition).[^1]

## Installation

```bash
npm install @capacitor-mlkit/digital-ink-recognition
npx cap sync
```

### Android

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$mlkitDigitalInkRecognitionVersion` version of `com.google.mlkit:digital-ink-recognition` (default: `18.1.0`)

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-mlkit-plugin-demo](https://github.com/robingenz/capacitor-mlkit-plugin-demo)

## Usage

```typescript
import { DigitalInkRecognition } from '@capacitor-mlkit/digital-ink-recognition';

const echo = async () => {
  await DigitalInkRecognition.echo();
};
```

## API

<docgen-index>

* [`deleteDownloadedModel(...)`](#deletedownloadedmodel)
* [`downloadModel(...)`](#downloadmodel)
* [`isModelDownloaded(...)`](#ismodeldownloaded)
* [`getDownloadedModels()`](#getdownloadedmodels)
* [`recognize(...)`](#recognize)
* [Interfaces](#interfaces)

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

**Since:** 5.1.0

--------------------


### downloadModel(...)

```typescript
downloadModel(options: DownloadModel) => Promise<void>
```

Download a language model for the given language.

Language models are around 20MB in size, so be sure to only download the models you need
and only download them using a WiFi connection unless the user has specified otherwise.

Only available on Android and iOS.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#downloadmodel">DownloadModel</a></code> |

**Since:** 5.1.0

--------------------


### isModelDownloaded(...)

```typescript
isModelDownloaded(options: IsModelDownloadedOptions) => Promise<IsModelDownloadedResult>
```

Returns whether or not the model for the given language is downloaded.

Only available on Android and iOS.

| Param         | Type                                                                          |
| ------------- | ----------------------------------------------------------------------------- |
| **`options`** | <code><a href="#ismodeldownloadedoptions">IsModelDownloadedOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#ismodeldownloadedresult">IsModelDownloadedResult</a>&gt;</code>

**Since:** 5.1.0

--------------------


### getDownloadedModels()

```typescript
getDownloadedModels() => Promise<GetDownloadedModelsResult>
```

Get the languages for which a model has been downloaded.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getdownloadedmodelsresult">GetDownloadedModelsResult</a>&gt;</code>

**Since:** 5.1.0

--------------------


### recognize(...)

```typescript
recognize(options: RecognizeOptions) => Promise<RecognizeResult>
```

Process an image and return the recognized text.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#recognizeoptions">RecognizeOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#recognizeresult">RecognizeResult</a>&gt;</code>

**Since:** 5.1.0

--------------------


### Interfaces


#### DeleteDownloadedModelOptions

| Prop           | Type                | Description                                               | Since |
| -------------- | ------------------- | --------------------------------------------------------- | ----- |
| **`language`** | <code>string</code> | The language as BCP-47 tag for which to delete the model. | 5.1.0 |


#### DownloadModel

| Prop           | Type                | Description                                           | Since |
| -------------- | ------------------- | ----------------------------------------------------- | ----- |
| **`language`** | <code>string</code> | The language as BCP-47 tag to download the model for. | 5.1.0 |


#### IsModelDownloadedResult

| Prop                    | Type                 | Description                             | Since |
| ----------------------- | -------------------- | --------------------------------------- | ----- |
| **`isModelDownloaded`** | <code>boolean</code> | Whether or not the model is downloaded. | 5.1.0 |


#### IsModelDownloadedOptions

| Prop           | Type                | Description                                                         | Since |
| -------------- | ------------------- | ------------------------------------------------------------------- | ----- |
| **`language`** | <code>string</code> | The language as BCP-47 tag to check if the model is downloaded for. | 5.1.0 |


#### GetDownloadedModelsResult

| Prop            | Type                  | Description                                                         |
| --------------- | --------------------- | ------------------------------------------------------------------- |
| **`languages`** | <code>string[]</code> | The languages as BCP-47 tags for which a model has been downloaded. |


#### RecognizeResult

| Prop             | Type                     | Description                | Since |
| ---------------- | ------------------------ | -------------------------- | ----- |
| **`candidates`** | <code>Candidate[]</code> | The recognized candidates. | 5.1.0 |


#### Candidate

| Prop        | Type                | Description                                    | Since |
| ----------- | ------------------- | ---------------------------------------------- | ----- |
| **`text`**  | <code>string</code> | The textual representation of the recognition. | 5.1.0 |
| **`score`** | <code>number</code> | The candidate's score.                         | 5.1.0 |


#### RecognizeOptions

| Prop           | Type                | Description                                          | Since |
| -------------- | ------------------- | ---------------------------------------------------- | ----- |
| **`language`** | <code>string</code> | The language as BCP-47 tag to recognize the text in. | 5.1.0 |

</docgen-api>

## Terms & Privacy

This plugin uses the [Google ML Kit](https://developers.google.com/ml-kit):

- [Terms & Privacy](https://developers.google.com/ml-kit/terms)
- [Android Data Disclosure](https://developers.google.com/ml-kit/android-data-disclosure)
- [iOS Data Disclosure](https://developers.google.com/ml-kit/ios-data-disclosure)

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/digital-ink-recognition/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/digital-ink-recognition/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.
