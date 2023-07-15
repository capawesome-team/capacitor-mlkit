# @capacitor-mlkit/face-detection

Unofficial Capacitor plugin for [ML Kit Face Detection](https://developers.google.com/ml-kit/vision/face-detection).[^1]

## Installation

```bash
npm install @capacitor-mlkit/face-detection
npx cap sync
```

### Android

You need to add the following meta data **in** the `application` tag in your `AndroidManifest.xml`:

```xml
<meta-data android:name="com.google.mlkit.vision.DEPENDENCIES" android:value="face"/>
<!-- To use multiple models: android:value="face,model2,model3" -->
```

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$mlkitFaceDetectionVersion` version of `com.google.mlkit:face-detection` (default: `16.1.5`)
- `$playServicesMlkitFaceDetectionVersion` version of `com.google.android.gms:play-services-mlkit-face-detection` (default: `17.1.0`)

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-mlkit-plugin-demo](https://github.com/robingenz/capacitor-mlkit-plugin-demo)

## Usage

```typescript
import { FaceDetection } from '@capacitor-mlkit/face-detection';

const echo = async () => {
  await FaceDetection.echo();
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

Detects human faces from the supplied image.

Only available on Android and iOS.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#processimageoptions">ProcessImageOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#processimageresult">ProcessImageResult</a>&gt;</code>

**Since:** 5.1.0

--------------------


### Interfaces


#### ProcessImageResult

| Prop        | Type                   | Description         | Since |
| ----------- | ---------------------- | ------------------- | ----- |
| **`faces`** | <code>unknown[]</code> | The detected faces. | 5.1.0 |


#### ProcessImageOptions

| Prop                     | Type                                                              | Description                                 | Since |
| ------------------------ | ----------------------------------------------------------------- | ------------------------------------------- | ----- |
| **`path`**               | <code>string</code>                                               | The local path to the image file.           | 5.1.0 |
| **`classificationMode`** | <code><a href="#classificationmode">ClassificationMode</a></code> | The classification mode for face detection. |       |


### Enums


#### ClassificationMode

| Members    | Value          | Description                                        | Since |
| ---------- | -------------- | -------------------------------------------------- | ----- |
| **`None`** | <code>1</code> | Performs no classification.                        | 5.1.0 |
| **`All`**  | <code>2</code> | Performs "eyes open" and "smiling" classification. | 5.1.0 |

</docgen-api>

## Terms & Privacy

This plugin uses the [Google ML Kit](https://developers.google.com/ml-kit):

- [Terms & Privacy](https://developers.google.com/ml-kit/terms)
- [Android Data Disclosure](https://developers.google.com/ml-kit/android-data-disclosure)
- [iOS Data Disclosure](https://developers.google.com/ml-kit/ios-data-disclosure)

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/face-detection/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/face-detection/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.
