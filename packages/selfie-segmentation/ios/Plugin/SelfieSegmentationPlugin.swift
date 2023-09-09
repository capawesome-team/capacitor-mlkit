import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(SelfieSegmentationPlugin)
public class SelfieSegmentationPlugin: CAPPlugin {
    public let tag = "SelfieSegmentation"
    public let errorPathMissing = "path must be provided."
    public let errorLoadImageFailed = "image could not be loaded."

    private var implementation: SelfieSegmentation?

    override public func load() {
        implementation = SelfieSegmentation(plugin: self)
    }

    @objc func processImage(_ call: CAPPluginCall) {
        guard let path = call.getString("path") else {
            call.reject(errorPathMissing)
            return
        }

        guard let visionImage = implementation?.createVisionImageFromFilePath(path) else {
            call.reject(errorLoadImageFailed)
            return
        }

        let options = ProcessImageOptions(visionImage: visionImage)

        implementation?.processImage(options, completion: { result, error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription, nil, error)
                return
            }
            if let result = result?.toJSObject() as? JSObject {
                call.resolve(result)
            }
        })
    }
}
