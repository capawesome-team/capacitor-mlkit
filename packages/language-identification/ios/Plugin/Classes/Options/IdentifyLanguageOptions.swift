import Foundation
import Capacitor

@objc public class IdentifyLanguageOptions: NSObject {
    let text: String
    let confidenceThreshold: Float?

    init(_ call: CAPPluginCall) throws {
        self.text = try IdentifyLanguageOptions.getTextFromCall(call)
        self.confidenceThreshold = IdentifyLanguageOptions.getConfidenceThresholdFromCall(call)
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
