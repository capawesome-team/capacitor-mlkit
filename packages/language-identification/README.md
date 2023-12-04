# @capacitor-mlkit/language-identification

⚡️ Capacitor plugin for ML Kit Language Identification.

## Installation

```bash
npm install @capacitor-mlkit/language-identification
npx cap sync
```

### Android

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$mlkitLanguageIdentificationVersion` version of `com.google.mlkit:language-id` (default: `17.0.1`)

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-mlkit-plugin-demo](https://github.com/robingenz/capacitor-mlkit-plugin-demo)

## Usage

```typescript
import { LanguageIdentification } from '@capacitor-mlkit/language-identification';

const deleteDownloadedModel = async () => {
  await LanguageIdentification.deleteDownloadedModel({
    language: Language.English,
  });
};
```

## API

<docgen-index>

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/language-identification/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/language-identification/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.
