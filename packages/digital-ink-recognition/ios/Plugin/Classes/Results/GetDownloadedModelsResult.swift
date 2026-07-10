import Foundation
import Capacitor
import MLKitDigitalInkRecognition

@objc public class GetDownloadedModelsResult: NSObject, Result {
    let models: Set<DigitalInkRecognitionModel>

    init(models: Set<DigitalInkRecognitionModel>) {
        self.models = models
    }

    @objc public func toJSObject() -> AnyObject {
        var languageTagsResult = JSArray()
        for model in models {
            languageTagsResult.append(model.modelIdentifier.languageTag)
        }
        var result = JSObject()
        result["languageTags"] = languageTagsResult
        return result as AnyObject
    }
}
