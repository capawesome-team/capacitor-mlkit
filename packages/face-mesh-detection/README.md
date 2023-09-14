# @capacitor-mlkit/face-mesh-detection

Unofficial Capacitor plugin for [ML Kit Face Mesh Detection](https://developers.google.com/ml-kit/vision/face-mesh-detection).[^1]

## Installation

```bash
npm install @capacitor-mlkit/face-mesh-detection
npx cap sync
```

### Android

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$mlkitFaceMeshDetectionVersion` version of `com.google.mlkit:face-mesh-detection` (default: `16.0.0-beta1`)

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-mlkit-plugin-demo](https://github.com/robingenz/capacitor-mlkit-plugin-demo)

## Usage

```typescript
import { FaceMeshDetection } from '@capacitor-mlkit/face-mesh-detection';

const echo = async () => {
  await FaceMeshDetection.echo();
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

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#processimageoptions">ProcessImageOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#processimageresult">ProcessImageResult</a>&gt;</code>

--------------------


### Interfaces


#### ProcessImageResult

| Prop             | Type               |
| ---------------- | ------------------ |
| **`faceMeshes`** | <code>any[]</code> |


#### ProcessImageOptions

| Prop       | Type                |
| ---------- | ------------------- |
| **`path`** | <code>string</code> |

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
