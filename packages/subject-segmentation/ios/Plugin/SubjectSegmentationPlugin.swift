import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(SubjectSegmentationPlugin)
public class SubjectSegmentationPlugin: CAPPlugin {
    private let implementation = SubjectSegmentation()

    @objc func processImage(_ call: CAPPluginCall) {
        call.reject("Not available on iOS")
    }

    @objc func isGoogleSubjectSegmentationModuleAvailable(_ call: CAPPluginCall) {
        call.reject("Not available on iOS")
    }

    @objc func installGoogleSubjectSegmentationModule(_ call: CAPPluginCall) {
        call.reject("Not available on iOS")
    }
}
