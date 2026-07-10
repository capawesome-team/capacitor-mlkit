import Foundation
import Capacitor

@objc public class ProcessImageOptions: NSObject {
    private var path: String
    private var shouldEnableClassification: Bool
    private var shouldEnableMultipleObjects: Bool

    init(_ call: CAPPluginCall) throws {
        guard let path = call.getString("path") else {
            throw CustomError.pathMissing
        }
        self.path = path
        self.shouldEnableClassification = call.getBool("shouldEnableClassification", false)
        self.shouldEnableMultipleObjects = call.getBool("shouldEnableMultipleObjects", false)
    }

    func getPath() -> String {
        return path
    }

    func isClassificationEnabled() -> Bool {
        return shouldEnableClassification
    }

    func isMultipleObjectsEnabled() -> Bool {
        return shouldEnableMultipleObjects
    }
}
