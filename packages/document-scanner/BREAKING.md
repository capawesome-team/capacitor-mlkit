# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases.

## Versions

- [Version 8.x.x](#version-8xx)

## Version 8.x.x

### Capacitor 8

This plugin now supports **Capacitor 8**. The minimum Android SDK version is **36**. Ensure your project meets these requirements before upgrading.

### Error code

On **iOS**, the `scanDocument` method now throws an error with code `Unimplemented` instead of a generic error message.

### Variables

- On Android, the `mlkitDocumentScannerVersion` variable has been updated to `16.0.0`.
