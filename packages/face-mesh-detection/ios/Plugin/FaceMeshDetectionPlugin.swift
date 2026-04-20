import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FaceMeshDetectionPlugin)
public class FaceMeshDetectionPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "FaceMeshDetectionPlugin"
    public let jsName = "FaceMeshDetection"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "processImage", returnType: CAPPluginReturnPromise)
    ]
    private let implementation = FaceMeshDetection()

    @objc func processImage(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    private func rejectCallAsUnavailable(_ call: CAPPluginCall) {
        call.unavailable("This method is not available on this platform.")
    }

    private func rejectCallAsUnimplemented(_ call: CAPPluginCall) {
        call.unimplemented("This method is not available on this platform.")
    }
}
