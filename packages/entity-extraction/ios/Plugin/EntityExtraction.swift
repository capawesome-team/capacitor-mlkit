import Foundation
import MLKitCommon
import MLKitEntityExtraction

@objc public class EntityExtraction: NSObject {
    private var extractor: EntityExtractor?
    private var extractorModelIdentifier: EntityExtractionModelIdentifier?

    @objc public func deleteDownloadedModel(_ options: DeleteDownloadedModelOptions, completion: @escaping (_ error: Error?) -> Void) {
        let model = EntityExtractorRemoteModel.entityExtractorRemoteModel(identifier: options.modelIdentifier)
        ModelManager.modelManager().deleteDownloadedModel(model) { error in
            completion(error)
        }
    }

    @objc public func downloadModel(_ options: DownloadModelOptions, completion: @escaping (_ error: Error?) -> Void) {
        let extractor = getExtractor(options.modelIdentifier)
        extractor.downloadModelIfNeeded { error in
            completion(error)
        }
    }

    @objc public func extractEntities(_ options: ExtractEntitiesOptions, completion: @escaping (_ result: ExtractEntitiesResult?, _ error: Error?) -> Void) {
        let model = EntityExtractorRemoteModel.entityExtractorRemoteModel(identifier: options.modelIdentifier)
        if !ModelManager.modelManager().isModelDownloaded(model) {
            completion(nil, CustomError.modelNotDownloaded)
            return
        }
        let extractor = getExtractor(options.modelIdentifier)
        let params = createParams(options)
        extractor.annotateText(options.text, params: params) { annotations, error in
            if let error = error {
                completion(nil, error)
                return
            }
            completion(ExtractEntitiesResult(text: options.text, annotations: annotations ?? []), nil)
        }
    }

    @objc public func getDownloadedModels() -> [EntityExtractionModelIdentifier] {
        return ModelManager.modelManager().downloadedEntityExtractionModels.map { $0.modelIdentifier }
    }

    private func createParams(_ options: ExtractEntitiesOptions) -> EntityExtractionParams {
        let params = EntityExtractionParams()
        if let referenceTime = options.referenceTime {
            params.referenceTime = referenceTime
        }
        if let referenceTimeZone = options.referenceTimeZone {
            params.referenceTimeZone = referenceTimeZone
        }
        if let preferredLocale = options.preferredLocale {
            params.preferredLocale = preferredLocale
        }
        if let entityTypesFilter = options.entityTypesFilter {
            params.typesFilter = entityTypesFilter
        }
        return params
    }

    private func getExtractor(_ modelIdentifier: EntityExtractionModelIdentifier) -> EntityExtractor {
        if let extractor = extractor, modelIdentifier == extractorModelIdentifier {
            return extractor
        }
        let options = EntityExtractorOptions(modelIdentifier: modelIdentifier)
        let extractor = EntityExtractor.entityExtractor(options: options)
        self.extractor = extractor
        self.extractorModelIdentifier = modelIdentifier
        return extractor
    }
}
