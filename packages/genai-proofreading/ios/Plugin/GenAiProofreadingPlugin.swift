import Foundation
import Capacitor

@objc(GenAiProofreadingPlugin)
public class GenAiProofreadingPlugin: CAPPlugin {
    @objc func checkFeatureStatus(_ call: CAPPluginCall) {
        rejectCallAsUnavailable(call)
    }

    @objc func downloadFeature(_ call: CAPPluginCall) {
        rejectCallAsUnavailable(call)
    }

    @objc func proofread(_ call: CAPPluginCall) {
        rejectCallAsUnavailable(call)
    }

    private func rejectCallAsUnavailable(_ call: CAPPluginCall) {
        call.unavailable("This method is not available on this platform.")
    }
}
