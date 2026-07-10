import Foundation

enum CustomError: Error {
    case messagesMissing
    case textMissing
    case timestampMissing
    case userIdMissing

    var code: String? {
        switch self {
        case .messagesMissing, .textMissing, .timestampMissing, .userIdMissing:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .messagesMissing:
            return NSLocalizedString("messages must be provided.", comment: "messagesMissing")
        case .textMissing:
            return NSLocalizedString("text must be provided.", comment: "textMissing")
        case .timestampMissing:
            return NSLocalizedString("timestamp must be provided.", comment: "timestampMissing")
        case .userIdMissing:
            return NSLocalizedString("userId must be provided.", comment: "userIdMissing")
        }
    }
}
