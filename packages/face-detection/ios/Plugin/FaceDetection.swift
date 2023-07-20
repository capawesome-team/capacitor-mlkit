import Foundation
import MLKitVision
import MLKitFaceDetection

@objc public class FaceDetection: NSObject {
    public let plugin: FaceDetectionPlugin

    init(plugin: FaceDetectionPlugin) {
        self.plugin = plugin
    }

    @objc func createVisionImageFromFilePath(_ path: String) -> VisionImage? {
        guard let url = URL.init(string: path) else {
            return nil
        }
        if FileManager.default.fileExists(atPath: url.path) {
            guard let image = UIImage.init(contentsOfFile: url.path) else {
                return nil
            }
            return VisionImage.init(
                image: image
            )
        } else {
            return nil
        }
    }

    @objc func processImage(_ options: ProcessImageOptions, completion: @escaping (ProcessImageResult?, Error?) -> Void) {
        let visionImage = options.getVisionImage()
        let performanceMode = options.getPerformanceMode()
        let landmarkMode = options.getLandmarkMode()
        let contourMode = options.getContourMode()
        let classificationMode = options.getClassificationMode()
        let minFaceSize = options.getMinFaceSize()
        let enableTracking = options.isTrackingEnabled()

        let faceDetectorOptions: FaceDetectorOptions = FaceDetectorOptions()
        faceDetectorOptions.performanceMode = FaceDetectorPerformanceMode(rawValue: performanceMode)
        faceDetectorOptions.landmarkMode = FaceDetectorLandmarkMode(rawValue: landmarkMode)
        faceDetectorOptions.contourMode = FaceDetectorContourMode(rawValue: contourMode)
        faceDetectorOptions.classificationMode = FaceDetectorClassificationMode(rawValue: classificationMode)
        faceDetectorOptions.minFaceSize = CGFloat(minFaceSize)
        faceDetectorOptions.isTrackingEnabled = enableTracking

        let faceDetector = FaceDetector.faceDetector(
            options: faceDetectorOptions
        )

        do {
            let faces = try faceDetector.results(
                in: visionImage
            )
            let result = ProcessImageResult(faces: faces)
            completion(result, nil)
        } catch let error {
            completion(nil, error)
        }
    }
}
