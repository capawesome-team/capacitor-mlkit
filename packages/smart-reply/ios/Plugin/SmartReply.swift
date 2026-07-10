import Foundation
import MLKitSmartReply

@objc public class SmartReply: NSObject {
    private static let statusSuccess = "SUCCESS"
    private static let statusNotSupportedLanguage = "NOT_SUPPORTED_LANGUAGE"
    private static let statusNoReply = "NO_REPLY"

    @objc public func suggestReplies(_ options: SuggestRepliesOptions, completion: @escaping (_ result: SuggestRepliesResult?, _ error: Error?) -> Void) {
        MLKitSmartReply.SmartReply.smartReply().suggestReplies(for: options.messages) { result, error in
            if let error = error {
                completion(nil, error)
                return
            }
            completion(self.createResult(result), nil)
        }
    }

    private func createResult(_ result: SmartReplySuggestionResult?) -> SuggestRepliesResult {
        guard let result = result else {
            return SuggestRepliesResult(status: SmartReply.statusNoReply, suggestions: [])
        }
        var suggestions: [String] = []
        if result.status == .success {
            suggestions = result.suggestions.map { $0.text }
        }
        return SuggestRepliesResult(status: mapStatus(result.status), suggestions: suggestions)
    }

    private func mapStatus(_ status: SmartReplyResultStatus) -> String {
        switch status {
        case .notSupportedLanguage:
            return SmartReply.statusNotSupportedLanguage
        case .noReply:
            return SmartReply.statusNoReply
        default:
            return SmartReply.statusSuccess
        }
    }
}
