# Capacitor ML Kit Entity Extraction Plugin

Unofficial Capacitor plugin for [ML Kit Entity Extraction](https://developers.google.com/ml-kit/language/entity-extraction).[^1]

> [!WARNING]
> The ML Kit Entity Extraction API is currently in **beta**. The API surface and behavior may change in a backwards-incompatible way in future releases.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Use Cases

The Entity Extraction plugin detects structured data such as addresses, date-times, and phone numbers in raw text directly on the device, for example:

- **Messaging apps**: Turn detected phone numbers, addresses, or date-times into tappable actions.
- **Email clients**: Recognize tracking numbers, flight numbers, or IBANs and offer quick actions.
- **Calendar apps**: Suggest events from date-times mentioned in a message.
- **Finance apps**: Detect payment cards, IBANs, or money amounts in pasted text.

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
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capacitor-mlkit/entity-extraction` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capacitor-mlkit/entity-extraction
npx cap sync
```

**Attention**: This plugin **only supports CocoaPods** for iOS dependency management. Swift Package Manager (SPM) is not supported for the ML Kit SDK, see [this comment](https://github.com/googlesamples/mlkit/issues/180#issuecomment-1298964099).

### Android

#### Variables

If needed, you can define the following project variable in your app’s `variables.gradle` file to change the default version of the dependency:

- `$mlkitEntityExtractionVersion` version of `com.google.mlkit:entity-extraction` (default: `16.0.0-beta6`)

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

Before you can extract entities, you must download the model for the language you want to use.
The model is downloaded once and then reused for all subsequent extractions.

The following example shows how to download the English model and extract a date-time entity from a text.
A `referenceTime` is provided so that relative date-times such as "tomorrow" can be resolved.

```typescript
import {
  EntityExtraction,
  EntityType,
  ErrorCode,
  Language,
} from '@capacitor-mlkit/entity-extraction';

const downloadModel = async () => {
  await EntityExtraction.downloadModel({
    language: Language.English,
  });
};

const getDownloadedModels = async () => {
  const { languages } = await EntityExtraction.getDownloadedModels();
  return languages;
};

const extractEntities = async () => {
  try {
    const { annotations } = await EntityExtraction.extractEntities({
      text: 'Let\'s meet tomorrow at 5pm.',
      language: Language.English,
      referenceTime: Date.now(),
      referenceTimeZone: 'America/New_York',
    });
    for (const annotation of annotations) {
      for (const entity of annotation.entities) {
        if (entity.type === EntityType.DateTime) {
          console.log(annotation.text, entity.timestamp);
        }
      }
    }
  } catch (error) {
    if (error.code === ErrorCode.ModelNotDownloaded) {
      // The model must be downloaded first using `downloadModel(...)`.
    }
  }
};

const deleteDownloadedModel = async () => {
  await EntityExtraction.deleteDownloadedModel({
    language: Language.English,
  });
};
```

## API

<docgen-index>

* [`deleteDownloadedModel(...)`](#deletedownloadedmodel)
* [`downloadModel(...)`](#downloadmodel)
* [`extractEntities(...)`](#extractentities)
* [`getDownloadedModels()`](#getdownloadedmodels)
* [Interfaces](#interfaces)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### deleteDownloadedModel(...)

```typescript
deleteDownloadedModel(options: DeleteDownloadedModelOptions) => Promise<void>
```

Delete the model for the given language.

Only available on Android and iOS.

| Param         | Type                                                                                  |
| ------------- | ------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#deletedownloadedmodeloptions">DeleteDownloadedModelOptions</a></code> |

**Since:** 8.2.0

--------------------


### downloadModel(...)

```typescript
downloadModel(options: DownloadModelOptions) => Promise<void>
```

Download the model for the given language.

The model must be downloaded before entities can be extracted for a language.

Only available on Android and iOS.

| Param         | Type                                                                  |
| ------------- | --------------------------------------------------------------------- |
| **`options`** | <code><a href="#downloadmodeloptions">DownloadModelOptions</a></code> |

**Since:** 8.2.0

--------------------


### extractEntities(...)

```typescript
extractEntities(options: ExtractEntitiesOptions) => Promise<ExtractEntitiesResult>
```

Extract entities (e.g. addresses, date-times or phone numbers) from the given text.

The model for the given language must be downloaded first using the `downloadModel(...)` method.
If the model is not downloaded, the method rejects with the `MODEL_NOT_DOWNLOADED` error code.

Only available on Android and iOS.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#extractentitiesoptions">ExtractEntitiesOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#extractentitiesresult">ExtractEntitiesResult</a>&gt;</code>

**Since:** 8.2.0

--------------------


### getDownloadedModels()

```typescript
getDownloadedModels() => Promise<GetDownloadedModelsResult>
```

Get the languages for which a model has been downloaded.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getdownloadedmodelsresult">GetDownloadedModelsResult</a>&gt;</code>

**Since:** 8.2.0

--------------------


### Interfaces


#### DeleteDownloadedModelOptions

| Prop           | Type                                          | Description                                 | Since |
| -------------- | --------------------------------------------- | ------------------------------------------- | ----- |
| **`language`** | <code><a href="#language">Language</a></code> | The language for which to delete the model. | 8.2.0 |


#### DownloadModelOptions

| Prop           | Type                                          | Description                                   | Since |
| -------------- | --------------------------------------------- | --------------------------------------------- | ----- |
| **`language`** | <code><a href="#language">Language</a></code> | The language for which to download the model. | 8.2.0 |


#### ExtractEntitiesResult

| Prop              | Type                            | Description                | Since |
| ----------------- | ------------------------------- | -------------------------- | ----- |
| **`annotations`** | <code>EntityAnnotation[]</code> | The extracted annotations. | 8.2.0 |


#### EntityAnnotation

| Prop           | Type                  | Description                                                               | Since |
| -------------- | --------------------- | ------------------------------------------------------------------------- | ----- |
| **`text`**     | <code>string</code>   | The text segment within the original text that this annotation refers to. | 8.2.0 |
| **`start`**    | <code>number</code>   | The start index (inclusive) of the annotation in the original text.       | 8.2.0 |
| **`end`**      | <code>number</code>   | The end index (exclusive) of the annotation in the original text.         | 8.2.0 |
| **`entities`** | <code>Entity[]</code> | The entities that this annotation could refer to.                         | 8.2.0 |


#### Entity

| Prop                       | Type                                                                    | Description                                                                                                   | Since |
| -------------------------- | ----------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------- | ----- |
| **`type`**                 | <code><a href="#entitytype">EntityType</a></code>                       | The type of the entity.                                                                                       | 8.2.0 |
| **`dateTimeGranularity`**  | <code><a href="#datetimegranularity">DateTimeGranularity</a></code>     | The granularity of the extracted date-time. Only available if `type` is `DATE_TIME`.                          | 8.2.0 |
| **`timestamp`**            | <code>number</code>                                                     | The extracted date-time in milliseconds since epoch. Only available if `type` is `DATE_TIME`.                 | 8.2.0 |
| **`unnormalizedCurrency`** | <code>string</code>                                                     | The currency of the money as it appears in the text (not canonicalized). Only available if `type` is `MONEY`. | 8.2.0 |
| **`integerPart`**          | <code>number</code>                                                     | The integer part of the money amount. Only available if `type` is `MONEY`.                                    | 8.2.0 |
| **`fractionalPart`**       | <code>number</code>                                                     | The fractional part of the money amount. Only available if `type` is `MONEY`.                                 | 8.2.0 |
| **`paymentCardNetwork`**   | <code><a href="#paymentcardnetwork">PaymentCardNetwork</a></code>       | The network of the payment card. Only available if `type` is `PAYMENT_CARD`.                                  | 8.2.0 |
| **`paymentCardNumber`**    | <code>string</code>                                                     | The number of the payment card in canonical format. Only available if `type` is `PAYMENT_CARD`.               | 8.2.0 |
| **`airlineCode`**          | <code>string</code>                                                     | The IATA airline designator (two or three letters). Only available if `type` is `FLIGHT_NUMBER`.              | 8.2.0 |
| **`flightNumber`**         | <code>string</code>                                                     | The flight number (1 to 4 digits). Only available if `type` is `FLIGHT_NUMBER`.                               | 8.2.0 |
| **`isbn`**                 | <code>string</code>                                                     | The full ISBN in canonical format. Only available if `type` is `ISBN`.                                        | 8.2.0 |
| **`iban`**                 | <code>string</code>                                                     | The full IBAN in canonical format. Only available if `type` is `IBAN`.                                        | 8.2.0 |
| **`ibanCountryCode`**      | <code>string</code>                                                     | The ISO 3166-1 alpha-2 country code of the IBAN. Only available if `type` is `IBAN`.                          | 8.2.0 |
| **`parcelCarrier`**        | <code><a href="#parceltrackingcarrier">ParcelTrackingCarrier</a></code> | The parcel tracking carrier. Only available if `type` is `TRACKING_NUMBER`.                                   | 8.2.0 |
| **`parcelTrackingNumber`** | <code>string</code>                                                     | The parcel tracking number in canonical format. Only available if `type` is `TRACKING_NUMBER`.                | 8.2.0 |


#### ExtractEntitiesOptions

| Prop                    | Type                                          | Description                                                                                                                           | Since |
| ----------------------- | --------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`text`**              | <code>string</code>                           | The text to extract entities from.                                                                                                    | 8.2.0 |
| **`language`**          | <code><a href="#language">Language</a></code> | The language of the model to use. The model for this language must be downloaded first using the `downloadModel(...)` method.         | 8.2.0 |
| **`referenceTime`**     | <code>number</code>                           | The reference time (in milliseconds since epoch) used to resolve relative date-times (e.g. "tomorrow"). Defaults to the current time. | 8.2.0 |
| **`referenceTimeZone`** | <code>string</code>                           | The reference time zone (as an IANA time zone identifier) used to resolve relative date-times. Defaults to the device's time zone.    | 8.2.0 |
| **`preferredLocale`**   | <code>string</code>                           | The preferred locale (as a BCP-47 language tag) used to disambiguate entities. Defaults to the device's locale.                       | 8.2.0 |
| **`entityTypes`**       | <code>EntityType[]</code>                     | The entity types to extract. If not set, all entity types are extracted.                                                              | 8.2.0 |


#### GetDownloadedModelsResult

| Prop            | Type                    | Description                                          | Since |
| --------------- | ----------------------- | ---------------------------------------------------- | ----- |
| **`languages`** | <code>Language[]</code> | The languages for which a model has been downloaded. | 8.2.0 |


### Enums


#### Language

| Members          | Value                     | Since |
| ---------------- | ------------------------- | ----- |
| **`Arabic`**     | <code>'ARABIC'</code>     | 8.2.0 |
| **`Chinese`**    | <code>'CHINESE'</code>    | 8.2.0 |
| **`Dutch`**      | <code>'DUTCH'</code>      | 8.2.0 |
| **`English`**    | <code>'ENGLISH'</code>    | 8.2.0 |
| **`French`**     | <code>'FRENCH'</code>     | 8.2.0 |
| **`German`**     | <code>'GERMAN'</code>     | 8.2.0 |
| **`Italian`**    | <code>'ITALIAN'</code>    | 8.2.0 |
| **`Japanese`**   | <code>'JAPANESE'</code>   | 8.2.0 |
| **`Korean`**     | <code>'KOREAN'</code>     | 8.2.0 |
| **`Polish`**     | <code>'POLISH'</code>     | 8.2.0 |
| **`Portuguese`** | <code>'PORTUGUESE'</code> | 8.2.0 |
| **`Russian`**    | <code>'RUSSIAN'</code>    | 8.2.0 |
| **`Spanish`**    | <code>'SPANISH'</code>    | 8.2.0 |
| **`Thai`**       | <code>'THAI'</code>       | 8.2.0 |
| **`Turkish`**    | <code>'TURKISH'</code>    | 8.2.0 |


#### EntityType

| Members              | Value                          | Description                                   | Since |
| -------------------- | ------------------------------ | --------------------------------------------- | ----- |
| **`Address`**        | <code>'ADDRESS'</code>         | A physical address.                           | 8.2.0 |
| **`DateTime`**       | <code>'DATE_TIME'</code>       | A date and time reference.                    | 8.2.0 |
| **`Email`**          | <code>'EMAIL'</code>           | An e-mail address.                            | 8.2.0 |
| **`FlightNumber`**   | <code>'FLIGHT_NUMBER'</code>   | A flight number in IATA format.               | 8.2.0 |
| **`Iban`**           | <code>'IBAN'</code>            | An International Bank Account Number (IBAN).  | 8.2.0 |
| **`Isbn`**           | <code>'ISBN'</code>            | An International Standard Book Number (ISBN). | 8.2.0 |
| **`Money`**          | <code>'MONEY'</code>           | An amount of money.                           | 8.2.0 |
| **`PaymentCard`**    | <code>'PAYMENT_CARD'</code>    | A payment card.                               | 8.2.0 |
| **`Phone`**          | <code>'PHONE'</code>           | A phone number.                               | 8.2.0 |
| **`TrackingNumber`** | <code>'TRACKING_NUMBER'</code> | A shipment tracking number.                   | 8.2.0 |
| **`Url`**            | <code>'URL'</code>             | A URL.                                        | 8.2.0 |


#### DateTimeGranularity

| Members       | Value                  | Since |
| ------------- | ---------------------- | ----- |
| **`Unknown`** | <code>'UNKNOWN'</code> | 8.2.0 |
| **`Year`**    | <code>'YEAR'</code>    | 8.2.0 |
| **`Month`**   | <code>'MONTH'</code>   | 8.2.0 |
| **`Week`**    | <code>'WEEK'</code>    | 8.2.0 |
| **`Day`**     | <code>'DAY'</code>     | 8.2.0 |
| **`Hour`**    | <code>'HOUR'</code>    | 8.2.0 |
| **`Minute`**  | <code>'MINUTE'</code>  | 8.2.0 |
| **`Second`**  | <code>'SECOND'</code>  | 8.2.0 |


#### PaymentCardNetwork

| Members            | Value                        | Since |
| ------------------ | ---------------------------- | ----- |
| **`Unknown`**      | <code>'UNKNOWN'</code>       | 8.2.0 |
| **`Amex`**         | <code>'AMEX'</code>          | 8.2.0 |
| **`DinersClub`**   | <code>'DINERS_CLUB'</code>   | 8.2.0 |
| **`Discover`**     | <code>'DISCOVER'</code>      | 8.2.0 |
| **`InterPayment`** | <code>'INTER_PAYMENT'</code> | 8.2.0 |
| **`Jcb`**          | <code>'JCB'</code>           | 8.2.0 |
| **`Maestro`**      | <code>'MAESTRO'</code>       | 8.2.0 |
| **`Mastercard`**   | <code>'MASTERCARD'</code>    | 8.2.0 |
| **`Mir`**          | <code>'MIR'</code>           | 8.2.0 |
| **`Troy`**         | <code>'TROY'</code>          | 8.2.0 |
| **`Unionpay`**     | <code>'UNIONPAY'</code>      | 8.2.0 |
| **`Visa`**         | <code>'VISA'</code>          | 8.2.0 |


#### ParcelTrackingCarrier

| Members          | Value                      | Since |
| ---------------- | -------------------------- | ----- |
| **`Unknown`**    | <code>'UNKNOWN'</code>     | 8.2.0 |
| **`Fedex`**      | <code>'FEDEX'</code>       | 8.2.0 |
| **`Ups`**        | <code>'UPS'</code>         | 8.2.0 |
| **`Dhl`**        | <code>'DHL'</code>         | 8.2.0 |
| **`Usps`**       | <code>'USPS'</code>        | 8.2.0 |
| **`Ontrac`**     | <code>'ONTRAC'</code>      | 8.2.0 |
| **`Lasership`**  | <code>'LASERSHIP'</code>   | 8.2.0 |
| **`IsraelPost`** | <code>'ISRAEL_POST'</code> | 8.2.0 |
| **`SwissPost`**  | <code>'SWISS_POST'</code>  | 8.2.0 |
| **`Msc`**        | <code>'MSC'</code>         | 8.2.0 |
| **`Amazon`**     | <code>'AMAZON'</code>      | 8.2.0 |
| **`IParcel`**    | <code>'I_PARCEL'</code>    | 8.2.0 |

</docgen-api>

## FAQ

### Which platforms are supported by this plugin?

The plugin is available on Android and iOS. All methods are only available on Android and iOS, so there is no web implementation.

### Which languages are supported?

The following languages are supported: Arabic, Chinese, Dutch, English, French, German, Italian, Japanese, Korean, Polish, Portuguese, Russian, Spanish, Thai and Turkish (see the [`Language`](#language) enum). Note that a single model can extract entities from text in its language.

### Why does `extractEntities(...)` reject with `MODEL_NOT_DOWNLOADED`?

Entities can only be extracted for a language whose model has been downloaded. Download the model first using the `downloadModel(...)` method and handle the `MODEL_NOT_DOWNLOADED` error code to prompt a download.

### How do I resolve relative date-times such as "tomorrow"?

Provide a `referenceTime` (and optionally a `referenceTimeZone`) to the `extractEntities(...)` method. Relative date-times are resolved against this reference time. If not set, the current time and the device's time zone are used.

### Can I install this plugin using Swift Package Manager?

No, this plugin only supports CocoaPods for iOS dependency management because the ML Kit SDK itself does not support Swift Package Manager. Also make sure to set the deployment target in your `ios/App/Podfile` to at least `15.5` (see [Installation](#installation)).

## Related Plugins

- [Translation](https://capawesome.io/docs/sdks/capacitor/mlkit/translation/): Translate text before or after extracting entities.
- [Language Identification](https://capawesome.io/docs/sdks/capacitor/mlkit/language-identification/): Detect the language of a text before choosing a model.

## Terms & Privacy

This plugin uses the [Google ML Kit](https://developers.google.com/ml-kit):

- [Terms & Privacy](https://developers.google.com/ml-kit/terms)
- [Android Data Disclosure](https://developers.google.com/ml-kit/android-data-disclosure)
- [iOS Data Disclosure](https://developers.google.com/ml-kit/ios-data-disclosure)

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/entity-extraction/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/entity-extraction/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.
