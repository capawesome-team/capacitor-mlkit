#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

// Define the plugin using the CAP_PLUGIN Macro, and
// each method the plugin supports using the CAP_PLUGIN_METHOD macro.
CAP_PLUGIN(BarcodeScannerPlugin, "BarcodeScanner",
           CAP_PLUGIN_METHOD(startScan, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(stopScan, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(readBarcodesFromImage, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(scan, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(isSupported, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(setZoomRatio, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getZoomRatio, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getMinZoomRatio, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getMaxZoomRatio, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(openSettings, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(isGoogleBarcodeScannerModuleAvailable, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(installGoogleBarcodeScannerModule, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(checkPermissions, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(requestPermissions, CAPPluginReturnPromise);
)
