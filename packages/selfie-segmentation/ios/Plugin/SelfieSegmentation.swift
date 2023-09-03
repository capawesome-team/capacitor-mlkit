import Foundation
import MLKitVision
import MLKitSegmentationSelfie

@objc public class SelfieSegmentation: NSObject {
    public let plugin: SelfieSegmentationPlugin

    init(plugin: SelfieSegmentationPlugin) {
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
        let enableRawSizeMask = options.shouldEnableRawSizeMask()

        let selfieSegmenterOptions: SelfieSegmenterOptions = SelfieSegmenterOptions()
        selfieSegmenterOptions.segmenterMode = .singleImage
        selfieSegmenterOptions.shouldEnableRawSizeMask = enableRawSizeMask

        let segmenter = Segmenter.segmenter(
            options: selfieSegmenterOptions
        )

        do {
            let mask: SegmentationMask = try segmenter.results(
                in: visionImage
            )
            let result = ProcessImageResult(segmentationMask: mask)
            completion(result, nil)
        } catch let error {
            completion(nil, error)
        }
    }
}
