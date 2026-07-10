import Foundation
import MLKitVision
import MLKitTextRecognition
import MLKitTextRecognitionChinese
import MLKitTextRecognitionDevanagari
import MLKitTextRecognitionJapanese
import MLKitTextRecognitionKorean

@objc public class TextRecognition: NSObject {
    public let plugin: TextRecognitionPlugin

    init(plugin: TextRecognitionPlugin) {
        self.plugin = plugin
    }

    @objc func processImage(_ options: ProcessImageOptions, completion: @escaping (ProcessImageResult?, Error?) -> Void) {
        guard let visionImage = createVisionImageFromFilePath(options.getPath()) else {
            completion(nil, CustomError.loadImageFailed)
            return
        }

        let recognizerOptions = createRecognizerOptionsForScript(options.getScript())
        let textRecognizer = TextRecognizer.textRecognizer(options: recognizerOptions)
        textRecognizer.process(visionImage) { text, error in
            if let error = error {
                completion(nil, error)
                return
            }
            guard let text = text else {
                completion(nil, CustomError.loadImageFailed)
                return
            }
            let result = ProcessImageResult(text: text)
            completion(result, nil)
        }
    }

    private func createRecognizerOptionsForScript(_ script: String) -> CommonTextRecognizerOptions {
        switch script {
        case "CHINESE":
            return ChineseTextRecognizerOptions()
        case "DEVANAGARI":
            return DevanagariTextRecognizerOptions()
        case "JAPANESE":
            return JapaneseTextRecognizerOptions()
        case "KOREAN":
            return KoreanTextRecognizerOptions()
        default:
            return TextRecognizerOptions()
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
            let visionImage = VisionImage.init(image: image)
            visionImage.orientation = image.imageOrientation
            return visionImage
        } else {
            return nil
        }
    }
}
