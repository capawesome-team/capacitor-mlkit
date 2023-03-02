import Foundation
import MLKitTranslate

@objc public class LanguageIdentification: NSObject {
    private let plugin: LanguageIdentificationPlugin
    
    init(plugin: LanguageIdentificationPlugin) {
        self.plugin = plugin
    }

    @objc public func deleteDownloadedModel(language: TranslateLanguage, completion: @escaping (Error?) -> Void) {
        let model = TranslateRemoteModel.translateRemoteModel(language: language)
        ModelManager.modelManager().deleteDownloadedModel(model) { error in
            completion(error)
        }
    }
}
