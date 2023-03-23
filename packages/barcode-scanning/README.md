# @capacitor-mlkit/barcode-scanning

Unofficial Capacitor plugin for [ML Kit Barcode scanning](https://developers.google.com/ml-kit/vision/barcode-scanning).[^1][^2]

## Features

- 🧩 Optional ready-to-use interface without webview customizations
- 🏎️ Extremely fast
- 📷 Scan multiple barcodes at once
- ⏺️ Define detection area
- 🏞️ Reading barcodes from images
- 🔦 Torch and Autofocus support
- 🔋 Supports Android and iOS

For a complete list of **supported barcodes**, see [BarcodeFormat](#barcodeformat).

## Demo

A working example can be found here: [https://github.com/robingenz/capacitor-mlkit-plugin-demo](https://github.com/robingenz/capacitor-mlkit-plugin-demo)

| Android                                                                                                                         |
| ------------------------------------------------------------------------------------------------------------------------------- |
| <img src="https://user-images.githubusercontent.com/13857929/224423595-b7e97595-8b2b-4cd7-b500-b30e10e11dfc.gif" width="324" /> |

## Installation

```bash
npm install @capacitor-mlkit/barcode-scanning
npx cap sync
```

### Android

This API requires the following permissions be added to your `AndroidManifest.xml` before the `application` tag:

```xml
<!-- To get access to the camera. -->
<uses-permission android:name="android.permission.CAMERA" />
<!-- To get access to the flashlight. -->
<uses-permission android:name="android.permission.FLASHLIGHT"/>
```

You also need to add the following meta data **in** the `application` tag in your `AndroidManifest.xml`:

```xml
<meta-data android:name="com.google.mlkit.vision.DEPENDENCIES" android:value="barcode_ui"/>
```

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$androidxCameraCamera2Version` version of `com.google.mlkit:barcode-scanning` (default: `1.1.0`)
- `$androidxCameraCoreVersion` version of `com.google.mlkit:barcode-scanning` (default: `1.1.0`)
- `$androidxCameraLifecycleVersion` version of `com.google.mlkit:barcode-scanning` (default: `1.1.0`)
- `$androidxCameraViewVersion` version of `com.google.mlkit:barcode-scanning` (default: `1.1.0`)
- `$mlkitBarcodeScanningVersion` version of `com.google.mlkit:barcode-scanning` (default: `17.0.3`)
- `$playServicesCodeScannerVersion` version of `com.google.mlkit:barcode-scanning` (default: `16.0.0-beta3`)

### iOS

Add the `NSCameraUsageDescription` key to the `ios/App/App/Info.plist` file, which tells the user why the app needs to use the camera:

```diff
+ <key>NSCameraUsageDescription</key>
+ <string>The app enables the scanning of various barcodes.</string>
```

If you also use a `@capacitor-firebase/*` dependency in your project, then implement [this workaround](https://github.com/capawesome-team/capacitor-mlkit/issues/23#issuecomment-1470739611) to avoid conflict with the Cocoapods dependencies.

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-mlkit-plugin-demo](https://github.com/robingenz/capacitor-mlkit-plugin-demo)

## Usage

```typescript
import {
  BarcodeScanner,
  BarcodeFormat,
  LensFacing,
} from '@capacitor-mlkit/barcode-scanning';

const startScan = async () => {
  // The camera is visible behind the WebView, so that you can customize the UI in the WebView.
  // However, this means that you have to hide all elements that should not be visible.
  // You can find an example in our demo repository.
  // In this case we set a class `barcode-scanner-active`, which then contains certain CSS rules for our app.
  document.querySelector('body')?.classList.add('barcode-scanner-active');

  // Add the `barcodeScanned` listener
  const listener = await BarcodeScanner.addListener(
    'barcodeScanned',
    async result => {
      console.log(result.barcode);
    },
  );

  // Start the barcode scanner
  await BarcodeScanner.startScan();
};

const stopScan = async () => {
  // Make all elements in the WebView visible again
  document.querySelector('body')?.classList.remove('barcode-scanner-active');

  // Remove all listeners
  await BarcodeScanner.removeAllListeners();

  // Stop the barcode scanner
  await BarcodeScanner.stopScan();
};

const scanSingleBarcode = async () => {
  return new Promise(async resolve => {
    document.querySelector('body')?.classList.add('barcode-scanner-active');

    const listener = await BarcodeScanner.addListener(
      'barcodeScanned',
      async result => {
        await listener.remove();
        document
          .querySelector('body')
          ?.classList.remove('barcode-scanner-active');
        await BarcodeScanner.stopScan();
        resolve(result.barcode);
      },
    );

    await BarcodeScanner.startScan();
  });
};

const scan = async () => {
  const { barcodes } = await BarcodeScanner.scan({
    formats: [BarcodeFormat.QrCode],
    lensFacing: LensFacing.Back,
  });
  return barcodes;
};

const isSupported = async () => {
  const { supported } = await BarcodeScanner.isSupported();
  return supported;
};

const enableTorch = async () => {
  await BarcodeScanner.enableTorch();
};

const disableTorch = async () => {
  await BarcodeScanner.disableTorch();
};

const toggleTorch = async () => {
  await BarcodeScanner.toggleTorch();
};

const isTorchEnabled = async () => {
  const { enabled } = await BarcodeScanner.isTorchEnabled();
  return enabled;
};

const isTorchAvailable = async () => {
  const { available } = await BarcodeScanner.isTorchAvailable();
  return available;
};

const openSettings = async () => {
  await BarcodeScanner.openSettings();
};

const checkPermissions = async () => {
  const { camera } = await BarcodeScanner.checkPermissions();
  return camera;
};

const requestPermissions = async () => {
  const { camera } = await BarcodeScanner.requestPermissions();
  return camera;
};
```

An example of the CSS class `barcode-scanner-active` **with** Ionic could be:

```css
// Hide all elements
body.barcode-scanner-active {
  visibility: hidden;
  --background: transparent;
  --ion-background-color: transparent;
}

// Show only the barcode scanner modal
.barcode-scanner-modal {
  visibility: visible;
}

@media (prefers-color-scheme: dark) {
  .barcode-scanner-modal {
    --background: transparent;
    --ion-background-color: transparent;
  }
}
```

An example of the CSS class `barcode-scanner-active` **without** Ionic could be:

```css
// Hide all elements
body.barcode-scanner-active {
  visibility: hidden;
}

// Show only the barcode scanner modal
.barcode-scanner-modal {
  visibility: visible;
}
```

If you can't see the camera view, make sure all elements in the DOM are not visible or have a transparent background to debug the issue.

## API

<docgen-index>

* [`startScan(...)`](#startscan)
* [`stopScan()`](#stopscan)
* [`readBarcodesFromImage(...)`](#readbarcodesfromimage)
* [`scan(...)`](#scan)
* [`isSupported()`](#issupported)
* [`enableTorch()`](#enabletorch)
* [`disableTorch()`](#disabletorch)
* [`toggleTorch()`](#toggletorch)
* [`isTorchEnabled()`](#istorchenabled)
* [`isTorchAvailable()`](#istorchavailable)
* [`openSettings()`](#opensettings)
* [`checkPermissions()`](#checkpermissions)
* [`requestPermissions()`](#requestpermissions)
* [`addListener('barcodeScanned', ...)`](#addlistenerbarcodescanned)
* [`addListener('scanError', ...)`](#addlistenerscanerror)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### startScan(...)

```typescript
startScan(options?: StartScanOptions | undefined) => Promise<void>
```

Start scanning for barcodes.

Only available on Android and iOS.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#startscanoptions">StartScanOptions</a></code> |

**Since:** 0.0.1

--------------------


### stopScan()

```typescript
stopScan() => Promise<void>
```

Stop scanning for barcodes.

Only available on Android and iOS.

**Since:** 0.0.1

--------------------


### readBarcodesFromImage(...)

```typescript
readBarcodesFromImage(options: ReadBarcodesFromImageOptions) => Promise<ReadBarcodesFromImageResult>
```

Read barcodes from an image.

Only available on Android and iOS.

| Param         | Type                                                                                  |
| ------------- | ------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#readbarcodesfromimageoptions">ReadBarcodesFromImageOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#readbarcodesfromimageresult">ReadBarcodesFromImageResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### scan(...)

```typescript
scan(options?: ScanOptions | undefined) => Promise<ScanResult>
```

Scan a barcode with a ready-to-use interface without WebView customization.

On **Android**, no camera permission is required.

Only available on Android and iOS.

| Param         | Type                                                |
| ------------- | --------------------------------------------------- |
| **`options`** | <code><a href="#scanoptions">ScanOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#scanresult">ScanResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### isSupported()

```typescript
isSupported() => Promise<IsSupportedResult>
```

Returns whether or not the barcode scanner is supported.

Available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#issupportedresult">IsSupportedResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### enableTorch()

```typescript
enableTorch() => Promise<void>
```

Enable camera's torch (flash) during a scan session.

Only available on Android and iOS.

**Since:** 0.0.1

--------------------


### disableTorch()

```typescript
disableTorch() => Promise<void>
```

Disable camera's torch (flash) during a scan session.

Only available on Android and iOS.

**Since:** 0.0.1

--------------------


### toggleTorch()

```typescript
toggleTorch() => Promise<void>
```

Toggle camera's torch (flash) during a scan session.

Only available on Android and iOS.

**Since:** 0.0.1

--------------------


### isTorchEnabled()

```typescript
isTorchEnabled() => Promise<IsTorchEnabledResult>
```

Returns whether or not the camera's torch (flash) is enabled.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#istorchenabledresult">IsTorchEnabledResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### isTorchAvailable()

```typescript
isTorchAvailable() => Promise<IsTorchAvailableResult>
```

Returns whether or not the camera's torch (flash) is available.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#istorchavailableresult">IsTorchAvailableResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### openSettings()

```typescript
openSettings() => Promise<void>
```

Open the settings of the app so that the user can grant the camera permission.

Only available on Android and iOS.

**Since:** 0.0.1

--------------------


### checkPermissions()

```typescript
checkPermissions() => Promise<PermissionStatus>
```

Check camera permission.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 0.0.1

--------------------


### requestPermissions()

```typescript
requestPermissions() => Promise<PermissionStatus>
```

Request camera permission.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('barcodeScanned', ...)

```typescript
addListener(eventName: 'barcodeScanned', listenerFunc: (event: BarcodeScannedEvent) => void) => Promise<PluginListenerHandle> & PluginListenerHandle
```

Called when a barcode is scanned.

Available on Android and iOS.

| Param              | Type                                                                                    |
| ------------------ | --------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'barcodeScanned'</code>                                                           |
| **`listenerFunc`** | <code>(event: <a href="#barcodescannedevent">BarcodeScannedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt; & <a href="#pluginlistenerhandle">PluginListenerHandle</a></code>

**Since:** 0.0.1

--------------------


### addListener('scanError', ...)

```typescript
addListener(eventName: 'scanError', listenerFunc: (event: ScanErrorEvent) => void) => Promise<PluginListenerHandle> & PluginListenerHandle
```

Called when an error occurs during the scan.

Available on Android and iOS.

| Param              | Type                                                                          |
| ------------------ | ----------------------------------------------------------------------------- |
| **`eventName`**    | <code>'scanError'</code>                                                      |
| **`listenerFunc`** | <code>(event: <a href="#scanerrorevent">ScanErrorEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt; & <a href="#pluginlistenerhandle">PluginListenerHandle</a></code>

**Since:** 0.0.1

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

**Since:** 0.0.1

--------------------


### Interfaces


#### StartScanOptions

| Prop             | Type                                              | Description                                                                              | Since |
| ---------------- | ------------------------------------------------- | ---------------------------------------------------------------------------------------- | ----- |
| **`formats`**    | <code>BarcodeFormat[]</code>                      | Improve the speed of the barcode scanner by configuring the barcode formats to scan for. | 0.0.1 |
| **`lensFacing`** | <code><a href="#lensfacing">LensFacing</a></code> | Configure the camera (front or back) to use.                                             | 0.0.1 |


#### ReadBarcodesFromImageResult

| Prop           | Type                   | Description            | Since |
| -------------- | ---------------------- | ---------------------- | ----- |
| **`barcodes`** | <code>Barcode[]</code> | The detected barcodes. | 0.0.1 |


#### Barcode

| Prop               | Type                                                                                  | Description                                                                      | Since |
| ------------------ | ------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------- | ----- |
| **`bytes`**        | <code>number[]</code>                                                                 | Raw bytes as it was encoded in the barcode.                                      | 0.0.1 |
| **`cornerPoints`** | <code>[[number, number], [number, number], [number, number], [number, number]]</code> | The four corner points of the barcode in clockwise order starting with top-left. | 0.0.1 |
| **`displayValue`** | <code>string</code>                                                                   | The barcode value in a human readable format.                                    | 0.0.1 |
| **`format`**       | <code><a href="#barcodeformat">BarcodeFormat</a></code>                               | The barcode format.                                                              | 0.0.1 |
| **`rawValue`**     | <code>string</code>                                                                   | The barcode value in a machine readable format.                                  | 0.0.1 |
| **`valueType`**    | <code><a href="#barcodevaluetype">BarcodeValueType</a></code>                         | The barcode value type.                                                          | 0.0.1 |


#### ReadBarcodesFromImageOptions

| Prop          | Type                         | Description                                                                              | Since |
| ------------- | ---------------------------- | ---------------------------------------------------------------------------------------- | ----- |
| **`formats`** | <code>BarcodeFormat[]</code> | Improve the speed of the barcode scanner by configuring the barcode formats to scan for. | 0.0.1 |
| **`path`**    | <code>string</code>          | The local path to the image file.                                                        | 0.0.1 |


#### ScanResult

| Prop           | Type                   | Description            | Since |
| -------------- | ---------------------- | ---------------------- | ----- |
| **`barcodes`** | <code>Barcode[]</code> | The detected barcodes. | 0.0.1 |


#### ScanOptions

| Prop          | Type                         | Description                                                                              | Since |
| ------------- | ---------------------------- | ---------------------------------------------------------------------------------------- | ----- |
| **`formats`** | <code>BarcodeFormat[]</code> | Improve the speed of the barcode scanner by configuring the barcode formats to scan for. | 0.0.1 |


#### IsSupportedResult

| Prop            | Type                 | Description                                      | Since |
| --------------- | -------------------- | ------------------------------------------------ | ----- |
| **`supported`** | <code>boolean</code> | Whether or not the barcode scanner is supported. | 0.0.1 |


#### IsTorchEnabledResult

| Prop          | Type                 | Description                          | Since |
| ------------- | -------------------- | ------------------------------------ | ----- |
| **`enabled`** | <code>boolean</code> | Whether or not the torch is enabled. | 0.0.1 |


#### IsTorchAvailableResult

| Prop            | Type                 | Description                            | Since |
| --------------- | -------------------- | -------------------------------------- | ----- |
| **`available`** | <code>boolean</code> | Whether or not the torch is available. | 0.0.1 |


#### PermissionStatus

| Prop         | Type                                                                    | Since |
| ------------ | ----------------------------------------------------------------------- | ----- |
| **`camera`** | <code><a href="#camerapermissionstate">CameraPermissionState</a></code> | 0.0.1 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### BarcodeScannedEvent

| Prop          | Type                                        | Description         | Since |
| ------------- | ------------------------------------------- | ------------------- | ----- |
| **`barcode`** | <code><a href="#barcode">Barcode</a></code> | A detected barcode. | 0.0.1 |


#### ScanErrorEvent

| Prop          | Type                | Description        | Since |
| ------------- | ------------------- | ------------------ | ----- |
| **`message`** | <code>string</code> | The error message. | 0.0.1 |


### Type Aliases


#### CameraPermissionState

<code><a href="#permissionstate">PermissionState</a> | 'limited'</code>


#### PermissionState

<code>'prompt' | 'prompt-with-rationale' | 'granted' | 'denied'</code>


### Enums


#### BarcodeFormat

| Members          | Value                      | Description                        | Since |
| ---------------- | -------------------------- | ---------------------------------- | ----- |
| **`Aztec`**      | <code>'AZTEC'</code>       | Only available on Android and iOS. | 0.0.1 |
| **`Codabar`**    | <code>'CODABAR'</code>     | Only available on Android and iOS. | 0.0.1 |
| **`Code39`**     | <code>'CODE_39'</code>     | Only available on Android and iOS. | 0.0.1 |
| **`Code93`**     | <code>'CODE_93'</code>     | Only available on Android and iOS. | 0.0.1 |
| **`Code128`**    | <code>'CODE_128'</code>    | Only available on Android and iOS. | 0.0.1 |
| **`DataMatrix`** | <code>'DATA_MATRIX'</code> | Only available on Android and iOS. | 0.0.1 |
| **`Ean8`**       | <code>'EAN_8'</code>       | Only available on Android and iOS. | 0.0.1 |
| **`Ean13`**      | <code>'EAN_13'</code>      | Only available on Android and iOS. | 0.0.1 |
| **`Itf`**        | <code>'ITF'</code>         | Only available on Android and iOS. | 0.0.1 |
| **`Pdf417`**     | <code>'PDF_417'</code>     | Only available on Android and iOS. | 0.0.1 |
| **`QrCode`**     | <code>'QR_CODE'</code>     | Only available on Android and iOS. | 0.0.1 |
| **`UpcA`**       | <code>'UPC_A'</code>       | Only available on Android and iOS. | 0.0.1 |
| **`UpcE`**       | <code>'UPC_E'</code>       | Only available on Android and iOS. | 0.0.1 |


#### LensFacing

| Members     | Value                | Since |
| ----------- | -------------------- | ----- |
| **`Front`** | <code>'FRONT'</code> | 0.0.1 |
| **`Back`**  | <code>'BACK'</code>  | 0.0.1 |


#### BarcodeValueType

| Members              | Value                          | Since |
| -------------------- | ------------------------------ | ----- |
| **`CalendarEvent`**  | <code>'CALENDAR_EVENT'</code>  | 0.0.1 |
| **`ContactInfo`**    | <code>'CONTACT_INFO'</code>    | 0.0.1 |
| **`DriversLicense`** | <code>'DRIVERS_LICENSE'</code> | 0.0.1 |
| **`Email`**          | <code>'EMAIL'</code>           | 0.0.1 |
| **`Geo`**            | <code>'GEO'</code>             | 0.0.1 |
| **`Isbn`**           | <code>'ISBN'</code>            | 0.0.1 |
| **`Phone`**          | <code>'PHONE'</code>           | 0.0.1 |
| **`Product`**        | <code>'PRODUCT'</code>         | 0.0.1 |
| **`Sms`**            | <code>'SMS'</code>             | 0.0.1 |
| **`Text`**           | <code>'TEXT'</code>            | 0.0.1 |
| **`Url`**            | <code>'URL'</code>             | 0.0.1 |
| **`Wifi`**           | <code>'WIFI'</code>            | 0.0.1 |
| **`Unknown`**        | <code>'UNKNOWN'</code>         | 0.0.1 |

</docgen-api>

## Terms & Privacy

This plugin uses the [Google ML Kit](https://developers.google.com/ml-kit):

- [Terms & Privacy](https://developers.google.com/ml-kit/terms)
- [Android Data Disclosure](https://developers.google.com/ml-kit/android-data-disclosure)
- [iOS Data Disclosure](https://developers.google.com/ml-kit/ios-data-disclosure)

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/barcode-scanning/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/barcode-scanning/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.
[^2]: `QR Code` is a registered trademark of DENSO WAVE INCORPORATED.
