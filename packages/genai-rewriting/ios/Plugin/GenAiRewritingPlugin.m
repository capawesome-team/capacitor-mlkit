#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

// Define the plugin using the CAP_PLUGIN Macro, and
// each method the plugin supports using the CAP_PLUGIN_METHOD macro.
CAP_PLUGIN(GenAiRewritingPlugin, "GenAiRewriting",
           CAP_PLUGIN_METHOD(checkFeatureStatus, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(downloadFeature, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(rewrite, CAPPluginReturnPromise);
)
