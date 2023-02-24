import Foundation
import Capacitor
import MLKitTranslate

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(TranslationPlugin)
public class TranslationPlugin: CAPPlugin {
    public let errorLanguageMissing = "language must be provided."
    public let errorTextMissing = "text must be provided."
    public let errorSourceLanguageMissing = "sourceLanguage must be provided."
    public let errorTargetLanguageMissing = "targetLanguage must be provided."
    public let errorDownloadModelFailed = "downloadModel failed."

    private var implementation: Translation?

    override public func load() {
        implementation = Translation(plugin: self)

        NotificationCenter.default.addObserver(
            forName: .mlkitModelDownloadDidSucceed,
            object: nil,
            queue: nil
        ) { [weak self] notification in
            guard let strongSelf = self else {
                return
            }
            strongSelf.handleModelDownloadDidSucceed(notification: notification)
        }

        NotificationCenter.default.addObserver(
            forName: .mlkitModelDownloadDidFail,
            object: nil,
            queue: nil
        ) { [weak self] notification in
            guard let strongSelf = self else {
                return
            }
            strongSelf.handleModelDownloadDidFail(notification: notification)
        }
    }

    @objc func deleteDownloadedModel(_ call: CAPPluginCall) {
        guard let languageOption = call.getString("language") else {
            call.reject(errorLanguageMissing)
            return
        }
        let language = TranslateLanguage(rawValue: languageOption)

        implementation?.deleteDownloadedModel(language: language, completion: { error in
            if let error = error {
                call.reject(error.localizedDescription)
                return
            }
            call.resolve()
        })
    }

    @objc func downloadModel(_ call: CAPPluginCall) {
        guard let languageOption = call.getString("language") else {
            call.reject(errorLanguageMissing)
            return
        }
        let language = TranslateLanguage(rawValue: languageOption)

        implementation?.downloadModel(language: language, completion: { error in
            if let error = error {
                call.reject(error.localizedDescription)
                return
            }
            call.resolve()
        })
    }

    @objc func getDownloadedModels(_ call: CAPPluginCall) {
        let models = implementation?.getDownloadedModels() ?? []
        var languagesResult = JSArray()
        for model in models {
            languagesResult.append(model.language.rawValue)
        }
        call.resolve([
            "languages": languagesResult
        ])
    }

    @objc func translate(_ call: CAPPluginCall) {
        guard let text = call.getString("text") else {
            call.reject(errorTextMissing)
            return
        }
        guard let sourceLanguageOption = call.getString("sourceLanguage") else {
            call.reject(errorSourceLanguageMissing)
            return
        }
        let sourceLanguage = TranslateLanguage(rawValue: sourceLanguageOption)
        guard let targetLanguageOption = call.getString("targetLanguage") else {
            call.reject(errorTargetLanguageMissing)
            return
        }
        let targetLanguage = TranslateLanguage(rawValue: targetLanguageOption)

        implementation?.translate(text: text, sourceLanguage: sourceLanguage, targetLanguage: targetLanguage, completion: { translatedText, error in
            if let error = error {
                call.reject(error.localizedDescription)
                return
            }
            call.resolve([
                "text": translatedText ?? ""
            ])
        })
    }

    @objc private func handleModelDownloadDidSucceed(notification: Notification) {
        guard let userInfo = notification.userInfo,
              let model = userInfo[ModelDownloadUserInfoKey.remoteModel.rawValue]
                as? TranslateRemoteModel else {
            return
        }
        implementation?.handleModelDownloadDidSucceed(model: model)
    }

    @objc private func handleModelDownloadDidFail(notification: Notification) {
        guard let userInfo = notification.userInfo,
              let model = userInfo[ModelDownloadUserInfoKey.remoteModel.rawValue]
                as? TranslateRemoteModel else {
            return
        }
        implementation?.handleModelDownloadDidFail(model: model)
    }
}
