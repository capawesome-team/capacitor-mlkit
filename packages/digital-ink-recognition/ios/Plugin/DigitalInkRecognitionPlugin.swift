import Foundation
import Capacitor
import MLKitDigitalInkRecognition

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(DigitalInkRecognitionPlugin)
public class DigitalInkRecognitionPlugin: CAPPlugin {
    public static let tag = "DigitalInkRecognition"

    private var implementation: DigitalInkRecognition?

    override public func load() {
        implementation = DigitalInkRecognition(plugin: self)

        NotificationCenter.default.addObserver(
            forName: .mlkitModelDownloadDidSucceed,
            object: nil,
            queue: nil
        ) { [weak self] notification in
            self?.handleModelDownloadDidSucceed(notification: notification)
        }

        NotificationCenter.default.addObserver(
            forName: .mlkitModelDownloadDidFail,
            object: nil,
            queue: nil
        ) { [weak self] notification in
            self?.handleModelDownloadDidFail(notification: notification)
        }
    }

    @objc func deleteDownloadedModel(_ call: CAPPluginCall) {
        do {
            let options = try DeleteDownloadedModelOptions(call)

            try implementation?.deleteDownloadedModel(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            self.rejectCall(call, error)
        }
    }

    @objc func downloadModel(_ call: CAPPluginCall) {
        do {
            let options = try DownloadModelOptions(call)

            try implementation?.downloadModel(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            self.rejectCall(call, error)
        }
    }

    @objc func getDownloadedModels(_ call: CAPPluginCall) {
        implementation?.getDownloadedModels(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func recognize(_ call: CAPPluginCall) {
        do {
            let options = try RecognizeOptions(call)

            try implementation?.recognize(options, completion: { result, error in
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

    private func handleModelDownloadDidFail(notification: Notification) {
        guard let userInfo = notification.userInfo,
              let model = userInfo[ModelDownloadUserInfoKey.remoteModel.rawValue]
                as? DigitalInkRecognitionModel else {
            return
        }
        let error = userInfo[ModelDownloadUserInfoKey.error.rawValue] as? Error
        implementation?.handleModelDownloadDidFail(model: model, error: error)
    }

    private func handleModelDownloadDidSucceed(notification: Notification) {
        guard let userInfo = notification.userInfo,
              let model = userInfo[ModelDownloadUserInfoKey.remoteModel.rawValue]
                as? DigitalInkRecognitionModel else {
            return
        }
        implementation?.handleModelDownloadDidSucceed(model: model)
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", DigitalInkRecognitionPlugin.tag, "] ", error)
        if let customError = error as? CustomError {
            call.reject(error.localizedDescription, customError.code)
        } else {
            call.reject(error.localizedDescription)
        }
    }

    private func resolveCall(_ call: CAPPluginCall) {
        call.resolve()
    }

    private func resolveCall(_ call: CAPPluginCall, _ result: Result?) {
        if let result = result?.toJSObject() as? JSObject {
            call.resolve(result)
        } else {
            call.resolve()
        }
    }
}
