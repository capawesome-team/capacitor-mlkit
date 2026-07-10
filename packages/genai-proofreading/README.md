# Capacitor ML Kit GenAI Proofreading Plugin

Unofficial Capacitor plugin for [ML Kit GenAI Proofreading](https://developers.google.com/ml-kit/genai/proofreading).[^1]

> [!WARNING]
> The ML Kit GenAI APIs are currently in **beta**. The API surface and behavior may change in future releases.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Use Cases

The GenAI Proofreading plugin is typically used to refine grammar and fix spelling in short content on-device using Gemini Nano, for example:

- **Message polishing**: Correct typos and grammar in chat or email drafts before sending.
- **Form input cleanup**: Proofread user-generated text such as reviews or comments.
- **Voice-to-text refinement**: Clean up text produced by voice dictation.

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
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capacitor-mlkit/genai-proofreading` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capacitor-mlkit/genai-proofreading
npx cap sync
```

### Android

#### API level

This plugin requires a minimum API level of 26. Make sure to set the `minSdkVersion` in your `variables.gradle` file to at least `26`.

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$mlkitGenaiProofreadingVersion` version of `com.google.mlkit:genai-proofreading` (default: `1.0.0-beta1`)

## Usage

The following example shows how to check the feature status, download the feature and proofread a text.

### Check the feature status

Before using the proofreading feature, check whether it is available on the device. The feature availability depends on the configured input type and language:

```typescript
import {
  FeatureStatus,
  GenAiProofreading,
  InputType,
  Language,
} from '@capacitor-mlkit/genai-proofreading';

const checkFeatureStatus = async () => {
  const { featureStatus } = await GenAiProofreading.checkFeatureStatus({
    inputType: InputType.Keyboard,
    language: Language.English,
  });
  return featureStatus === FeatureStatus.Available;
};
```

### Download the feature

If the feature status is `DOWNLOADABLE`, download the feature before using it. Add a listener for the `downloadProgress` event to get notified about the download progress:

```typescript
import { GenAiProofreading, InputType, Language } from '@capacitor-mlkit/genai-proofreading';

const downloadFeature = async () => {
  await GenAiProofreading.addListener('downloadProgress', event => {
    console.log('Total bytes downloaded:', event.totalBytesDownloaded);
  });
  await GenAiProofreading.downloadFeature({
    inputType: InputType.Keyboard,
    language: Language.English,
  });
};
```

### Proofread a text

Pass the text to `proofread(...)` to generate corrected suggestions. Add a listener for the `inferenceProgress` event to receive partial results as they are generated:

```typescript
import { GenAiProofreading, InputType, Language } from '@capacitor-mlkit/genai-proofreading';

const proofread = async () => {
  await GenAiProofreading.addListener('inferenceProgress', event => {
    console.log('Partial result:', event.text);
  });
  const { results } = await GenAiProofreading.proofread({
    text: 'Capacitor is an open source natvie runtime for buildng Web Native apps.',
    inputType: InputType.Keyboard,
    language: Language.English,
  });
  return results;
};
```

## API

<docgen-index>

* [`checkFeatureStatus(...)`](#checkfeaturestatus)
* [`downloadFeature(...)`](#downloadfeature)
* [`proofread(...)`](#proofread)
* [`addListener('downloadProgress', ...)`](#addlistenerdownloadprogress-)
* [`addListener('inferenceProgress', ...)`](#addlistenerinferenceprogress-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### checkFeatureStatus(...)

```typescript
checkFeatureStatus(options?: FeatureOptions | undefined) => Promise<CheckFeatureStatusResult>
```

Check the current availability status of the proofreading feature.

If the status is `DOWNLOADABLE`, you can download the feature
using `downloadFeature(...)`.

Only available on Android.

| Param         | Type                                                      |
| ------------- | --------------------------------------------------------- |
| **`options`** | <code><a href="#featureoptions">FeatureOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#checkfeaturestatusresult">CheckFeatureStatusResult</a>&gt;</code>

**Since:** 8.2.0

--------------------


### downloadFeature(...)

```typescript
downloadFeature(options?: FeatureOptions | undefined) => Promise<void>
```

Download the proofreading feature.

The `downloadProgress` event listener will notify you about
the download progress. The returned promise resolves when
the download is complete or the feature is already available.

Only available on Android.

| Param         | Type                                                      |
| ------------- | --------------------------------------------------------- |
| **`options`** | <code><a href="#featureoptions">FeatureOptions</a></code> |

**Since:** 8.2.0

--------------------


### proofread(...)

```typescript
proofread(options: ProofreadOptions) => Promise<ProofreadResult>
```

Proofread the given text.

The `inferenceProgress` event listener will notify you about
partial results as they are generated. The returned promise
resolves with the full result.

Only available on Android.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#proofreadoptions">ProofreadOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#proofreadresult">ProofreadResult</a>&gt;</code>

**Since:** 8.2.0

--------------------


### addListener('downloadProgress', ...)

```typescript
addListener(eventName: 'downloadProgress', listenerFunc: (event: DownloadProgressEvent) => void) => Promise<PluginListenerHandle>
```

Called while the proofreading feature is being downloaded.

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

| Prop                | Type                                                    | Description                                                  | Since |
| ------------------- | ------------------------------------------------------- | ------------------------------------------------------------ | ----- |
| **`featureStatus`** | <code><a href="#featurestatus">FeatureStatus</a></code> | The current availability status of the proofreading feature. | 8.2.0 |


#### FeatureOptions

The feature availability depends on the configured input type
and language. Therefore, `checkFeatureStatus(...)` and
`downloadFeature(...)` take the same base options as `proofread(...)`.

| Prop            | Type                                            | Description                     | Default                         | Since |
| --------------- | ----------------------------------------------- | ------------------------------- | ------------------------------- | ----- |
| **`inputType`** | <code><a href="#inputtype">InputType</a></code> | The type of the input text.     | <code>InputType.Keyboard</code> | 8.2.0 |
| **`language`**  | <code><a href="#language">Language</a></code>   | The language of the input text. | <code>Language.English</code>   | 8.2.0 |


#### ProofreadResult

| Prop          | Type                  | Description                                                                                                                                                                  | Since |
| ------------- | --------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`results`** | <code>string[]</code> | The proofread suggestions for the input text. More than one result may be returned. If multiple suggestions are returned, they are sorted in descending order of confidence. | 8.2.0 |


#### ProofreadOptions

| Prop       | Type                | Description            | Since |
| ---------- | ------------------- | ---------------------- | ----- |
| **`text`** | <code>string</code> | The text to proofread. | 8.2.0 |


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


#### InputType

| Members        | Value                   | Description                                              | Since |
| -------------- | ----------------------- | -------------------------------------------------------- | ----- |
| **`Keyboard`** | <code>'KEYBOARD'</code> | The input text was typed on a keyboard.                  | 8.2.0 |
| **`Voice`**    | <code>'VOICE'</code>    | The input text was produced by converting voice to text. | 8.2.0 |


#### Language

| Members        | Value                   | Since |
| -------------- | ----------------------- | ----- |
| **`English`**  | <code>'ENGLISH'</code>  | 8.2.0 |
| **`French`**   | <code>'FRENCH'</code>   | 8.2.0 |
| **`German`**   | <code>'GERMAN'</code>   | 8.2.0 |
| **`Italian`**  | <code>'ITALIAN'</code>  | 8.2.0 |
| **`Japanese`** | <code>'JAPANESE'</code> | 8.2.0 |
| **`Korean`**   | <code>'KOREAN'</code>   | 8.2.0 |
| **`Spanish`**  | <code>'SPANISH'</code>  | 8.2.0 |

</docgen-api>

## FAQ

### What are the requirements to use this plugin?

The proofreading is performed on-device by Gemini Nano. This requires a device that supports Gemini Nano, Android API level 26 or higher and a locked bootloader (see [Requirements](#requirements)). Use the `checkFeatureStatus(...)` method to check whether the feature is available on the device.

### Why is this plugin only available on Android?

The ML Kit GenAI APIs are based on Gemini Nano and AICore, which are currently only available on Android. On iOS and Web, all methods reject with an appropriate error.

### Which languages are supported?

Proofreading is supported for English, French, German, Italian, Japanese, Korean and Spanish. Use the `Language` enum to configure the language of the input text.

### How do I get the result as a stream?

Add a listener for the `inferenceProgress` event before calling `proofread(...)`. The listener is called with each partial result as it is generated. The promise returned by `proofread(...)` resolves with the full result.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [GenAI Summarization](https://capawesome.io/docs/sdks/capacitor/mlkit/genai-summarization/): Unofficial Capacitor plugin for ML Kit GenAI Summarization.
- [Translation](https://capawesome.io/docs/sdks/capacitor/mlkit/translation/): Unofficial Capacitor plugin for ML Kit Translation.

## Terms & Privacy

This plugin uses the [Google ML Kit](https://developers.google.com/ml-kit):

- [Terms & Privacy](https://developers.google.com/ml-kit/terms)
- [Android Data Disclosure](https://developers.google.com/ml-kit/android-data-disclosure)
- [iOS Data Disclosure](https://developers.google.com/ml-kit/ios-data-disclosure)

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/genai-proofreading/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/genai-proofreading/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.
