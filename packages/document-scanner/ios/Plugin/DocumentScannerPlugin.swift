import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(DocumentScannerPlugin)
public class DocumentScannerPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "DocumentScannerPlugin"
    public let jsName = "DocumentScanner"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "scanDocument", returnType: CAPPluginReturnPromise)
    ]

    @objc func scanDocument(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    private func rejectCallAsUnavailable(_ call: CAPPluginCall) {
        call.unavailable("This method is not available on this platform.")
    }

    private func rejectCallAsUnimplemented(_ call: CAPPluginCall) {
        call.unimplemented("This method is not available on this platform.")
    }
}
