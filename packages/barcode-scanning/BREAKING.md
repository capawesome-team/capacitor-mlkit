# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases.

## Versions

- [Version 8.x.x](#version-8xx)
- [Version 7.x.x](#version-7xx)
- [Version 6.x.x](#version-6xx)
- [Version 5.x.x](#version-5xx)

## Version 8.x.x

### Capacitor 8

This plugin now supports **Capacitor 8**. The minimum Android SDK version is **36**. Ensure your project meets these requirements before upgrading.

### Variables

- On Android, the `androidxCameraCamera2Version` variable has been updated to `1.5.2`.
- On Android, the `androidxCameraCoreVersion` variable has been updated to `1.5.2`.
- On Android, the `androidxCameraLifecycleVersion` variable has been updated to `1.5.2`.
- On Android, the `androidxCameraViewVersion` variable has been updated to `1.5.2`.

### `rawValue` property

The `rawValue` property in the `Barcode` interface is now optional since a raw value is only available if the barcode is encoded in the UTF-8 character set. Previously, if the barcode was encoded in a different character set, the `rawValue` property was an empty string.

## Version 7.x.x

### Variables

- On Android, the `mlkitBarcodeScanningVersion` variable has been updated to `17.3.0`.

### Minimum Deployment Target

On **iOS**, make sure to set the deployment target in your `ios/App/Podfile` to at least `15.5`:

```ruby
platform :ios, '15.5'
```

### Image Resolution

on **Android**, the image resolution used for barcode scanning has been increased to `1280x720` to improve the barcode scanning performance and be consistent with the iOS implementation. Previously, the resolution was `640x480`. You can change the resolution by setting the `resolution` option when calling the `startScan` method.

```typescript
import { BarcodeScanner, Resolution } from '@capacitor-mlkit/barcode-scanning';

const startScan = async () => {
  await BarcodeScanner.startScan({
    resolution: Resolution['640x480'],
  });
};
```

### Torch support

All the methods related to the torch have been moved into a separate [Torch](https://capawesome.io/plugins/torch/) plugin. If you want to use the torch, just install the `@capawesome/capacitor-torch` package:

```bash
npm install @capawesome/capacitor-torch
npx cap sync
```

### `barcodeScanned` event

The `barcodeScanned` event has been replaced by the `barcodesScanned` event. The event payload now contains an array of `Barcode` objects instead of a single `Barcode` object since multiple barcodes can be scanned at the same time.

## Version 6.x.x

### Variables

- On Android, the `mlkitBarcodeScanningVersion` variable has been updated to `17.2.0`.
- On Android, the `playServicesCodeScannerVersion` variable has been updated to `16.1.0`.

## Version 5.x.x

### Capacitor 5

This plugin now supports Capacitor 5 only. Please run `npx cap sync` after updating this package.

If you want to use this plugin with Capacitor 4, please install version `0.0.3`:

```
npm i @capacitor-mlkit/barcode-scanning@0.0.3
```

### Corner Points

The calculation of the corner points has been improved. The coordinates are now multiplied by the pixel ratio of the device.
