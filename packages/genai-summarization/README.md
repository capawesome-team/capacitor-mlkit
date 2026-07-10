# Capacitor ML Kit GenAI Summarization Plugin

Unofficial Capacitor plugin for [ML Kit GenAI Summarization](https://developers.google.com/ml-kit/genai/summarization).[^1]

> [!WARNING]
> The ML Kit GenAI APIs are currently in **beta**. The API surface and behavior may change in future releases.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Use Cases

The GenAI Summarization plugin is typically used to summarize text on-device using Gemini Nano, for example:

- **Article summaries**: Give users a quick overview of long articles or documents in one to three bullet points.
- **Chat recaps**: Summarize long chat conversations so users can catch up at a glance.
- **Note-taking**: Condense meeting notes or transcripts into their key points.

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
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capacitor-mlkit/genai-summarization` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capacitor-mlkit/genai-summarization
npx cap sync
```

### Android

#### API level

This plugin requires a minimum API level of 26. Make sure to set the `minSdkVersion` in your `variables.gradle` file to at least `26`.

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$mlkitGenaiSummarizationVersion` version of `com.google.mlkit:genai-summarization` (default: `1.0.0-beta1`)

## Usage

The following example shows how to check the feature status, download the feature and summarize a text.

### Check the feature status

Before using the summarization feature, check whether it is available on the device. The feature availability depends on the configured input type, output type and language:

```typescript
import {
  FeatureStatus,
  GenAiSummarization,
  InputType,
  Language,
  OutputType,
} from '@capacitor-mlkit/genai-summarization';

const checkFeatureStatus = async () => {
  const { featureStatus } = await GenAiSummarization.checkFeatureStatus({
    inputType: InputType.Article,
    language: Language.English,
    outputType: OutputType.ThreeBullets,
  });
  return featureStatus === FeatureStatus.Available;
};
```

### Download the feature

If the feature status is `DOWNLOADABLE`, download the feature before using it. Add a listener for the `downloadProgress` event to get notified about the download progress:

```typescript
import { GenAiSummarization, InputType, Language, OutputType } from '@capacitor-mlkit/genai-summarization';

const downloadFeature = async () => {
  await GenAiSummarization.addListener('downloadProgress', event => {
    console.log('Total bytes downloaded:', event.totalBytesDownloaded);
  });
  await GenAiSummarization.downloadFeature({
    inputType: InputType.Article,
    language: Language.English,
    outputType: OutputType.ThreeBullets,
  });
};
```

### Summarize a text

Pass the text to `summarize(...)` to generate a summary. Add a listener for the `inferenceProgress` event to receive partial results as they are generated:

```typescript
import { GenAiSummarization, InputType, Language, OutputType } from '@capacitor-mlkit/genai-summarization';

const summarize = async () => {
  await GenAiSummarization.addListener('inferenceProgress', event => {
    console.log('Partial result:', event.text);
  });
  const { summary } = await GenAiSummarization.summarize({
    text: 'Capacitor is an open source native runtime for building Web Native apps. Create cross-platform iOS, Android, and Progressive Web Apps with JavaScript, HTML, and CSS.',
    inputType: InputType.Article,
    language: Language.English,
    outputType: OutputType.ThreeBullets,
  });
  return summary;
};
```

## API

<docgen-index>

* [`checkFeatureStatus(...)`](#checkfeaturestatus)
* [`downloadFeature(...)`](#downloadfeature)
* [`summarize(...)`](#summarize)
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

Check the current availability status of the summarization feature.

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

Download the summarization feature.

The `downloadProgress` event listener will notify you about
the download progress. The returned promise resolves when
the download is complete or the feature is already available.

Only available on Android.

| Param         | Type                                                      |
| ------------- | --------------------------------------------------------- |
| **`options`** | <code><a href="#featureoptions">FeatureOptions</a></code> |

**Since:** 8.2.0

--------------------


### summarize(...)

```typescript
summarize(options: SummarizeOptions) => Promise<SummarizeResult>
```

Summarize the given text.

The `inferenceProgress` event listener will notify you about
partial results as they are generated. The returned promise
resolves with the full result.

Only available on Android.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#summarizeoptions">SummarizeOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#summarizeresult">SummarizeResult</a>&gt;</code>

**Since:** 8.2.0

--------------------


### addListener('downloadProgress', ...)

```typescript
addListener(eventName: 'downloadProgress', listenerFunc: (event: DownloadProgressEvent) => void) => Promise<PluginListenerHandle>
```

Called while the summarization feature is being downloaded.

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

| Prop                | Type                                                    | Description                                                   | Since |
| ------------------- | ------------------------------------------------------- | ------------------------------------------------------------- | ----- |
| **`featureStatus`** | <code><a href="#featurestatus">FeatureStatus</a></code> | The current availability status of the summarization feature. | 8.2.0 |


#### FeatureOptions

The feature availability depends on the configured input type,
output type and language. Therefore, `checkFeatureStatus(...)` and
`downloadFeature(...)` take the same base options as `summarize(...)`.

| Prop             | Type                                              | Description                     | Default                           | Since |
| ---------------- | ------------------------------------------------- | ------------------------------- | --------------------------------- | ----- |
| **`inputType`**  | <code><a href="#inputtype">InputType</a></code>   | The type of the input text.     | <code>InputType.Article</code>    | 8.2.0 |
| **`language`**   | <code><a href="#language">Language</a></code>     | The language of the input text. | <code>Language.English</code>     | 8.2.0 |
| **`outputType`** | <code><a href="#outputtype">OutputType</a></code> | The type of the output summary. | <code>OutputType.OneBullet</code> | 8.2.0 |


#### SummarizeResult

| Prop          | Type                | Description                    | Since |
| ------------- | ------------------- | ------------------------------ | ----- |
| **`summary`** | <code>string</code> | The summary of the input text. | 8.2.0 |


#### SummarizeOptions

| Prop       | Type                | Description                                                                                 | Since |
| ---------- | ------------------- | ------------------------------------------------------------------------------------------- | ----- |
| **`text`** | <code>string</code> | The text to summarize. The input must be under 4,000 tokens (or about 3,000 English words). | 8.2.0 |


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

| Members            | Value                       | Description                                                                                       | Since |
| ------------------ | --------------------------- | ------------------------------------------------------------------------------------------------- | ----- |
| **`Article`**      | <code>'ARTICLE'</code>      | The input text is an article.                                                                     | 8.2.0 |
| **`Conversation`** | <code>'CONVERSATION'</code> | The input text is a conversation where each line uses the format `&lt;name&gt;: &lt;message&gt;`. | 8.2.0 |


#### Language

| Members        | Value                   | Since |
| -------------- | ----------------------- | ----- |
| **`English`**  | <code>'ENGLISH'</code>  | 8.2.0 |
| **`Japanese`** | <code>'JAPANESE'</code> | 8.2.0 |
| **`Korean`**   | <code>'KOREAN'</code>   | 8.2.0 |


#### OutputType

| Members            | Value                        | Description                                        | Since |
| ------------------ | ---------------------------- | -------------------------------------------------- | ----- |
| **`OneBullet`**    | <code>'ONE_BULLET'</code>    | The summary consists of one bullet point.          | 8.2.0 |
| **`TwoBullets`**   | <code>'TWO_BULLETS'</code>   | The summary consists of up to two bullet points.   | 8.2.0 |
| **`ThreeBullets`** | <code>'THREE_BULLETS'</code> | The summary consists of up to three bullet points. | 8.2.0 |

</docgen-api>

## FAQ

### What are the requirements to use this plugin?

The summarization is performed on-device by Gemini Nano. This requires a device that supports Gemini Nano, Android API level 26 or higher and a locked bootloader (see [Requirements](#requirements)). Use the `checkFeatureStatus(...)` method to check whether the feature is available on the device.

### Why is this plugin only available on Android?

The ML Kit GenAI APIs are based on Gemini Nano and AICore, which are currently only available on Android. On iOS and Web, all methods reject with an appropriate error.

### How large can the input text be?

The input must be under 4,000 tokens (or about 3,000 English words). For articles, the input should contain at least 400 characters for the best results.

### How do I get the summary as a stream?

Add a listener for the `inferenceProgress` event before calling `summarize(...)`. The listener is called with each partial result as it is generated. The promise returned by `summarize(...)` resolves with the full result.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Translation](https://capawesome.io/docs/sdks/capacitor/mlkit/translation/): Unofficial Capacitor plugin for ML Kit Translation.
- [Smart Reply](https://capawesome.io/docs/sdks/capacitor/mlkit/smart-reply/): Unofficial Capacitor plugin for ML Kit Smart Reply.

## Terms & Privacy

This plugin uses the [Google ML Kit](https://developers.google.com/ml-kit):

- [Terms & Privacy](https://developers.google.com/ml-kit/terms)
- [Android Data Disclosure](https://developers.google.com/ml-kit/android-data-disclosure)
- [iOS Data Disclosure](https://developers.google.com/ml-kit/ios-data-disclosure)

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/genai-summarization/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/genai-summarization/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.
