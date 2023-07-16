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
* [Type Aliases](#type-aliases)
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

| Prop        | Type                | Description         | Since |
| ----------- | ------------------- | ------------------- | ----- |
| **`faces`** | <code>Face[]</code> | The detected faces. | 5.1.0 |


#### Face

Represents a face detected by `FaceDetector`.
https://developers.google.com/android/reference/com/google/mlkit/vision/face/Face

| Prop                          | Type                                  |
| ----------------------------- | ------------------------------------- |
| **`bounds`**                  | <code><a href="#rect">Rect</a></code> |
| **`landmarks`**               | <code>FaceLandmark[]</code>           |
| **`contours`**                | <code>FaceContour[]</code>            |
| **`trackingId`**              | <code>number</code>                   |
| **`headEulerAngleX`**         | <code>number</code>                   |
| **`headEulerAngleY`**         | <code>number</code>                   |
| **`headEulerAngleZ`**         | <code>number</code>                   |
| **`smilingProbability`**      | <code>number</code>                   |
| **`leftEyeOpenProbability`**  | <code>number</code>                   |
| **`rightEyeOpenProbability`** | <code>number</code>                   |


#### Rect

<a href="#rect">Rect</a> holds four integer coordinates for a rectangle.
https://developer.android.com/reference/android/graphics/Rect.html

| Prop         | Type                |
| ------------ | ------------------- |
| **`left`**   | <code>number</code> |
| **`top`**    | <code>number</code> |
| **`right`**  | <code>number</code> |
| **`bottom`** | <code>number</code> |
| **`x`**      | <code>number</code> |
| **`y`**      | <code>number</code> |
| **`width`**  | <code>number</code> |
| **`height`** | <code>number</code> |


#### FaceLandmark

Represent a face landmark.
A landmark is a point on a detected face, such as an eye, nose, or mouth.
https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceLandmark

| Prop           | Type                                                  |
| -------------- | ----------------------------------------------------- |
| **`type`**     | <code><a href="#landmarktype">LandmarkType</a></code> |
| **`position`** | <code><a href="#pointf">PointF</a></code>             |


#### PointF

<a href="#pointf">PointF</a> holds two float coordinates
https://developer.android.com/reference/android/graphics/PointF.html

| Prop    | Type                |
| ------- | ------------------- |
| **`x`** | <code>number</code> |
| **`y`** | <code>number</code> |


#### FaceContour

Represent a face contour.
A contour is a list of points on a detected face, such as the mouth.
https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceContour

| Prop         | Type                                                |
| ------------ | --------------------------------------------------- |
| **`type`**   | <code><a href="#contourtype">ContourType</a></code> |
| **`points`** | <code>PointF[]</code>                               |


#### FaceDetectorOptions

https://developers.google.com/android/reference/com/google/mlkit/vision/face/FaceDetectorOptions

| Prop                     | Type                                                              |
| ------------------------ | ----------------------------------------------------------------- |
| **`performanceMode`**    | <code><a href="#performancemode">PerformanceMode</a></code>       |
| **`landmarkMode`**       | <code><a href="#landmarkmode">LandmarkMode</a></code>             |
| **`contourMode`**        | <code><a href="#contourmode">ContourMode</a></code>               |
| **`classificationMode`** | <code><a href="#classificationmode">ClassificationMode</a></code> |
| **`minFaceSize`**        | <code>number</code>                                               |
| **`enableTracking`**     | <code>boolean</code>                                              |


### Type Aliases


#### ProcessImageOptions

<code>( | { /** * The local path to the image file. * * @since 5.1.0 */ path: string; } | { /** * Represents an image object (Base64 encoded). * * @since 5.1.0 */ image: string; } ) & { /** * The options for the face detector. */ options?: <a href="#facedetectoroptions">FaceDetectorOptions</a>; }</code>


### Enums


#### LandmarkType

| Members            | Value           |
| ------------------ | --------------- |
| **`MOUTH_BOTTOM`** | <code>0</code>  |
| **`LEFT_CHEEK`**   | <code>1</code>  |
| **`LEFT_EAR`**     | <code>3</code>  |
| **`LEFT_EYE`**     | <code>4</code>  |
| **`MOUTH_LEFT`**   | <code>5</code>  |
| **`NOSE_BASE`**    | <code>6</code>  |
| **`RIGHT_CHEEK`**  | <code>7</code>  |
| **`RIGHT_EAR`**    | <code>9</code>  |
| **`RIGHT_EYE`**    | <code>10</code> |
| **`MOUTH_RIGHT`**  | <code>11</code> |


#### ContourType

| Members                    | Value           |
| -------------------------- | --------------- |
| **`FACE`**                 | <code>1</code>  |
| **`LEFT_EYEBROW_TOP`**     | <code>2</code>  |
| **`LEFT_EYEBROW_BOTTOM`**  | <code>3</code>  |
| **`RIGHT_EYEBROW_TOP`**    | <code>4</code>  |
| **`RIGHT_EYEBROW_BOTTOM`** | <code>5</code>  |
| **`LEFT_EYE`**             | <code>6</code>  |
| **`RIGHT_EYE`**            | <code>7</code>  |
| **`UPPER_LIP_TOP`**        | <code>8</code>  |
| **`UPPER_LIP_BOTTOM`**     | <code>9</code>  |
| **`LOWER_LIP_TOP`**        | <code>10</code> |
| **`LOWER_LIP_BOTTOM`**     | <code>11</code> |
| **`NOSE_BRIDGE`**          | <code>12</code> |
| **`NOSE_BOTTOM`**          | <code>13</code> |
| **`LEFT_CHEEK`**           | <code>14</code> |
| **`RIGHT_CHEEK`**          | <code>15</code> |


#### PerformanceMode

| Members        | Value          |
| -------------- | -------------- |
| **`FAST`**     | <code>1</code> |
| **`ACCURATE`** | <code>2</code> |


#### LandmarkMode

| Members    | Value          |
| ---------- | -------------- |
| **`NONE`** | <code>1</code> |
| **`ALL`**  | <code>2</code> |


#### ContourMode

| Members    | Value          |
| ---------- | -------------- |
| **`NONE`** | <code>1</code> |
| **`ALL`**  | <code>2</code> |


#### ClassificationMode

| Members    | Value          |
| ---------- | -------------- |
| **`NONE`** | <code>1</code> |
| **`ALL`**  | <code>2</code> |

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
