import Foundation
import MLKitEntityExtraction

// swiftlint:disable cyclomatic_complexity
@objc public class EntityExtractionHelper: NSObject {
    static let allModelIdentifiers: [EntityExtractionModelIdentifier] = [
        .arabic, .chinese, .dutch, .english, .french, .german, .italian, .japanese,
        .korean, .polish, .portuguese, .russian, .spanish, .thai, .turkish
    ]

    static func convertLanguageToModelIdentifier(_ language: String) throws -> EntityExtractionModelIdentifier {
        switch language {
        case "ARABIC":
            return .arabic
        case "CHINESE":
            return .chinese
        case "DUTCH":
            return .dutch
        case "ENGLISH":
            return .english
        case "FRENCH":
            return .french
        case "GERMAN":
            return .german
        case "ITALIAN":
            return .italian
        case "JAPANESE":
            return .japanese
        case "KOREAN":
            return .korean
        case "POLISH":
            return .polish
        case "PORTUGUESE":
            return .portuguese
        case "RUSSIAN":
            return .russian
        case "SPANISH":
            return .spanish
        case "THAI":
            return .thai
        case "TURKISH":
            return .turkish
        default:
            throw CustomError.languageInvalid
        }
    }

    static func convertModelIdentifierToLanguage(_ modelIdentifier: EntityExtractionModelIdentifier) -> String {
        switch modelIdentifier {
        case .arabic:
            return "ARABIC"
        case .chinese:
            return "CHINESE"
        case .dutch:
            return "DUTCH"
        case .french:
            return "FRENCH"
        case .german:
            return "GERMAN"
        case .italian:
            return "ITALIAN"
        case .japanese:
            return "JAPANESE"
        case .korean:
            return "KOREAN"
        case .polish:
            return "POLISH"
        case .portuguese:
            return "PORTUGUESE"
        case .russian:
            return "RUSSIAN"
        case .spanish:
            return "SPANISH"
        case .thai:
            return "THAI"
        case .turkish:
            return "TURKISH"
        default:
            return "ENGLISH"
        }
    }

    static func convertEntityTypeToFilter(_ entityType: String) throws -> EntityType {
        switch entityType {
        case "ADDRESS":
            return .address
        case "DATE_TIME":
            return .dateTime
        case "EMAIL":
            return .email
        case "FLIGHT_NUMBER":
            return .flightNumber
        case "IBAN":
            return .IBAN
        case "ISBN":
            return .ISBN
        case "MONEY":
            return .money
        case "PAYMENT_CARD":
            return .paymentCard
        case "PHONE":
            return .phone
        case "TRACKING_NUMBER":
            return .trackingNumber
        case "URL":
            return .URL
        default:
            throw CustomError.entityTypeInvalid
        }
    }

    static func convertEntityTypeToString(_ entityType: EntityType) -> String {
        switch entityType {
        case .address:
            return "ADDRESS"
        case .dateTime:
            return "DATE_TIME"
        case .email:
            return "EMAIL"
        case .flightNumber:
            return "FLIGHT_NUMBER"
        case .IBAN:
            return "IBAN"
        case .ISBN:
            return "ISBN"
        case .money:
            return "MONEY"
        case .paymentCard:
            return "PAYMENT_CARD"
        case .phone:
            return "PHONE"
        case .trackingNumber:
            return "TRACKING_NUMBER"
        default:
            return "URL"
        }
    }

    static func convertDateTimeGranularityToString(_ granularity: DateTimeGranularity) -> String {
        switch granularity {
        case .year:
            return "YEAR"
        case .month:
            return "MONTH"
        case .week:
            return "WEEK"
        case .day:
            return "DAY"
        case .hour:
            return "HOUR"
        case .minute:
            return "MINUTE"
        case .second:
            return "SECOND"
        default:
            return "UNKNOWN"
        }
    }

    static func convertPaymentCardNetworkToString(_ network: PaymentCardNetwork) -> String {
        switch network {
        case .amex:
            return "AMEX"
        case .dinersClub:
            return "DINERS_CLUB"
        case .discover:
            return "DISCOVER"
        case .interPayment:
            return "INTER_PAYMENT"
        case .JCB:
            return "JCB"
        case .maestro:
            return "MAESTRO"
        case .mastercard:
            return "MASTERCARD"
        case .mir:
            return "MIR"
        case .troy:
            return "TROY"
        case .unionpay:
            return "UNIONPAY"
        case .visa:
            return "VISA"
        default:
            return "UNKNOWN"
        }
    }

    static func convertParcelCarrierToString(_ carrier: ParcelTrackingCarrier) -> String {
        switch carrier {
        case .fedEx:
            return "FEDEX"
        case .UPS:
            return "UPS"
        case .DHL:
            return "DHL"
        case .USPS:
            return "USPS"
        case .ontrac:
            return "ONTRAC"
        case .lasership:
            return "LASERSHIP"
        case .israelPost:
            return "ISRAEL_POST"
        case .swissPost:
            return "SWISS_POST"
        case .MSC:
            return "MSC"
        case .amazon:
            return "AMAZON"
        case .iParcel:
            return "I_PARCEL"
        default:
            return "UNKNOWN"
        }
    }
}
