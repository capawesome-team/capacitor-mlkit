import Foundation
import Capacitor

@objc(GenAiSummarizationPlugin)
public class GenAiSummarizationPlugin: CAPPlugin {
    @objc func checkFeatureStatus(_ call: CAPPluginCall) {
        rejectCallAsUnavailable(call)
    }

    @objc func downloadFeature(_ call: CAPPluginCall) {
        rejectCallAsUnavailable(call)
    }

    @objc func summarize(_ call: CAPPluginCall) {
        rejectCallAsUnavailable(call)
    }

    private func rejectCallAsUnavailable(_ call: CAPPluginCall) {
        call.unavailable("This method is not available on this platform.")
    }
}
