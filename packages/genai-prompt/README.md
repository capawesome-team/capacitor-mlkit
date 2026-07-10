# Capacitor ML Kit GenAI Prompt Plugin

Unofficial Capacitor plugin for the [ML Kit GenAI Prompt API](https://developers.google.com/ml-kit/genai/prompt).[^1]

> [!WARNING]
> The ML Kit GenAI APIs are currently in **beta**. The API surface and behavior may change in future releases.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Use Cases

The GenAI Prompt plugin is typically used to send custom natural-language requests to Gemini Nano on-device, for example:

- **Content generation**: Generate short texts such as poems, captions or replies from a custom prompt.
- **Text transformation**: Rephrase, classify or extract information from user-provided text.
- **Image understanding**: Ask questions about an image by combining an image with a text prompt.

## Requirements

This plugin uses [Gemini Nano](https://developers.google.com/ml-kit/genai), which runs entirely on-device. It is only available on Android and has the following device requirements:

- The device must support Gemini Nano (see [supported devices](https://developers.google.com/ml-kit/genai#supported_devices)).
- The device must run Android API level 26 or higher.
- The device must not have an unlocked bootloader.

Use the `checkFeatureStatus(...)` method to check whether the feature is available on the device.

> [!IMPORTANT]
> The Prompt API performs open-ended generation with an on-device model. The output is **not moderated**. You are responsible for reviewing the model output for your use case (see [Responsible AI](https://developers.google.com/ml-kit/genai/prompt#responsible_ai)).

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
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capacitor-mlkit/genai-prompt` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capacitor-mlkit/genai-prompt
npx cap sync
```

### Android

#### API level

This plugin requires a minimum API level of 26. Make sure to set the `minSdkVersion` in your `variables.gradle` file to at least `26`.

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$mlkitGenaiPromptVersion` version of `com.google.mlkit:genai-prompt` (default: `1.0.0-beta2`)

## Usage

The following example shows how to check the feature status, download the feature and generate content from a prompt.

> The prompt must be under 4,000 tokens (or about 3,000 English words). Use cases that require a long output (more than 256 tokens) should be avoided. The Prompt API is currently only validated for English and Korean.

### Check the feature status

Before using the prompt feature, check whether it is available on the device:

```typescript
import { FeatureStatus, GenAiPrompt } from '@capacitor-mlkit/genai-prompt';

const checkFeatureStatus = async () => {
  const { featureStatus } = await GenAiPrompt.checkFeatureStatus();
  return featureStatus === FeatureStatus.Available;
};
```

### Download the feature

If the feature status is `DOWNLOADABLE`, download the feature before using it. Add a listener for the `downloadProgress` event to get notified about the download progress:

```typescript
import { GenAiPrompt } from '@capacitor-mlkit/genai-prompt';

const downloadFeature = async () => {
  await GenAiPrompt.addListener('downloadProgress', event => {
    console.log('Total bytes downloaded:', event.totalBytesDownloaded);
  });
  await GenAiPrompt.downloadFeature();
};
```

### Generate content

Pass a prompt to `generateContent(...)` to generate content. Add a listener for the `inferenceProgress` event to receive partial results as they are generated:

```typescript
import { GenAiPrompt } from '@capacitor-mlkit/genai-prompt';

const generateContent = async () => {
  await GenAiPrompt.addListener('inferenceProgress', event => {
    console.log('Partial result:', event.text);
  });
  const { text } = await GenAiPrompt.generateContent({
    prompt: 'Write a short poem about the sea.',
  });
  return text;
};
```

You can also include an image in the prompt for a multimodal (image and text) request and tune the generation with optional parameters:

```typescript
import { GenAiPrompt } from '@capacitor-mlkit/genai-prompt';

const generateContentFromImage = async () => {
  const { text } = await GenAiPrompt.generateContent({
    prompt: 'What is shown in this image?',
    imagePath: 'file:///path/to/image.jpg',
    temperature: 0.2,
    topK: 16,
    maxOutputTokens: 256,
  });
  return text;
};
```

## API

<docgen-index>

* [`checkFeatureStatus()`](#checkfeaturestatus)
* [`downloadFeature()`](#downloadfeature)
* [`generateContent(...)`](#generatecontent)
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

Check the current availability status of the prompt feature.

If the status is `DOWNLOADABLE`, you can download the feature
using `downloadFeature(...)`.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#checkfeaturestatusresult">CheckFeatureStatusResult</a>&gt;</code>

**Since:** 8.2.0

--------------------


### downloadFeature()

```typescript
downloadFeature() => Promise<void>
```

Download the prompt feature.

The `downloadProgress` event listener will notify you about
the download progress. The returned promise resolves when
the download is complete or the feature is already available.

Only available on Android.

**Since:** 8.2.0

--------------------


### generateContent(...)

```typescript
generateContent(options: GenerateContentOptions) => Promise<GenerateContentResult>
```

Generate content from a custom text-only or multimodal
(image and text) prompt.

The `inferenceProgress` event listener will notify you about
partial results as they are generated. The returned promise
resolves with the full result.

The output is open-ended generation from an on-device model
and is not moderated. The prompt is currently only validated
for English and Korean.

Only available on Android.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#generatecontentoptions">GenerateContentOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#generatecontentresult">GenerateContentResult</a>&gt;</code>

**Since:** 8.2.0

--------------------


### addListener('downloadProgress', ...)

```typescript
addListener(eventName: 'downloadProgress', listenerFunc: (event: DownloadProgressEvent) => void) => Promise<PluginListenerHandle>
```

Called while the prompt feature is being downloaded.

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

| Prop                | Type                                                    | Description                                            | Since |
| ------------------- | ------------------------------------------------------- | ------------------------------------------------------ | ----- |
| **`featureStatus`** | <code><a href="#featurestatus">FeatureStatus</a></code> | The current availability status of the prompt feature. | 8.2.0 |


#### GenerateContentResult

| Prop       | Type                | Description         | Since |
| ---------- | ------------------- | ------------------- | ----- |
| **`text`** | <code>string</code> | The generated text. | 8.2.0 |


#### GenerateContentOptions

| Prop                  | Type                | Description                                                                                                                                                                                            | Since |
| --------------------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ----- |
| **`imagePath`**       | <code>string</code> | The local file path of an image to include in the prompt for a multimodal (image and text) request.                                                                                                    | 8.2.0 |
| **`maxOutputTokens`** | <code>number</code> | The maximum number of tokens that can be generated in the response. Use cases that require a long output (more than 256 tokens) should be avoided. If not set, the default value of the model is used. | 8.2.0 |
| **`prompt`**          | <code>string</code> | The prompt to generate content from. The input must be under 4,000 tokens (or about 3,000 English words).                                                                                              | 8.2.0 |
| **`seed`**            | <code>number</code> | The random seed used during inference. Using the same seed with the same options produces deterministic results. If not set, the default value of the model is used.                                   | 8.2.0 |
| **`temperature`**     | <code>number</code> | Controls the randomness of the output. Lower values produce more deterministic output, higher values produce more creative output. If not set, the default value of the model is used.                 | 8.2.0 |
| **`topK`**            | <code>number</code> | The number of highest-probability tokens that are considered when sampling the next token. If not set, the default value of the model is used.                                                         | 8.2.0 |


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

The content generation is performed on-device by Gemini Nano. This requires a device that supports Gemini Nano, Android API level 26 or higher and a locked bootloader (see [Requirements](#requirements)). Use the `checkFeatureStatus(...)` method to check whether the feature is available on the device.

### Why is this plugin only available on Android?

The ML Kit GenAI APIs are based on Gemini Nano and AICore, which are currently only available on Android. On iOS and Web, all methods reject with an appropriate error.

### How large can the prompt be?

The prompt must be under 4,000 tokens (or about 3,000 English words). Use cases that require a long output (more than 256 tokens) should be avoided.

### Is the output moderated?

No, the Prompt API performs open-ended generation with an on-device model and the output is not moderated. You are responsible for reviewing the model output for your use case.

### How do I get the output as a stream?

Add a listener for the `inferenceProgress` event before calling `generateContent(...)`. The listener is called with each partial result as it is generated. The promise returned by `generateContent(...)` resolves with the full result.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [GenAI Image Description](https://capawesome.io/docs/sdks/capacitor/mlkit/genai-image-description/): Unofficial Capacitor plugin for ML Kit GenAI Image Description.
- [GenAI Summarization](https://capawesome.io/docs/sdks/capacitor/mlkit/genai-summarization/): Unofficial Capacitor plugin for ML Kit GenAI Summarization.

## Terms & Privacy

This plugin uses the [Google ML Kit](https://developers.google.com/ml-kit):

- [Terms & Privacy](https://developers.google.com/ml-kit/terms)
- [Android Data Disclosure](https://developers.google.com/ml-kit/android-data-disclosure)
- [iOS Data Disclosure](https://developers.google.com/ml-kit/ios-data-disclosure)

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/genai-prompt/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/genai-prompt/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.
