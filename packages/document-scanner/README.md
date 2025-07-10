# @capacitor-mlkit/document-scanner

Unofficial Capacitor plugin for [ML Kit Document Scanner](https://developers.google.com/ml-kit/vision/doc-scanner).[^1]

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-deploy-real-time-app-updates.png?t=1" />
  </a>
</div>

## Installation

```bash
npm install @capacitor-mlkit/document-scanner
npx cap sync
```

### Android

#### Variables

If needed, you can define the following project variable in your app’s `variables.gradle` file to change the default version of the dependency:

- `$mlkitDocumentScannerVersion` version of `com.google.android.gms:play-services-mlkit-document-scanner` (default: `16.0.0-beta1`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

## Usage

```typescript
import { DocumentScanner } from '@capacitor-mlkit/document-scanner';

async function startScan() {
  try {
    const result = await DocumentScanner.scanDocument({
      galleryImportAllowed: true,
      pageLimit: 5,
      resultFormats: 'JPEG_PDF',
      scannerMode: 'FULL',
    });

    console.log('Scan successful:', result);

    if (result.scannedImages && result.scannedImages.length > 0) {
      console.log('First image URI:', result.scannedImages[0]);
      // Handle image URIs (e.g., display them)
    }

    if (result.pdf) {
      console.log('PDF URI:', result.pdf.uri);
      console.log('PDF Page Count:', result.pdf.pageCount);
      // Handle PDF URI (e.g., open or upload it)
    }
  } catch (error) {
    console.error('Scan failed:', error);
  }
}
```

## API

<docgen-index>

* [`scanDocument(...)`](#scandocument)
* [`isGoogleDocumentScannerModuleAvailable()`](#isgoogledocumentscannermoduleavailable)
* [`installGoogleDocumentScannerModule()`](#installgoogledocumentscannermodule)
* [`addListener('googleDocumentScannerModuleInstallProgress', ...)`](#addlistenergoogledocumentscannermoduleinstallprogress-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### scanDocument(...)

```typescript
scanDocument(options: ScanOptions) => Promise<ScanResult>
```

Starts the document scanning process.

Only available on Android.

| Param         | Type                                                | Description                            |
| ------------- | --------------------------------------------------- | -------------------------------------- |
| **`options`** | <code><a href="#scanoptions">ScanOptions</a></code> | Configuration options for the scanner. |

**Returns:** <code>Promise&lt;<a href="#scanresult">ScanResult</a>&gt;</code>

**Since:** 7.2.1

--------------------


### isGoogleDocumentScannerModuleAvailable()

```typescript
isGoogleDocumentScannerModuleAvailable() => Promise<IsGoogleDocumentScannerModuleAvailableResult>
```

Check if the Google Document Scanner module is available.

If the Google Document Scanner module is not available, you can install it by using `installGoogleDocumentScannerModule()`.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#isgoogledocumentscannermoduleavailableresult">IsGoogleDocumentScannerModuleAvailableResult</a>&gt;</code>

**Since:** 7.2.1

--------------------


### installGoogleDocumentScannerModule()

```typescript
installGoogleDocumentScannerModule() => Promise<void>
```

Install the Google Document Scanner module.

**Attention**: This only starts the installation.
The `googleDocumentScannerModuleInstallProgress` event listener will
notify you when the installation is complete.

Only available on Android.

**Since:** 7.2.1

--------------------


### addListener('googleDocumentScannerModuleInstallProgress', ...)

```typescript
addListener(eventName: 'googleDocumentScannerModuleInstallProgress', listenerFunc: (event: GoogleDocumentScannerModuleInstallProgressEvent) => void) => Promise<PluginListenerHandle>
```

Called when the Google Document Scanner module is installed.

Only available on Android.

| Param              | Type                                                                                                                                            |
| ------------------ | ----------------------------------------------------------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'googleDocumentScannerModuleInstallProgress'</code>                                                                                       |
| **`listenerFunc`** | <code>(event: <a href="#googledocumentscannermoduleinstallprogressevent">GoogleDocumentScannerModuleInstallProgressEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 7.2.1

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

Only available on Android.

**Since:** 7.2.1

--------------------


### Interfaces


#### ScanResult

Result of a document scan operation.

| Prop                | Type                                        | Description                                                                                                          | Since |
| ------------------- | ------------------------------------------- | -------------------------------------------------------------------------------------------------------------------- | ----- |
| **`scannedImages`** | <code>string[]</code>                       | An array of URIs for the scanned image pages (JPEG). Present if 'JPEG' or 'JPEG_PDF' was requested in resultFormats. | 7.2.1 |
| **`pdf`**           | <code><a href="#pdfinfo">PdfInfo</a></code> | Information about the generated PDF. Present if 'PDF' or 'JPEG_PDF' was requested in resultFormats.                  | 7.2.1 |


#### PdfInfo

Information about a generated PDF document.

| Prop            | Type                | Description                        | Since |
| --------------- | ------------------- | ---------------------------------- | ----- |
| **`uri`**       | <code>string</code> | The URI of the generated PDF file. | 7.2.1 |
| **`pageCount`** | <code>number</code> | The number of pages in the PDF.    | 7.2.1 |


#### ScanOptions

Options for the document scanner.

| Prop                       | Type                                                | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      | Default                 | Since |
| -------------------------- | --------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------- | ----- |
| **`galleryImportAllowed`** | <code>boolean</code>                                | Whether to allow importing from the photo gallery.                                                                                                                                                                                                                                                                                                                                                                                                                                                                               | <code>false</code>      | 7.2.1 |
| **`pageLimit`**            | <code>number</code>                                 | The maximum number of pages that can be scanned.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 | <code>10</code>         | 7.2.1 |
| **`resultFormats`**        | <code>'JPEG' \| 'PDF' \| 'JPEG_PDF'</code>          | The desired result formats. Can be 'JPEG', 'PDF', or 'JPEG_PDF'.                                                                                                                                                                                                                                                                                                                                                                                                                                                                 | <code>'JPEG_PDF'</code> | 7.2.1 |
| **`scannerMode`**          | <code>'FULL' \| 'BASE' \| 'BASE_WITH_FILTER'</code> | The scanner mode. BASE: Basic editing capabilities (crop, rotate, reorder pages, etc.). BASE_WITH_FILTER: Adds image filters (grayscale, auto image enhancement, etc.) to the BASE mode. FULL: Adds ML-enabled image cleaning capabilities (erase stains, fingers, etc.) to the BASE_WITH_FILTER mode. This mode will also allow future major features to be automatically added along with Google Play services updates, while the other two modes will maintain their current feature sets and only receive minor refinements. | <code>"FULL"</code>     | 7.2.1 |


#### IsGoogleDocumentScannerModuleAvailableResult

| Prop            | Type                 | Description                                                     | Since |
| --------------- | -------------------- | --------------------------------------------------------------- | ----- |
| **`available`** | <code>boolean</code> | Whether or not the Google Document Scanner module is available. | 7.2.1 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### GoogleDocumentScannerModuleInstallProgressEvent

| Prop           | Type                                                                                                        | Description                                                    | Since |
| -------------- | ----------------------------------------------------------------------------------------------------------- | -------------------------------------------------------------- | ----- |
| **`state`**    | <code><a href="#googledocumentscannermoduleinstallstate">GoogleDocumentScannerModuleInstallState</a></code> | The current state of the installation.                         | 7.2.1 |
| **`progress`** | <code>number</code>                                                                                         | The progress of the installation in percent between 0 and 100. | 7.2.1 |


### Enums


#### GoogleDocumentScannerModuleInstallState

| Members               | Value          | Since |
| --------------------- | -------------- | ----- |
| **`UNKNOWN`**         | <code>0</code> | 7.2.1 |
| **`PENDING`**         | <code>1</code> | 7.2.1 |
| **`DOWNLOADING`**     | <code>2</code> | 7.2.1 |
| **`CANCELED`**        | <code>3</code> | 7.2.1 |
| **`COMPLETED`**       | <code>4</code> | 7.2.1 |
| **`FAILED`**          | <code>5</code> | 7.2.1 |
| **`INSTALLING`**      | <code>6</code> | 7.2.1 |
| **`DOWNLOAD_PAUSED`** | <code>7</code> | 7.2.1 |

</docgen-api>

## Notes

- The ML Kit Document Scanner models, scanning logic, and UI flow are dynamically downloaded by Google Play services. Users might have to wait for these to download before the first use. You can use the isGoogleDocumentScannerModuleAvailable and installGoogleDocumentScannerModule methods to check for and install the module, and listen to the googleDocumentScannerModuleInstallProgress event for progress updates.
- This API requires Android API level 21 or above.
- It also requires a minimal device total RAM of 1.7GB. If lower, it returns an `MlKitException` with error code `UNSUPPORTED` when calling the API (this plugin will reject the promise).
- Consider that generating document files takes time and requires processing power, so only request the output formats (JPEG, or PDF, or both) you actually need via the `resultFormats` option.

## Terms & Privacy

This plugin uses the [Google ML Kit](https://developers.google.com/ml-kit):

- [Terms & Privacy](https://developers.google.com/ml-kit/terms)
- [Android Data Disclosure](https://developers.google.com/ml-kit/android-data-disclosure)
- [iOS Data Disclosure](https://developers.google.com/ml-kit/ios-data-disclosure)

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/document-scanner/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/document-scanner/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.
