# Capacitor ML Kit Image Labeling Plugin

Unofficial Capacitor plugin for [ML Kit Image Labeling](https://developers.google.com/ml-kit/vision/image-labeling).[^1]

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Use Cases

The Image Labeling plugin is typically used whenever an app needs to understand the content of an image, for example:

- **Content categorization**: Automatically tag and organize photos by the objects, places, or activities they contain.
- **Accessibility**: Generate descriptive labels for images to support screen readers and other assistive technologies.
- **Content moderation**: Detect the presence of certain objects or scenes before an image is published.
- **Search and discovery**: Make an image library searchable by the labels detected in each image.

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
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capacitor-mlkit/image-labeling` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capacitor-mlkit/image-labeling
npx cap sync
```

**Attention**: This plugin **only supports CocoaPods** for iOS dependency management. Swift Package Manager (SPM) is not supported for the ML Kit SDK, see [this comment](https://github.com/googlesamples/mlkit/issues/180#issuecomment-1298964099).

### Android

#### Variables

If needed, you can define the following project variable in your app’s `variables.gradle` file to change the default version of the dependency:

- `$mlkitImageLabelingVersion` version of `com.google.mlkit:image-labeling` (default: `17.0.9`)

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

The following example shows how to detect labels in an image.

### Detect labels in an image

Detect labels in an image at a local path. You can configure the confidence threshold to control which labels are returned. Only available on Android and iOS:

```typescript
import { ImageLabeling } from '@capacitor-mlkit/image-labeling';

const processImage = async () => {
  const { labels } = await ImageLabeling.processImage({
    path: 'path/to/image.jpg',
    confidenceThreshold: 0.5,
  });
  return labels;
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

Detects labels in the supplied image.

Labels can describe objects, locations, activities, animal species, products and more.

Only available on Android and iOS.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#processimageoptions">ProcessImageOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#processimageresult">ProcessImageResult</a>&gt;</code>

**Since:** 8.2.0

--------------------


### Interfaces


#### ProcessImageResult

| Prop         | Type                      | Description          | Since |
| ------------ | ------------------------- | -------------------- | ----- |
| **`labels`** | <code>ImageLabel[]</code> | The detected labels. | 8.2.0 |


#### ImageLabel

Represents a label detected in an image.

| Prop             | Type                | Description                                                            | Since |
| ---------------- | ------------------- | ---------------------------------------------------------------------- | ----- |
| **`index`**      | <code>number</code> | The index of the label within the model.                               | 8.2.0 |
| **`text`**       | <code>string</code> | The text of the label (e.g. `Bicycle`). The text is always in English. | 8.2.0 |
| **`confidence`** | <code>number</code> | The confidence of the label. The value is between `0.0` and `1.0`.     | 8.2.0 |


#### ProcessImageOptions

| Prop                      | Type                | Description                                                                                                                                                         | Default          | Since |
| ------------------------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------- | ----- |
| **`path`**                | <code>string</code> | The local path to the image file.                                                                                                                                   |                  | 8.2.0 |
| **`confidenceThreshold`** | <code>number</code> | The confidence threshold for the labels. Only labels with a confidence greater than or equal to this value are returned. The value must be between `0.0` and `1.0`. | <code>0.5</code> | 8.2.0 |

</docgen-api>

## FAQ

### Which platforms are supported by this plugin?

The `processImage(...)` method is only available on Android and iOS. The Web platform is not supported by the underlying ML Kit Image Labeling SDK.

### Which model does the plugin use?

The plugin uses the base image labeling model, which is bundled with the app and can recognize more than 400 labels covering objects, places, activities, animal species, products, and more. Custom TensorFlow Lite models are currently not supported.

### What does the confidence threshold do?

The `confidenceThreshold` option controls which labels are returned. Only labels with a confidence greater than or equal to the threshold are included in the result. The value must be between `0.0` and `1.0` and defaults to `0.5`.

### In which language are the labels returned?

The label text is always returned in English. Use the `index` property of a label together with your own localized lookup table if you need translated labels.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [ML Kit Barcode Scanning](https://capawesome.io/docs/sdks/capacitor/mlkit/barcode-scanning/): Scan barcodes with ML Kit Barcode Scanning.
- [ML Kit Face Detection](https://capawesome.io/docs/sdks/capacitor/mlkit/face-detection/): Detect faces in images with ML Kit Face Detection.
- [ML Kit Subject Segmentation](https://capawesome.io/docs/sdks/capacitor/mlkit/subject-segmentation/): Separate subjects from the background with ML Kit Subject Segmentation.

## Terms & Privacy

This plugin uses the [Google ML Kit](https://developers.google.com/ml-kit):

- [Terms & Privacy](https://developers.google.com/ml-kit/terms)
- [Android Data Disclosure](https://developers.google.com/ml-kit/android-data-disclosure)
- [iOS Data Disclosure](https://developers.google.com/ml-kit/ios-data-disclosure)

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/image-labeling/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/image-labeling/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.
