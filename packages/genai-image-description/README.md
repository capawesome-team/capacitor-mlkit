# Capacitor ML Kit GenAI Image Description Plugin

Unofficial Capacitor plugin for [ML Kit GenAI Image Description](https://developers.google.com/ml-kit/genai/image-description).[^1]

> [!WARNING]
> The ML Kit GenAI APIs are currently in **beta**. The API surface and behavior may change in future releases.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Use Cases

The GenAI Image Description plugin is typically used to generate a natural-language description of an image on-device using Gemini Nano, for example:

- **Accessibility**: Generate alternative text for images to support screen readers.
- **Content moderation**: Get a textual summary of user-uploaded images.
- **Search and organization**: Describe images to make them searchable or to auto-tag a photo library.

## Requirements

This plugin uses [Gemini Nano](https://developers.google.com/ml-kit/genai), which runs entirely on-device. It is only available on Android and has the following device requirements:

- The device must support Gemini Nano (see [supported devices](https://developers.google.com/ml-kit/genai#supported_devices)).
- The device must run Android API level 26 or higher.
- The device must not have an unlocked bootloader.

Use the `checkFeatureStatus(...)` method to check whether the feature is available on the device.

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
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capacitor-mlkit/genai-image-description` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capacitor-mlkit/genai-image-description
npx cap sync
```

### Android

#### API level

This plugin requires a minimum API level of 26. Make sure to set the `minSdkVersion` in your `variables.gradle` file to at least `26`.

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$mlkitGenaiImageDescriptionVersion` version of `com.google.mlkit:genai-image-description` (default: `1.0.0-beta1`)

## Usage

The following example shows how to check the feature status, download the feature and describe an image.

> The image description is currently only available in English.

### Check the feature status

Before using the image description feature, check whether it is available on the device:

```typescript
import {
  FeatureStatus,
  GenAiImageDescription,
} from '@capacitor-mlkit/genai-image-description';

const checkFeatureStatus = async () => {
  const { featureStatus } = await GenAiImageDescription.checkFeatureStatus();
  return featureStatus === FeatureStatus.Available;
};
```

### Download the feature

If the feature status is `DOWNLOADABLE`, download the feature before using it. Add a listener for the `downloadProgress` event to get notified about the download progress:

```typescript
import { GenAiImageDescription } from '@capacitor-mlkit/genai-image-description';

const downloadFeature = async () => {
  await GenAiImageDescription.addListener('downloadProgress', event => {
    console.log('Total bytes downloaded:', event.totalBytesDownloaded);
  });
  await GenAiImageDescription.downloadFeature();
};
```

### Describe an image

Pass the local file path of the image to `describeImage(...)` to generate a description. Add a listener for the `inferenceProgress` event to receive partial results as they are generated:

```typescript
import { GenAiImageDescription } from '@capacitor-mlkit/genai-image-description';

const describeImage = async () => {
  await GenAiImageDescription.addListener('inferenceProgress', event => {
    console.log('Partial result:', event.text);
  });
  const { description } = await GenAiImageDescription.describeImage({
    path: 'file:///path/to/image.jpg',
  });
  return description;
};
```

## API

<docgen-index>

* [`checkFeatureStatus()`](#checkfeaturestatus)
* [`describeImage(...)`](#describeimage)
* [`downloadFeature()`](#downloadfeature)
* [`addListener('downloadProgress', ...)`](#addlistenerdownloadprogress-)
* [`addListener('inferenceProgress', ...)`](#addlistenerinferenceprogress-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### checkFeatureStatus()

```typescript
checkFeatureStatus() => Promise<CheckFeatureStatusResult>
```

Check the current availability status of the image description feature.

If the status is `DOWNLOADABLE`, you can download the feature
using `downloadFeature(...)`.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#checkfeaturestatusresult">CheckFeatureStatusResult</a>&gt;</code>

**Since:** 8.2.0

--------------------


### describeImage(...)

```typescript
describeImage(options: DescribeImageOptions) => Promise<DescribeImageResult>
```

Generate a natural-language description of the given image.

The `inferenceProgress` event listener will notify you about
partial results as they are generated. The returned promise
resolves with the full result.

The description is currently only available in English.

Only available on Android.

| Param         | Type                                                                  |
| ------------- | --------------------------------------------------------------------- |
| **`options`** | <code><a href="#describeimageoptions">DescribeImageOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#describeimageresult">DescribeImageResult</a>&gt;</code>

**Since:** 8.2.0

--------------------


### downloadFeature()

```typescript
downloadFeature() => Promise<void>
```

Download the image description feature.

The `downloadProgress` event listener will notify you about
the download progress. The returned promise resolves when
the download is complete or the feature is already available.

Only available on Android.

**Since:** 8.2.0

--------------------


### addListener('downloadProgress', ...)

```typescript
addListener(eventName: 'downloadProgress', listenerFunc: (event: DownloadProgressEvent) => void) => Promise<PluginListenerHandle>
```

Called while the image description feature is being downloaded.

Only available on Android.

| Param              | Type                                                                                        |
| ------------------ | ------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'downloadProgress'</code>                                                             |
| **`listenerFunc`** | <code>(event: <a href="#downloadprogressevent">DownloadProgressEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 8.2.0

--------------------


### addListener('inferenceProgress', ...)

```typescript
addListener(eventName: 'inferenceProgress', listenerFunc: (event: InferenceProgressEvent) => void) => Promise<PluginListenerHandle>
```

Called when a new partial result is available during inference.

Only available on Android.

| Param              | Type                                                                                          |
| ------------------ | --------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'inferenceProgress'</code>                                                              |
| **`listenerFunc`** | <code>(event: <a href="#inferenceprogressevent">InferenceProgressEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 8.2.0

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

**Since:** 8.2.0

--------------------


### Interfaces


#### CheckFeatureStatusResult

| Prop                | Type                                                    | Description                                                       | Since |
| ------------------- | ------------------------------------------------------- | ----------------------------------------------------------------- | ----- |
| **`featureStatus`** | <code><a href="#featurestatus">FeatureStatus</a></code> | The current availability status of the image description feature. | 8.2.0 |


#### DescribeImageResult

| Prop              | Type                | Description                                    | Since |
| ----------------- | ------------------- | ---------------------------------------------- | ----- |
| **`description`** | <code>string</code> | The natural-language description of the image. | 8.2.0 |


#### DescribeImageOptions

| Prop       | Type                | Description                                   | Since |
| ---------- | ------------------- | --------------------------------------------- | ----- |
| **`path`** | <code>string</code> | The local file path of the image to describe. | 8.2.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### DownloadProgressEvent

| Prop                       | Type                | Description                                  | Since |
| -------------------------- | ------------------- | -------------------------------------------- | ----- |
| **`totalBytesDownloaded`** | <code>number</code> | The total number of bytes downloaded so far. | 8.2.0 |


#### InferenceProgressEvent

| Prop       | Type                | Description                         | Since |
| ---------- | ------------------- | ----------------------------------- | ----- |
| **`text`** | <code>string</code> | The new text of the partial result. | 8.2.0 |


### Enums


#### FeatureStatus

| Members            | Value                       | Description                                                 | Since |
| ------------------ | --------------------------- | ----------------------------------------------------------- | ----- |
| **`Available`**    | <code>'AVAILABLE'</code>    | The feature is available to use.                            | 8.2.0 |
| **`Downloadable`** | <code>'DOWNLOADABLE'</code> | The feature can be downloaded using `downloadFeature(...)`. | 8.2.0 |
| **`Downloading`**  | <code>'DOWNLOADING'</code>  | The feature is currently being downloaded.                  | 8.2.0 |
| **`Unavailable`**  | <code>'UNAVAILABLE'</code>  | The feature is not available on this device.                | 8.2.0 |

</docgen-api>

## FAQ

### What are the requirements to use this plugin?

The image description is performed on-device by Gemini Nano. This requires a device that supports Gemini Nano, Android API level 26 or higher and a locked bootloader (see [Requirements](#requirements)). Use the `checkFeatureStatus(...)` method to check whether the feature is available on the device.

### Why is this plugin only available on Android?

The ML Kit GenAI APIs are based on Gemini Nano and AICore, which are currently only available on Android. On iOS and Web, all methods reject with an appropriate error.

### In which language is the description generated?

The image description is currently only available in English. Support for more languages is planned for future releases.

### How do I get the description as a stream?

Add a listener for the `inferenceProgress` event before calling `describeImage(...)`. The listener is called with each partial result as it is generated. The promise returned by `describeImage(...)` resolves with the full result.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Translation](https://capawesome.io/docs/sdks/capacitor/mlkit/translation/): Unofficial Capacitor plugin for ML Kit Translation.
- [Smart Reply](https://capawesome.io/docs/sdks/capacitor/mlkit/smart-reply/): Unofficial Capacitor plugin for ML Kit Smart Reply.
- [GenAI Prompt](https://capawesome.io/docs/sdks/capacitor/mlkit/genai-prompt/): Run custom GenAI prompts, for example to expand on a generated image description.

## Terms & Privacy

This plugin uses the [Google ML Kit](https://developers.google.com/ml-kit):

- [Terms & Privacy](https://developers.google.com/ml-kit/terms)
- [Android Data Disclosure](https://developers.google.com/ml-kit/android-data-disclosure)
- [iOS Data Disclosure](https://developers.google.com/ml-kit/ios-data-disclosure)

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/genai-image-description/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/genai-image-description/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.
