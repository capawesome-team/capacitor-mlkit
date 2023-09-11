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
    public let errorLoadImageFailed = "The image could not be loaded."
    public let errorWriteFileFailed = "The result could not be created."

    public let defaultConfidence: Float = 0.9

    private var implementation: SelfieSegmentation?

    override public func load() {
        implementation = SelfieSegmentation(plugin: self)
    }

    @objc func processImage(_ call: CAPPluginCall) {
        guard let path = call.getString("path") else {
            call.reject(errorPathMissing)
            return
        }

        let width = call.getInt("width")
        let height = call.getInt("height")

        let confidence = call.getFloat("confidence", defaultConfidence)

        guard let image = implementation?.createImageFromFilePath(path) else {
            call.reject(errorLoadImageFailed)
            return
        }

        let options = ProcessImageOptions(image: image,
                                          width: width,
                                          height: height,
                                          confidence: CGFloat(confidence))

        implementation?.processImage(options, completion: { result, error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription, nil, error)
                return
            }

            if let result = result {
                do {
                    call.resolve(try result.toJSObject())
                } catch {
                    call.reject(self.errorWriteFileFailed)
                }
            }
        })
    }
}
