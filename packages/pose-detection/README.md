# Capacitor ML Kit Pose Detection Plugin

Unofficial Capacitor plugin for [ML Kit Pose Detection](https://developers.google.com/ml-kit/vision/pose-detection).[^1]

> [!WARNING]
> The ML Kit Pose Detection API is currently in **beta**. The API surface and behavior may change in future releases.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Use Cases

The Pose Detection plugin is typically used whenever an app needs to track the position of a person's body in an image, for example:

- **Fitness tracking**: Analyze a user's posture and body position while they exercise.
- **Gesture recognition**: Detect body gestures to control an app or a game.
- **Augmented reality**: Overlay effects or clothing on top of a person's body.
- **Physical therapy**: Monitor a patient's movements and range of motion.

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
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capacitor-mlkit/pose-detection` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capacitor-mlkit/pose-detection
npx cap sync
```

**Attention**: This plugin **only supports CocoaPods** for iOS dependency management. Swift Package Manager (SPM) is not supported for the ML Kit SDK, see [this comment](https://github.com/googlesamples/mlkit/issues/180#issuecomment-1298964099).

### Android

#### Variables

If needed, you can define the following project variables in your app’s `variables.gradle` file to change the default version of the dependencies:

- `$mlkitPoseDetectionVersion` version of `com.google.mlkit:pose-detection` (default: `18.0.0-beta5`)
- `$mlkitPoseDetectionAccurateVersion` version of `com.google.mlkit:pose-detection-accurate` (default: `18.0.0-beta5`)

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

The following example shows how to detect the pose of a person in an image.

### Detect a pose in an image

Detect the pose of a person in an image at a local path. You can optionally select the performance mode of the pose detector. Only available on Android and iOS:

```typescript
import { PoseDetection, PerformanceMode } from '@capacitor-mlkit/pose-detection';

const processImage = async () => {
  const { poses } = await PoseDetection.processImage({
    path: 'path/to/image.jpg',
    performanceMode: PerformanceMode.Base,
  });
  return poses;
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

Detects the pose of a person in the supplied image.

The API detects the pose of a single (most prominent) person and returns
all 33 skeletal landmarks with their positions.

Only available on Android and iOS.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#processimageoptions">ProcessImageOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#processimageresult">ProcessImageResult</a>&gt;</code>

**Since:** 8.2.0

--------------------


### Interfaces


#### ProcessImageResult

| Prop        | Type                | Description                                                                                                               | Since |
| ----------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`poses`** | <code>Pose[]</code> | The detected poses. The array contains either zero or one pose, because the API only detects the pose of a single person. | 8.2.0 |


#### Pose

Represents a pose detected in an image.

| Prop            | Type                        | Description                                                                     | Since |
| --------------- | --------------------------- | ------------------------------------------------------------------------------- | ----- |
| **`landmarks`** | <code>PoseLandmark[]</code> | The detected landmarks of the pose. The array always contains all 33 landmarks. | 8.2.0 |


#### PoseLandmark

Represents a landmark of a detected pose.

| Prop                    | Type                                                          | Description                                                                                                                                                                                     | Since |
| ----------------------- | ------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`type`**              | <code><a href="#poselandmarktype">PoseLandmarkType</a></code> | The type of the landmark (e.g. `NOSE`).                                                                                                                                                         | 8.2.0 |
| **`x`**                 | <code>number</code>                                           | The x coordinate of the landmark in the image in pixels.                                                                                                                                        | 8.2.0 |
| **`y`**                 | <code>number</code>                                           | The y coordinate of the landmark in the image in pixels.                                                                                                                                        | 8.2.0 |
| **`z`**                 | <code>number</code>                                           | The z coordinate of the landmark. The value represents the depth of the landmark. The smaller the value, the closer the landmark is to the camera. The origin is the midpoint between the hips. | 8.2.0 |
| **`inFrameLikelihood`** | <code>number</code>                                           | The likelihood that the landmark is within the image frame. The value is between `0.0` and `1.0`.                                                                                               | 8.2.0 |


#### ProcessImageOptions

| Prop                  | Type                                                        | Description                                | Default                           | Since |
| --------------------- | ----------------------------------------------------------- | ------------------------------------------ | --------------------------------- | ----- |
| **`path`**            | <code>string</code>                                         | The local path to the image file.          |                                   | 8.2.0 |
| **`performanceMode`** | <code><a href="#performancemode">PerformanceMode</a></code> | The performance mode of the pose detector. | <code>PerformanceMode.Base</code> | 8.2.0 |


### Enums


#### PoseLandmarkType

| Members              | Value                           |
| -------------------- | ------------------------------- |
| **`Nose`**           | <code>'NOSE'</code>             |
| **`LeftEyeInner`**   | <code>'LEFT_EYE_INNER'</code>   |
| **`LeftEye`**        | <code>'LEFT_EYE'</code>         |
| **`LeftEyeOuter`**   | <code>'LEFT_EYE_OUTER'</code>   |
| **`RightEyeInner`**  | <code>'RIGHT_EYE_INNER'</code>  |
| **`RightEye`**       | <code>'RIGHT_EYE'</code>        |
| **`RightEyeOuter`**  | <code>'RIGHT_EYE_OUTER'</code>  |
| **`LeftEar`**        | <code>'LEFT_EAR'</code>         |
| **`RightEar`**       | <code>'RIGHT_EAR'</code>        |
| **`LeftMouth`**      | <code>'LEFT_MOUTH'</code>       |
| **`RightMouth`**     | <code>'RIGHT_MOUTH'</code>      |
| **`LeftShoulder`**   | <code>'LEFT_SHOULDER'</code>    |
| **`RightShoulder`**  | <code>'RIGHT_SHOULDER'</code>   |
| **`LeftElbow`**      | <code>'LEFT_ELBOW'</code>       |
| **`RightElbow`**     | <code>'RIGHT_ELBOW'</code>      |
| **`LeftWrist`**      | <code>'LEFT_WRIST'</code>       |
| **`RightWrist`**     | <code>'RIGHT_WRIST'</code>      |
| **`LeftPinky`**      | <code>'LEFT_PINKY'</code>       |
| **`RightPinky`**     | <code>'RIGHT_PINKY'</code>      |
| **`LeftIndex`**      | <code>'LEFT_INDEX'</code>       |
| **`RightIndex`**     | <code>'RIGHT_INDEX'</code>      |
| **`LeftThumb`**      | <code>'LEFT_THUMB'</code>       |
| **`RightThumb`**     | <code>'RIGHT_THUMB'</code>      |
| **`LeftHip`**        | <code>'LEFT_HIP'</code>         |
| **`RightHip`**       | <code>'RIGHT_HIP'</code>        |
| **`LeftKnee`**       | <code>'LEFT_KNEE'</code>        |
| **`RightKnee`**      | <code>'RIGHT_KNEE'</code>       |
| **`LeftAnkle`**      | <code>'LEFT_ANKLE'</code>       |
| **`RightAnkle`**     | <code>'RIGHT_ANKLE'</code>      |
| **`LeftHeel`**       | <code>'LEFT_HEEL'</code>        |
| **`RightHeel`**      | <code>'RIGHT_HEEL'</code>       |
| **`LeftFootIndex`**  | <code>'LEFT_FOOT_INDEX'</code>  |
| **`RightFootIndex`** | <code>'RIGHT_FOOT_INDEX'</code> |


#### PerformanceMode

| Members        | Value                   | Description                                     | Since |
| -------------- | ----------------------- | ----------------------------------------------- | ----- |
| **`Base`**     | <code>'BASE'</code>     | The base model is faster but less accurate.     | 8.2.0 |
| **`Accurate`** | <code>'ACCURATE'</code> | The accurate model is slower but more accurate. | 8.2.0 |

</docgen-api>

## FAQ

### Which platforms are supported by this plugin?

The `processImage(...)` method is only available on Android and iOS. The Web platform is not supported by the underlying ML Kit Pose Detection SDK.

### Which models does the plugin use?

The plugin bundles both the **base** and the **accurate** pose detection models. You can select the model at runtime via the `performanceMode` option. The base model is faster but less accurate, while the accurate model is slower but more accurate.

### How much does the plugin increase the app size?

The pose detection models are bundled with the app and increase the app size. The base model adds around 10 MB and the accurate model adds around 26 MB.

### How many people can the plugin detect?

The ML Kit Pose Detection API only detects the pose of a single (most prominent) person in an image. If you need to detect the pose of multiple people, this plugin is not suitable.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [ML Kit Barcode Scanning](https://capawesome.io/docs/sdks/capacitor/mlkit/barcode-scanning/): Scan barcodes with ML Kit Barcode Scanning.
- [ML Kit Face Detection](https://capawesome.io/docs/sdks/capacitor/mlkit/face-detection/): Detect faces in images with ML Kit Face Detection.
- [ML Kit Object Detection](https://capawesome.io/docs/sdks/capacitor/mlkit/object-detection/): Detect and track objects in images with ML Kit Object Detection.

## Terms & Privacy

This plugin uses the [Google ML Kit](https://developers.google.com/ml-kit):

- [Terms & Privacy](https://developers.google.com/ml-kit/terms)
- [Android Data Disclosure](https://developers.google.com/ml-kit/android-data-disclosure)
- [iOS Data Disclosure](https://developers.google.com/ml-kit/ios-data-disclosure)

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/pose-detection/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/pose-detection/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.
