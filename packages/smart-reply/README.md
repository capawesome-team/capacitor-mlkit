# Capacitor ML Kit Smart Reply Plugin

Unofficial Capacitor plugin for [ML Kit Smart Reply](https://developers.google.com/ml-kit/language/smart-reply).[^1]

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Use Cases

The Smart Reply plugin generates contextual reply suggestions for a conversation directly on the device, for example:

- **Messaging apps**: Offer quick reply suggestions below an incoming message.
- **Customer support**: Help agents respond faster with suggested replies.
- **Wearables**: Provide tappable reply chips where typing is inconvenient.
- **Chatbots**: Suggest follow-up responses based on the recent conversation.

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
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capacitor-mlkit/smart-reply` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capacitor-mlkit/smart-reply
npx cap sync
```

**Attention**: This plugin **only supports CocoaPods** for iOS dependency management. Swift Package Manager (SPM) is not supported for the ML Kit SDK, see [this comment](https://github.com/googlesamples/mlkit/issues/180#issuecomment-1298964099).

### Android

#### Variables

If needed, you can define the following project variable in your app’s `variables.gradle` file to change the default version of the dependency:

- `$mlkitSmartReplyVersion` version of `com.google.mlkit:smart-reply` (default: `17.0.4`)

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

The following example shows how to generate reply suggestions for a conversation between two users.

Smart Reply only supports conversations in **English**. For any other language, the `status` will be `NOT_SUPPORTED_LANGUAGE` and no suggestions are returned. Reply suggestions are only generated for the messages of remote users (`isLocalUser: false`), so remote messages must provide a stable `userId`.

```typescript
import {
  SmartReply,
  SmartReplySuggestionResultStatus,
} from '@capacitor-mlkit/smart-reply';

const suggestReplies = async () => {
  const { status, suggestions } = await SmartReply.suggestReplies({
    messages: [
      {
        text: 'Are you free for lunch today?',
        timestamp: Date.now() - 60_000,
        isLocalUser: false,
        userId: 'user-1',
      },
      {
        text: 'Sure, where do you want to go?',
        timestamp: Date.now() - 30_000,
        isLocalUser: true,
      },
      {
        text: 'How about the new place downtown?',
        timestamp: Date.now(),
        isLocalUser: false,
        userId: 'user-1',
      },
    ],
  });

  if (status === SmartReplySuggestionResultStatus.Success) {
    return suggestions;
  }
  return [];
};
```

## API

<docgen-index>

* [`suggestReplies(...)`](#suggestreplies)
* [Interfaces](#interfaces)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### suggestReplies(...)

```typescript
suggestReplies(options: SuggestRepliesOptions) => Promise<SuggestRepliesResult>
```

Generate reply suggestions for a conversation.

Smart Reply only supports conversations in English. If the conversation is
in another language or no suitable reply is found, the `status` will
reflect this and `suggestions` will be empty.

Only available on Android and iOS.

| Param         | Type                                                                    |
| ------------- | ----------------------------------------------------------------------- |
| **`options`** | <code><a href="#suggestrepliesoptions">SuggestRepliesOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#suggestrepliesresult">SuggestRepliesResult</a>&gt;</code>

**Since:** 8.2.0

--------------------


### Interfaces


#### SuggestRepliesResult

| Prop              | Type                                                                                          | Description                                                                                              | Since |
| ----------------- | --------------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------- | ----- |
| **`status`**      | <code><a href="#smartreplysuggestionresultstatus">SmartReplySuggestionResultStatus</a></code> | The status of the suggestion generation.                                                                 | 8.2.0 |
| **`suggestions`** | <code>string[]</code>                                                                         | The generated reply suggestions. Contains up to 3 suggestions and is empty unless `status` is `SUCCESS`. | 8.2.0 |


#### SuggestRepliesOptions

| Prop           | Type                       | Description                                                                                                                                                        | Since |
| -------------- | -------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ----- |
| **`messages`** | <code>TextMessage[]</code> | The recent messages of the conversation in chronological order (oldest first). Only the last 10 messages are considered by the model. The array must not be empty. | 8.2.0 |


#### TextMessage

| Prop              | Type                 | Description                                                                                                             | Since |
| ----------------- | -------------------- | ----------------------------------------------------------------------------------------------------------------------- | ----- |
| **`text`**        | <code>string</code>  | The text content of the message.                                                                                        | 8.2.0 |
| **`timestamp`**   | <code>number</code>  | The time the message was sent in milliseconds since epoch.                                                              | 8.2.0 |
| **`isLocalUser`** | <code>boolean</code> | Whether the message was sent by the local user. Reply suggestions are only generated for messages sent by remote users. | 8.2.0 |
| **`userId`**      | <code>string</code>  | A stable identifier of the user who sent the message. Required when `isLocalUser` is `false`.                           | 8.2.0 |


### Enums


#### SmartReplySuggestionResultStatus

| Members                    | Value                                 | Description                                                                       | Since |
| -------------------------- | ------------------------------------- | --------------------------------------------------------------------------------- | ----- |
| **`Success`**              | <code>'SUCCESS'</code>                | Suggestions were generated successfully.                                          | 8.2.0 |
| **`NotSupportedLanguage`** | <code>'NOT_SUPPORTED_LANGUAGE'</code> | No suggestions were generated because the conversation language is not supported. | 8.2.0 |
| **`NoReply`**              | <code>'NO_REPLY'</code>               | No suggestions were generated for the conversation.                               | 8.2.0 |

</docgen-api>

## FAQ

### Which platforms are supported by this plugin?

The plugin is available on Android and iOS. The `suggestReplies(...)` method is only available on Android and iOS, so there is no web implementation.

### Which languages are supported?

Smart Reply only supports conversations in English. If the conversation is in another language, the result `status` is `NOT_SUPPORTED_LANGUAGE` and no suggestions are returned.

### Why did I not get any suggestions?

The model may not return suggestions for every conversation, for example when the language is not supported (`NOT_SUPPORTED_LANGUAGE`) or when it cannot generate a suitable reply for the given context (`NO_REPLY`). Always check the `status` and handle the case where `suggestions` is empty.

### How many suggestions are returned?

The model returns up to 3 reply suggestions. It only considers the last 10 messages of the conversation.

### Do I need to provide a `userId` for every message?

You must provide a `userId` for messages sent by remote users (`isLocalUser: false`), as the model uses it to distinguish participants. Messages sent by the local user (`isLocalUser: true`) do not need a `userId`.

### Can I install this plugin using Swift Package Manager?

No, this plugin only supports CocoaPods for iOS dependency management because the ML Kit SDK itself does not support Swift Package Manager. Also make sure to set the deployment target in your `ios/App/Podfile` to at least `15.5` (see [Installation](#installation)).

## Related Plugins

- [Translation](https://capawesome.io/docs/sdks/capacitor/mlkit/translation/): Translate a conversation before generating reply suggestions.
- [Language Identification](https://capawesome.io/docs/sdks/capacitor/mlkit/language-identification/): Detect the language of a conversation.

## Terms & Privacy

This plugin uses the [Google ML Kit](https://developers.google.com/ml-kit):

- [Terms & Privacy](https://developers.google.com/ml-kit/terms)
- [Android Data Disclosure](https://developers.google.com/ml-kit/android-data-disclosure)
- [iOS Data Disclosure](https://developers.google.com/ml-kit/ios-data-disclosure)

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/smart-reply/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/smart-reply/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.
