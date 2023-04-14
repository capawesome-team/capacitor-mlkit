# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases of Capacitor ML Kit Barcode Scanning plugin.

- [Version 5.x.x](#version-5xx)

## Version 5.x.x

### Capacitor 5

This plugin now supports Capacitor 5 only. Please run `npx cap sync` after updating this package.

If you want to use this plugin with Capacitor 4, please install version `0.0.3`:

```
npm i @capacitor-mlkit/barcode-scanning@0.0.3
```

### Corner Points

The calculation of the corner points has been improved. The coordinates are now multiplied by the pixel ratio of the device.