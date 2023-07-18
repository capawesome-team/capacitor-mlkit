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
    public let errorPathMissing = "path must be provided."
    public let errorLoadImageFailed = "image could not be loaded."

    private var implementation: FaceDetection?

    override public func load() {
        implementation = FaceDetection(plugin: self)
    }

    @objc func processImage(_ call: CAPPluginCall) {
        // An image or image buffer used for vision detection.
        let image: VisionImage
        // Options for specifying a face detector.
        let options: FaceDetectorOptions = FaceDetectorOptions()

        guard let path = call.getString("path") else {
            call.reject(errorPathMissing)
            return
        }
        guard let url = implementation?.getFileUrlByPath(path) else {
            call.reject(errorLoadImageFailed)
            return
        }

        // Initializes a `VisionImage` object with the given image.
        image = VisionImage.init(
            // Image to use in vision detection.
            image: UIImage.init(contentsOfFile: url.path)!
        )

        if let performanceMode = call.getInt("performanceMode") {
            // The face detector performance mode that determines the accuracy of the results and the speed of the detection.
            options.performanceMode = FaceDetectorPerformanceMode(rawValue: performanceMode)
        }

        if let landmarkMode = call.getInt("landmarkMode") {
            // The face detector landmark mode that determines the type of landmark results returned by detection.
            options.landmarkMode = FaceDetectorLandmarkMode(rawValue: landmarkMode)
        }
        if let contourMode = call.getInt("contourMode") {
            // The face detector contour mode that determines the type of contour results returned by detection.
            options.contourMode = FaceDetectorContourMode(rawValue: contourMode)
        }

        if let classificationMode = call.getInt("classificationMode") {
            // The face detector classification mode for characterizing attributes such as smiling.
            options.classificationMode = FaceDetectorClassificationMode(rawValue: classificationMode)
        }

        if let minFaceSize = call.getFloat("minFaceSize") {
            // The smallest desired face size.
            // The size is expressed as a proportion of the width of the head to the image width.
            options.minFaceSize = CGFloat(minFaceSize)
        }

        if let enableTracking = call.getBool("enableTracking") {
            // Whether the face tracking feature is enabled for face detection.
            options.isTrackingEnabled = enableTracking
        }

        implementation?.processImage(image, options, completion: { faces, error in
            if let error = error {
                call.reject(error.localizedDescription, nil, error)
                return
            }
            call.resolve([
                "faces": faces as Any
            ])
        })
    }
}
