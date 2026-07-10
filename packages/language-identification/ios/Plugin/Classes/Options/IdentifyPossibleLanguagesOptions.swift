import Foundation
import Capacitor

@objc public class IdentifyPossibleLanguagesOptions: NSObject {
    let text: String
    let confidenceThreshold: Float?

    init(_ call: CAPPluginCall) throws {
        self.text = try IdentifyPossibleLanguagesOptions.getTextFromCall(call)
        self.confidenceThreshold = IdentifyPossibleLanguagesOptions.getConfidenceThresholdFromCall(call)
    }

    private static func getConfidenceThresholdFromCall(_ call: CAPPluginCall) -> Float? {
        return call.getFloat("confidenceThreshold")
    }

    private static func getTextFromCall(_ call: CAPPluginCall) throws -> String {
        guard let text = call.getString("text") else {
            throw CustomError.textMissing
        }
        return text
    }
}
