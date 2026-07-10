import Foundation
import Capacitor
import MLKitEntityExtraction

@objc public class ExtractEntitiesResult: NSObject, Result {
    let text: String
    let annotations: [EntityAnnotation]

    init(text: String, annotations: [EntityAnnotation]) {
        self.text = text
        self.annotations = annotations
    }

    @objc public func toJSObject() -> AnyObject {
        let nsText = text as NSString
        var annotationsResult = JSArray()
        for annotation in annotations {
            annotationsResult.append(ExtractEntitiesResult.createAnnotationResult(annotation, text: nsText))
        }
        var result = JSObject()
        result["annotations"] = annotationsResult
        return result as AnyObject
    }

    private static func createAnnotationResult(_ annotation: EntityAnnotation, text: NSString) -> JSObject {
        var entitiesResult = JSArray()
        for entity in annotation.entities {
            entitiesResult.append(ExtractEntitiesResult.createEntityResult(entity))
        }
        var result = JSObject()
        result["text"] = text.substring(with: annotation.range)
        result["start"] = annotation.range.location
        result["end"] = annotation.range.location + annotation.range.length
        result["entities"] = entitiesResult
        return result
    }

    private static func createEntityResult(_ entity: Entity) -> JSObject {
        var result = JSObject()
        result["type"] = EntityExtractionHelper.convertEntityTypeToString(entity.entityType)

        if let dateTimeEntity = entity.dateTimeEntity {
            result["dateTimeGranularity"] = EntityExtractionHelper.convertDateTimeGranularityToString(
                dateTimeEntity.dateTimeGranularity)
            result["timestamp"] = Int(dateTimeEntity.dateTime.timeIntervalSince1970 * 1000)
        }

        if let moneyEntity = entity.moneyEntity {
            result["unnormalizedCurrency"] = moneyEntity.unnormalizedCurrency
            result["integerPart"] = moneyEntity.integerPart
            result["fractionalPart"] = moneyEntity.fractionalPart
        }

        if let paymentCardEntity = entity.paymentCardEntity {
            result["paymentCardNetwork"] = EntityExtractionHelper.convertPaymentCardNetworkToString(
                paymentCardEntity.paymentCardNetwork)
            result["paymentCardNumber"] = paymentCardEntity.paymentCardNumber
        }

        if let flightNumberEntity = entity.flightNumberEntity {
            result["airlineCode"] = flightNumberEntity.airlineCode
            result["flightNumber"] = flightNumberEntity.flightNumber
        }

        if let isbnEntity = entity.isbnEntity {
            result["isbn"] = isbnEntity.isbn
        }

        if let ibanEntity = entity.ibanEntity {
            result["iban"] = ibanEntity.iban
            result["ibanCountryCode"] = ibanEntity.countryCode
        }

        if let trackingNumberEntity = entity.trackingNumberEntity {
            result["parcelCarrier"] = EntityExtractionHelper.convertParcelCarrierToString(
                trackingNumberEntity.parcelCarrier)
            result["parcelTrackingNumber"] = trackingNumberEntity.parcelTrackingNumber
        }

        return result
    }
}
