import Foundation
import Capacitor

@objc(GenAiRewritingPlugin)
public class GenAiRewritingPlugin: CAPPlugin {
    @objc func checkFeatureStatus(_ call: CAPPluginCall) {
        rejectCallAsUnavailable(call)
    }

    @objc func downloadFeature(_ call: CAPPluginCall) {
        rejectCallAsUnavailable(call)
    }

    @objc func rewrite(_ call: CAPPluginCall) {
        rejectCallAsUnavailable(call)
    }

    private func rejectCallAsUnavailable(_ call: CAPPluginCall) {
        call.unavailable("This method is not available on this platform.")
    }
}
