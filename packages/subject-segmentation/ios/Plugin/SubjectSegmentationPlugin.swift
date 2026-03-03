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
        rejectCallAsUnimplemented(call)
    }

    @objc func isGoogleSubjectSegmentationModuleAvailable(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc func installGoogleSubjectSegmentationModule(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    private func rejectCallAsUnavailable(_ call: CAPPluginCall) {
        call.unavailable("This method is not available on this platform.")
    }

    private func rejectCallAsUnimplemented(_ call: CAPPluginCall) {
        call.unimplemented("This method is not available on this platform.")
    }
}
