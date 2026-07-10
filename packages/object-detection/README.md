# Capacitor ML Kit Object Detection Plugin

Unofficial Capacitor plugin for [ML Kit Object Detection and Tracking](https://developers.google.com/ml-kit/vision/object-detection).[^1]

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Use Cases

The Object Detection plugin is typically used whenever an app needs to locate and classify the prominent objects in an image, for example:

- **Visual search**: Let users draw a box around an object in a photo and search for similar products.
- **Content categorization**: Automatically tag images by the objects they contain (fashion goods, food, plants, and more).
- **Image cropping**: Use the bounding box of the most prominent object to automatically crop or focus an image.
- **Inventory management**: Detect and count objects in a photo to support cataloging workflows.

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
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capacitor-mlkit/object-detection` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capacitor-mlkit/object-detection
npx cap sync
```

**Attention**: This plugin **only supports CocoaPods** for iOS dependency management. Swift Package Manager (SPM) is not supported for the ML Kit SDK, see [this comment](https://github.com/googlesamples/mlkit/issues/180#issuecomment-1298964099).

### Android

#### Variables

If needed, you can define the following project variable in your app’s `variables.gradle` file to change the default version of the dependency:

- `$mlkitObjectDetectionVersion` version of `com.google.mlkit:object-detection` (default: `17.0.2`)

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

The following example shows how to detect objects in an image.

### Detect objects in an image

Detect objects in an image at a local path. You can optionally enable classification and the detection of multiple objects. Only available on Android and iOS:

```typescript
import { ObjectDetection } from '@capacitor-mlkit/object-detection';

const processImage = async () => {
  const { detectedObjects } = await ObjectDetection.processImage({
    path: 'path/to/image.jpg',
    shouldEnableClassification: true,
    shouldEnableMultipleObjects: true,
  });
  return detectedObjects;
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

Detects objects in the supplied image.

Each detected object is localized with a bounding box and, if classification
is enabled, labeled with one of five coarse categories.

Only available on Android and iOS.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#processimageoptions">ProcessImageOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#processimageresult">ProcessImageResult</a>&gt;</code>

**Since:** 8.2.0

--------------------


### Interfaces


#### ProcessImageResult

| Prop                  | Type                          | Description           | Since |
| --------------------- | ----------------------------- | --------------------- | ----- |
| **`detectedObjects`** | <code>DetectedObject[]</code> | The detected objects. | 8.2.0 |


#### DetectedObject

Represents an object detected in an image.

| Prop              | Type                                  | Description                                                                                          | Since |
| ----------------- | ------------------------------------- | ---------------------------------------------------------------------------------------------------- | ----- |
| **`boundingBox`** | <code><a href="#rect">Rect</a></code> | The bounding box of the detected object.                                                             | 8.2.0 |
| **`trackingId`**  | <code>number</code>                   | The tracking identifier of the detected object. Only set when the object is detected in stream mode. | 8.2.0 |
| **`labels`**      | <code>ObjectLabel[]</code>            | The labels of the detected object. The list is empty if classification is disabled.                  | 8.2.0 |


#### Rect

Represents a rectangle.

| Prop         | Type                | Description                                       | Since |
| ------------ | ------------------- | ------------------------------------------------- | ----- |
| **`left`**   | <code>number</code> | The left coordinate of the rectangle in pixels.   | 8.2.0 |
| **`top`**    | <code>number</code> | The top coordinate of the rectangle in pixels.    | 8.2.0 |
| **`right`**  | <code>number</code> | The right coordinate of the rectangle in pixels.  | 8.2.0 |
| **`bottom`** | <code>number</code> | The bottom coordinate of the rectangle in pixels. | 8.2.0 |


#### ObjectLabel

Represents a label of a detected object.

| Prop             | Type                | Description                                                         | Since |
| ---------------- | ------------------- | ------------------------------------------------------------------- | ----- |
| **`index`**      | <code>number</code> | The index of the label.                                             | 8.2.0 |
| **`text`**       | <code>string</code> | The text of the label (e.g. `Food`). The text is always in English. | 8.2.0 |
| **`confidence`** | <code>number</code> | The confidence of the label. The value is between `0.0` and `1.0`.  | 8.2.0 |


#### ProcessImageOptions

| Prop                              | Type                 | Description                                                                                                                                                                     | Default            | Since |
| --------------------------------- | -------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`path`**                        | <code>string</code>  | The local path to the image file.                                                                                                                                               |                    | 8.2.0 |
| **`shouldEnableClassification`**  | <code>boolean</code> | Whether to classify the detected objects into coarse categories. Classification returns only five coarse categories (`Fashion good`, `Food`, `Home good`, `Place` and `Plant`). | <code>false</code> | 8.2.0 |
| **`shouldEnableMultipleObjects`** | <code>boolean</code> | Whether to detect multiple objects instead of only the most prominent one. When enabled, up to five objects are detected.                                                       | <code>false</code> | 8.2.0 |

</docgen-api>

## FAQ

### Which platforms are supported by this plugin?

The `processImage(...)` method is only available on Android and iOS. The Web platform is not supported by the underlying ML Kit Object Detection SDK.

### Which model does the plugin use?

The plugin uses the base object detection and tracking model, which is bundled with the app. Custom TensorFlow Lite classifier models are currently not supported.

### Which categories does classification return?

When `shouldEnableClassification` is enabled, the base model classifies detected objects into only five coarse categories: `Fashion good`, `Food`, `Home good`, `Place`, and `Plant`. Any object that does not fall into one of these categories receives no label.

### Why is the `trackingId` not set?

The plugin processes still images in single-image mode, where objects are not tracked across frames. The `trackingId` is therefore not set. It is only populated when objects are detected in stream mode, which is not supported by this plugin.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [ML Kit Barcode Scanning](https://capawesome.io/docs/sdks/capacitor/mlkit/barcode-scanning/): Scan barcodes with ML Kit Barcode Scanning.
- [ML Kit Face Detection](https://capawesome.io/docs/sdks/capacitor/mlkit/face-detection/): Detect faces in images with ML Kit Face Detection.
- [ML Kit Image Labeling](https://capawesome.io/docs/sdks/capacitor/mlkit/image-labeling/): Detect labels in images with ML Kit Image Labeling.

## Terms & Privacy

This plugin uses the [Google ML Kit](https://developers.google.com/ml-kit):

- [Terms & Privacy](https://developers.google.com/ml-kit/terms)
- [Android Data Disclosure](https://developers.google.com/ml-kit/android-data-disclosure)
- [iOS Data Disclosure](https://developers.google.com/ml-kit/ios-data-disclosure)

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/object-detection/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/object-detection/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.
