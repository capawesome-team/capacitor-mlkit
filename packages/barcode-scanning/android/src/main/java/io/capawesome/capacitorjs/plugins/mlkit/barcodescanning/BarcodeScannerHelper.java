/**
 * Copyright (c) 2023 Robin Genz
 */
package io.capawesome.capacitorjs.plugins.mlkit.barcodescanning;

import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;
import org.json.JSONArray;
import org.json.JSONException;

public class BarcodeScannerHelper {

    public static JSObject createBarcodeResultForBarcode(@NonNull Barcode barcode, @Nullable Point imageSize, @Nullable Point screenSize) {
        Point[] cornerPoints = barcode.getCornerPoints();
        JSArray cornerPointsResult = new JSArray();
        if (cornerPoints != null && imageSize != null && screenSize != null) {
            Point[] normalizedCornerPoints = normalizeCornerPoints(cornerPoints, imageSize, screenSize);
            for (int i = 0; i < normalizedCornerPoints.length; i++) {
                JSArray cornerPointResult = new JSArray();
                cornerPointResult.put(normalizedCornerPoints[i].x);
                cornerPointResult.put(normalizedCornerPoints[i].y);
                cornerPointsResult.put(cornerPointResult);
            }
        }

        JSObject result = new JSObject();
        result.put("bytes", convertByteArrayToJsonArray(barcode.getRawBytes()));
        if (cornerPoints != null) {
            result.put("cornerPoints", cornerPointsResult);
        }
        result.put("displayValue", barcode.getDisplayValue());
        result.put("format", convertBarcodeScannerFormatToString(barcode.getFormat()));
        result.put("rawValue", barcode.getRawValue());
        result.put("valueType", convertBarcodeValueTypeToString(barcode.getValueType()));
        return result;
    }

    public static int[] convertStringsToBarcodeScannerFormats(String[] values) throws JSONException {
        int[] formats = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            Integer format = convertStringToBarcodeScannerFormat(values[i]);
            if (format == null) {
                continue;
            }
            formats[i] = format;
        }
        return formats;
    }

    @Nullable
    public static Integer convertStringToBarcodeScannerFormat(String value) {
        switch (value) {
            case "AZTEC":
                return Barcode.FORMAT_AZTEC;
            case "CODABAR":
                return Barcode.FORMAT_CODABAR;
            case "CODE_39":
                return Barcode.FORMAT_CODE_39;
            case "CODE_93":
                return Barcode.FORMAT_CODE_93;
            case "CODE_128":
                return Barcode.FORMAT_CODE_128;
            case "DATA_MATRIX":
                return Barcode.FORMAT_DATA_MATRIX;
            case "EAN_8":
                return Barcode.FORMAT_EAN_8;
            case "EAN_13":
                return Barcode.FORMAT_EAN_13;
            case "ITF":
                return Barcode.FORMAT_ITF;
            case "PDF_417":
                return Barcode.FORMAT_PDF417;
            case "QR_CODE":
                return Barcode.FORMAT_QR_CODE;
            case "UPC_A":
                return Barcode.FORMAT_UPC_A;
            case "UPC_E":
                return Barcode.FORMAT_UPC_E;
            default:
                return null;
        }
    }

    @Nullable
    public static String convertBarcodeScannerFormatToString(int format) {
        switch (format) {
            case Barcode.FORMAT_AZTEC:
                return "AZTEC";
            case Barcode.FORMAT_CODABAR:
                return "CODABAR";
            case Barcode.FORMAT_CODE_39:
                return "CODE_39";
            case Barcode.FORMAT_CODE_93:
                return "CODE_93";
            case Barcode.FORMAT_CODE_128:
                return "CODE_128";
            case Barcode.FORMAT_DATA_MATRIX:
                return "DATA_MATRIX";
            case Barcode.FORMAT_EAN_8:
                return "EAN_8";
            case Barcode.FORMAT_EAN_13:
                return "EAN_13";
            case Barcode.FORMAT_ITF:
                return "ITF";
            case Barcode.FORMAT_PDF417:
                return "PDF_417";
            case Barcode.FORMAT_QR_CODE:
                return "QR_CODE";
            case Barcode.FORMAT_UPC_A:
                return "UPC_A";
            case Barcode.FORMAT_UPC_E:
                return "UPC_E";
            default:
                return null;
        }
    }

    @Nullable
    public static String convertBarcodeValueTypeToString(int format) {
        switch (format) {
            case Barcode.TYPE_CALENDAR_EVENT:
                return "CALENDAR_EVENT";
            case Barcode.TYPE_CONTACT_INFO:
                return "CONTACT_INFO";
            case Barcode.TYPE_DRIVER_LICENSE:
                return "DRIVERS_LICENSE";
            case Barcode.TYPE_EMAIL:
                return "EMAIL";
            case Barcode.TYPE_GEO:
                return "GEO";
            case Barcode.TYPE_ISBN:
                return "ISBN";
            case Barcode.TYPE_PHONE:
                return "PHONE";
            case Barcode.TYPE_PRODUCT:
                return "PRODUCT";
            case Barcode.TYPE_SMS:
                return "SMS";
            case Barcode.TYPE_TEXT:
                return "TEXT";
            case Barcode.TYPE_URL:
                return "URL";
            case Barcode.TYPE_WIFI:
                return "WIFI";
            default:
                return "UNKNOWN";
        }
    }

    private static JSONArray convertByteArrayToJsonArray(byte[] bytes) {
        JSONArray ret = new JSONArray();
        for (byte _byte : bytes) {
            ret.put(_byte);
        }
        return ret;
    }

    private static Point[] normalizeCornerPoints(@NonNull Point[] cornerPoints, @NonNull Point imageSize, @NonNull Point screenSize) {
        double screenWidth = screenSize.x;
        double screenHeight = screenSize.y;
        double imageWidth = imageSize.x;
        double imageHeight = imageSize.y;
        if (screenWidth > screenHeight) {
            imageWidth = imageSize.y;
            imageHeight = imageSize.x;
        }
        double scale = Math.max(screenHeight / imageWidth, screenWidth / imageHeight);
        double invisibleWidth = imageHeight * scale - screenWidth;
        double invisibleHeight = imageWidth * scale - screenHeight;
        Point[] normalizedCornerPoints = new Point[cornerPoints.length];
        for (int i = 0; i < cornerPoints.length; i++) {
            int x = (int) ((cornerPoints[i].x * scale) - (invisibleWidth / 2));
            int y = (int) ((cornerPoints[i].y * scale) - (invisibleHeight / 2));
            normalizedCornerPoints[i] = new Point(x, y);
        }
        return normalizedCornerPoints;
    }
}
