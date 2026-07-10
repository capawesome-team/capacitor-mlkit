import Foundation
import Capacitor

@objc public class DownloadModelOptions: NSObject {
    let languageTag: String

    init(_ call: CAPPluginCall) throws {
        self.languageTag = try DownloadModelOptions.getLanguageTagFromCall(call)
    }

    private static func getLanguageTagFromCall(_ call: CAPPluginCall) throws -> String {
        guard let languageTag = call.getString("languageTag") else {
            throw CustomError.languageTagMissing
        }
        return languageTag
    }
}
