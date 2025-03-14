# @capacitor-mlkit/subject-segmentation

Capacitor plugin for ML Kit Subject Segmentation.

## Installation

```bash
npm install @capacitor-mlkit/subject-segmentation
npx cap sync
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

</docgen-api>
