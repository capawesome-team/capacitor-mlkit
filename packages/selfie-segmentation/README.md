# @capacitor-mlkit/selfie-segmentation

Unofficial Capacitor plugin for [ML Kit Face Segmentation](https://developers.google.com/ml-kit/vision/selfie-segmentation).[^1]

## Installation

```bash
npm install @capacitor-mlkit/selfie-segmentation
npx cap sync
```

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$mlkitSelfieSegmentationVersion` version of `com.google.mlkit:segmentation-selfie` (default: `16.0.0-beta4`)

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-mlkit-plugin-demo](https://github.com/robingenz/capacitor-mlkit-plugin-demo)

## Usage

```typescript
import { SelfieSegmentation } from '@capacitor-mlkit/selfie-segmentation';

const echo = async () => {
  await SelfieSegmentation.echo();
};
```

## API

<docgen-index>

* [`processImage(...)`](#processimage)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### processImage(...)

```typescript
processImage(options: ProcessImageOptions) => Promise<ProcessImageResult>
```

Performs segmentation on an input image.

Only available on Android and iOS.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#processimageoptions">ProcessImageOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#processimageresult">ProcessImageResult</a>&gt;</code>

**Since:** 5.2.0

--------------------


### Interfaces


#### ProcessImageResult

| Prop         | Type                  | Description                                                                                                                                                                 | Since |
| ------------ | --------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`mask`**   | <code>number[]</code> | Returns a mask that indicates the foreground and background segmentation. This mask’s dimensions could vary, depending on whether a raw size mask is requested via options. | 5.2.0 |
| **`width`**  | <code>number</code>   | Returns the width of the mask.                                                                                                                                              | 5.2.0 |
| **`height`** | <code>number</code>   | Returns the height of the mask.                                                                                                                                             | 5.2.0 |


#### ProcessImageOptions

| Prop              | Type                 | Description                                                                         | Since |
| ----------------- | -------------------- | ----------------------------------------------------------------------------------- | ----- |
| **`path`**        | <code>string</code>  | The local path to the image file.                                                   | 5.2.0 |
| **`rawSizeMask`** | <code>boolean</code> | Asks the segmenter to return the raw size mask which matches the model output size. | 5.2.0 |

</docgen-api>

## Terms & Privacy

This plugin uses the [Google ML Kit](https://developers.google.com/ml-kit):

- [Terms & Privacy](https://developers.google.com/ml-kit/terms)
- [Android Data Disclosure](https://developers.google.com/ml-kit/android-data-disclosure)
- [iOS Data Disclosure](https://developers.google.com/ml-kit/ios-data-disclosure)

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/selfie-segmentation/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/selfie-segmentation/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.
