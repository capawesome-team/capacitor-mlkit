#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

// Define the plugin using the CAP_PLUGIN Macro, and
// each method the plugin supports using the CAP_PLUGIN_METHOD macro.
CAP_PLUGIN(TranslationPlugin, "Translation",
           CAP_PLUGIN_METHOD(deleteDownloadedModel, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(downloadModel, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getDownloadedModels, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(translate, CAPPluginReturnPromise);
)
