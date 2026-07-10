import Foundation
import Capacitor

@objc public class ProcessImageOptions: NSObject {
    private var path: String
    private var confidenceThreshold: Float

    init(_ call: CAPPluginCall) throws {
        guard let path = call.getString("path") else {
            throw CustomError.pathMissing
        }
        self.path = path
        self.confidenceThreshold = call.getFloat("confidenceThreshold", 0.5)
    }

    func getPath() -> String {
        return path
    }

    func getConfidenceThreshold() -> Float {
        return confidenceThreshold
    }
}
