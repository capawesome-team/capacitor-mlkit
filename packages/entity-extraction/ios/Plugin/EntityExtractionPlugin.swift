import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(EntityExtractionPlugin)
public class EntityExtractionPlugin: CAPPlugin {
    public static let tag = "EntityExtraction"

    private var implementation: EntityExtraction?

    override public func load() {
        implementation = EntityExtraction()
    }

    @objc func deleteDownloadedModel(_ call: CAPPluginCall) {
        do {
            let options = try DeleteDownloadedModelOptions(call)

            implementation?.deleteDownloadedModel(options, completion: { error in
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

            implementation?.downloadModel(options, completion: { error in
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

    @objc func extractEntities(_ call: CAPPluginCall) {
        do {
            let options = try ExtractEntitiesOptions(call)

            implementation?.extractEntities(options, completion: { result, error in
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

    @objc func getDownloadedModels(_ call: CAPPluginCall) {
        let modelIdentifiers = implementation?.getDownloadedModels() ?? []
        let result = GetDownloadedModelsResult(modelIdentifiers: modelIdentifiers)
        self.resolveCall(call, result)
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", EntityExtractionPlugin.tag, "] ", error)
        let code = (error as? CustomError)?.code
        call.reject(error.localizedDescription, code)
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
