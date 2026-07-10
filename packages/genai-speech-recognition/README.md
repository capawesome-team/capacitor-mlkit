# Capacitor ML Kit GenAI Speech Recognition Plugin

Unofficial Capacitor plugin for [ML Kit GenAI Speech Recognition](https://developers.google.com/ml-kit/genai/speech-recognition).[^1]

> [!WARNING]
> The ML Kit GenAI Speech Recognition API is currently in **alpha**. The API surface and behavior may change in future releases and breaking changes are expected. Additionally, the advanced recognition mode is currently only available on a very limited set of devices (e.g. Pixel 10).

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Use Cases

The GenAI Speech Recognition plugin is typically used to transcribe spoken words into text in real time, for example:

- **Voice input**: Let users dictate messages, notes or search queries instead of typing.
- **Live transcription**: Transcribe meetings, lectures or interviews on-device.
- **Voice commands**: Build hands-free experiences that react to spoken input.

## Requirements

This plugin performs speech recognition entirely on-device. It is only available on Android and supports two recognition modes with different requirements:

- **Basic mode**: Speech recognition using a traditional speech model. Requires Android API level 31 or higher. Supported locales: `en-US`, `fr-FR` (beta), `it-IT` (beta), `de-DE` (beta), `es-ES` (beta), `hi-IN` (beta), `ja-JP` (beta), `pt-BR` (beta), `tr-TR` (beta), `pl-PL` (beta), `cmn-Hans-CN` (beta), `ko-KR` (beta), `cmn-Hant-TW` (beta), `ru-RU` (beta), `vi-VN` (beta).
- **Advanced mode**: Speech recognition using a GenAI model. Currently only available on a very limited set of devices (e.g. Pixel 10). Supported locales: `en-US`, `ko-KR`, `es-ES`, `fr-FR`, `de-DE`, `it-IT`, `pt-PT`, `cmn-Hans-CN`, `cmn-Hant-TW`, `ja-JP`, `th-TH`, `ru-RU`, `nl-NL` (beta), `da-DK` (beta), `sv-SE` (beta), `pl-PL` (beta), `hi-IN` (beta), `vi-VN` (beta), `id-ID` (beta), `ar-SA` (beta), `tr-TR` (beta).

Use the `checkFeatureStatus(...)` method to check whether the feature is available on the device for the configured mode and locale.

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
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capacitor-mlkit/genai-speech-recognition` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capacitor-mlkit/genai-speech-recognition
npx cap sync
```

### Android

#### API level

This plugin requires a minimum API level of 26. Make sure to set the `minSdkVersion` in your `variables.gradle` file to at least `26`. Note that the basic recognition mode is only supported on devices with API level 31 or higher.

#### Permissions

The plugin declares the `android.permission.RECORD_AUDIO` permission in its manifest, so no manual configuration is needed. The microphone permission is requested automatically when you start a recognition session. You can also check and request the permission manually using the `checkPermissions()` and `requestPermissions()` methods.

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$mlkitGenaiSpeechRecognitionVersion` version of `com.google.mlkit:genai-speech-recognition` (default: `1.0.0-alpha1`)
- `$kotlinVersion` version of the Kotlin Gradle plugin (default: `2.2.20`)

## Usage

The following example shows how to check the feature status, download the feature and run a speech recognition session.

### Check the feature status

Before using the speech recognition feature, check whether it is available on the device. The feature availability depends on the configured mode and locale:

```typescript
import {
  FeatureStatus,
  GenAiSpeechRecognition,
  RecognitionMode,
} from '@capacitor-mlkit/genai-speech-recognition';

const checkFeatureStatus = async () => {
  const { featureStatus } = await GenAiSpeechRecognition.checkFeatureStatus({
    locale: 'en-US',
    mode: RecognitionMode.Basic,
  });
  return featureStatus === FeatureStatus.Available;
};
```

### Download the feature

If the feature status is `DOWNLOADABLE`, download the feature before using it. Add a listener for the `downloadProgress` event to get notified about the download progress:

```typescript
import { GenAiSpeechRecognition, RecognitionMode } from '@capacitor-mlkit/genai-speech-recognition';

const downloadFeature = async () => {
  await GenAiSpeechRecognition.addListener('downloadProgress', event => {
    console.log('Total bytes downloaded:', event.totalBytesDownloaded);
  });
  await GenAiSpeechRecognition.downloadFeature({
    locale: 'en-US',
    mode: RecognitionMode.Basic,
  });
};
```

### Start and stop a recognition session

Start a recognition session with `startRecognition(...)`. The audio is captured from the device microphone and the microphone permission is requested automatically if it has not been granted yet. Add listeners for the `partialResult` and `finalResult` events to receive the recognition results and for the `error` event to get notified about errors that occur during the session:

```typescript
import { GenAiSpeechRecognition, RecognitionMode } from '@capacitor-mlkit/genai-speech-recognition';

const startRecognition = async () => {
  await GenAiSpeechRecognition.addListener('partialResult', event => {
    console.log('Partial result:', event.text);
  });
  await GenAiSpeechRecognition.addListener('finalResult', event => {
    console.log('Final result:', event.text);
  });
  await GenAiSpeechRecognition.addListener('error', event => {
    console.error('Error:', event.message);
  });
  await GenAiSpeechRecognition.startRecognition({
    locale: 'en-US',
    mode: RecognitionMode.Basic,
  });
};

const stopRecognition = async () => {
  await GenAiSpeechRecognition.stopRecognition();
};
```

## API

<docgen-index>

* [`checkFeatureStatus(...)`](#checkfeaturestatus)
* [`checkPermissions()`](#checkpermissions)
* [`downloadFeature(...)`](#downloadfeature)
* [`requestPermissions()`](#requestpermissions)
* [`startRecognition(...)`](#startrecognition)
* [`stopRecognition()`](#stoprecognition)
* [`addListener('downloadProgress', ...)`](#addlistenerdownloadprogress-)
* [`addListener('error', ...)`](#addlistenererror-)
* [`addListener('finalResult', ...)`](#addlistenerfinalresult-)
* [`addListener('partialResult', ...)`](#addlistenerpartialresult-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### checkFeatureStatus(...)

```typescript
checkFeatureStatus(options?: FeatureOptions | undefined) => Promise<CheckFeatureStatusResult>
```

Check the current availability status of the speech recognition feature.

The feature availability depends on the configured mode and locale.
If the status is `DOWNLOADABLE`, you can download the feature
using `downloadFeature(...)`.

Only available on Android.

| Param         | Type                                                      |
| ------------- | --------------------------------------------------------- |
| **`options`** | <code><a href="#featureoptions">FeatureOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#checkfeaturestatusresult">CheckFeatureStatusResult</a>&gt;</code>

**Since:** 8.2.0

--------------------


### checkPermissions()

```typescript
checkPermissions() => Promise<PermissionStatus>
```

Check the status of the microphone permission.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 8.2.0

--------------------


### downloadFeature(...)

```typescript
downloadFeature(options?: FeatureOptions | undefined) => Promise<void>
```

Download the speech recognition feature.

The `downloadProgress` event listener will notify you about
the download progress. The returned promise resolves when
the download is complete or the feature is already available.

Only available on Android.

| Param         | Type                                                      |
| ------------- | --------------------------------------------------------- |
| **`options`** | <code><a href="#featureoptions">FeatureOptions</a></code> |

**Since:** 8.2.0

--------------------


### requestPermissions()

```typescript
requestPermissions() => Promise<PermissionStatus>
```

Request the microphone permission.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 8.2.0

--------------------


### startRecognition(...)

```typescript
startRecognition(options?: FeatureOptions | undefined) => Promise<void>
```

Start a speech recognition session.

The audio is captured from the device microphone. If the microphone
permission has not been granted yet, it will be requested automatically.
The `partialResult` and `finalResult` event listeners will notify you
about the recognition results. The `error` event listener will notify
you about errors that occur during the session.

Rejects with the `RECOGNITION_ALREADY_RUNNING` error code if a
recognition session is already running.

Only available on Android.

| Param         | Type                                                      |
| ------------- | --------------------------------------------------------- |
| **`options`** | <code><a href="#featureoptions">FeatureOptions</a></code> |

**Since:** 8.2.0

--------------------


### stopRecognition()

```typescript
stopRecognition() => Promise<void>
```

Stop the current speech recognition session.

Only available on Android.

**Since:** 8.2.0

--------------------


### addListener('downloadProgress', ...)

```typescript
addListener(eventName: 'downloadProgress', listenerFunc: (event: DownloadProgressEvent) => void) => Promise<PluginListenerHandle>
```

Called while the speech recognition feature is being downloaded.

Only available on Android.

| Param              | Type                                                                                        |
| ------------------ | ------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'downloadProgress'</code>                                                             |
| **`listenerFunc`** | <code>(event: <a href="#downloadprogressevent">DownloadProgressEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 8.2.0

--------------------


### addListener('error', ...)

```typescript
addListener(eventName: 'error', listenerFunc: (event: ErrorEvent) => void) => Promise<PluginListenerHandle>
```

Called when an error occurs during a recognition session.

Only available on Android.

| Param              | Type                                                                  |
| ------------------ | --------------------------------------------------------------------- |
| **`eventName`**    | <code>'error'</code>                                                  |
| **`listenerFunc`** | <code>(event: <a href="#errorevent">ErrorEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 8.2.0

--------------------


### addListener('finalResult', ...)

```typescript
addListener(eventName: 'finalResult', listenerFunc: (event: RecognitionResultEvent) => void) => Promise<PluginListenerHandle>
```

Called when a final recognition result is available.

Only available on Android.

| Param              | Type                                                                                          |
| ------------------ | --------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'finalResult'</code>                                                                    |
| **`listenerFunc`** | <code>(event: <a href="#recognitionresultevent">RecognitionResultEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 8.2.0

--------------------


### addListener('partialResult', ...)

```typescript
addListener(eventName: 'partialResult', listenerFunc: (event: RecognitionResultEvent) => void) => Promise<PluginListenerHandle>
```

Called when a partial recognition result is available.

Only available on Android.

| Param              | Type                                                                                          |
| ------------------ | --------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'partialResult'</code>                                                                  |
| **`listenerFunc`** | <code>(event: <a href="#recognitionresultevent">RecognitionResultEvent</a>) =&gt; void</code> |

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

| Prop                | Type                                                    | Description                                                        | Since |
| ------------------- | ------------------------------------------------------- | ------------------------------------------------------------------ | ----- |
| **`featureStatus`** | <code><a href="#featurestatus">FeatureStatus</a></code> | The current availability status of the speech recognition feature. | 8.2.0 |


#### FeatureOptions

The feature availability depends on the configured mode and locale.
Therefore, `checkFeatureStatus(...)` and `downloadFeature(...)` take
the same base options as `startRecognition(...)`.

| Prop         | Type                                                        | Description                                                                                                          | Default                            | Since |
| ------------ | ----------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------- | ---------------------------------- | ----- |
| **`locale`** | <code>string</code>                                         | The locale of the speech to recognize as a BCP-47 language tag. The supported locales depend on the configured mode. | <code>The device locale.</code>    | 8.2.0 |
| **`mode`**   | <code><a href="#recognitionmode">RecognitionMode</a></code> | The speech recognition mode.                                                                                         | <code>RecognitionMode.Basic</code> | 8.2.0 |


#### PermissionStatus

| Prop             | Type                                                        | Description                              | Since |
| ---------------- | ----------------------------------------------------------- | ---------------------------------------- | ----- |
| **`microphone`** | <code><a href="#permissionstate">PermissionState</a></code> | The status of the microphone permission. | 8.2.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### DownloadProgressEvent

| Prop                       | Type                | Description                                  | Since |
| -------------------------- | ------------------- | -------------------------------------------- | ----- |
| **`totalBytesDownloaded`** | <code>number</code> | The total number of bytes downloaded so far. | 8.2.0 |


#### ErrorEvent

| Prop          | Type                | Description        | Since |
| ------------- | ------------------- | ------------------ | ----- |
| **`message`** | <code>string</code> | The error message. | 8.2.0 |


#### RecognitionResultEvent

| Prop       | Type                | Description          | Since |
| ---------- | ------------------- | -------------------- | ----- |
| **`text`** | <code>string</code> | The recognized text. | 8.2.0 |


### Type Aliases


#### PermissionState

<code>'prompt' | 'prompt-with-rationale' | 'granted' | 'denied'</code>


#### StartRecognitionOptions

<code><a href="#featureoptions">FeatureOptions</a></code>


### Enums


#### FeatureStatus

| Members            | Value                       | Description                                                 | Since |
| ------------------ | --------------------------- | ----------------------------------------------------------- | ----- |
| **`Available`**    | <code>'AVAILABLE'</code>    | The feature is available to use.                            | 8.2.0 |
| **`Downloadable`** | <code>'DOWNLOADABLE'</code> | The feature can be downloaded using `downloadFeature(...)`. | 8.2.0 |
| **`Downloading`**  | <code>'DOWNLOADING'</code>  | The feature is currently being downloaded.                  | 8.2.0 |
| **`Unavailable`**  | <code>'UNAVAILABLE'</code>  | The feature is not available on this device.                | 8.2.0 |


#### RecognitionMode

| Members        | Value                   | Description                                                                                                                                              | Since |
| -------------- | ----------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`Advanced`** | <code>'ADVANCED'</code> | Speech recognition using a GenAI model. This mode provides the best quality but is currently only available on a limited set of devices (e.g. Pixel 10). | 8.2.0 |
| **`Basic`**    | <code>'BASIC'</code>    | Speech recognition using a traditional speech model. This mode requires Android API level 31 or higher.                                                  | 8.2.0 |

</docgen-api>

## FAQ

### What are the requirements to use this plugin?

The speech recognition is performed entirely on-device. The basic mode requires Android API level 31 or higher. The advanced mode uses a GenAI model and is currently only available on a very limited set of devices (e.g. Pixel 10). Use the `checkFeatureStatus(...)` method to check whether the feature is available on the device (see [Requirements](#requirements)).

### Why is this plugin only available on Android?

The ML Kit GenAI APIs are currently only available on Android. On iOS and Web, all methods reject with an appropriate error.

### What is the difference between the basic and advanced mode?

The basic mode uses a traditional on-device speech model and is supported on Android devices with API level 31 or higher. The advanced mode uses a GenAI model, which provides the best quality but is currently only available on a very limited set of devices (e.g. Pixel 10). Each mode supports a different set of locales (see [Requirements](#requirements)).

### How do I receive the recognition results?

Add listeners for the `partialResult` and `finalResult` events before calling `startRecognition(...)`. The `partialResult` event is called with intermediate results while the user is speaking. The `finalResult` event is called with the final recognized text.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Translation](https://capawesome.io/docs/sdks/capacitor/mlkit/translation/): Unofficial Capacitor plugin for ML Kit Translation.
- [Language Identification](https://capawesome.io/docs/sdks/capacitor/mlkit/language-identification/): Unofficial Capacitor plugin for ML Kit Language Identification.

## Terms & Privacy

This plugin uses the [Google ML Kit](https://developers.google.com/ml-kit):

- [Terms & Privacy](https://developers.google.com/ml-kit/terms)
- [Android Data Disclosure](https://developers.google.com/ml-kit/android-data-disclosure)
- [iOS Data Disclosure](https://developers.google.com/ml-kit/ios-data-disclosure)

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/genai-speech-recognition/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/genai-speech-recognition/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.
