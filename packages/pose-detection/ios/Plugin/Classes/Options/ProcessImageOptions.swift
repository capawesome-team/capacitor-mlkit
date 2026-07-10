import Foundation
import Capacitor

@objc public class ProcessImageOptions: NSObject {
    private static let performanceModeBase = "BASE"
    private static let performanceModeAccurate = "ACCURATE"

    private var path: String
    private var accurate: Bool

    init(_ call: CAPPluginCall) throws {
        guard let path = call.getString("path") else {
            throw CustomError.pathMissing
        }
        self.path = path
        let performanceMode = call.getString("performanceMode", ProcessImageOptions.performanceModeBase)
        switch performanceMode {
        case ProcessImageOptions.performanceModeBase:
            self.accurate = false
        case ProcessImageOptions.performanceModeAccurate:
            self.accurate = true
        default:
            throw CustomError.performanceModeInvalid
        }
    }

    func getPath() -> String {
        return path
    }

    func isAccurateModeEnabled() -> Bool {
        return accurate
    }
}
