import Foundation
import Capacitor

@objc public class IdentifyLanguageResult: NSObject, Result {
    let language: String

    init(language: String) {
        self.language = language
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["language"] = language
        return result as AnyObject
    }
}
