import Foundation
import MLKitLanguageID

@objc public class LanguageIdentification: NSObject {
    private static let undeterminedLanguageTag = "und"

    @objc public func identifyLanguage(_ options: IdentifyLanguageOptions, completion: @escaping (_ result: IdentifyLanguageResult?, _ error: Error?) -> Void) {
        let client = createClient(options.confidenceThreshold)
        client.identifyLanguage(for: options.text) { languageTag, error in
            if let error = error {
                completion(nil, error)
                return
            }
            completion(IdentifyLanguageResult(language: languageTag ?? LanguageIdentification.undeterminedLanguageTag), nil)
        }
    }

    @objc public func identifyPossibleLanguages(_ options: IdentifyPossibleLanguagesOptions, completion: @escaping (_ result: IdentifyPossibleLanguagesResult?, _ error: Error?) -> Void) {
        let client = createClient(options.confidenceThreshold)
        client.identifyPossibleLanguages(for: options.text) { identifiedLanguages, error in
            if let error = error {
                completion(nil, error)
                return
            }
            completion(IdentifyPossibleLanguagesResult(identifiedLanguages: identifiedLanguages ?? []), nil)
        }
    }

    private func createClient(_ confidenceThreshold: Float?) -> MLKitLanguageID.LanguageIdentification {
        if let confidenceThreshold = confidenceThreshold {
            let options = LanguageIdentificationOptions(confidenceThreshold: confidenceThreshold)
            return MLKitLanguageID.LanguageIdentification.languageIdentification(options: options)
        }
        return MLKitLanguageID.LanguageIdentification.languageIdentification()
    }
}
