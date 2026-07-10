import Foundation
import MLKitVision
import MLKitImageLabeling

@objc public class ImageLabeling: NSObject {
    public let plugin: ImageLabelingPlugin

    init(plugin: ImageLabelingPlugin) {
        self.plugin = plugin
    }

    @objc func processImage(_ options: ProcessImageOptions, completion: @escaping (ProcessImageResult?, Error?) -> Void) {
        guard let visionImage = createVisionImageFromFilePath(options.getPath()) else {
            completion(nil, CustomError.loadImageFailed)
            return
        }
        let confidenceThreshold = options.getConfidenceThreshold()

        let imageLabelerOptions = ImageLabelerOptions()
        imageLabelerOptions.confidenceThreshold = NSNumber(value: confidenceThreshold)

        let imageLabeler = ImageLabeler.imageLabeler(options: imageLabelerOptions)

        do {
            let labels = try imageLabeler.results(in: visionImage)
            let result = ProcessImageResult(labels: labels)
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
