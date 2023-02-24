import Foundation
import MLKitTranslate

@objc public class Translation: NSObject {
    private let plugin: TranslationPlugin

    private var downloadQueue: [String: ((Error?) -> Void)] = {
        return [String: ((Error?) -> Void)]()
    }()

    init(plugin: TranslationPlugin) {
        self.plugin = plugin
    }

    @objc public func deleteDownloadedModel(language: TranslateLanguage, completion: @escaping (Error?) -> Void) {
        let model = TranslateRemoteModel.translateRemoteModel(language: language)
        ModelManager.modelManager().deleteDownloadedModel(model) { error in
            completion(error)
        }
    }

    @objc public func downloadModel(language: TranslateLanguage, completion: @escaping (Error?) -> Void) {
        let model = TranslateRemoteModel.translateRemoteModel(language: language)
        let conditions = ModelDownloadConditions(
            allowsCellularAccess: true,
            allowsBackgroundDownloading: true
        )
        ModelManager.modelManager().download(model, conditions: conditions)
        self.downloadQueue[language.rawValue] = completion
    }

    @objc public func getDownloadedModels() -> Set<TranslateRemoteModel> {
        return ModelManager.modelManager().downloadedTranslateModels
    }

    @objc public func translate(text: String, sourceLanguage: TranslateLanguage, targetLanguage: TranslateLanguage, completion: @escaping (String?, Error?) -> Void) {
        let options = TranslatorOptions(sourceLanguage: sourceLanguage, targetLanguage: targetLanguage)
        let translator = Translator.translator(options: options)
        let conditions = ModelDownloadConditions(
            allowsCellularAccess: true,
            allowsBackgroundDownloading: true
        )
        translator.downloadModelIfNeeded(with: conditions) { error in
            if let error = error {
                completion(nil, error)
                return
            }

            translator.translate(text) { translatedText, error in
                completion(translatedText, error)
            }
        }
    }

    public func handleModelDownloadDidSucceed(model: TranslateRemoteModel) {
        if let completion = self.downloadQueue[model.language.rawValue] {
            completion(nil)
        }
    }

    public func handleModelDownloadDidFail(model: TranslateRemoteModel) {
        if let completion = self.downloadQueue[model.language.rawValue] {
            completion(plugin.errorDownloadModelFailed)
        }
    }
}

extension String: LocalizedError {
    public var localizedDescription: String? { return self }
}
