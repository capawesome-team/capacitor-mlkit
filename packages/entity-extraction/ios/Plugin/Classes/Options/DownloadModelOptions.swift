import Foundation
import Capacitor
import MLKitEntityExtraction

@objc public class DownloadModelOptions: NSObject {
    let modelIdentifier: EntityExtractionModelIdentifier

    init(_ call: CAPPluginCall) throws {
        self.modelIdentifier = try DownloadModelOptions.getModelIdentifierFromCall(call)
    }

    private static func getModelIdentifierFromCall(_ call: CAPPluginCall) throws -> EntityExtractionModelIdentifier {
        guard let language = call.getString("language") else {
            throw CustomError.languageMissing
        }
        return try EntityExtractionHelper.convertLanguageToModelIdentifier(language)
    }
}
