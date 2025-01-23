# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases of Capacitor ML Kit Translation plugin.

- [Version 7.x.x](#version-7xx)
- [Version 6.x.x](#version-6xx)
- [Version 5.x.x](#version-5xx)

## Version 7.x.x

### Variables

- On Android, the `mlkitTranslateVersion` variable has been updated to `17.0.3`.

### Minimum Deployment Target

On **iOS**, make sure to set the deployment target in your `ios/App/Podfile` to at least `15.5`:

```ruby
platform :ios, '15.5'
```

## Version 6.x.x

### Variables

- On Android, the `mlkitTranslateVersion` variable has been updated to `17.0.2`.

## Version 5.x.x

### Capacitor 5

This plugin now supports Capacitor 5 only. Please run `npx cap sync` after updating this package.

If you want to use this plugin with Capacitor 4, please install version `0.0.3`:

```
npm i @capacitor-mlkit/translation@0.0.3
```
