# Capacitor ML Kit Text Recognition Plugin

Unofficial Capacitor plugin for [ML Kit Text Recognition](https://developers.google.com/ml-kit/vision/text-recognition/v2).[^1]

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Use Cases

The Text Recognition plugin is typically used whenever an app needs to extract text from an image, for example:

- **Document digitization**: Convert printed documents, receipts, or business cards into machine-readable text.
- **Data entry automation**: Extract text from forms or labels to prefill input fields and reduce manual typing.
- **Accessibility**: Read text found in images aloud to support screen readers and other assistive technologies.
- **Translation pipelines**: Recognize text in an image before passing it on to a translation service.

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
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capacitor-mlkit/text-recognition` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capacitor-mlkit/text-recognition
npx cap sync
```

**Attention**: This plugin **only supports CocoaPods** for iOS dependency management. Swift Package Manager (SPM) is not supported for the ML Kit SDK, see [this comment](https://github.com/googlesamples/mlkit/issues/180#issuecomment-1298964099).

### App Size Impact

This plugin bundles a separate model for each supported script (Latin, Chinese, Devanagari, Japanese, and Korean). Each model increases the size of your app by several megabytes. Keep this in mind when deciding which scripts your app actually needs.

### Android

#### Variables

If needed, you can define the following project variables in your app’s `variables.gradle` file to change the default version of the dependencies:

- `$mlkitTextRecognitionVersion` version of `com.google.mlkit:text-recognition` (default: `16.0.1`)
- `$mlkitTextRecognitionChineseVersion` version of `com.google.mlkit:text-recognition-chinese` (default: `16.0.1`)
- `$mlkitTextRecognitionDevanagariVersion` version of `com.google.mlkit:text-recognition-devanagari` (default: `16.0.1`)
- `$mlkitTextRecognitionJapaneseVersion` version of `com.google.mlkit:text-recognition-japanese` (default: `16.0.1`)
- `$mlkitTextRecognitionKoreanVersion` version of `com.google.mlkit:text-recognition-korean` (default: `16.0.1`)

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

The following example shows how to recognize text in an image.

### Recognize text in an image

Recognize text in an image at a local path. You can select the script of the text to recognize. Only available on Android and iOS:

```typescript
import { Script, TextRecognition } from '@capacitor-mlkit/text-recognition';

const processImage = async () => {
  const { text, blocks } = await TextRecognition.processImage({
    path: 'path/to/image.jpg',
    script: Script.Latin,
  });
  return { text, blocks };
};
```

## API

<docgen-index>

* [`processImage(...)`](#processimage)
* [Interfaces](#interfaces)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### processImage(...)

```typescript
processImage(options: ProcessImageOptions) => Promise<ProcessImageResult>
```

Recognizes text in the supplied image.

Only available on Android and iOS.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#processimageoptions">ProcessImageOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#processimageresult">ProcessImageResult</a>&gt;</code>

**Since:** 8.2.0

--------------------


### Interfaces


#### ProcessImageResult

| Prop         | Type                     | Description                    | Since |
| ------------ | ------------------------ | ------------------------------ | ----- |
| **`text`**   | <code>string</code>      | The full recognized text.      | 8.2.0 |
| **`blocks`** | <code>TextBlock[]</code> | The recognized blocks of text. | 8.2.0 |


#### TextBlock

Represents a block of text.

A block is a contiguous set of text lines, such as a paragraph or a column.

| Prop                     | Type                                  | Description                                                                                                     | Since |
| ------------------------ | ------------------------------------- | --------------------------------------------------------------------------------------------------------------- | ----- |
| **`text`**               | <code>string</code>                   | The recognized text of the block.                                                                               | 8.2.0 |
| **`boundingBox`**        | <code><a href="#rect">Rect</a></code> | The bounding box of the block.                                                                                  | 8.2.0 |
| **`cornerPoints`**       | <code>Point[]</code>                  | The four corner points of the block in clockwise order, starting with the top-left point relative to the image. | 8.2.0 |
| **`recognizedLanguage`** | <code>string</code>                   | The BCP-47 language code of the recognized language of the block.                                               | 8.2.0 |
| **`lines`**              | <code>TextLine[]</code>               | The recognized lines of text within the block.                                                                  | 8.2.0 |


#### Rect

Represents a rectangle.

| Prop         | Type                | Description                             | Since |
| ------------ | ------------------- | --------------------------------------- | ----- |
| **`left`**   | <code>number</code> | The left coordinate of the rectangle.   | 8.2.0 |
| **`top`**    | <code>number</code> | The top coordinate of the rectangle.    | 8.2.0 |
| **`right`**  | <code>number</code> | The right coordinate of the rectangle.  | 8.2.0 |
| **`bottom`** | <code>number</code> | The bottom coordinate of the rectangle. | 8.2.0 |


#### Point

Represents a point.

| Prop    | Type                | Description                    | Since |
| ------- | ------------------- | ------------------------------ | ----- |
| **`x`** | <code>number</code> | The x coordinate of the point. | 8.2.0 |
| **`y`** | <code>number</code> | The y coordinate of the point. | 8.2.0 |


#### TextLine

Represents a line of text.

| Prop                     | Type                                  | Description                                                                                                    | Since |
| ------------------------ | ------------------------------------- | -------------------------------------------------------------------------------------------------------------- | ----- |
| **`text`**               | <code>string</code>                   | The recognized text of the line.                                                                               | 8.2.0 |
| **`boundingBox`**        | <code><a href="#rect">Rect</a></code> | The bounding box of the line.                                                                                  | 8.2.0 |
| **`cornerPoints`**       | <code>Point[]</code>                  | The four corner points of the line in clockwise order, starting with the top-left point relative to the image. | 8.2.0 |
| **`recognizedLanguage`** | <code>string</code>                   | The BCP-47 language code of the recognized language of the line.                                               | 8.2.0 |
| **`elements`**           | <code>TextElement[]</code>            | The recognized elements of text within the line.                                                               | 8.2.0 |


#### TextElement

Represents an element of text.

An element is a contiguous set of characters, such as a word.

| Prop                     | Type                                  | Description                                                                                                       | Since |
| ------------------------ | ------------------------------------- | ----------------------------------------------------------------------------------------------------------------- | ----- |
| **`text`**               | <code>string</code>                   | The recognized text of the element.                                                                               | 8.2.0 |
| **`boundingBox`**        | <code><a href="#rect">Rect</a></code> | The bounding box of the element.                                                                                  | 8.2.0 |
| **`cornerPoints`**       | <code>Point[]</code>                  | The four corner points of the element in clockwise order, starting with the top-left point relative to the image. | 8.2.0 |
| **`recognizedLanguage`** | <code>string</code>                   | The BCP-47 language code of the recognized language of the element.                                               | 8.2.0 |


#### ProcessImageOptions

| Prop         | Type                                      | Description                                                                                              | Default                   | Since |
| ------------ | ----------------------------------------- | -------------------------------------------------------------------------------------------------------- | ------------------------- | ----- |
| **`path`**   | <code>string</code>                       | The local path to the image file.                                                                        |                           | 8.2.0 |
| **`script`** | <code><a href="#script">Script</a></code> | The script of the text to recognize. Each script requires a separate model that is bundled with the app. | <code>Script.Latin</code> | 8.2.0 |


### Enums


#### Script

| Members          | Value                     | Description            | Since |
| ---------------- | ------------------------- | ---------------------- | ----- |
| **`Latin`**      | <code>'LATIN'</code>      | The Latin script.      | 8.2.0 |
| **`Chinese`**    | <code>'CHINESE'</code>    | The Chinese script.    | 8.2.0 |
| **`Devanagari`** | <code>'DEVANAGARI'</code> | The Devanagari script. | 8.2.0 |
| **`Japanese`**   | <code>'JAPANESE'</code>   | The Japanese script.   | 8.2.0 |
| **`Korean`**     | <code>'KOREAN'</code>     | The Korean script.     | 8.2.0 |

</docgen-api>

## FAQ

### Which platforms are supported by this plugin?

The `processImage(...)` method is only available on Android and iOS. The Web platform is not supported by the underlying ML Kit Text Recognition SDK.

### Which scripts are supported?

The plugin supports the Latin, Chinese, Devanagari, Japanese, and Korean scripts. Use the `script` option to select the script of the text you want to recognize. Each script uses a separate model that is bundled with your app.

### Does bundling all scripts increase the app size?

Yes. Each script model adds several megabytes to your app. All five models are bundled so that every script can be used at runtime without an additional download.

### In which format do I have to provide the image path?

The `path` must be a local file path (e.g. `file:///path/to/image.jpg`). Remote URLs are not supported.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [ML Kit Barcode Scanning](https://capawesome.io/docs/sdks/capacitor/mlkit/barcode-scanning/): Scan barcodes with ML Kit Barcode Scanning.
- [ML Kit Image Labeling](https://capawesome.io/docs/sdks/capacitor/mlkit/image-labeling/): Detect labels in images with ML Kit Image Labeling.
- [ML Kit Face Detection](https://capawesome.io/docs/sdks/capacitor/mlkit/face-detection/): Detect faces in images with ML Kit Face Detection.

## Terms & Privacy

This plugin uses the [Google ML Kit](https://developers.google.com/ml-kit):

- [Terms & Privacy](https://developers.google.com/ml-kit/terms)
- [Android Data Disclosure](https://developers.google.com/ml-kit/android-data-disclosure)
- [iOS Data Disclosure](https://developers.google.com/ml-kit/ios-data-disclosure)

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/text-recognition/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/text-recognition/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.
