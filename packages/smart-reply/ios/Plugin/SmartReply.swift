import Foundation
import MLKitSmartReply

@objc public class SmartReplies: NSObject {
    private let plugin: SmartReplyPlugin

    init(plugin: SmartReplyPlugin) {
        self.plugin = plugin
    }

    @objc public func createConversation(messages: [TextMessage]) -> [TextMessage] {
        var conversation: [TextMessage] = []
        for message in messages {
            // Then, for each message sent and received:
            // let message = TextMessage(
            //     text: "How are you?",
            //     timestamp: Date().timeIntervalSince1970,
            //     userID: "userId",
            //     isLocalUser: false)
            conversation.append(message)
        }
        return conversation
    }

    @objc public func reply(conversation: [TextMessage], completion: @escaping (SmartReplySuggestionResult?, Error?) -> Void) {
        SmartReply.smartReply().suggestReplies(for: conversation) { result, error in
            guard error == nil, let result = result else {
                completion(nil, error)
                return
            }
            if (result.status == .notSupportedLanguage) {
                // The conversation's language isn't supported, so
                // the result doesn't contain any suggestions.
                completion(nil, "The conversation's language isn't supported")
            } else if (result.status == .success) {
                // Successfully suggested smart replies.
                completion(result, nil)
            }
        }
    }
}

extension String: LocalizedError {
    public var localizedDescription: String? { return self }
}
