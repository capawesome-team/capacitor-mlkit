package io.capawesome.capacitorjs.plugins.mlkit.digitalinkrecognition.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import com.google.mlkit.vision.digitalink.recognition.Ink;
import com.google.mlkit.vision.digitalink.recognition.WritingArea;
import org.json.JSONArray;
import org.json.JSONObject;

public class RecognizeOptions {

    private static final String ERROR_LANGUAGE_TAG_MISSING = "languageTag must be provided.";
    private static final String ERROR_STROKES_MISSING = "strokes must be provided.";

    private static final int DEFAULT_MAX_RESULT_COUNT = 5;

    @NonNull
    private final Ink ink;

    @NonNull
    private final String languageTag;

    private final int maxResultCount;

    @Nullable
    private final String preContext;

    @Nullable
    private final WritingArea writingArea;

    public RecognizeOptions(@NonNull PluginCall call) throws Exception {
        this.ink = RecognizeOptions.getInkFromCall(call);
        this.languageTag = RecognizeOptions.getLanguageTagFromCall(call);
        this.maxResultCount = RecognizeOptions.getMaxResultCountFromCall(call);
        this.preContext = call.getString("preContext");
        this.writingArea = RecognizeOptions.getWritingAreaFromCall(call);
    }

    @NonNull
    public Ink getInk() {
        return ink;
    }

    @NonNull
    public String getLanguageTag() {
        return languageTag;
    }

    public int getMaxResultCount() {
        return maxResultCount;
    }

    @Nullable
    public String getPreContext() {
        return preContext;
    }

    @Nullable
    public WritingArea getWritingArea() {
        return writingArea;
    }

    @NonNull
    private static Ink getInkFromCall(@NonNull PluginCall call) throws Exception {
        JSArray strokes = call.getArray("strokes");
        if (strokes == null) {
            throw new Exception(ERROR_STROKES_MISSING);
        }
        Ink.Builder inkBuilder = Ink.builder();
        for (int strokeIndex = 0; strokeIndex < strokes.length(); strokeIndex++) {
            JSONObject stroke = strokes.getJSONObject(strokeIndex);
            JSONArray points = stroke.getJSONArray("points");
            Ink.Stroke.Builder strokeBuilder = Ink.Stroke.builder();
            for (int pointIndex = 0; pointIndex < points.length(); pointIndex++) {
                JSONObject point = points.getJSONObject(pointIndex);
                float x = (float) point.getDouble("x");
                float y = (float) point.getDouble("y");
                if (point.has("t")) {
                    strokeBuilder.addPoint(Ink.Point.create(x, y, point.getLong("t")));
                } else {
                    strokeBuilder.addPoint(Ink.Point.create(x, y));
                }
            }
            inkBuilder.addStroke(strokeBuilder.build());
        }
        return inkBuilder.build();
    }

    @NonNull
    private static String getLanguageTagFromCall(@NonNull PluginCall call) throws Exception {
        String languageTag = call.getString("languageTag");
        if (languageTag == null) {
            throw new Exception(ERROR_LANGUAGE_TAG_MISSING);
        }
        return languageTag;
    }

    private static int getMaxResultCountFromCall(@NonNull PluginCall call) {
        Integer maxResultCount = call.getInt("maxResultCount");
        if (maxResultCount == null) {
            return DEFAULT_MAX_RESULT_COUNT;
        }
        return maxResultCount;
    }

    @Nullable
    private static WritingArea getWritingAreaFromCall(@NonNull PluginCall call) throws Exception {
        JSObject writingArea = call.getObject("writingArea", null);
        if (writingArea == null) {
            return null;
        }
        float width = (float) writingArea.getDouble("width");
        float height = (float) writingArea.getDouble("height");
        return new WritingArea(width, height);
    }
}
