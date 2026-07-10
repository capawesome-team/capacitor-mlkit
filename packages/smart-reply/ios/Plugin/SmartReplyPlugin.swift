import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(SmartReplyPlugin)
public class SmartReplyPlugin: CAPPlugin {
    public static let tag = "SmartReply"

    private var implementation: SmartReply?

    override public func load() {
        implementation = SmartReply()
    }

    @objc func suggestReplies(_ call: CAPPluginCall) {
        do {
            let options = try SuggestRepliesOptions(call)

            implementation?.suggestReplies(options, completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            })
        } catch {
            self.rejectCall(call, error)
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", SmartReplyPlugin.tag, "] ", error)
        call.reject(error.localizedDescription)
    }

    private func resolveCall(_ call: CAPPluginCall, _ result: Result?) {
        if let result = result?.toJSObject() as? JSObject {
            call.resolve(result)
        } else {
            call.resolve()
        }
    }
}
