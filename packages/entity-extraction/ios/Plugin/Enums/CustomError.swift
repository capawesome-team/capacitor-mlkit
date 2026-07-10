import Foundation

enum CustomError: Error {
    case entityTypeInvalid
    case languageInvalid
    case languageMissing
    case modelNotDownloaded
    case textMissing

    var code: String? {
        switch self {
        case .entityTypeInvalid, .languageInvalid, .languageMissing, .textMissing:
            return nil
        case .modelNotDownloaded:
            return "MODEL_NOT_DOWNLOADED"
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .entityTypeInvalid:
            return NSLocalizedString("entity type is invalid.", comment: "entityTypeInvalid")
        case .languageInvalid:
            return NSLocalizedString("language is invalid.", comment: "languageInvalid")
        case .languageMissing:
            return NSLocalizedString("language must be provided.", comment: "languageMissing")
        case .modelNotDownloaded:
            return NSLocalizedString("model must be downloaded.", comment: "modelNotDownloaded")
        case .textMissing:
            return NSLocalizedString("text must be provided.", comment: "textMissing")
        }
    }
}
