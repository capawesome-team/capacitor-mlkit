# Capacitor ML Kit Digital Ink Recognition Plugin

Unofficial Capacitor plugin for [ML Kit Digital Ink Recognition](https://developers.google.com/ml-kit/vision/digital-ink-recognition).[^1]

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Use Cases

The Digital Ink Recognition plugin recognizes handwritten text and hand-drawn shapes from stroke coordinates directly on the device, for example:

- **Handwriting input**: Let users write text with their finger or a stylus instead of typing.
- **Note taking**: Convert handwritten notes into digital text.
- **Form filling**: Recognize handwritten input in signature or annotation fields.
- **Sketch recognition**: Classify hand-drawn shapes and emojis with the special gesture models.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 8.x.x          | >=8.x.x           | Active support |

## Installation

You can use our **AI-Assisted Setup** to install the plugin.
Add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills --skill capacitor-plugins
```

Then use the following prompt:

```
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capacitor-mlkit/digital-ink-recognition` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capacitor-mlkit/digital-ink-recognition
npx cap sync
```

**Attention**: This plugin **only supports CocoaPods** for iOS dependency management. Swift Package Manager (SPM) is not supported for the ML Kit SDK, see [this comment](https://github.com/googlesamples/mlkit/issues/180#issuecomment-1298964099).

### Android

#### Variables

If needed, you can define the following project variable in your app’s `variables.gradle` file to change the default version of the dependency:

- `$mlkitDigitalInkRecognitionVersion` version of `com.google.mlkit:digital-ink-recognition` (default: `19.0.0`)

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

Before any ink can be recognized, the model for the desired language must be downloaded:

```typescript
import { DigitalInkRecognition } from '@capacitor-mlkit/digital-ink-recognition';

const downloadModel = async () => {
  await DigitalInkRecognition.downloadModel({
    languageTag: 'en-US',
  });
};
```

The plugin does not render anything. Capture the strokes with your own drawing surface, for example a canvas with pointer events:

```typescript
import type { Stroke, StrokePoint } from '@capacitor-mlkit/digital-ink-recognition';

const canvas = document.querySelector('canvas');

const strokes: Stroke[] = [];
let currentPoints: StrokePoint[] | undefined;

canvas.addEventListener('pointerdown', event => {
  currentPoints = [{ x: event.offsetX, y: event.offsetY, t: Date.now() }];
});
canvas.addEventListener('pointermove', event => {
  currentPoints?.push({ x: event.offsetX, y: event.offsetY, t: Date.now() });
});
canvas.addEventListener('pointerup', event => {
  if (currentPoints) {
    currentPoints.push({ x: event.offsetX, y: event.offsetY, t: Date.now() });
    strokes.push({ points: currentPoints });
    currentPoints = undefined;
  }
});
```

Finally, pass the captured strokes to the `recognize(...)` method:

```typescript
const recognize = async () => {
  const { candidates } = await DigitalInkRecognition.recognize({
    languageTag: 'en-US',
    strokes,
    writingArea: {
      width: canvas.width,
      height: canvas.height,
    },
  });
  return candidates;
};
```

Besides the language models, there are also special models for recognizing hand-drawn shapes (`zxx-Zsym-x-autodraw`) and emojis (`zxx-Zsym-x-emoji`).

## API

<docgen-index>

* [`deleteDownloadedModel(...)`](#deletedownloadedmodel)
* [`downloadModel(...)`](#downloadmodel)
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

Delete the downloaded model for the given language.

Only available on Android and iOS.

| Param         | Type                                                                                  |
| ------------- | ------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#deletedownloadedmodeloptions">DeleteDownloadedModelOptions</a></code> |

**Since:** 8.2.0

--------------------


### downloadModel(...)

```typescript
downloadModel(options: DownloadModelOptions) => Promise<void>
```

Download a model for the given language.

Only available on Android and iOS.

| Param         | Type                                                                  |
| ------------- | --------------------------------------------------------------------- |
| **`options`** | <code><a href="#downloadmodeloptions">DownloadModelOptions</a></code> |

**Since:** 8.2.0

--------------------


### getDownloadedModels()

```typescript
getDownloadedModels() => Promise<GetDownloadedModelsResult>
```

Get the languages for which a model has been downloaded.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getdownloadedmodelsresult">GetDownloadedModelsResult</a>&gt;</code>

**Since:** 8.2.0

--------------------


### recognize(...)

```typescript
recognize(options: RecognizeOptions) => Promise<RecognizeResult>
```

Recognize the handwritten text or hand-drawn shapes in the given strokes.

The model for the given language must be downloaded before calling this method,
otherwise the call is rejected with the error code `MODEL_NOT_DOWNLOADED`.

Only available on Android and iOS.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#recognizeoptions">RecognizeOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#recognizeresult">RecognizeResult</a>&gt;</code>

**Since:** 8.2.0

--------------------


### Interfaces


#### DeleteDownloadedModelOptions

| Prop              | Type                | Description                                     | Since |
| ----------------- | ------------------- | ----------------------------------------------- | ----- |
| **`languageTag`** | <code>string</code> | The BCP-47 language tag of the model to delete. | 8.2.0 |


#### DownloadModelOptions

| Prop              | Type                | Description                                                                                                                                                                                                                                                                                    | Since |
| ----------------- | ------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`languageTag`** | <code>string</code> | The BCP-47 language tag of the model to download. See the [supported languages](https://developers.google.com/ml-kit/vision/digital-ink-recognition/base-models) for a list of available models. The special tag `zxx-Zsym-x-autodraw` identifies the model for recognizing hand-drawn shapes. | 8.2.0 |


#### GetDownloadedModelsResult

| Prop               | Type                  | Description                                        | Since |
| ------------------ | --------------------- | -------------------------------------------------- | ----- |
| **`languageTags`** | <code>string[]</code> | The BCP-47 language tags of the downloaded models. | 8.2.0 |


#### RecognizeResult

| Prop             | Type                                | Description                                               | Since |
| ---------------- | ----------------------------------- | --------------------------------------------------------- | ----- |
| **`candidates`** | <code>RecognitionCandidate[]</code> | The recognition candidates ordered by descending quality. | 8.2.0 |


#### RecognitionCandidate

| Prop        | Type                | Description                                                                                            | Since |
| ----------- | ------------------- | ------------------------------------------------------------------------------------------------------ | ----- |
| **`text`**  | <code>string</code> | The recognized text.                                                                                   | 8.2.0 |
| **`score`** | <code>number</code> | The score of the candidate. Lower scores indicate better results. The score is not set for all models. | 8.2.0 |


#### RecognizeOptions

| Prop                 | Type                                                | Description                                                                                                                                                                                         | Default        | Since |
| -------------------- | --------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | -------------- | ----- |
| **`languageTag`**    | <code>string</code>                                 | The BCP-47 language tag of the model to use for recognition.                                                                                                                                        |                | 8.2.0 |
| **`strokes`**        | <code>Stroke[]</code>                               | The strokes to recognize.                                                                                                                                                                           |                | 8.2.0 |
| **`maxResultCount`** | <code>number</code>                                 | The maximum number of recognition candidates to return.                                                                                                                                             | <code>5</code> | 8.2.0 |
| **`preContext`**     | <code>string</code>                                 | The characters immediately preceding the strokes to improve recognition accuracy. A good rule of thumb is to provide as many characters as possible, up to around 20 characters (including spaces). |                | 8.2.0 |
| **`writingArea`**    | <code><a href="#writingarea">WritingArea</a></code> | The size of the writing area to improve recognition accuracy.                                                                                                                                       |                | 8.2.0 |


#### Stroke

| Prop         | Type                       | Description                                                        | Since |
| ------------ | -------------------------- | ------------------------------------------------------------------ | ----- |
| **`points`** | <code>StrokePoint[]</code> | The points of the stroke in the order in which they were captured. | 8.2.0 |


#### StrokePoint

| Prop    | Type                | Description                                                                                      | Since |
| ------- | ------------------- | ------------------------------------------------------------------------------------------------ | ----- |
| **`x`** | <code>number</code> | The horizontal coordinate of the point. Increases to the right.                                  | 8.2.0 |
| **`y`** | <code>number</code> | The vertical coordinate of the point. Increases going downward.                                  | 8.2.0 |
| **`t`** | <code>number</code> | The timestamp of the point in milliseconds. Providing a timestamp improves recognition accuracy. | 8.2.0 |


#### WritingArea

| Prop         | Type                | Description                                                                                           | Since |
| ------------ | ------------------- | ----------------------------------------------------------------------------------------------------- | ----- |
| **`width`**  | <code>number</code> | The width of the writing area. The unit must be the same as the one used for the stroke coordinates.  | 8.2.0 |
| **`height`** | <code>number</code> | The height of the writing area. The unit must be the same as the one used for the stroke coordinates. | 8.2.0 |

</docgen-api>

## FAQ

### Which platforms are supported by this plugin?

The plugin is available on Android and iOS. The recognition and model management methods are only available on Android and iOS, so there is no web implementation.

### Which languages are supported?

Models are available for [over 300 languages](https://developers.google.com/ml-kit/vision/digital-ink-recognition/base-models) identified by BCP-47 language tags. There are also special models for recognizing hand-drawn shapes (`zxx-Zsym-x-autodraw`) and emojis (`zxx-Zsym-x-emoji`).

### Do I need to download a model?

Yes. A model must be downloaded for each language before it can be used with the `recognize(...)` method. If the model has not been downloaded yet, the call is rejected with the error code `MODEL_NOT_DOWNLOADED`.

### How do I capture the strokes?

The plugin does not provide a drawing surface. Capture the pointer events on your own canvas and pass the collected strokes to the `recognize(...)` method (see [Usage](#usage)).

### Can I install this plugin using Swift Package Manager?

No, this plugin only supports CocoaPods for iOS dependency management because the ML Kit SDK itself does not support Swift Package Manager. Also make sure to set the deployment target in your `ios/App/Podfile` to at least `15.5` (see [Installation](#installation)).

## Related Plugins

- [Translation](https://capawesome.io/docs/sdks/capacitor/mlkit/translation/): Translate the recognized text into other languages.
- [Language Identification](https://capawesome.io/docs/sdks/capacitor/mlkit/language-identification/): Identify the language of the recognized text.

## Terms & Privacy

This plugin uses the [Google ML Kit](https://developers.google.com/ml-kit):

- [Terms & Privacy](https://developers.google.com/ml-kit/terms)
- [Android Data Disclosure](https://developers.google.com/ml-kit/android-data-disclosure)
- [iOS Data Disclosure](https://developers.google.com/ml-kit/ios-data-disclosure)

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/digital-ink-recognition/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/digital-ink-recognition/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.
