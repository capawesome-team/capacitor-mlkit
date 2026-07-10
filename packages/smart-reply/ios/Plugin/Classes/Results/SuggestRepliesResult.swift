import Foundation
import Capacitor

@objc public class SuggestRepliesResult: NSObject, Result {
    let status: String
    let suggestions: [String]

    init(status: String, suggestions: [String]) {
        self.status = status
        self.suggestions = suggestions
    }

    @objc public func toJSObject() -> AnyObject {
        var suggestionsResult = JSArray()
        for suggestion in suggestions {
            suggestionsResult.append(suggestion)
        }
        var result = JSObject()
        result["status"] = status
        result["suggestions"] = suggestionsResult
        return result as AnyObject
    }
}
