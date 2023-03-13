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
           CAP_PLUGIN_METHOD(enableTorch, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(disableTorch, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(toggleTorch, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(isTorchEnabled, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(isTorchAvailable, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(openSettings, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(checkPermissions, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(requestPermissions, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(removeAllListeners, CAPPluginReturnPromise);
)
