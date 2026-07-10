import Foundation
import Capacitor
import MLKitEntityExtraction

@objc public class ExtractEntitiesOptions: NSObject {
    let text: String
    let modelIdentifier: EntityExtractionModelIdentifier
    let referenceTime: Date?
    let referenceTimeZone: TimeZone?
    let preferredLocale: Locale?
    let entityTypesFilter: Set<EntityType>?

    init(_ call: CAPPluginCall) throws {
        self.text = try ExtractEntitiesOptions.getTextFromCall(call)
        self.modelIdentifier = try ExtractEntitiesOptions.getModelIdentifierFromCall(call)
        self.referenceTime = ExtractEntitiesOptions.getReferenceTimeFromCall(call)
        self.referenceTimeZone = ExtractEntitiesOptions.getReferenceTimeZoneFromCall(call)
        self.preferredLocale = ExtractEntitiesOptions.getPreferredLocaleFromCall(call)
        self.entityTypesFilter = try ExtractEntitiesOptions.getEntityTypesFilterFromCall(call)
    }

    private static func getEntityTypesFilterFromCall(_ call: CAPPluginCall) throws -> Set<EntityType>? {
        guard let entityTypes = call.getArray("entityTypes") as? [String] else {
            return nil
        }
        var entityTypesFilter = Set<EntityType>()
        for entityType in entityTypes {
            entityTypesFilter.insert(try EntityExtractionHelper.convertEntityTypeToFilter(entityType))
        }
        return entityTypesFilter
    }

    private static func getModelIdentifierFromCall(_ call: CAPPluginCall) throws -> EntityExtractionModelIdentifier {
        guard let language = call.getString("language") else {
            throw CustomError.languageMissing
        }
        return try EntityExtractionHelper.convertLanguageToModelIdentifier(language)
    }

    private static func getPreferredLocaleFromCall(_ call: CAPPluginCall) -> Locale? {
        guard let preferredLocale = call.getString("preferredLocale") else {
            return nil
        }
        return Locale(identifier: preferredLocale)
    }

    private static func getReferenceTimeFromCall(_ call: CAPPluginCall) -> Date? {
        guard let referenceTime = call.getDouble("referenceTime") else {
            return nil
        }
        return Date(timeIntervalSince1970: referenceTime / 1000)
    }

    private static func getReferenceTimeZoneFromCall(_ call: CAPPluginCall) -> TimeZone? {
        guard let referenceTimeZone = call.getString("referenceTimeZone") else {
            return nil
        }
        return TimeZone(identifier: referenceTimeZone)
    }

    private static func getTextFromCall(_ call: CAPPluginCall) throws -> String {
        guard let text = call.getString("text") else {
            throw CustomError.textMissing
        }
        return text
    }
}
