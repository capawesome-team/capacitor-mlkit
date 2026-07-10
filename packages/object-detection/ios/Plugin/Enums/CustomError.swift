import Foundation

enum CustomError: Error {
    case loadImageFailed
    case pathMissing

    var code: String? {
        switch self {
        case .loadImageFailed, .pathMissing:
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
        }
    }
}
