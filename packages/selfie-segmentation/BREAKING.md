# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases.

## Versions

- [Version 8.x.x](#version-8xx)
- [Version 7.x.x](#version-7xx)
- [Version 6.x.x](#version-6xx)

## Version 8.x.x

### Capacitor 8

This plugin now supports **Capacitor 8**. The minimum Android SDK version is **36**. Ensure your project meets these requirements before upgrading.

## Version 7.x.x

### Variables

- On Android, the `mlkitSelfieSegmentationVersion` variable has been updated to `16.0.0-beta6`.

### Minimum Deployment Target

On **iOS**, make sure to set the deployment target in your `ios/App/Podfile` to at least `15.5`:

```ruby
platform :ios, '15.5'
```

## Version 6.x.x

### Variables

- On Android, the `mlkitSelfieSegmentationVersion` variable has been updated to `16.0.0-beta5`.
