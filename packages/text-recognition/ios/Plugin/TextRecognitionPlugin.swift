import Foundation
import Capacitor
import MLKitVision
import MLKitTextRecognition

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(TextRecognitionPlugin)
public class TextRecognitionPlugin: CAPPlugin {
    public let tag = "TextRecognition"

    private var implementation: TextRecognition?

    override public func load() {
        implementation = TextRecognition(plugin: self)
    }

    @objc func processImage(_ call: CAPPluginCall) {
        do {
            let options = try ProcessImageOptions(call)

            implementation?.processImage(options, completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            })
        } catch {
            self.rejectCall(call, error)
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", self.tag, "] ", error)
        call.reject(error.localizedDescription)
    }

    private func resolveCall(_ call: CAPPluginCall, _ result: Result?) {
        if let result = result?.toJSObject() as? JSObject {
            call.resolve(result)
        } else {
            call.resolve()
        }
    }
}
