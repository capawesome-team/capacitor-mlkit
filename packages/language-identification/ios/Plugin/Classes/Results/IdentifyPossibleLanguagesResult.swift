import Foundation
import Capacitor
import MLKitLanguageID

@objc public class IdentifyPossibleLanguagesResult: NSObject, Result {
    let identifiedLanguages: [IdentifiedLanguage]

    init(identifiedLanguages: [IdentifiedLanguage]) {
        self.identifiedLanguages = identifiedLanguages
    }

    @objc public func toJSObject() -> AnyObject {
        var identifiedLanguagesResult = JSArray()
        for identifiedLanguage in identifiedLanguages {
            var identifiedLanguageResult = JSObject()
            identifiedLanguageResult["language"] = identifiedLanguage.languageTag
            identifiedLanguageResult["confidence"] = identifiedLanguage.confidence
            identifiedLanguagesResult.append(identifiedLanguageResult)
        }
        var result = JSObject()
        result["identifiedLanguages"] = identifiedLanguagesResult
        return result as AnyObject
    }
}
