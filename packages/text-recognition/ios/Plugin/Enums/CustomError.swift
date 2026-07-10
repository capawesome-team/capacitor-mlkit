import Foundation

enum CustomError: Error {
    case loadImageFailed
    case pathMissing
    case scriptInvalid

    var code: String? {
        switch self {
        case .loadImageFailed, .pathMissing, .scriptInvalid:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .loadImageFailed:
            return NSLocalizedString("image could not be loaded.", comment: "loadImageFailed")
        case .pathMissing:
            return NSLocalizedString("path must be provided.", comment: "pathMissing")
        case .scriptInvalid:
            return NSLocalizedString("script is invalid.", comment: "scriptInvalid")
        }
    }
}
