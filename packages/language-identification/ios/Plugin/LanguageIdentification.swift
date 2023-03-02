import Foundation
import MLKitNaturalLanguage

@objc public class LanguageIdentification: NSObject {
    private let plugin: LanguageIdentificationPlugin
    
    init(plugin: LanguageIdentificationPlugin) {
        self.plugin = plugin
    }

    @objc public func identifyLanguage(text: String, completion: @escaping (String?, Error?) -> Void) {
        let languageIdentification = NaturalLanguage.languageIdentification()
        languageIdentification.identifyLanguage(for: text) { (languageCode, error) in
            if let error = error {
                completion(nil, error)
              return
            }
            completion(languageCode, nil)
          }
    }
}
