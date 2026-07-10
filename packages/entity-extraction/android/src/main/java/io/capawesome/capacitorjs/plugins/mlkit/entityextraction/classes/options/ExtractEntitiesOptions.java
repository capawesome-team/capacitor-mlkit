package io.capawesome.capacitorjs.plugins.mlkit.entityextraction.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.mlkit.entityextraction.EntityExtractionHelper;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

public class ExtractEntitiesOptions {

    private static final String ERROR_LANGUAGE_MISSING = "language must be provided.";
    private static final String ERROR_TEXT_MISSING = "text must be provided.";

    @NonNull
    private final String text;

    @NonNull
    private final String modelIdentifier;

    @Nullable
    private final Long referenceTime;

    @Nullable
    private final TimeZone referenceTimeZone;

    @Nullable
    private final Locale preferredLocale;

    @Nullable
    private final Set<Integer> entityTypesFilter;

    public ExtractEntitiesOptions(@NonNull PluginCall call) throws Exception {
        this.text = ExtractEntitiesOptions.getTextFromCall(call);
        this.modelIdentifier = ExtractEntitiesOptions.getModelIdentifierFromCall(call);
        this.referenceTime = call.getLong("referenceTime");
        this.referenceTimeZone = ExtractEntitiesOptions.getReferenceTimeZoneFromCall(call);
        this.preferredLocale = ExtractEntitiesOptions.getPreferredLocaleFromCall(call);
        this.entityTypesFilter = ExtractEntitiesOptions.getEntityTypesFilterFromCall(call);
    }

    @Nullable
    public Set<Integer> getEntityTypesFilter() {
        return entityTypesFilter;
    }

    @NonNull
    public String getModelIdentifier() {
        return modelIdentifier;
    }

    @Nullable
    public Locale getPreferredLocale() {
        return preferredLocale;
    }

    @Nullable
    public Long getReferenceTime() {
        return referenceTime;
    }

    @Nullable
    public TimeZone getReferenceTimeZone() {
        return referenceTimeZone;
    }

    @NonNull
    public String getText() {
        return text;
    }

    @Nullable
    private static Set<Integer> getEntityTypesFilterFromCall(@NonNull PluginCall call) throws Exception {
        JSArray entityTypesArray = call.getArray("entityTypes");
        if (entityTypesArray == null) {
            return null;
        }
        List<String> entityTypes = entityTypesArray.toList();
        Set<Integer> entityTypesFilter = new HashSet<>();
        for (String entityType : entityTypes) {
            entityTypesFilter.add(EntityExtractionHelper.convertEntityTypeToFilterValue(entityType));
        }
        return entityTypesFilter;
    }

    @NonNull
    private static String getModelIdentifierFromCall(@NonNull PluginCall call) throws Exception {
        String language = call.getString("language");
        if (language == null) {
            throw new Exception(ERROR_LANGUAGE_MISSING);
        }
        return EntityExtractionHelper.convertLanguageToModelIdentifier(language);
    }

    @Nullable
    private static Locale getPreferredLocaleFromCall(@NonNull PluginCall call) {
        String preferredLocale = call.getString("preferredLocale");
        if (preferredLocale == null) {
            return null;
        }
        return Locale.forLanguageTag(preferredLocale);
    }

    @Nullable
    private static TimeZone getReferenceTimeZoneFromCall(@NonNull PluginCall call) {
        String referenceTimeZone = call.getString("referenceTimeZone");
        if (referenceTimeZone == null) {
            return null;
        }
        return TimeZone.getTimeZone(referenceTimeZone);
    }

    @NonNull
    private static String getTextFromCall(@NonNull PluginCall call) throws Exception {
        String text = call.getString("text");
        if (text == null) {
            throw new Exception(ERROR_TEXT_MISSING);
        }
        return text;
    }
}
