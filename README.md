> [!WARNING]
> These plugins do **not** support Swift Package Manager (SPM) because the underlying [ML Kit SDKs do not support SPM](https://github.com/googlesamples/mlkit/issues/180#issuecomment-1298964099) (see [#186](https://github.com/capawesome-team/capacitor-mlkit/issues/186)).

<br />
<div align="center">
  <img src="https://avatars.githubusercontent.com/u/105555861" width="128" height="128" />
</div>
<h3 align="center">ML Kit</h3>
<br />
<p align="center">
  <a href="https://github.com/capawesome-team/capacitor-mlkit"><img src="https://img.shields.io/maintenance/yes/2026?style=flat-square" /></a>
  <a href="https://github.com/capawesome-team/capacitor-mlkit/actions/workflows/ci.yml"><img src="https://img.shields.io/github/actions/workflow/status/capawesome-team/capacitor-mlkit/ci.yml?branch=main&style=flat-square" /></a>
  <a href="https://github.com/capawesome-team/capacitor-mlkit"><img src="https://img.shields.io/github/license/capawesome-team/capacitor-mlkit?style=flat-square" /></a>
  <a href="https://github.com/capawesome-team"><img src="https://img.shields.io/badge/part%20of-capawesome-%234f46e5?style=flat-square" /></a>
  <a href="https://turborepo.org/"><img src="https://img.shields.io/badge/maintained%20with-turborepo-%237f6ab2?style=flat-square" /></a>
  <!-- <a href="https://devlibrary.withgoogle.com/products/mlkit/repos/robingenz-capacitor-mlkit"><img src="https://img.shields.io/badge/part%20of-DevLibrary-9cf?color=4285F4&logoColor=4285F4&logo=google&style=flat-square" /></a> -->
</p>

## Features

Capacitor ML Kit is a collection of Capacitor plugins that enable the use of the [ML Kit SDKs](https://developers.google.com/ml-kit) in Capacitor.[^1]

- 🔋 Supports **Android and iOS**
- ⚡️ **Capacitor 7** support
- 🦋 Consistent versioning (no more SDK versions conflicts)
- 👁 Unified Typescript definitions
- 📄 Full documentation
- ⚙️ Under active development, more plugins coming soon

## Maintainers

| Maintainer | GitHub                                    | Social                                        |
| ---------- | ----------------------------------------- | --------------------------------------------- |
| Robin Genz | [robingenz](https://github.com/robingenz) | [@robin_genz](https://twitter.com/robin_genz) |

## Installation

Each plugin has its own installation instructions.
Click on the name of the desired plugin under the [`Plugins`](#plugins) section to get to the installation guide.

## Plugins

| Name                                                            | Package                                     | Version                                                                                                                                                                           | Downloads                                                                                                                                                                              |
| --------------------------------------------------------------- | ------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| [Barcode Scanning](./packages/barcode-scanning)                 | `@capacitor-mlkit/barcode-scanning`         | [![npm badge](https://img.shields.io/npm/v/@capacitor-mlkit/barcode-scanning?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/barcode-scanning)                 | [![npm downloads](https://img.shields.io/npm/dw/@capacitor-mlkit/barcode-scanning?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/barcode-scanning)                 |
| [Digital Ink Recognition](./packages/digital-ink-recognition)   | `@capacitor-mlkit/digital-ink-recognition`  | [![npm badge](https://img.shields.io/npm/v/@capacitor-mlkit/digital-ink-recognition?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/digital-ink-recognition)   | [![npm downloads](https://img.shields.io/npm/dw/@capacitor-mlkit/digital-ink-recognition?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/digital-ink-recognition)   |
| [Document Scanner](./packages/document-scanner)                 | `@capacitor-mlkit/document-scanner`         | [![npm badge](https://img.shields.io/npm/v/@capacitor-mlkit/document-scanner?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/document-scanner)                 | [![npm downloads](https://img.shields.io/npm/dw/@capacitor-mlkit/document-scanner?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/document-scanner)                 |
| [Entity Extraction](./packages/entity-extraction)               | `@capacitor-mlkit/entity-extraction`        | [![npm badge](https://img.shields.io/npm/v/@capacitor-mlkit/entity-extraction?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/entity-extraction)               | [![npm downloads](https://img.shields.io/npm/dw/@capacitor-mlkit/entity-extraction?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/entity-extraction)               |
| [Face Detection](./packages/face-detection)                     | `@capacitor-mlkit/face-detection`           | [![npm badge](https://img.shields.io/npm/v/@capacitor-mlkit/face-detection?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/face-detection)                     | [![npm downloads](https://img.shields.io/npm/dw/@capacitor-mlkit/face-detection?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/face-detection)                     |
| [Face Mesh Detection](./packages/face-mesh-detection)           | `@capacitor-mlkit/face-mesh-detection`      | [![npm badge](https://img.shields.io/npm/v/@capacitor-mlkit/face-mesh-detection?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/face-mesh-detection)           | [![npm downloads](https://img.shields.io/npm/dw/@capacitor-mlkit/face-mesh-detection?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/face-mesh-detection)           |
| [GenAI Image Description](./packages/genai-image-description)   | `@capacitor-mlkit/genai-image-description`  | [![npm badge](https://img.shields.io/npm/v/@capacitor-mlkit/genai-image-description?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/genai-image-description)   | [![npm downloads](https://img.shields.io/npm/dw/@capacitor-mlkit/genai-image-description?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/genai-image-description)   |
| [GenAI Prompt](./packages/genai-prompt)                         | `@capacitor-mlkit/genai-prompt`             | [![npm badge](https://img.shields.io/npm/v/@capacitor-mlkit/genai-prompt?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/genai-prompt)                         | [![npm downloads](https://img.shields.io/npm/dw/@capacitor-mlkit/genai-prompt?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/genai-prompt)                         |
| [GenAI Proofreading](./packages/genai-proofreading)             | `@capacitor-mlkit/genai-proofreading`       | [![npm badge](https://img.shields.io/npm/v/@capacitor-mlkit/genai-proofreading?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/genai-proofreading)             | [![npm downloads](https://img.shields.io/npm/dw/@capacitor-mlkit/genai-proofreading?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/genai-proofreading)             |
| [GenAI Rewriting](./packages/genai-rewriting)                   | `@capacitor-mlkit/genai-rewriting`          | [![npm badge](https://img.shields.io/npm/v/@capacitor-mlkit/genai-rewriting?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/genai-rewriting)                   | [![npm downloads](https://img.shields.io/npm/dw/@capacitor-mlkit/genai-rewriting?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/genai-rewriting)                   |
| [GenAI Speech Recognition](./packages/genai-speech-recognition) | `@capacitor-mlkit/genai-speech-recognition` | [![npm badge](https://img.shields.io/npm/v/@capacitor-mlkit/genai-speech-recognition?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/genai-speech-recognition) | [![npm downloads](https://img.shields.io/npm/dw/@capacitor-mlkit/genai-speech-recognition?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/genai-speech-recognition) |
| [GenAI Summarization](./packages/genai-summarization)           | `@capacitor-mlkit/genai-summarization`      | [![npm badge](https://img.shields.io/npm/v/@capacitor-mlkit/genai-summarization?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/genai-summarization)           | [![npm downloads](https://img.shields.io/npm/dw/@capacitor-mlkit/genai-summarization?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/genai-summarization)           |
| [Image Labeling](./packages/image-labeling)                     | `@capacitor-mlkit/image-labeling`           | [![npm badge](https://img.shields.io/npm/v/@capacitor-mlkit/image-labeling?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/image-labeling)                     | [![npm downloads](https://img.shields.io/npm/dw/@capacitor-mlkit/image-labeling?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/image-labeling)                     |
| [Language Identification](./packages/language-identification)   | `@capacitor-mlkit/language-identification`  | [![npm badge](https://img.shields.io/npm/v/@capacitor-mlkit/language-identification?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/language-identification)   | [![npm downloads](https://img.shields.io/npm/dw/@capacitor-mlkit/language-identification?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/language-identification)   |
| [Object Detection](./packages/object-detection)                 | `@capacitor-mlkit/object-detection`         | [![npm badge](https://img.shields.io/npm/v/@capacitor-mlkit/object-detection?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/object-detection)                 | [![npm downloads](https://img.shields.io/npm/dw/@capacitor-mlkit/object-detection?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/object-detection)                 |
| [Pose Detection](./packages/pose-detection)                     | `@capacitor-mlkit/pose-detection`           | [![npm badge](https://img.shields.io/npm/v/@capacitor-mlkit/pose-detection?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/pose-detection)                     | [![npm downloads](https://img.shields.io/npm/dw/@capacitor-mlkit/pose-detection?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/pose-detection)                     |
| [Selfie Segmentation](./packages/selfie-segmentation)           | `@capacitor-mlkit/selfie-segmentation`      | [![npm badge](https://img.shields.io/npm/v/@capacitor-mlkit/selfie-segmentation?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/selfie-segmentation)           | [![npm downloads](https://img.shields.io/npm/dw/@capacitor-mlkit/selfie-segmentation?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/selfie-segmentation)           |
| [Smart Reply](./packages/smart-reply)                           | `@capacitor-mlkit/smart-reply`              | [![npm badge](https://img.shields.io/npm/v/@capacitor-mlkit/smart-reply?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/smart-reply)                           | [![npm downloads](https://img.shields.io/npm/dw/@capacitor-mlkit/smart-reply?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/smart-reply)                           |
| [Subject Segmentation](./packages/subject-segmentation)         | `@capacitor-mlkit/subject-segmentation`     | [![npm badge](https://img.shields.io/npm/v/@capacitor-mlkit/subject-segmentation?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/subject-segmentation)         | [![npm downloads](https://img.shields.io/npm/dw/@capacitor-mlkit/subject-segmentation?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/subject-segmentation)         |
| [Text Recognition](./packages/text-recognition)                 | `@capacitor-mlkit/text-recognition`         | [![npm badge](https://img.shields.io/npm/v/@capacitor-mlkit/text-recognition?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/text-recognition)                 | [![npm downloads](https://img.shields.io/npm/dw/@capacitor-mlkit/text-recognition?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/text-recognition)                 |
| [Translation](./packages/translation)                           | `@capacitor-mlkit/translation`              | [![npm badge](https://img.shields.io/npm/v/@capacitor-mlkit/translation?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/translation)                           | [![npm downloads](https://img.shields.io/npm/dw/@capacitor-mlkit/translation?style=flat-square)](https://www.npmjs.com/package/@capacitor-mlkit/translation)                           |

## Changelogs

Each plugin has its own `CHANGELOG.md` file which contains information about version changes.
Click on the name of the desired plugin under the [`Plugins`](#plugins) section to get to the plugin folder.

## Breaking Changes

Each plugin has its own `BREAKING.md` file which contains information about breaking changes.
Click on the name of the desired plugin under the [`Plugins`](#plugins) section to get to the plugin folder.

## Terms & Privacy

This plugin uses the [Google ML Kit](https://developers.google.com/ml-kit):

- [Terms & Privacy](https://developers.google.com/ml-kit/terms)
- [Android Data Disclosure](https://developers.google.com/ml-kit/android-data-disclosure)
- [iOS Data Disclosure](https://developers.google.com/ml-kit/ios-data-disclosure)

## Contributing

See [CONTRIBUTING.md](./CONTRIBUTING.md).

## License

See [LICENSE](./LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.
