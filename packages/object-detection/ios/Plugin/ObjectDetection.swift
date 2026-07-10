import Foundation
import MLKitVision
import MLKitObjectDetection

@objc public class ObjectDetection: NSObject {
    public let plugin: ObjectDetectionPlugin

    init(plugin: ObjectDetectionPlugin) {
        self.plugin = plugin
    }

    @objc func processImage(_ options: ProcessImageOptions, completion: @escaping (ProcessImageResult?, Error?) -> Void) {
        guard let visionImage = createVisionImageFromFilePath(options.getPath()) else {
            completion(nil, CustomError.loadImageFailed)
            return
        }

        let objectDetectorOptions = ObjectDetectorOptions()
        objectDetectorOptions.detectorMode = .singleImage
        objectDetectorOptions.shouldEnableClassification = options.isClassificationEnabled()
        objectDetectorOptions.shouldEnableMultipleObjects = options.isMultipleObjectsEnabled()

        let objectDetector = ObjectDetector.objectDetector(options: objectDetectorOptions)

        do {
            let objects = try objectDetector.results(in: visionImage)
            let result = ProcessImageResult(objects: objects)
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
