import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(DigitalInkRecognitionPlugin)
public class DigitalInkRecognitionPlugin: CAPPlugin {

    private var implementation: DigitalInkRecognition?

    override public func load() {
        implementation = DigitalInkRecognition()
    }

    @objc func echo(_ call: CAPPluginCall) {
        implementation?.echo()
        call.resolve()
    }
}
