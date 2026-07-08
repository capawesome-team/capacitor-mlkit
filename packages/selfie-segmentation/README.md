# Capacitor ML Kit Selfie Segmentation Plugin

Unofficial Capacitor plugin for [ML Kit Selfie Segmentation](https://developers.google.com/ml-kit/vision/selfie-segmentation).[^1]

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Use Cases

The Selfie Segmentation plugin is typically used to separate a person from the background of a photo, for example:

- **Background removal**: Remove or replace the background of a selfie, for example for profile pictures or avatars.
- **Photo effects**: Build editing features that combine the segmented image with new backgrounds or overlays.
- **Sticker creation**: Turn selfies into cutouts or stickers that users can share in chats and posts.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 8.x.x          | >=8.x.x           | Active support |
| 7.x.x          | 7.x.x             | Deprecated     |
| 6.x.x          | 6.x.x             | Deprecated     |

## Installation

You can use our **AI-Assisted Setup** to install the plugin.
Add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills --skill capacitor-plugins
```

Then use the following prompt:

```
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capacitor-mlkit/selfie-segmentation` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capacitor-mlkit/selfie-segmentation
npx cap sync
```

**Attention**: This plugin **only supports CocoaPods** for iOS dependency management. Swift Package Manager (SPM) is not supported for the ML Kit SDK, see [this comment](https://github.com/googlesamples/mlkit/issues/180#issuecomment-1298964099).

### Android

#### Variables

If needed, you can define the following project variable in your app’s `variables.gradle` file to change the default version of the dependency:

- `$mlkitSelfieSegmentationVersion` version of `com.google.mlkit:segmentation-selfie` (default: `16.0.0-beta6`)

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

| Android                                                                                                                           | iOS                                                                                                                               |
| --------------------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------- |
| <img src="https://github.com/capawesome-team/capacitor-mlkit/assets/13857929/1cef9ea9-570e-47d9-a751-d778535ffdff" width="324" alt="Android demo of the Selfie Segmentation plugin" /> | <img src="https://github.com/capawesome-team/capacitor-mlkit/assets/13857929/b9caa88c-31b9-4a25-a731-961625207af4" width="324" alt="iOS demo of the Selfie Segmentation plugin" /> |

## Usage

The following example shows how to segment a person from the background.

### Segment a person from the background

Pass the local path of an image file to `processImage(...)` to perform the segmentation. You can optionally scale the image using the `width` and `height` options and adjust the confidence threshold. The result contains the path to the segmented image file along with its width and height. Only available on Android and iOS:

```typescript
import { SelfieSegmentation } from '@capacitor-mlkit/selfie-segmentation';

const processImage = async () => {
  const { path } = await SelfieSegmentation.processImage({
    path: 'path/to/image.jpg',
    confidence: 0.7,
  });
  return path;
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

| Prop         | Type                | Description                           | Since |
| ------------ | ------------------- | ------------------------------------- | ----- |
| **`path`**   | <code>string</code> | The path to the segmented image file. | 5.2.0 |
| **`width`**  | <code>number</code> | Returns the width of the image file.  | 5.2.0 |
| **`height`** | <code>number</code> | Returns the height of the image file. | 5.2.0 |


#### ProcessImageOptions

| Prop             | Type                | Description                                                                               | Default          | Since |
| ---------------- | ------------------- | ----------------------------------------------------------------------------------------- | ---------------- | ----- |
| **`path`**       | <code>string</code> | The local path to the image file.                                                         |                  | 5.2.0 |
| **`width`**      | <code>number</code> | Scale the image to this width. If no `height` is given, it will respect the aspect ratio. |                  | 5.2.0 |
| **`height`**     | <code>number</code> | Scale the image to this height. If no `width` is given, it will respect the aspect ratio. |                  | 5.2.0 |
| **`confidence`** | <code>number</code> | Sets the confidence threshold.                                                            | <code>0.9</code> | 5.2.0 |

</docgen-api>

## FAQ

### Which platforms are supported by this plugin?

The plugin is available on Android and iOS. The `processImage(...)` method is only available on Android and iOS, so there is no web implementation.

### Can I install this plugin using Swift Package Manager?

No, this plugin only supports CocoaPods for iOS dependency management because the ML Kit SDK itself does not support Swift Package Manager. Also make sure to set the deployment target in your `ios/App/Podfile` to at least `15.5` (see [Installation](#installation)).

### How can I adjust the accuracy of the segmentation?

You can set the confidence threshold using the `confidence` option of the `processImage(...)` method. The default value is `0.9`. Experiment with different values to find the best result for your images.

### How can I scale the image before it is processed?

Use the `width` and `height` options of the `processImage(...)` method to scale the image. If only one of the two values is given, the aspect ratio of the image is respected.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Subject Segmentation](https://capawesome.io/docs/sdks/capacitor/mlkit/subject-segmentation/): Unofficial Capacitor plugin for ML Kit Subject Segmentation.
- [Face Detection](https://capawesome.io/docs/sdks/capacitor/mlkit/face-detection/): Unofficial Capacitor plugin for ML Kit Face Detection.
- [Face Mesh Detection](https://capawesome.io/docs/sdks/capacitor/mlkit/face-mesh-detection/): Unofficial Capacitor plugin for ML Kit Face Mesh Detection.

## Terms & Privacy

This plugin uses the [Google ML Kit](https://developers.google.com/ml-kit):

- [Terms & Privacy](https://developers.google.com/ml-kit/terms)
- [Android Data Disclosure](https://developers.google.com/ml-kit/android-data-disclosure)
- [iOS Data Disclosure](https://developers.google.com/ml-kit/ios-data-disclosure)

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/selfie-segmentation/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/selfie-segmentation/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.
