import Foundation
import Capacitor

@objc public class ProcessImageOptions: NSObject {
    private var path: String
    private var script: String

    init(_ call: CAPPluginCall) throws {
        guard let path = call.getString("path") else {
            throw CustomError.pathMissing
        }
        self.path = path
        self.script = try ProcessImageOptions.getScriptFromCall(call)
    }

    func getPath() -> String {
        return path
    }

    func getScript() -> String {
        return script
    }

    private static func getScriptFromCall(_ call: CAPPluginCall) throws -> String {
        let script = call.getString("script", "LATIN")
        switch script {
        case "LATIN", "CHINESE", "DEVANAGARI", "JAPANESE", "KOREAN":
            return script
        default:
            throw CustomError.scriptInvalid
        }
    }
}
