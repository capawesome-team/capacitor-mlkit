import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FaceDetectionPlugin)
public class FaceDetectionPlugin: CAPPlugin {

    private var implementation: FaceDetection?

    override public func load() {
        implementation = FaceDetection()
    }

    @objc func processImage(_ call: CAPPluginCall) {
        implementation?.processImage()
        call.resolve()
    }
}
