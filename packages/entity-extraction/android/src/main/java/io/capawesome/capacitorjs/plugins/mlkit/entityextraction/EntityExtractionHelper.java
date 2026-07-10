package io.capawesome.capacitorjs.plugins.mlkit.entityextraction;

import androidx.annotation.NonNull;
import com.google.mlkit.nl.entityextraction.DateTimeEntity;
import com.google.mlkit.nl.entityextraction.Entity;
import com.google.mlkit.nl.entityextraction.EntityExtractorOptions;
import com.google.mlkit.nl.entityextraction.PaymentCardEntity;
import com.google.mlkit.nl.entityextraction.TrackingNumberEntity;
import java.util.Locale;

public class EntityExtractionHelper {

    private static final String ERROR_ENTITY_TYPE_INVALID = "entity type is invalid.";
    private static final String ERROR_LANGUAGE_INVALID = "language is invalid.";

    @NonNull
    public static String convertLanguageToModelIdentifier(@NonNull String language) throws Exception {
        switch (language) {
            case "ARABIC":
                return EntityExtractorOptions.ARABIC;
            case "CHINESE":
                return EntityExtractorOptions.CHINESE;
            case "DUTCH":
                return EntityExtractorOptions.DUTCH;
            case "ENGLISH":
                return EntityExtractorOptions.ENGLISH;
            case "FRENCH":
                return EntityExtractorOptions.FRENCH;
            case "GERMAN":
                return EntityExtractorOptions.GERMAN;
            case "ITALIAN":
                return EntityExtractorOptions.ITALIAN;
            case "JAPANESE":
                return EntityExtractorOptions.JAPANESE;
            case "KOREAN":
                return EntityExtractorOptions.KOREAN;
            case "POLISH":
                return EntityExtractorOptions.POLISH;
            case "PORTUGUESE":
                return EntityExtractorOptions.PORTUGUESE;
            case "RUSSIAN":
                return EntityExtractorOptions.RUSSIAN;
            case "SPANISH":
                return EntityExtractorOptions.SPANISH;
            case "THAI":
                return EntityExtractorOptions.THAI;
            case "TURKISH":
                return EntityExtractorOptions.TURKISH;
            default:
                throw new Exception(ERROR_LANGUAGE_INVALID);
        }
    }

    @NonNull
    public static String convertModelIdentifierToLanguage(@NonNull String modelIdentifier) {
        // The model identifiers (e.g. `EntityExtractorOptions.ENGLISH` = "english") are the
        // lowercase form of the corresponding `Language` enum values (e.g. "ENGLISH").
        return modelIdentifier.toUpperCase(Locale.ROOT);
    }

    public static int convertEntityTypeToFilterValue(@NonNull String entityType) throws Exception {
        switch (entityType) {
            case "ADDRESS":
                return Entity.TYPE_ADDRESS;
            case "DATE_TIME":
                return Entity.TYPE_DATE_TIME;
            case "EMAIL":
                return Entity.TYPE_EMAIL;
            case "FLIGHT_NUMBER":
                return Entity.TYPE_FLIGHT_NUMBER;
            case "IBAN":
                return Entity.TYPE_IBAN;
            case "ISBN":
                return Entity.TYPE_ISBN;
            case "MONEY":
                return Entity.TYPE_MONEY;
            case "PAYMENT_CARD":
                return Entity.TYPE_PAYMENT_CARD;
            case "PHONE":
                return Entity.TYPE_PHONE;
            case "TRACKING_NUMBER":
                return Entity.TYPE_TRACKING_NUMBER;
            case "URL":
                return Entity.TYPE_URL;
            default:
                throw new Exception(ERROR_ENTITY_TYPE_INVALID);
        }
    }

    @NonNull
    public static String convertEntityTypeToString(int type) {
        switch (type) {
            case Entity.TYPE_ADDRESS:
                return "ADDRESS";
            case Entity.TYPE_DATE_TIME:
                return "DATE_TIME";
            case Entity.TYPE_EMAIL:
                return "EMAIL";
            case Entity.TYPE_FLIGHT_NUMBER:
                return "FLIGHT_NUMBER";
            case Entity.TYPE_IBAN:
                return "IBAN";
            case Entity.TYPE_ISBN:
                return "ISBN";
            case Entity.TYPE_MONEY:
                return "MONEY";
            case Entity.TYPE_PAYMENT_CARD:
                return "PAYMENT_CARD";
            case Entity.TYPE_PHONE:
                return "PHONE";
            case Entity.TYPE_TRACKING_NUMBER:
                return "TRACKING_NUMBER";
            default:
                return "URL";
        }
    }

    @NonNull
    public static String convertDateTimeGranularityToString(int granularity) {
        switch (granularity) {
            case DateTimeEntity.GRANULARITY_YEAR:
                return "YEAR";
            case DateTimeEntity.GRANULARITY_MONTH:
                return "MONTH";
            case DateTimeEntity.GRANULARITY_WEEK:
                return "WEEK";
            case DateTimeEntity.GRANULARITY_DAY:
                return "DAY";
            case DateTimeEntity.GRANULARITY_HOUR:
                return "HOUR";
            case DateTimeEntity.GRANULARITY_MINUTE:
                return "MINUTE";
            case DateTimeEntity.GRANULARITY_SECOND:
                return "SECOND";
            default:
                return "UNKNOWN";
        }
    }

    @NonNull
    public static String convertPaymentCardNetworkToString(int network) {
        switch (network) {
            case PaymentCardEntity.CARD_AMEX:
                return "AMEX";
            case PaymentCardEntity.CARD_DINERS_CLUB:
                return "DINERS_CLUB";
            case PaymentCardEntity.CARD_DISCOVER:
                return "DISCOVER";
            case PaymentCardEntity.CARD_INTER_PAYMENT:
                return "INTER_PAYMENT";
            case PaymentCardEntity.CARD_JCB:
                return "JCB";
            case PaymentCardEntity.CARD_MAESTRO:
                return "MAESTRO";
            case PaymentCardEntity.CARD_MASTERCARD:
                return "MASTERCARD";
            case PaymentCardEntity.CARD_MIR:
                return "MIR";
            case PaymentCardEntity.CARD_TROY:
                return "TROY";
            case PaymentCardEntity.CARD_UNIONPAY:
                return "UNIONPAY";
            case PaymentCardEntity.CARD_VISA:
                return "VISA";
            default:
                return "UNKNOWN";
        }
    }

    @NonNull
    public static String convertParcelCarrierToString(int carrier) {
        switch (carrier) {
            case TrackingNumberEntity.CARRIER_FEDEX:
                return "FEDEX";
            case TrackingNumberEntity.CARRIER_UPS:
                return "UPS";
            case TrackingNumberEntity.CARRIER_DHL:
                return "DHL";
            case TrackingNumberEntity.CARRIER_USPS:
                return "USPS";
            case TrackingNumberEntity.CARRIER_ONTRAC:
                return "ONTRAC";
            case TrackingNumberEntity.CARRIER_LASERSHIP:
                return "LASERSHIP";
            case TrackingNumberEntity.CARRIER_ISRAEL_POST:
                return "ISRAEL_POST";
            case TrackingNumberEntity.CARRIER_SWISS_POST:
                return "SWISS_POST";
            case TrackingNumberEntity.CARRIER_MSC:
                return "MSC";
            case TrackingNumberEntity.CARRIER_AMAZON:
                return "AMAZON";
            case TrackingNumberEntity.CARRIER_I_PARCEL:
                return "I_PARCEL";
            default:
                return "UNKNOWN";
        }
    }
}
