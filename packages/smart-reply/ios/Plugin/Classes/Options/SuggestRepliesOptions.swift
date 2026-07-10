import Foundation
import Capacitor
import MLKitSmartReply

@objc public class SuggestRepliesOptions: NSObject {
    let messages: [TextMessage]

    init(_ call: CAPPluginCall) throws {
        self.messages = try SuggestRepliesOptions.getMessagesFromCall(call)
    }

    private static func createTextMessage(_ messageObject: [String: Any]) throws -> TextMessage {
        guard let text = messageObject["text"] as? String else {
            throw CustomError.textMissing
        }
        guard let timestampInMilliseconds = messageObject["timestamp"] as? Double else {
            throw CustomError.timestampMissing
        }
        let timestamp = timestampInMilliseconds / 1000
        let isLocalUser = messageObject["isLocalUser"] as? Bool ?? false
        if isLocalUser {
            return TextMessage(text: text, timestamp: timestamp, userID: "", isLocalUser: true)
        }
        guard let userId = messageObject["userId"] as? String else {
            throw CustomError.userIdMissing
        }
        return TextMessage(text: text, timestamp: timestamp, userID: userId, isLocalUser: false)
    }

    private static func getMessagesFromCall(_ call: CAPPluginCall) throws -> [TextMessage] {
        guard let messagesOption = call.getArray("messages") as? [[String: Any]], !messagesOption.isEmpty else {
            throw CustomError.messagesMissing
        }
        var messages: [TextMessage] = []
        for messageObject in messagesOption {
            messages.append(try SuggestRepliesOptions.createTextMessage(messageObject))
        }
        return messages
    }
}
