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

const echo = async () => {
  await SubjectSegmentation.echo();
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
