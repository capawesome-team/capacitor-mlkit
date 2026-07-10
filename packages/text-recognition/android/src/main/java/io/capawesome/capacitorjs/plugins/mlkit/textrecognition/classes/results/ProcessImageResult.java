package io.capawesome.capacitorjs.plugins.mlkit.textrecognition.classes.results;

import android.graphics.Point;
import android.graphics.Rect;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.google.mlkit.vision.text.Text;
import io.capawesome.capacitorjs.plugins.mlkit.textrecognition.interfaces.Result;
import java.util.List;

public class ProcessImageResult implements Result {

    @NonNull
    private final Text text;

    public ProcessImageResult(@NonNull Text text) {
        this.text = text;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("text", text.getText());
        result.put("blocks", createBlocksResult(text.getTextBlocks()));
        return result;
    }

    private JSArray createBlocksResult(@NonNull List<Text.TextBlock> blocks) {
        JSArray result = new JSArray();
        for (Text.TextBlock block : blocks) {
            JSObject blockResult = new JSObject();
            blockResult.put("text", block.getText());
            putBoundingBox(blockResult, block.getBoundingBox());
            putCornerPoints(blockResult, block.getCornerPoints());
            putRecognizedLanguage(blockResult, block.getRecognizedLanguage());
            blockResult.put("lines", createLinesResult(block.getLines()));
            result.put(blockResult);
        }
        return result;
    }

    private JSArray createLinesResult(@NonNull List<Text.Line> lines) {
        JSArray result = new JSArray();
        for (Text.Line line : lines) {
            JSObject lineResult = new JSObject();
            lineResult.put("text", line.getText());
            putBoundingBox(lineResult, line.getBoundingBox());
            putCornerPoints(lineResult, line.getCornerPoints());
            putRecognizedLanguage(lineResult, line.getRecognizedLanguage());
            lineResult.put("elements", createElementsResult(line.getElements()));
            result.put(lineResult);
        }
        return result;
    }

    private JSArray createElementsResult(@NonNull List<Text.Element> elements) {
        JSArray result = new JSArray();
        for (Text.Element element : elements) {
            JSObject elementResult = new JSObject();
            elementResult.put("text", element.getText());
            putBoundingBox(elementResult, element.getBoundingBox());
            putCornerPoints(elementResult, element.getCornerPoints());
            putRecognizedLanguage(elementResult, element.getRecognizedLanguage());
            result.put(elementResult);
        }
        return result;
    }

    private void putBoundingBox(@NonNull JSObject target, @Nullable Rect boundingBox) {
        if (boundingBox == null) {
            return;
        }
        JSObject result = new JSObject();
        result.put("left", boundingBox.left);
        result.put("top", boundingBox.top);
        result.put("right", boundingBox.right);
        result.put("bottom", boundingBox.bottom);
        target.put("boundingBox", result);
    }

    private void putCornerPoints(@NonNull JSObject target, @Nullable Point[] cornerPoints) {
        if (cornerPoints == null) {
            return;
        }
        JSArray result = new JSArray();
        for (Point cornerPoint : cornerPoints) {
            JSObject pointResult = new JSObject();
            pointResult.put("x", cornerPoint.x);
            pointResult.put("y", cornerPoint.y);
            result.put(pointResult);
        }
        target.put("cornerPoints", result);
    }

    private void putRecognizedLanguage(@NonNull JSObject target, @Nullable String recognizedLanguage) {
        if (recognizedLanguage == null || recognizedLanguage.isEmpty()) {
            return;
        }
        target.put("recognizedLanguage", recognizedLanguage);
    }
}
