import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(SubjectSegmentationPlugin)
public class SubjectSegmentationPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "SubjectSegmentationPlugin"
    public let jsName = "SubjectSegmentation"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "echo", returnType: CAPPluginReturnPromise)
    ]
    private let implementation = SubjectSegmentation()

    @objc func echo(_ call: CAPPluginCall) {
        let value = call.getString("value") ?? ""
        call.resolve([
            "value": implementation.echo(value)
        ])
    }
}
