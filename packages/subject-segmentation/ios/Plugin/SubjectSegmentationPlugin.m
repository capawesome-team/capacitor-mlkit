#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

// Define the plugin using the CAP_PLUGIN Macro, and
// each method the plugin supports using the CAP_PLUGIN_METHOD macro.
CAP_PLUGIN(SubjectSegmentationPlugin, "SubjectSegmentation",
           CAP_PLUGIN_METHOD(processImage, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(isGoogleSubjectSegmentationModuleAvailable, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(installGoogleSubjectSegmentationModule, CAPPluginReturnPromise);
)
