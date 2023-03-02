import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(LanguageIdentificationPlugin)
public class LanguageIdentificationPlugin: CAPPlugin {
    public let errorTextMissing = "text must be provided."

    private var implementation: LanguageIdentification?

    override public func load() {
        implementation = LanguageIdentification(plugin: self)
    }

    @objc func identifyLanguage(_ call: CAPPluginCall) {
        guard let text = call.getString("text") else {
            call.reject(errorTextMissing)
            return
        }

        implementation?.identifyLanguage(text: text, completion: { languageCode, error in
            if let error = error {
                call.reject(error.localizedDescription)
                return
            }
            if let languageCode = languageCode, languageCode != "und" {
              print("Identified Language: \(languageCode)")
            } else {
              print("No language was identified")
            }
            call.resolve()
        })
    }
}
