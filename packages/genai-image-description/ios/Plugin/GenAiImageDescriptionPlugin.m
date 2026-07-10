#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

// Define the plugin using the CAP_PLUGIN Macro, and
// each method the plugin supports using the CAP_PLUGIN_METHOD macro.
CAP_PLUGIN(GenAiImageDescriptionPlugin, "GenAiImageDescription",
           CAP_PLUGIN_METHOD(checkFeatureStatus, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(describeImage, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(downloadFeature, CAPPluginReturnPromise);
)
