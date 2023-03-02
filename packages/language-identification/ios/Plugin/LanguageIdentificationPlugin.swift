import Foundation
import Capacitor
import MLKitTranslate

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(LanguageIdentificationPlugin)
public class LanguageIdentificationPlugin: CAPPlugin {
    public let errorLanguageMissing = "language must be provided."

    private var implementation: LanguageIdentification?

    override public func load() {
        implementation = LanguageIdentification(plugin: self)
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
}
