# @capacitor-mlkit/barcode-scanning

Unofficial Capacitor plugin for [ML Kit Barcode Scanning](https://developers.google.com/ml-kit/vision/barcode-scanning).[^1][^2]

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-deploy-real-time-app-updates.png?t=1" />
  </a>
</div>

## Features

- 🧩 Optional ready-to-use interface without webview customizations
- 🏎️ Extremely fast
- 📷 Scan multiple barcodes at once
- ⏺️ Define detection area
- 🏞️ Reading barcodes from images
- 🔦 Torch and Autofocus support
- 🔋 Supports Android, iOS and web

For a complete list of **supported barcodes**, see [BarcodeFormat](#barcodeformat).

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 8.x.x          | >=8.x.x           | Active support |
| 7.x.x          | 7.x.x             | Deprecated     |
| 6.x.x          | 6.x.x             | Deprecated     |
| 5.x.x          | 5.x.x             | Deprecated     |

## Demo

A working example can be found here: [https://github.com/robingenz/capacitor-mlkit-plugin-demo](https://github.com/robingenz/capacitor-mlkit-plugin-demo)

| Android                                                                                                                         |
| ------------------------------------------------------------------------------------------------------------------------------- |
| <img src="https://user-images.githubusercontent.com/13857929/224423595-b7e97595-8b2b-4cd7-b500-b30e10e11dfc.gif" width="324" /> |

## Guides

- [Announcing the Capacitor ML Kit Barcode Scanning Plugin](https://capawesome.io/blog/announcing-the-capacitor-mlkit-barcode-scanner-plugin/)
- [How to build an Ionic Barcode Scanner with Capacitor](https://ionic.io/blog/how-to-build-an-ionic-barcode-scanner-with-capacitor)

## Installation

```bash
npm install @capacitor-mlkit/barcode-scanning
npx cap sync
```

**Attention**: This plugin **only supports CocoaPods** for iOS dependency management. Swift Package Manager (SPM) is not supported for the ML Kit SDK, see [this comment](https://github.com/googlesamples/mlkit/issues/180#issuecomment-1298964099).

### Android

#### Permissions

This API requires the following permissions be added to your `AndroidManifest.xml` before the `application` tag:

```xml
<uses-permission android:name="android.permission.CAMERA" />
```

You also need to add the following meta data **in** the `application` tag in your `AndroidManifest.xml`:

```xml
<meta-data android:name="com.google.mlkit.vision.DEPENDENCIES" android:value="barcode_ui"/>
<!-- To use multiple models: android:value="face,model2,model3" -->
```

#### Variables

If needed, you can define the following project variable in your app’s `variables.gradle` file to change the default version of the dependency:

- `$androidxCameraCamera2Version` version of `androidx.camera:camera-camera2` (default: `1.5.2`)
- `$androidxCameraCoreVersion` version of `androidx.camera:camera-core` (default: `1.5.2`)
- `$androidxCameraLifecycleVersion` version of `androidx.camera:camera-lifecycle` (default: `1.5.2`)
- `$androidxCameraViewVersion` version of `androidx.camera:camera-view` (default: `1.5.2`)
- `$mlkitBarcodeScanningVersion` version of `com.google.mlkit:barcode-scanning` (default: `17.3.0`)
- `$playServicesCodeScannerVersion` version of `com.google.android.gms:play-services-code-scanner` (default: `16.1.0`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

### iOS

#### Minimum Deployment Target

Make sure to set the deployment target in your `ios/App/Podfile` to at least `15.5`:

```ruby
platform :ios, '15.5'
```

### Usage Description

Add the `NSCameraUsageDescription` key to the `ios/App/App/Info.plist` file, which tells the user why the app needs to use the camera:

```diff
+ <key>NSCameraUsageDescription</key>
+ <string>The app enables the scanning of various barcodes.</string>
```

### Web

#### Polyfill

This plugin uses the [Barcode Detection API](https://developer.mozilla.org/en-US/docs/Web/API/Barcode_Detection_API) to scan barcodes in the browser. This API is not yet supported in all browsers. You can check the compatibility [here](https://caniuse.com/mdn-api_barcodedetector). For this reason, we recommend installing the [barcode-detector](https://www.npmjs.com/package/barcode-detector) package for a better compatibility:

```bash
npm install barcode-detector
```

This package provides a polyfill that uses [ZXing-C++ WebAssembly](https://github.com/Sec-ant/zxing-wasm) under the hood. After installing the package, you just need to import the polyfill in your code:

```js
import "barcode-detector/polyfill";
```

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
import { Torch } from '@capawesome/capacitor-torch';

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
    autoZoom: true,
  });
  return barcodes;
};

const isSupported = async () => {
  const { supported } = await BarcodeScanner.isSupported();
  return supported;
};

const enableTorch = async () => {
  await Torch.enable();
};

const disableTorch = async () => {
  await Torch.disable();
};

const toggleTorch = async () => {
  await Torch.toggle();
};

const isTorchEnabled = async () => {
  const { enabled } = await Torch.isEnabled();
  return enabled;
};

const isTorchAvailable = async () => {
  const { available } = await Torch.isAvailable();
  return available;
};

const setZoomRatio = async () => {
  await BarcodeScanner.setZoomRatio({ zoomRatio: 0.5 });
};

const getZoomRatio = async () => {
  const { zoomRatio } = await BarcodeScanner.getZoomRatio();
  return zoomRatio;
};

const getMinZoomRatio = async () => {
  const { zoomRatio } = await BarcodeScanner.getMinZoomRatio();
  return zoomRatio;
};

const getMaxZoomRatio = async () => {
  const { zoomRatio } = await BarcodeScanner.getMaxZoomRatio();
  return zoomRatio;
};

const openSettings = async () => {
  await BarcodeScanner.openSettings();
};

const isGoogleBarcodeScannerModuleAvailable = async () => {
  const { available } =
    await BarcodeScanner.isGoogleBarcodeScannerModuleAvailable();
  return available;
};

const installGoogleBarcodeScannerModule = async () => {
  await BarcodeScanner.installGoogleBarcodeScannerModule();
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

An example of the CSS class `barcode-scanner-active` **with** Ionic Framework could be:

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

An example of the CSS class `barcode-scanner-active` **without** Ionic Framework could be:

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
* [`setZoomRatio(...)`](#setzoomratio)
* [`getZoomRatio()`](#getzoomratio)
* [`getMinZoomRatio()`](#getminzoomratio)
* [`getMaxZoomRatio()`](#getmaxzoomratio)
* [`openSettings()`](#opensettings)
* [`isGoogleBarcodeScannerModuleAvailable()`](#isgooglebarcodescannermoduleavailable)
* [`installGoogleBarcodeScannerModule()`](#installgooglebarcodescannermodule)
* [`checkPermissions()`](#checkpermissions)
* [`requestPermissions()`](#requestpermissions)
* [`addListener('barcodesScanned', ...)`](#addlistenerbarcodesscanned-)
* [`addListener('scanError', ...)`](#addlistenerscanerror-)
* [`addListener('googleBarcodeScannerModuleInstallProgress', ...)`](#addlistenergooglebarcodescannermoduleinstallprogress-)
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

On **Android**, this method is only available on devices with Google Play Services
installed. Therefore, no camera permission is required.

**Attention:** Before using this method on *Android*, first check if the Google <a href="#barcode">Barcode</a> Scanner module is available
by using `isGoogleBarcodeScannerModuleAvailable()`.

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

**Returns:** <code>Promise&lt;<a href="#issupportedresult">IsSupportedResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### enableTorch()

```typescript
enableTorch() => Promise<void>
```

Enable camera's torch (flash) during a scan session.

Only available on Android and iOS.

**Since:** 7.2.0

--------------------


### disableTorch()

```typescript
disableTorch() => Promise<void>
```

Disable camera's torch (flash) during a scan session.

Only available on Android and iOS.

**Since:** 7.2.0

--------------------


### toggleTorch()

```typescript
toggleTorch() => Promise<void>
```

Toggle camera's torch (flash) during a scan session.

Only available on Android and iOS.

**Since:** 7.2.0

--------------------


### isTorchEnabled()

```typescript
isTorchEnabled() => Promise<IsTorchEnabledResult>
```

Returns whether or not the camera's torch (flash) is enabled.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#istorchenabledresult">IsTorchEnabledResult</a>&gt;</code>

**Since:** 7.2.0

--------------------


### isTorchAvailable()

```typescript
isTorchAvailable() => Promise<IsTorchAvailableResult>
```

Returns whether or not the camera's torch (flash) is available.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#istorchavailableresult">IsTorchAvailableResult</a>&gt;</code>

**Since:** 7.2.0

--------------------


### setZoomRatio(...)

```typescript
setZoomRatio(options: SetZoomRatioOptions) => Promise<void>
```

Set the zoom ratio of the camera.

Only available on Android and iOS.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#setzoomratiooptions">SetZoomRatioOptions</a></code> |

**Since:** 5.4.0

--------------------


### getZoomRatio()

```typescript
getZoomRatio() => Promise<GetZoomRatioResult>
```

Get the zoom ratio of the camera.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getzoomratioresult">GetZoomRatioResult</a>&gt;</code>

**Since:** 5.4.0

--------------------


### getMinZoomRatio()

```typescript
getMinZoomRatio() => Promise<GetMinZoomRatioResult>
```

Get the minimum zoom ratio of the camera.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getminzoomratioresult">GetMinZoomRatioResult</a>&gt;</code>

**Since:** 5.4.0

--------------------


### getMaxZoomRatio()

```typescript
getMaxZoomRatio() => Promise<GetMaxZoomRatioResult>
```

Get the maximum zoom ratio of the camera.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getmaxzoomratioresult">GetMaxZoomRatioResult</a>&gt;</code>

**Since:** 5.4.0

--------------------


### openSettings()

```typescript
openSettings() => Promise<void>
```

Open the settings of the app so that the user can grant the camera permission.

Only available on Android and iOS.

**Since:** 0.0.1

--------------------


### isGoogleBarcodeScannerModuleAvailable()

```typescript
isGoogleBarcodeScannerModuleAvailable() => Promise<IsGoogleBarcodeScannerModuleAvailableResult>
```

Check if the Google <a href="#barcode">Barcode</a> Scanner module is available.

If the Google <a href="#barcode">Barcode</a> Scanner module is not available, you can install it by using `installGoogleBarcodeScannerModule()`.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#isgooglebarcodescannermoduleavailableresult">IsGoogleBarcodeScannerModuleAvailableResult</a>&gt;</code>

**Since:** 5.1.0

--------------------


### installGoogleBarcodeScannerModule()

```typescript
installGoogleBarcodeScannerModule() => Promise<void>
```

Install the Google <a href="#barcode">Barcode</a> Scanner module.

**Attention**: This only starts the installation.
The `googleBarcodeScannerModuleInstallProgress` event listener will
notify you when the installation is complete.

Only available on Android.

**Since:** 5.1.0

--------------------


### checkPermissions()

```typescript
checkPermissions() => Promise<PermissionStatus>
```

Check camera permission.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 0.0.1

--------------------


### requestPermissions()

```typescript
requestPermissions() => Promise<PermissionStatus>
```

Request camera permission.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('barcodesScanned', ...)

```typescript
addListener(eventName: 'barcodesScanned', listenerFunc: (event: BarcodesScannedEvent) => void) => Promise<PluginListenerHandle>
```

Called when barcodes are scanned.

| Param              | Type                                                                                      |
| ------------------ | ----------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'barcodesScanned'</code>                                                            |
| **`listenerFunc`** | <code>(event: <a href="#barcodesscannedevent">BarcodesScannedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 6.2.0

--------------------


### addListener('scanError', ...)

```typescript
addListener(eventName: 'scanError', listenerFunc: (event: ScanErrorEvent) => void) => Promise<PluginListenerHandle>
```

Called when an error occurs during the scan.

Available on Android and iOS.

| Param              | Type                                                                          |
| ------------------ | ----------------------------------------------------------------------------- |
| **`eventName`**    | <code>'scanError'</code>                                                      |
| **`listenerFunc`** | <code>(event: <a href="#scanerrorevent">ScanErrorEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('googleBarcodeScannerModuleInstallProgress', ...)

```typescript
addListener(eventName: 'googleBarcodeScannerModuleInstallProgress', listenerFunc: (event: GoogleBarcodeScannerModuleInstallProgressEvent) => void) => Promise<PluginListenerHandle>
```

Called when the Google <a href="#barcode">Barcode</a> Scanner module is installed.

Available on Android.

| Param              | Type                                                                                                                                          |
| ------------------ | --------------------------------------------------------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'googleBarcodeScannerModuleInstallProgress'</code>                                                                                      |
| **`listenerFunc`** | <code>(event: <a href="#googlebarcodescannermoduleinstallprogressevent">GoogleBarcodeScannerModuleInstallProgressEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 5.1.0

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

| Prop                                 | Type                                              | Description                                                                                                                                                                                                           | Default                             | Since |
| ------------------------------------ | ------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------- | ----- |
| **`formats`**                        | <code>BarcodeFormat[]</code>                      | Improve the speed of the barcode scanner by configuring the barcode formats to scan for. Only available on Android and iOS.                                                                                           |                                     | 0.0.1 |
| **`lensFacing`**                     | <code><a href="#lensfacing">LensFacing</a></code> | Configure the camera (front or back) to use.                                                                                                                                                                          |                                     | 0.0.1 |
| **`resolution`**                     | <code><a href="#resolution">Resolution</a></code> | Configure the resolution of the captured image that is used for barcode scanning. If the resolution is not supported by the device, the closest supported resolution will be used. Only available on Android and iOS. | <code>Resolution['1280x720']</code> | 7.0.0 |
| **`enableMultitaskingCameraAccess`** | <code>boolean</code>                              | Allow camera usage on iPad while in multitasking mode. Only available on iOS (16.0+).                                                                                                                                 | <code>false</code>                  | 7.5.0 |
| **`videoElement`**                   | <code>HTMLVideoElement</code>                     | The HTML video element to use for the camera preview. Only available on web.                                                                                                                                          |                                     | 7.1.0 |


#### ReadBarcodesFromImageResult

| Prop           | Type                   | Description            | Since |
| -------------- | ---------------------- | ---------------------- | ----- |
| **`barcodes`** | <code>Barcode[]</code> | The detected barcodes. | 0.0.1 |


#### Barcode

| Prop                | Type                                                                                  | Description                                                                                                                                                                        | Since |
| ------------------- | ------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`bytes`**         | <code>number[]</code>                                                                 | Raw bytes as it was encoded in the barcode.                                                                                                                                        | 0.0.1 |
| **`calendarEvent`** | <code><a href="#barcodecalendarevent">BarcodeCalendarEvent</a></code>                 | Calendar event info.                                                                                                                                                               | 7.0.0 |
| **`contactInfo`**   | <code><a href="#barcodecontactinfo">BarcodeContactInfo</a></code>                     | Person's or organization's business card.                                                                                                                                          | 7.0.0 |
| **`cornerPoints`**  | <code>[[number, number], [number, number], [number, number], [number, number]]</code> | The four corner points of the barcode in clockwise order starting with top-left. This property is currently only supported by the `startScan(...)` method.                         | 0.0.1 |
| **`displayValue`**  | <code>string</code>                                                                   | The barcode value in a human readable format.                                                                                                                                      | 0.0.1 |
| **`driverLicense`** | <code><a href="#barcodedriverlicense">BarcodeDriverLicense</a></code>                 | Driver license or ID card.                                                                                                                                                         | 7.0.0 |
| **`email`**         | <code><a href="#barcodeemail">BarcodeEmail</a></code>                                 | An email message from a 'MAILTO:'.                                                                                                                                                 | 7.0.0 |
| **`format`**        | <code><a href="#barcodeformat">BarcodeFormat</a></code>                               | The barcode format.                                                                                                                                                                | 0.0.1 |
| **`geoPoint`**      | <code><a href="#barcodegeopoint">BarcodeGeoPoint</a></code>                           | GPS coordinates from a 'GEO:'.                                                                                                                                                     | 7.0.0 |
| **`phone`**         | <code><a href="#barcodephone">BarcodePhone</a></code>                                 | Phone number info.                                                                                                                                                                 | 7.0.0 |
| **`rawValue`**      | <code>string</code>                                                                   | The barcode value in a machine readable format. This value is only available if the barcode is encoded in the UTF-8 character set. Otherwise, the `bytes` property should be used. | 0.0.1 |
| **`sms`**           | <code><a href="#barcodesms">BarcodeSms</a></code>                                     | A sms message from a 'SMS:'.                                                                                                                                                       | 7.0.0 |
| **`urlBookmark`**   | <code><a href="#barcodeurlbookmark">BarcodeUrlBookmark</a></code>                     | A URL and title from a 'MEBKM:'.                                                                                                                                                   | 7.0.0 |
| **`valueType`**     | <code><a href="#barcodevaluetype">BarcodeValueType</a></code>                         | The barcode value type.                                                                                                                                                            | 0.0.1 |
| **`wifi`**          | <code><a href="#barcodewifi">BarcodeWifi</a></code>                                   | A wifi network parameters from a 'WIFI:'.                                                                                                                                          | 7.0.0 |


#### BarcodeCalendarEvent

| Prop              | Type                | Description                              | Since |
| ----------------- | ------------------- | ---------------------------------------- | ----- |
| **`description`** | <code>string</code> | The event description.                   | 7.0.0 |
| **`end`**         | <code>string</code> | The event end date as ISO 8601 string.   | 7.0.0 |
| **`location`**    | <code>string</code> | The event location.                      | 7.0.0 |
| **`organizer`**   | <code>string</code> | The event organizer.                     | 7.0.0 |
| **`start`**       | <code>string</code> | The event start date as ISO 8601 string. | 7.0.0 |
| **`status`**      | <code>string</code> | The event status.                        | 7.0.0 |
| **`summary`**     | <code>string</code> | The event summary.                       | 7.0.0 |


#### BarcodeContactInfo

| Prop               | Type                                              | Description                 | Since |
| ------------------ | ------------------------------------------------- | --------------------------- | ----- |
| **`addresses`**    | <code>Address[]</code>                            | The contact's addresses.    | 7.0.0 |
| **`emails`**       | <code>BarcodeEmail[]</code>                       | The contact's emails.       | 7.0.0 |
| **`personName`**   | <code><a href="#personname">PersonName</a></code> | The contact's name.         | 7.0.0 |
| **`organization`** | <code>string</code>                               | The contact's organization. | 7.0.0 |
| **`phones`**       | <code>BarcodePhone[]</code>                       | The contact's phones.       | 7.0.0 |
| **`title`**        | <code>string</code>                               | The contact's title.        | 7.0.0 |
| **`urls`**         | <code>string[]</code>                             | The contact's urls.         | 7.0.0 |


#### Address

| Prop               | Type                                                | Description                                         | Since |
| ------------------ | --------------------------------------------------- | --------------------------------------------------- | ----- |
| **`addressLines`** | <code>string[]</code>                               | Formatted address, multiple lines when appropriate. | 7.0.0 |
| **`type`**         | <code><a href="#addresstype">AddressType</a></code> | <a href="#address">Address</a> type.                | 7.0.0 |


#### BarcodeEmail

| Prop          | Type                                                        | Description             | Since |
| ------------- | ----------------------------------------------------------- | ----------------------- | ----- |
| **`address`** | <code>string</code>                                         | The email address.      | 7.0.0 |
| **`body`**    | <code>string</code>                                         | The email body.         | 7.0.0 |
| **`subject`** | <code>string</code>                                         | The email subject.      | 7.0.0 |
| **`type`**    | <code><a href="#emailformattype">EmailFormatType</a></code> | The email address type. | 7.0.0 |


#### PersonName

| Prop                | Type                | Description                                                                          | Since |
| ------------------- | ------------------- | ------------------------------------------------------------------------------------ | ----- |
| **`first`**         | <code>string</code> | First name.                                                                          | 7.0.0 |
| **`formattedName`** | <code>string</code> | The formatted name.                                                                  | 7.0.0 |
| **`last`**          | <code>string</code> | Last name.                                                                           | 7.0.0 |
| **`middle`**        | <code>string</code> | Middle name.                                                                         | 7.0.0 |
| **`prefix`**        | <code>string</code> | Name prefix.                                                                         | 7.0.0 |
| **`pronunciation`** | <code>string</code> | Text string to be set as the kana name in the phonebook. Used for Japanese contacts. | 7.0.0 |
| **`suffix`**        | <code>string</code> | Name suffix.                                                                         | 7.0.0 |


#### BarcodePhone

| Prop         | Type                                                        | Description            | Since |
| ------------ | ----------------------------------------------------------- | ---------------------- | ----- |
| **`number`** | <code>string</code>                                         | The phone number.      | 7.0.0 |
| **`type`**   | <code><a href="#phoneformattype">PhoneFormatType</a></code> | The phone number type. | 7.0.0 |


#### BarcodeDriverLicense

| Prop                 | Type                | Description                                        | Since |
| -------------------- | ------------------- | -------------------------------------------------- | ----- |
| **`addressCity`**    | <code>string</code> | City of holder's address.                          | 7.0.0 |
| **`addressState`**   | <code>string</code> | State of holder's address.                         | 7.0.0 |
| **`addressStreet`**  | <code>string</code> | Street of holder's address.                        | 7.0.0 |
| **`addressZip`**     | <code>string</code> | Postal code of holder's address.                   | 7.0.0 |
| **`birthDate`**      | <code>string</code> | Birthdate of the holder.                           | 7.0.0 |
| **`documentType`**   | <code>string</code> | "DL" for driver's licenses, "ID" for ID cards.     | 7.0.0 |
| **`expiryDate`**     | <code>string</code> | Expiration date of the license.                    | 7.0.0 |
| **`firstName`**      | <code>string</code> | Holder's first name.                               | 7.0.0 |
| **`gender`**         | <code>string</code> | Holder's gender.                                   | 7.0.0 |
| **`issueDate`**      | <code>string</code> | Issue date of the license.                         | 7.0.0 |
| **`issuingCountry`** | <code>string</code> | ISO 3166-1 alpha-3 code in which DL/ID was issued. | 7.0.0 |
| **`lastName`**       | <code>string</code> | Holder's last name.                                | 7.0.0 |
| **`licenseNumber`**  | <code>string</code> | Driver license ID number.                          | 7.0.0 |
| **`middleName`**     | <code>string</code> | Holder's middle name.                              | 7.0.0 |


#### BarcodeGeoPoint

| Prop            | Type                | Description | Since |
| --------------- | ------------------- | ----------- | ----- |
| **`latitude`**  | <code>number</code> | Latitude.   | 7.0.0 |
| **`longitude`** | <code>number</code> | Longitude.  | 7.0.0 |


#### BarcodeSms

| Prop              | Type                | Description                     | Since |
| ----------------- | ------------------- | ------------------------------- | ----- |
| **`phoneNumber`** | <code>string</code> | The phone number of the sms.    | 7.0.0 |
| **`message`**     | <code>string</code> | The message content of the sms. | 7.0.0 |


#### BarcodeUrlBookmark

| Prop        | Type                | Description                | Since |
| ----------- | ------------------- | -------------------------- | ----- |
| **`url`**   | <code>string</code> | The URL of the bookmark.   | 7.0.0 |
| **`title`** | <code>string</code> | The title of the bookmark. | 7.0.0 |


#### BarcodeWifi

| Prop                 | Type                                                              | Description                   | Since |
| -------------------- | ----------------------------------------------------------------- | ----------------------------- | ----- |
| **`encryptionType`** | <code><a href="#wifiencryptiontype">WifiEncryptionType</a></code> | Encryption type of the WI-FI. | 7.0.0 |
| **`password`**       | <code>string</code>                                               | Password of the WI-FI.        | 7.0.0 |
| **`ssid`**           | <code>string</code>                                               | SSID of the WI-FI.            | 7.0.0 |


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

| Prop           | Type                         | Description                                                                              | Since |
| -------------- | ---------------------------- | ---------------------------------------------------------------------------------------- | ----- |
| **`formats`**  | <code>BarcodeFormat[]</code> | Improve the speed of the barcode scanner by configuring the barcode formats to scan for. | 0.0.1 |
| **`autoZoom`** | <code>boolean</code>         | Toggle the auto zoom feature.                                                            | 7.4.0 |


#### IsSupportedResult

| Prop            | Type                 | Description                                                                             | Since |
| --------------- | -------------------- | --------------------------------------------------------------------------------------- | ----- |
| **`supported`** | <code>boolean</code> | Whether or not the barcode scanner is supported by checking if the device has a camera. | 0.0.1 |


#### IsTorchEnabledResult

| Prop          | Type                 | Description                          | Since |
| ------------- | -------------------- | ------------------------------------ | ----- |
| **`enabled`** | <code>boolean</code> | Whether or not the torch is enabled. | 0.0.1 |


#### IsTorchAvailableResult

| Prop            | Type                 | Description                            | Since |
| --------------- | -------------------- | -------------------------------------- | ----- |
| **`available`** | <code>boolean</code> | Whether or not the torch is available. | 0.0.1 |


#### SetZoomRatioOptions

| Prop            | Type                | Description            | Since |
| --------------- | ------------------- | ---------------------- | ----- |
| **`zoomRatio`** | <code>number</code> | The zoom ratio to set. | 5.4.0 |


#### GetZoomRatioResult

| Prop            | Type                | Description     | Since |
| --------------- | ------------------- | --------------- | ----- |
| **`zoomRatio`** | <code>number</code> | The zoom ratio. | 5.4.0 |


#### GetMinZoomRatioResult

| Prop            | Type                | Description             | Since |
| --------------- | ------------------- | ----------------------- | ----- |
| **`zoomRatio`** | <code>number</code> | The minimum zoom ratio. | 5.4.0 |


#### GetMaxZoomRatioResult

| Prop            | Type                | Description             | Since |
| --------------- | ------------------- | ----------------------- | ----- |
| **`zoomRatio`** | <code>number</code> | The maximum zoom ratio. | 5.4.0 |


#### IsGoogleBarcodeScannerModuleAvailableResult

| Prop            | Type                 | Description                                                                           | Since |
| --------------- | -------------------- | ------------------------------------------------------------------------------------- | ----- |
| **`available`** | <code>boolean</code> | Whether or not the Google <a href="#barcode">Barcode</a> Scanner module is available. | 5.1.0 |


#### PermissionStatus

| Prop         | Type                                                                    | Since |
| ------------ | ----------------------------------------------------------------------- | ----- |
| **`camera`** | <code><a href="#camerapermissionstate">CameraPermissionState</a></code> | 0.0.1 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### BarcodesScannedEvent

| Prop           | Type                   | Description            | Since |
| -------------- | ---------------------- | ---------------------- | ----- |
| **`barcodes`** | <code>Barcode[]</code> | The detected barcodes. | 6.2.0 |


#### ScanErrorEvent

| Prop          | Type                | Description        | Since |
| ------------- | ------------------- | ------------------ | ----- |
| **`message`** | <code>string</code> | The error message. | 0.0.1 |


#### GoogleBarcodeScannerModuleInstallProgressEvent

| Prop           | Type                                                                                                      | Description                                                    | Since |
| -------------- | --------------------------------------------------------------------------------------------------------- | -------------------------------------------------------------- | ----- |
| **`state`**    | <code><a href="#googlebarcodescannermoduleinstallstate">GoogleBarcodeScannerModuleInstallState</a></code> | The current state of the installation.                         | 5.1.0 |
| **`progress`** | <code>number</code>                                                                                       | The progress of the installation in percent between 0 and 100. | 5.1.0 |


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


#### Resolution

| Members           | Value          | Since |
| ----------------- | -------------- | ----- |
| **`'640x480'`**   | <code>0</code> | 7.0.0 |
| **`'1280x720'`**  | <code>1</code> | 7.0.0 |
| **`'1920x1080'`** | <code>2</code> | 7.0.0 |
| **`'3840x2160'`** | <code>3</code> | 7.2.0 |


#### AddressType

| Members       | Value          | Since |
| ------------- | -------------- | ----- |
| **`HOME`**    | <code>0</code> | 7.0.0 |
| **`UNKNOWN`** | <code>1</code> | 7.0.0 |
| **`WORK`**    | <code>2</code> | 7.0.0 |


#### EmailFormatType

| Members       | Value          | Since |
| ------------- | -------------- | ----- |
| **`HOME`**    | <code>0</code> | 7.0.0 |
| **`UNKNOWN`** | <code>1</code> | 7.0.0 |
| **`WORK`**    | <code>2</code> | 7.0.0 |


#### PhoneFormatType

| Members       | Value          | Since |
| ------------- | -------------- | ----- |
| **`FAX`**     | <code>0</code> | 7.0.0 |
| **`HOME`**    | <code>1</code> | 7.0.0 |
| **`MOBILE`**  | <code>2</code> | 7.0.0 |
| **`UNKNOWN`** | <code>3</code> | 7.0.0 |
| **`WORK`**    | <code>4</code> | 7.0.0 |


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


#### WifiEncryptionType

| Members    | Value          | Since |
| ---------- | -------------- | ----- |
| **`OPEN`** | <code>1</code> | 7.0.0 |
| **`WEP`**  | <code>2</code> | 7.0.0 |
| **`WPA`**  | <code>3</code> | 7.0.0 |


#### GoogleBarcodeScannerModuleInstallState

| Members               | Value          | Since |
| --------------------- | -------------- | ----- |
| **`UNKNOWN`**         | <code>0</code> | 5.1.0 |
| **`PENDING`**         | <code>1</code> | 5.1.0 |
| **`DOWNLOADING`**     | <code>2</code> | 5.1.0 |
| **`CANCELED`**        | <code>3</code> | 5.1.0 |
| **`COMPLETED`**       | <code>4</code> | 5.1.0 |
| **`FAILED`**          | <code>5</code> | 5.1.0 |
| **`INSTALLING`**      | <code>6</code> | 5.1.0 |
| **`DOWNLOAD_PAUSED`** | <code>7</code> | 5.1.0 |

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
