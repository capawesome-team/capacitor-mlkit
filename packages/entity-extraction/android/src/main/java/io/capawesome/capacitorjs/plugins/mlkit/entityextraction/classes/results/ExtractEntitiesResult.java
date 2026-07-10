package io.capawesome.capacitorjs.plugins.mlkit.entityextraction.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.google.mlkit.nl.entityextraction.DateTimeEntity;
import com.google.mlkit.nl.entityextraction.Entity;
import com.google.mlkit.nl.entityextraction.EntityAnnotation;
import com.google.mlkit.nl.entityextraction.FlightNumberEntity;
import com.google.mlkit.nl.entityextraction.IbanEntity;
import com.google.mlkit.nl.entityextraction.IsbnEntity;
import com.google.mlkit.nl.entityextraction.MoneyEntity;
import com.google.mlkit.nl.entityextraction.PaymentCardEntity;
import com.google.mlkit.nl.entityextraction.TrackingNumberEntity;
import io.capawesome.capacitorjs.plugins.mlkit.entityextraction.EntityExtractionHelper;
import io.capawesome.capacitorjs.plugins.mlkit.entityextraction.interfaces.Result;
import java.util.List;

public class ExtractEntitiesResult implements Result {

    @NonNull
    private final List<EntityAnnotation> annotations;

    public ExtractEntitiesResult(@NonNull List<EntityAnnotation> annotations) {
        this.annotations = annotations;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSArray annotationsArray = new JSArray();
        for (EntityAnnotation annotation : annotations) {
            annotationsArray.put(createAnnotationResult(annotation));
        }
        JSObject result = new JSObject();
        result.put("annotations", annotationsArray);
        return result;
    }

    @NonNull
    private static JSObject createAnnotationResult(@NonNull EntityAnnotation annotation) {
        JSArray entitiesArray = new JSArray();
        for (Entity entity : annotation.getEntities()) {
            entitiesArray.put(createEntityResult(entity));
        }
        JSObject result = new JSObject();
        result.put("text", annotation.getAnnotatedText());
        result.put("start", annotation.getStart());
        result.put("end", annotation.getEnd());
        result.put("entities", entitiesArray);
        return result;
    }

    @NonNull
    private static JSObject createEntityResult(@NonNull Entity entity) {
        JSObject result = new JSObject();
        result.put("type", EntityExtractionHelper.convertEntityTypeToString(entity.getType()));

        DateTimeEntity dateTimeEntity = entity.asDateTimeEntity();
        if (dateTimeEntity != null) {
            result.put(
                "dateTimeGranularity",
                EntityExtractionHelper.convertDateTimeGranularityToString(dateTimeEntity.getDateTimeGranularity())
            );
            result.put("timestamp", dateTimeEntity.getTimestampMillis());
        }

        MoneyEntity moneyEntity = entity.asMoneyEntity();
        if (moneyEntity != null) {
            result.put("unnormalizedCurrency", moneyEntity.getUnnormalizedCurrency());
            result.put("integerPart", moneyEntity.getIntegerPart());
            result.put("fractionalPart", moneyEntity.getFractionalPart());
        }

        PaymentCardEntity paymentCardEntity = entity.asPaymentCardEntity();
        if (paymentCardEntity != null) {
            result.put(
                "paymentCardNetwork",
                EntityExtractionHelper.convertPaymentCardNetworkToString(paymentCardEntity.getPaymentCardNetwork())
            );
            result.put("paymentCardNumber", paymentCardEntity.getPaymentCardNumber());
        }

        FlightNumberEntity flightNumberEntity = entity.asFlightNumberEntity();
        if (flightNumberEntity != null) {
            result.put("airlineCode", flightNumberEntity.getAirlineCode());
            result.put("flightNumber", flightNumberEntity.getFlightNumber());
        }

        IsbnEntity isbnEntity = entity.asIsbnEntity();
        if (isbnEntity != null) {
            result.put("isbn", isbnEntity.getIsbn());
        }

        IbanEntity ibanEntity = entity.asIbanEntity();
        if (ibanEntity != null) {
            result.put("iban", ibanEntity.getIban());
            result.put("ibanCountryCode", ibanEntity.getIbanCountryCode());
        }

        TrackingNumberEntity trackingNumberEntity = entity.asTrackingNumberEntity();
        if (trackingNumberEntity != null) {
            result.put("parcelCarrier", EntityExtractionHelper.convertParcelCarrierToString(trackingNumberEntity.getParcelCarrier()));
            result.put("parcelTrackingNumber", trackingNumberEntity.getParcelTrackingNumber());
        }

        return result;
    }
}
