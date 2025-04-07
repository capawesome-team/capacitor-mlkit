# @capacitor-mlkit/subject-segmentation

Unofficial Capacitor plugin for [ML Kit Subject Segmentation](https://developers.google.com/ml-kit/vision/subject-segmentation).[^1]

## Installation

```bash
npm install @capacitor-mlkit/subject-segmentation
npx cap sync
```

### Android

#### API level

This plugin requires a minimum API level of 24.

#### Permissions

This API requires the following permissions be added to your `AndroidManifest.xml` before the `application` tag:

```xml
<uses-permission android:name="android.permission.CAMERA" />
```

You also need to add the following meta data **in** the `application` tag in your `AndroidManifest.xml`:

```xml
<meta-data android:name="com.google.mlkit.vision.DEPENDENCIES" android:value="subject_segment"/>
<!-- To use multiple models: android:value="face,model2,model3" -->
```

## Usage

```typescript
import { SubjectSegmentation } from '@capacitor-mlkit/subject-segmentation';

const processImage = async () => {
  const { path } = await SubjectSegmentation.processImage({
    path: 'path/to/image.jpg',
    confidence: 0.7,
  });
  return path;
};
```

## API

<docgen-index>

* [`processImage(...)`](#processimage)
* [`isGoogleSubjectSegmentationModuleAvailable()`](#isgooglesubjectsegmentationmoduleavailable)
* [`installGoogleSubjectSegmentationModule()`](#installgooglesubjectsegmentationmodule)
* [`addListener('googleSubjectSegmentationModuleInstallProgress', ...)`](#addlistenergooglesubjectsegmentationmoduleinstallprogress-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Enums](#enums)

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

**Since:** 7.2.0

--------------------


### isGoogleSubjectSegmentationModuleAvailable()

```typescript
isGoogleSubjectSegmentationModuleAvailable() => Promise<IsGoogleSubjectSegmentationModuleAvailableResult>
```

Check if the Google Subject Segmentation module is available.

If the Google Subject Segmentation module is not available, you can install it by using `installGoogleSubjectSegmentationModule()`.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#isgooglesubjectsegmentationmoduleavailableresult">IsGoogleSubjectSegmentationModuleAvailableResult</a>&gt;</code>

**Since:** 7.2.0

--------------------


### installGoogleSubjectSegmentationModule()

```typescript
installGoogleSubjectSegmentationModule() => Promise<void>
```

Install the Google Subject Segmentation module.

**Attention**: This only starts the installation.
The `googleSubjectSegmentationModuleInstallProgress` event listener will
notify you when the installation is complete.

Only available on Android.

**Since:** 7.2.0

--------------------


### addListener('googleSubjectSegmentationModuleInstallProgress', ...)

```typescript
addListener(eventName: 'googleSubjectSegmentationModuleInstallProgress', listenerFunc: (event: GoogleSubjectSegmentationModuleInstallProgressEvent) => void) => Promise<PluginListenerHandle>
```

Called when the Google Subject Segmentation module is installed.

Only available on Android.

| Param              | Type                                                                                                                                                    |
| ------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'googleSubjectSegmentationModuleInstallProgress'</code>                                                                                           |
| **`listenerFunc`** | <code>(event: <a href="#googlesubjectsegmentationmoduleinstallprogressevent">GoogleSubjectSegmentationModuleInstallProgressEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 7.2.0

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

Only available on Android.

**Since:** 7.2.0

--------------------


### Interfaces


#### ProcessImageResult

| Prop         | Type                | Description                           | Since |
| ------------ | ------------------- | ------------------------------------- | ----- |
| **`path`**   | <code>string</code> | The path to the segmented image file. | 7.2.0 |
| **`width`**  | <code>number</code> | Returns the width of the image file.  | 7.2.0 |
| **`height`** | <code>number</code> | Returns the height of the image file. | 7.2.0 |


#### ProcessImageOptions

| Prop             | Type                | Description                                                                               | Default          | Since |
| ---------------- | ------------------- | ----------------------------------------------------------------------------------------- | ---------------- | ----- |
| **`path`**       | <code>string</code> | The local path to the image file.                                                         |                  | 7.2.0 |
| **`width`**      | <code>number</code> | Scale the image to this width. If no `height` is given, it will respect the aspect ratio. |                  | 7.2.0 |
| **`height`**     | <code>number</code> | Scale the image to this height. If no `width` is given, it will respect the aspect ratio. |                  | 7.2.0 |
| **`confidence`** | <code>number</code> | Sets the confidence threshold.                                                            | <code>0.9</code> | 7.2.0 |


#### IsGoogleSubjectSegmentationModuleAvailableResult

| Prop            | Type                 | Description                                                         | Since |
| --------------- | -------------------- | ------------------------------------------------------------------- | ----- |
| **`available`** | <code>boolean</code> | Whether or not the Google Subject Segmentation module is available. | 7.2.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### GoogleSubjectSegmentationModuleInstallProgressEvent

| Prop           | Type                                                                                                                | Description                                                    | Since |
| -------------- | ------------------------------------------------------------------------------------------------------------------- | -------------------------------------------------------------- | ----- |
| **`state`**    | <code><a href="#googlesubjectsegmentationmoduleinstallstate">GoogleSubjectSegmentationModuleInstallState</a></code> | The current state of the installation.                         | 7.2.0 |
| **`progress`** | <code>number</code>                                                                                                 | The progress of the installation in percent between 0 and 100. | 7.2.0 |


### Enums


#### GoogleSubjectSegmentationModuleInstallState

| Members               | Value          | Since |
| --------------------- | -------------- | ----- |
| **`UNKNOWN`**         | <code>0</code> | 7.2.0 |
| **`PENDING`**         | <code>1</code> | 7.2.0 |
| **`DOWNLOADING`**     | <code>2</code> | 7.2.0 |
| **`CANCELED`**        | <code>3</code> | 7.2.0 |
| **`COMPLETED`**       | <code>4</code> | 7.2.0 |
| **`FAILED`**          | <code>5</code> | 7.2.0 |
| **`INSTALLING`**      | <code>6</code> | 7.2.0 |
| **`DOWNLOAD_PAUSED`** | <code>7</code> | 7.2.0 |

</docgen-api>

## Terms & Privacy

This plugin uses the [Google ML Kit](https://developers.google.com/ml-kit):

- [Terms & Privacy](https://developers.google.com/ml-kit/terms)
- [Android Data Disclosure](https://developers.google.com/ml-kit/android-data-disclosure)
- [iOS Data Disclosure](https://developers.google.com/ml-kit/ios-data-disclosure)

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/subject-segmentation/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/subject-segmentation/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.
