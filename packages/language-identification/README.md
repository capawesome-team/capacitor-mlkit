# Capacitor ML Kit Language Identification Plugin

Unofficial Capacitor plugin for [ML Kit Language Identification](https://developers.google.com/ml-kit/language/identification).[^1]

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Use Cases

The Language Identification plugin determines the language of a string of text directly on the device, for example:

- **Localization**: Detect the language of user-generated content to display it correctly.
- **Translation**: Determine the source language before translating a text.
- **Content routing**: Route messages to the right agent or bot based on their language.
- **Smart Reply**: Check whether a conversation is in a supported language before generating reply suggestions.

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
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capacitor-mlkit/language-identification` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capacitor-mlkit/language-identification
npx cap sync
```

**Attention**: This plugin **only supports CocoaPods** for iOS dependency management. Swift Package Manager (SPM) is not supported for the ML Kit SDK, see [this comment](https://github.com/googlesamples/mlkit/issues/180#issuecomment-1298964099).

### Android

#### Variables

If needed, you can define the following project variable in your app’s `variables.gradle` file to change the default version of the dependency:

- `$mlkitLanguageIdVersion` version of `com.google.mlkit:language-id` (default: `17.0.6`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

### iOS

#### Minimum Deployment Target

Make sure to set the deployment target in your `ios/App/Podfile` to at least `15.5`:

```ruby
platform :ios, '15.5'
```

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-mlkit-plugin-demo](https://github.com/robingenz/capacitor-mlkit-plugin-demo)

## Usage

The language identification model ships with the SDK, so no model download is required.

```typescript
import { LanguageIdentification } from '@capacitor-mlkit/language-identification';

const identifyLanguage = async () => {
  const { language } = await LanguageIdentification.identifyLanguage({
    text: 'Wie geht es dir?',
  });
  // `language` is a BCP-47 tag (e.g. `de`) or `und` if no language met the threshold.
  return language;
};

const identifyPossibleLanguages = async () => {
  const { identifiedLanguages } =
    await LanguageIdentification.identifyPossibleLanguages({
      text: 'Wie geht es dir?',
    });
  return identifiedLanguages;
};
```

The returned language is a [BCP-47 language tag](https://en.wikipedia.org/wiki/IETF_language_tag) such as `en`, `de` or `zh`. If no language could be identified with sufficient confidence, the value `und` (undetermined) is returned.

## API

<docgen-index>

* [`identifyLanguage(...)`](#identifylanguage)
* [`identifyPossibleLanguages(...)`](#identifypossiblelanguages)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### identifyLanguage(...)

```typescript
identifyLanguage(options: IdentifyLanguageOptions) => Promise<IdentifyLanguageResult>
```

Identify the most likely language of the given text.

Only available on Android and iOS.

| Param         | Type                                                                        |
| ------------- | --------------------------------------------------------------------------- |
| **`options`** | <code><a href="#identifylanguageoptions">IdentifyLanguageOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#identifylanguageresult">IdentifyLanguageResult</a>&gt;</code>

**Since:** 8.2.0

--------------------


### identifyPossibleLanguages(...)

```typescript
identifyPossibleLanguages(options: IdentifyPossibleLanguagesOptions) => Promise<IdentifyPossibleLanguagesResult>
```

Identify all possible languages of the given text with their confidence.

Only available on Android and iOS.

| Param         | Type                                                                                          |
| ------------- | --------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#identifypossiblelanguagesoptions">IdentifyPossibleLanguagesOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#identifypossiblelanguagesresult">IdentifyPossibleLanguagesResult</a>&gt;</code>

**Since:** 8.2.0

--------------------


### Interfaces


#### IdentifyLanguageResult

| Prop           | Type                | Description                                                                                                                           | Since |
| -------------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`language`** | <code>string</code> | The BCP-47 language tag of the most likely language. The value is `'und'` (undetermined) if no language met the confidence threshold. | 8.2.0 |


#### IdentifyLanguageOptions

| Prop                      | Type                | Description                                                                                                                                                       | Default          | Since |
| ------------------------- | ------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------- | ----- |
| **`text`**                | <code>string</code> | The text to identify the language for.                                                                                                                            |                  | 8.2.0 |
| **`confidenceThreshold`** | <code>number</code> | The confidence threshold (`0.0` to `1.0`) a language must meet to be returned. If no language meets the threshold, the `language` will be `'und'` (undetermined). | <code>0.5</code> | 8.2.0 |


#### IdentifyPossibleLanguagesResult

| Prop                      | Type                              | Description                                                                                                                                                                        | Since |
| ------------------------- | --------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`identifiedLanguages`** | <code>IdentifiedLanguage[]</code> | The identified languages ordered by descending confidence. The array contains a single entry with the language `'und'` (undetermined) if no language met the confidence threshold. | 8.2.0 |


#### IdentifiedLanguage

| Prop             | Type                | Description                                                 | Since |
| ---------------- | ------------------- | ----------------------------------------------------------- | ----- |
| **`language`**   | <code>string</code> | The BCP-47 language tag of the identified language.         | 8.2.0 |
| **`confidence`** | <code>number</code> | The confidence (`0.0` to `1.0`) of the identified language. | 8.2.0 |


#### IdentifyPossibleLanguagesOptions

| Prop                      | Type                | Description                                                                    | Default           | Since |
| ------------------------- | ------------------- | ------------------------------------------------------------------------------ | ----------------- | ----- |
| **`text`**                | <code>string</code> | The text to identify the languages for.                                        |                   | 8.2.0 |
| **`confidenceThreshold`** | <code>number</code> | The confidence threshold (`0.0` to `1.0`) a language must meet to be returned. | <code>0.01</code> | 8.2.0 |

</docgen-api>

## FAQ

### Which platforms are supported by this plugin?

The plugin is available on Android and iOS. The `identifyLanguage(...)` and `identifyPossibleLanguages(...)` methods are only available on Android and iOS, so there is no web implementation.

### Which languages are supported?

The model can identify [more than 100 languages](https://developers.google.com/ml-kit/language/identification/langid-support). The returned value is a BCP-47 language tag.

### What does `und` mean?

`und` stands for _undetermined_. It is returned when no language could be identified with a confidence above the configured threshold.

### Do I need to download a model?

No. The language identification model is bundled with the SDK, so no model download or management is required.

### How can I change the confidence threshold?

Both methods accept an optional `confidenceThreshold` (between `0.0` and `1.0`). The default is `0.5` for `identifyLanguage(...)` and `0.01` for `identifyPossibleLanguages(...)`.

### Can I install this plugin using Swift Package Manager?

No, this plugin only supports CocoaPods for iOS dependency management because the ML Kit SDK itself does not support Swift Package Manager. Also make sure to set the deployment target in your `ios/App/Podfile` to at least `15.5` (see [Installation](#installation)).

## Related Plugins

- [Translation](https://capawesome.io/docs/sdks/capacitor/mlkit/translation/): Translate text once its language has been identified.
- [Smart Reply](https://capawesome.io/docs/sdks/capacitor/mlkit/smart-reply/): Generate reply suggestions for a conversation.
- [ML Kit Digital Ink Recognition](https://capawesome.io/docs/sdks/capacitor/mlkit/digital-ink-recognition/): Recognize handwriting, then identify the language of the recognized text.
- [GenAI Speech Recognition](https://capawesome.io/docs/sdks/capacitor/mlkit/genai-speech-recognition/): Transcribe speech, then identify the language of the transcript.

## Terms & Privacy

This plugin uses the [Google ML Kit](https://developers.google.com/ml-kit):

- [Terms & Privacy](https://developers.google.com/ml-kit/terms)
- [Android Data Disclosure](https://developers.google.com/ml-kit/android-data-disclosure)
- [iOS Data Disclosure](https://developers.google.com/ml-kit/ios-data-disclosure)

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/language-identification/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/language-identification/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.
