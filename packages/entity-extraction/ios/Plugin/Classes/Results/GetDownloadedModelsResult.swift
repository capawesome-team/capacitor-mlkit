import Foundation
import Capacitor
import MLKitEntityExtraction

@objc public class GetDownloadedModelsResult: NSObject, Result {
    let modelIdentifiers: [EntityExtractionModelIdentifier]

    init(modelIdentifiers: [EntityExtractionModelIdentifier]) {
        self.modelIdentifiers = modelIdentifiers
    }

    @objc public func toJSObject() -> AnyObject {
        var languagesResult = JSArray()
        for modelIdentifier in modelIdentifiers {
            languagesResult.append(EntityExtractionHelper.convertModelIdentifierToLanguage(modelIdentifier))
        }
        var result = JSObject()
        result["languages"] = languagesResult
        return result as AnyObject
    }
}
