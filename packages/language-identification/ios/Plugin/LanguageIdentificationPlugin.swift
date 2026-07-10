import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(LanguageIdentificationPlugin)
public class LanguageIdentificationPlugin: CAPPlugin {
    public static let tag = "LanguageIdentification"

    private var implementation: LanguageIdentification?

    override public func load() {
        implementation = LanguageIdentification()
    }

    @objc func identifyLanguage(_ call: CAPPluginCall) {
        do {
            let options = try IdentifyLanguageOptions(call)

            implementation?.identifyLanguage(options, completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            })
        } catch {
            self.rejectCall(call, error)
        }
    }

    @objc func identifyPossibleLanguages(_ call: CAPPluginCall) {
        do {
            let options = try IdentifyPossibleLanguagesOptions(call)

            implementation?.identifyPossibleLanguages(options, completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            })
        } catch {
            self.rejectCall(call, error)
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", LanguageIdentificationPlugin.tag, "] ", error)
        call.reject(error.localizedDescription)
    }

    private func resolveCall(_ call: CAPPluginCall, _ result: Result?) {
        if let result = result?.toJSObject() as? JSObject {
            call.resolve(result)
        } else {
            call.resolve()
        }
    }
}
