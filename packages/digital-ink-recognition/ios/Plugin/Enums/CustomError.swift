import Foundation

enum CustomError: Error {
    case downloadModelFailed
    case languageTagMissing
    case modelNotDownloaded
    case strokesMissing
    case unsupportedLanguageTag

    var code: String? {
        switch self {
        case .modelNotDownloaded:
            return "MODEL_NOT_DOWNLOADED"
        case .unsupportedLanguageTag:
            return "UNSUPPORTED_LANGUAGE_TAG"
        default:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .downloadModelFailed:
            return NSLocalizedString("downloadModel failed.", comment: "downloadModelFailed")
        case .languageTagMissing:
            return NSLocalizedString("languageTag must be provided.", comment: "languageTagMissing")
        case .modelNotDownloaded:
            return NSLocalizedString("model must be downloaded.", comment: "modelNotDownloaded")
        case .strokesMissing:
            return NSLocalizedString("strokes must be provided.", comment: "strokesMissing")
        case .unsupportedLanguageTag:
            return NSLocalizedString("languageTag is not supported.", comment: "unsupportedLanguageTag")
        }
    }
}
