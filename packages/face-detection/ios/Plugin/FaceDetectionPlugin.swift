import Foundation
import Capacitor
import MLKitVision
import MLKitFaceDetection

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FaceDetectionPlugin)
public class FaceDetectionPlugin: CAPPlugin {
    public let tag = "FaceDetection"
    public let errorPathMissing = "path must be provided."
    public let errorLoadImageFailed = "image could not be loaded."

    private var implementation: FaceDetection?

    override public func load() {
        implementation = FaceDetection(plugin: self)
    }

    @objc func processImage(_ call: CAPPluginCall) {
        guard let path = call.getString("path") else {
            call.reject(errorPathMissing)
            return
        }
        let performanceMode = call.getInt("performanceMode", FaceDetectorPerformanceMode.fast.rawValue)
        let landmarkMode = call.getInt("landmarkMode", FaceDetectorLandmarkMode.none.rawValue)
        let contourMode = call.getInt("contourMode", FaceDetectorContourMode.none.rawValue)
        let classificationMode = call.getInt("classificationMode", FaceDetectorClassificationMode.none.rawValue)
        let minFaceSize = call.getFloat("minFaceSize", 0.1)
        let enableTracking = call.getBool("enableTracking", false)

        guard let visionImage = implementation?.createVisionImageFromFilePath(path) else {
            call.reject(errorLoadImageFailed)
            return
        }

        let options = ProcessImageOptions(visionImage: visionImage, performanceMode: performanceMode, landmarkMode: landmarkMode,
                                          contourMode: contourMode, classificationMode: classificationMode, minFaceSize: minFaceSize, enableTracking: enableTracking)

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
