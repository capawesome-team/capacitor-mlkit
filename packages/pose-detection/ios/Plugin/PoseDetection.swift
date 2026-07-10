import Foundation
import MLKitVision
import MLKitPoseDetection
import MLKitPoseDetectionAccurate
import MLKitPoseDetectionCommon

@objc public class PoseDetection: NSObject {
    public let plugin: PoseDetectionPlugin

    init(plugin: PoseDetectionPlugin) {
        self.plugin = plugin
    }

    @objc func processImage(_ options: ProcessImageOptions, completion: @escaping (ProcessImageResult?, Error?) -> Void) {
        guard let visionImage = createVisionImageFromFilePath(options.getPath()) else {
            completion(nil, CustomError.loadImageFailed)
            return
        }

        let poseDetectorOptions: CommonPoseDetectorOptions
        if options.isAccurateModeEnabled() {
            let accurateOptions = AccuratePoseDetectorOptions()
            accurateOptions.detectorMode = .singleImage
            poseDetectorOptions = accurateOptions
        } else {
            let baseOptions = PoseDetectorOptions()
            baseOptions.detectorMode = .singleImage
            poseDetectorOptions = baseOptions
        }

        let poseDetector = PoseDetector.poseDetector(options: poseDetectorOptions)

        do {
            let poses = try poseDetector.results(in: visionImage)
            let result = ProcessImageResult(poses: poses)
            completion(result, nil)
        } catch let error {
            completion(nil, error)
        }
    }

    private func createVisionImageFromFilePath(_ path: String) -> VisionImage? {
        guard let url = URL.init(string: path) else {
            return nil
        }
        if FileManager.default.fileExists(atPath: url.path) {
            guard let image = UIImage.init(contentsOfFile: url.path) else {
                return nil
            }
            return VisionImage.init(image: image)
        } else {
            return nil
        }
    }
}
