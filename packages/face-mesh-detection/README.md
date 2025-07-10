# @capacitor-mlkit/face-mesh-detection

Unofficial Capacitor plugin for [ML Kit Face Mesh Detection](https://developers.google.com/ml-kit/vision/face-mesh-detection).[^1]

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-deploy-real-time-app-updates.png?t=1" />
  </a>
</div>

## Installation

```bash
npm install @capacitor-mlkit/face-mesh-detection
npx cap sync
```

### Android

#### Variables

If needed, you can define the following project variable in your app’s `variables.gradle` file to change the default version of the dependency:

- `$mlkitFaceMeshDetectionVersion` version of `com.google.mlkit:face-mesh-detection` (default: `16.0.0-beta1`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-mlkit-plugin-demo](https://github.com/robingenz/capacitor-mlkit-plugin-demo)

## Usage

```typescript
import { FaceMeshDetection, UseCase } from '@capacitor-mlkit/face-mesh-detection';

const processImage = async () => {
  const { faceMeshs } = await FaceMeshDetection.processImage({
    path: 'path/to/image.jpg',
    useCase: UseCase.FaceMesh,
  });
  return faceMeshs;
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

Detects face mesh from the supplied image.

Only available on Android.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#processimageoptions">ProcessImageOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#processimageresult">ProcessImageResult</a>&gt;</code>

**Since:** 5.3.0

--------------------


### Interfaces


#### ProcessImageResult

| Prop            | Type                    | Description              | Since |
| --------------- | ----------------------- | ------------------------ | ----- |
| **`faceMeshs`** | <code>FaceMesh[]</code> | The detected face meshs. | 5.3.0 |


#### FaceMesh

Represents a face mesh detected by `FaceMeshDetector`.

When `BoundingBoxOnly` is selected, <a href="#facemesh">`FaceMesh`</a> only contains valid bounding box.

When <a href="#facemesh">`FaceMesh`</a> is selected, <a href="#facemesh">`FaceMesh`</a> also contains a group of 468 3D face mesh points and related triangle information.
Each point is represented by <a href="#facemeshpoint">`FaceMeshPoint`</a> describing a specific position in detected face.
The triangle information is a group of 3 `FaceMeshPoint`s representing a valid surface on Face (e.g. a valid small surface on nose tip).

| Prop                 | Type                                          | Description                                                                                                                                                                                                                                                                                                                                        | Since |
| -------------------- | --------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`bounds`**         | <code><a href="#rect">Rect</a></code>         | Returns the axis-aligned bounding rectangle of the detected face mesh.                                                                                                                                                                                                                                                                             | 5.3.0 |
| **`contours`**       | <code><a href="#contours">Contours</a></code> | Returns contours with a list of <a href="#facemeshpoint">`FaceMeshPoint`</a> representing the detected face.                                                                                                                                                                                                                                       | 5.3.0 |
| **`faceMeshPoints`** | <code>FaceMeshPoint[]</code>                  | Returns a list of <a href="#facemeshpoint">`FaceMeshPoint`</a> representing the whole detected face.                                                                                                                                                                                                                                               | 5.3.0 |
| **`triangles`**      | <code>Triangle[]</code>                       | Returns a list of <a href="#triangle">`Triangle`</a> representing logical triangle surfaces of detected face. Each <a href="#triangle">`Triangle`</a> contains 3 <a href="#facemeshpoint">`FaceMeshPoint`</a>, representing 3 points of the triangle surface. The sequence of the 3 points are constant and always counter clockwise in face mesh. | 5.3.0 |


#### Rect

<a href="#rect">Rect</a> holds four integer coordinates for a rectangle.

| Prop         | Type                | Description                                          | Since |
| ------------ | ------------------- | ---------------------------------------------------- | ----- |
| **`left`**   | <code>number</code> | The X coordinate of the left side of the rectangle.  | 5.3.0 |
| **`top`**    | <code>number</code> | The Y coordinate of the top of the rectangle.        | 5.3.0 |
| **`right`**  | <code>number</code> | The X coordinate of the right side of the rectangle. | 5.3.0 |
| **`bottom`** | <code>number</code> | The Y coordinate of the bottom of the rectangle.     | 5.3.0 |


#### Contours

Represents contours with their face mesh points.

| Prop                     | Type                         | Description                                              | Since |
| ------------------------ | ---------------------------- | -------------------------------------------------------- | ----- |
| **`faceOval`**           | <code>FaceMeshPoint[]</code> | Returns all points for the `FaceOval` contour.           | 5.3.0 |
| **`leftEyebrowTop`**     | <code>FaceMeshPoint[]</code> | Returns all points for the `LeftEyebrowTop` contour.     | 5.3.0 |
| **`leftEyebrowBottom`**  | <code>FaceMeshPoint[]</code> | Returns all points for the `LeftEyebrowBottom` contour.  | 5.3.0 |
| **`rightEyebrowTop`**    | <code>FaceMeshPoint[]</code> | Returns all points for the `RightEyebrowTop` contour.    | 5.3.0 |
| **`rightEyebrowBottom`** | <code>FaceMeshPoint[]</code> | Returns all points for the `RightEyebrowBottom` contour. | 5.3.0 |
| **`leftEye`**            | <code>FaceMeshPoint[]</code> | Returns all points for the `LeftEye` contour.            | 5.3.0 |
| **`rightEye`**           | <code>FaceMeshPoint[]</code> | Returns all points for the `RightEye` contour.           | 5.3.0 |
| **`upperLipTop`**        | <code>FaceMeshPoint[]</code> | Returns all points for the `UpperLipTop` contour.        | 5.3.0 |
| **`upperLipBottom`**     | <code>FaceMeshPoint[]</code> | Returns all points for the `UpperLipBottom` contour.     | 5.3.0 |
| **`lowerLipTop`**        | <code>FaceMeshPoint[]</code> | Returns all points for the `LowerLipTop` contour.        | 5.3.0 |
| **`lowerLipBottom`**     | <code>FaceMeshPoint[]</code> | Returns all points for the `LowerLipBottom` contour.     | 5.3.0 |
| **`noseBridge`**         | <code>FaceMeshPoint[]</code> | Returns all points for the `NoseBridge` contour.         | 5.3.0 |


#### FaceMeshPoint

Represents a 3D point in face mesh.

The index is an unique ID meaning a fixed position on face, ranging from 0 to 467.

In <a href="#point3d">`Point3D`</a>, `x` and `y` are pixel location of detected face in `InputImage`.
`z` is also scaled to image size, while the origin will be somewhere in the center of all 468 face mesh points.

| Prop        | Type                                        | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 | Since |
| ----------- | ------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`index`** | <code>number</code>                         | Gets the index of the face mesh point, ranging from 0 to 467. For each specific point, the index is a constant value.                                                                                                                                                                                                                                                                                                                                                                                       | 5.3.0 |
| **`point`** | <code><a href="#point3d">Point3D</a></code> | Gets a 3D point in face mesh. Inside <a href="#point3d">`Point3D`</a>, `X` and `Y` means a 2D position in original image. More information on the `Z` value: - The unit of measure for the `Z` value is the same as `X` and `Y`. - The smaller the `Z` value, the closer that landmark is to the camera. - The `Z` origin is approximately at the center of all 468 face mesh points. `Z` value will be negative if the point is close to camera and will be positive if the point is away from the camera. | 5.3.0 |


#### Point3D

Represents a 3D point.

| Prop    | Type                | Description                       | Since |
| ------- | ------------------- | --------------------------------- | ----- |
| **`x`** | <code>number</code> | Returns the X value of the point. | 5.3.0 |
| **`y`** | <code>number</code> | Returns the Y value of the point. | 5.3.0 |
| **`z`** | <code>number</code> | Returns the Z value of the point. | 5.3.0 |


#### Triangle

Represents a triangle with 3 generic points.

| Prop         | Type                         | Description                                                       | Since |
| ------------ | ---------------------------- | ----------------------------------------------------------------- | ----- |
| **`points`** | <code>FaceMeshPoint[]</code> | Returns all points inside the <a href="#triangle">`Triangle`</a>. | 5.3.0 |


#### ProcessImageOptions

| Prop          | Type                                        | Description                                                                                                                                                                                                                                                                                                                                                                                                                          | Default                       | Since |
| ------------- | ------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ----------------------------- | ----- |
| **`path`**    | <code>string</code>                         | The local path to the image file.                                                                                                                                                                                                                                                                                                                                                                                                    |                               | 5.3.0 |
| **`useCase`** | <code><a href="#usecase">UseCase</a></code> | Sets the use case. When `BoundingBoxOnly` is selected, the returned <a href="#facemesh">`FaceMesh`</a> only contains bounding box. When <a href="#facemesh">`FaceMesh`</a> is selected, the returned <a href="#facemesh">`FaceMesh`</a> contains bounding box as well as 468 <a href="#facemeshpoint">`FaceMeshPoint`</a> and triangle information. It detects at most 2 faces in this case and it is slower than `BoundingBoxOnly`. | <code>UseCase.FaceMesh</code> | 5.3.0 |


### Enums


#### UseCase

| Members               | Value          | Description                                                                           | Since |
| --------------------- | -------------- | ------------------------------------------------------------------------------------- | ----- |
| **`BoundingBoxOnly`** | <code>0</code> | Return bounding box for detected face.                                                | 5.3.0 |
| **`FaceMesh`**        | <code>1</code> | Return face mesh info for detected face. It detects at most 2 faces in this use case. | 5.3.0 |

</docgen-api>

## Terms & Privacy

This plugin uses the [Google ML Kit](https://developers.google.com/ml-kit):

- [Terms & Privacy](https://developers.google.com/ml-kit/terms)
- [Android Data Disclosure](https://developers.google.com/ml-kit/android-data-disclosure)
- [iOS Data Disclosure](https://developers.google.com/ml-kit/ios-data-disclosure)

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/face-mesh-detection/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/face-mesh-detection/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.
