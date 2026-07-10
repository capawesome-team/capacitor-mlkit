import Foundation
import Capacitor

@objc(GenAiSpeechRecognitionPlugin)
public class GenAiSpeechRecognitionPlugin: CAPPlugin {
    @objc func checkFeatureStatus(_ call: CAPPluginCall) {
        rejectCallAsUnavailable(call)
    }

    @objc override public func checkPermissions(_ call: CAPPluginCall) {
        rejectCallAsUnavailable(call)
    }

    @objc func downloadFeature(_ call: CAPPluginCall) {
        rejectCallAsUnavailable(call)
    }

    @objc override public func requestPermissions(_ call: CAPPluginCall) {
        rejectCallAsUnavailable(call)
    }

    @objc func startRecognition(_ call: CAPPluginCall) {
        rejectCallAsUnavailable(call)
    }

    @objc func stopRecognition(_ call: CAPPluginCall) {
        rejectCallAsUnavailable(call)
    }

    private func rejectCallAsUnavailable(_ call: CAPPluginCall) {
        call.unavailable("This method is not available on this platform.")
    }
}
