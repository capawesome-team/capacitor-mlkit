import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FaceMeshDetectionPlugin)
public class FaceMeshDetectionPlugin: CAPPlugin {
    private let implementation = FaceMeshDetection()

    @objc func processImage(_ call: CAPPluginCall) {
        call.reject("Not available on iOS")
    }
}
