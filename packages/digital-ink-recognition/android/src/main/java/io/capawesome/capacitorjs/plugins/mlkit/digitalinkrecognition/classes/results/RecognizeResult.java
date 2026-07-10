package io.capawesome.capacitorjs.plugins.mlkit.digitalinkrecognition.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.google.mlkit.vision.digitalink.common.RecognitionCandidate;
import com.google.mlkit.vision.digitalink.common.RecognitionResult;
import io.capawesome.capacitorjs.plugins.mlkit.digitalinkrecognition.interfaces.Result;

public class RecognizeResult implements Result {

    @NonNull
    private final RecognitionResult recognitionResult;

    public RecognizeResult(@NonNull RecognitionResult recognitionResult) {
        this.recognitionResult = recognitionResult;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSArray candidatesResult = new JSArray();
        for (RecognitionCandidate candidate : recognitionResult.getCandidates()) {
            JSObject candidateResult = new JSObject();
            candidateResult.put("text", candidate.getText());
            Float score = candidate.getScore();
            if (score != null) {
                candidateResult.put("score", score);
            }
            candidatesResult.put(candidateResult);
        }
        JSObject result = new JSObject();
        result.put("candidates", candidatesResult);
        return result;
    }
}
