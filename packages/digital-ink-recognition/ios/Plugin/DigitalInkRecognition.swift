import Foundation
import MLKitDigitalInkRecognition

@objc public class DigitalInkRecognition: NSObject {
    private let plugin: DigitalInkRecognitionPlugin

    private var downloadQueue: [String: ((Error?) -> Void)] = [:]

    init(plugin: DigitalInkRecognitionPlugin) {
        self.plugin = plugin
    }

    @objc public func deleteDownloadedModel(_ options: DeleteDownloadedModelOptions, completion: @escaping (_ error: Error?) -> Void) throws {
        let model = try createModel(languageTag: options.languageTag)
        ModelManager.modelManager().deleteDownloadedModel(model) { error in
            completion(error)
        }
    }

    @objc public func downloadModel(_ options: DownloadModelOptions, completion: @escaping (_ error: Error?) -> Void) throws {
        let model = try createModel(languageTag: options.languageTag)
        if ModelManager.modelManager().isModelDownloaded(model) {
            completion(nil)
            return
        }
        let conditions = ModelDownloadConditions(
            allowsCellularAccess: true,
            allowsBackgroundDownloading: true
        )
        downloadQueue[model.modelIdentifier.languageTag] = completion
        ModelManager.modelManager().download(model, conditions: conditions)
    }

    @objc public func getDownloadedModels(completion: @escaping (_ result: GetDownloadedModelsResult?, _ error: Error?) -> Void) {
        DispatchQueue.global().async {
            var models = Set<DigitalInkRecognitionModel>()
            for modelIdentifier in DigitalInkRecognitionModelIdentifier.allModelIdentifiers() {
                let model = DigitalInkRecognitionModel(modelIdentifier: modelIdentifier)
                if ModelManager.modelManager().isModelDownloaded(model) {
                    models.insert(model)
                }
            }
            completion(GetDownloadedModelsResult(models: models), nil)
        }
    }

    public func handleModelDownloadDidFail(model: DigitalInkRecognitionModel, error: Error?) {
        let languageTag = model.modelIdentifier.languageTag
        if let completion = downloadQueue[languageTag] {
            downloadQueue[languageTag] = nil
            completion(error ?? CustomError.downloadModelFailed)
        }
    }

    public func handleModelDownloadDidSucceed(model: DigitalInkRecognitionModel) {
        let languageTag = model.modelIdentifier.languageTag
        if let completion = downloadQueue[languageTag] {
            downloadQueue[languageTag] = nil
            completion(nil)
        }
    }

    @objc public func recognize(_ options: RecognizeOptions, completion: @escaping (_ result: RecognizeResult?, _ error: Error?) -> Void) throws {
        let model = try createModel(languageTag: options.languageTag)
        if !ModelManager.modelManager().isModelDownloaded(model) {
            completion(nil, CustomError.modelNotDownloaded)
            return
        }
        let recognizerOptions = DigitalInkRecognizerOptions(model: model)
        recognizerOptions.maxResultCount = Int32(options.maxResultCount)
        let recognizer = DigitalInkRecognizer.digitalInkRecognizer(options: recognizerOptions)
        let handleRecognitionResult: (DigitalInkRecognitionResult?, Error?) -> Void = { recognitionResult, error in
            if let error = error {
                completion(nil, error)
                return
            }
            completion(RecognizeResult(candidates: recognitionResult?.candidates ?? []), nil)
        }
        if options.preContext == nil && options.writingArea == nil {
            recognizer.recognize(ink: options.ink, completion: handleRecognitionResult)
        } else {
            let context = DigitalInkRecognitionContext(preContext: options.preContext, writingArea: options.writingArea)
            recognizer.recognize(ink: options.ink, context: context, completion: handleRecognitionResult)
        }
    }

    private func createModel(languageTag: String) throws -> DigitalInkRecognitionModel {
        guard let modelIdentifier = try? DigitalInkRecognitionModelIdentifier.from(languageTag: languageTag) else {
            throw CustomError.unsupportedLanguageTag
        }
        return DigitalInkRecognitionModel(modelIdentifier: modelIdentifier)
    }
}
