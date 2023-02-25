import Foundation
import Capacitor
import MLKitSmartReply

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(SmartReplyPlugin)
public class SmartReplyPlugin: CAPPlugin {
    public let errorMessagesMissing = "messages must be provided."
    public let errorNotSupportedLanguage = "the conversation's language isn't supported"

    private var implementation: SmartReplies?

    override public func load() {
        implementation = SmartReplies(plugin: self)
    }

    @objc public func smartReply(_ call: CAPPluginCall) {
        guard let messages = (call.getArray("messages") ?? []) as? [TextMessage] else {
            call.reject(errorMessagesMissing)
            return
        }
        let conversation = implementation?.createConversation(messages: messages)
        implementation?.reply(conversation: conversation ?? []) { result, error in
            if (error != nil) {
                call.reject(error as! String)
            }
            if (result?.status == .notSupportedLanguage) {
                // The conversation's language isn't supported, so
                // the result doesn't contain any suggestions.
                call.reject(self.errorNotSupportedLanguage)
            } else if (result?.status == .success) {
                // Successfully suggested smart replies.
                call.resolve([
                    "value": result?.suggestions ?? []
                ])
            }
        }
    }
}
