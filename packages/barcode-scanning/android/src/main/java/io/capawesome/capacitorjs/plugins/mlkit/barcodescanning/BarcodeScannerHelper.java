/**
 * Copyright (c) 2023 Robin Genz
 */
package io.capawesome.capacitorjs.plugins.mlkit.barcodescanning;

import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Size;
import android.view.Display;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.google.mlkit.vision.barcode.common.Barcode;
import java.util.List;
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
        } else if (cornerPoints != null && screenSize == null) {
            for (Point cornerPoint : cornerPoints) {
                JSArray cornerPointResult = new JSArray();
                cornerPointResult.put(cornerPoint.x);
                cornerPointResult.put(cornerPoint.y);
                cornerPointsResult.put(cornerPointResult);
            }
        }

        JSObject result = new JSObject();
        result.put("bytes", convertByteArrayToJsonArray(barcode.getRawBytes()));
        if (cornerPoints != null) {
            result.put("cornerPoints", cornerPointsResult);
        }
        if (barcode.getCalendarEvent() != null) {
            result.put("calendarEvent", extractCalendarEventProperties(barcode.getCalendarEvent()));
        }
        if (barcode.getContactInfo() != null) {
            result.put("contactInfo", extractContactInfoProperties(barcode.getContactInfo()));
        }
        result.put("displayValue", barcode.getDisplayValue());
        if (barcode.getDriverLicense() != null) {
            result.put("driverLicense", extractDriverLicenseProperties(barcode.getDriverLicense()));
        }
        if (barcode.getEmail() != null) {
            result.put("email", extractEmailProperties(barcode.getEmail()));
        }
        result.put("format", convertBarcodeScannerFormatToString(barcode.getFormat()));
        if (barcode.getGeoPoint() != null) {
            result.put("geoPoint", extractGeoPointProperties(barcode.getGeoPoint()));
        }
        if (barcode.getPhone() != null) {
            result.put("phone", extractPhoneProperties(barcode.getPhone()));
        }
        result.put("rawValue", barcode.getRawValue() == null ? "" : barcode.getRawValue());
        if (barcode.getSms() != null) {
            result.put("sms", extractSmsProperties(barcode.getSms()));
        }
        if (barcode.getUrl() != null) {
            result.put("urlBookmark", extractUrlBookmark(barcode.getUrl()));
        }
        result.put("valueType", convertBarcodeValueTypeToString(barcode.getValueType()));
        if (barcode.getWifi() != null) {
            result.put("wifi", extractWifiProperties(barcode.getWifi()));
        }
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

    @Nullable
    private static JSONArray convertByteArrayToJsonArray(@Nullable byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        JSONArray ret = new JSONArray();
        for (byte _byte : bytes) {
            ret.put(_byte);
        }
        return ret;
    }

    private static String convertCalendarDateTimeToString(Barcode.CalendarDateTime dateTime) {
        int year = dateTime.getYear() == -1 ? 0 : dateTime.getYear();
        int month = dateTime.getMonth() == -1 ? 0 : dateTime.getMonth();
        int day = dateTime.getDay() == -1 ? 0 : dateTime.getDay();
        int hour = dateTime.getHours() == -1 ? 0 : dateTime.getHours();
        int minute = dateTime.getMinutes() == -1 ? 0 : dateTime.getMinutes();
        int second = dateTime.getSeconds() == -1 ? 0 : dateTime.getSeconds();
        boolean isUtc = dateTime.isUtc();
        return String.format("%04d-%02d-%02dT%02d:%02d:%02d%s", year, month, day, hour, minute, second, isUtc ? "Z" : "");
    }

    private static JSArray extractAddresses(List<Barcode.Address> addresses) {
        JSArray addressArray = new JSArray();
        for (Barcode.Address address : addresses) {
            JSObject addressObj = new JSObject();
            addressObj.put("addressLines", address.getAddressLines());
            addressObj.put("type", address.getType());
            addressArray.put(addressObj);
        }

        return addressArray;
    }

    private static JSObject extractCalendarEventProperties(Barcode.CalendarEvent calendarEvent) {
        JSObject result = new JSObject();

        result.put("description", calendarEvent.getDescription());
        result.put("end", convertCalendarDateTimeToString(calendarEvent.getEnd()));
        result.put("location", calendarEvent.getLocation());
        result.put("organizer", calendarEvent.getOrganizer());
        result.put("start", convertCalendarDateTimeToString(calendarEvent.getStart()));
        result.put("status", calendarEvent.getStatus());
        result.put("summary", calendarEvent.getSummary());

        return result;
    }

    private static JSObject extractContactInfoProperties(Barcode.ContactInfo contactInfo) {
        JSObject result = new JSObject();

        result.put("addresses", extractAddresses(contactInfo.getAddresses()));
        result.put("emails", extractEmails(contactInfo.getEmails()));
        result.put("name", extractPersonNameProperties(contactInfo.getName()));
        result.put("organization", contactInfo.getOrganization());
        result.put("phones", extractPhones(contactInfo.getPhones()));
        result.put("title", contactInfo.getTitle());
        result.put("urls", extractUrls(contactInfo.getUrls()));

        return result;
    }

    private static JSObject extractDriverLicenseProperties(Barcode.DriverLicense driverLicense) {
        JSObject result = new JSObject();

        result.put("addressCity", driverLicense.getAddressCity());
        result.put("addressState", driverLicense.getAddressState());
        result.put("addressStreet", driverLicense.getAddressStreet());
        result.put("addressZip", driverLicense.getAddressZip());
        result.put("birthDate", driverLicense.getBirthDate());
        result.put("documentType", driverLicense.getDocumentType());
        result.put("expiryDate", driverLicense.getExpiryDate());
        result.put("firstName", driverLicense.getFirstName());
        result.put("gender", driverLicense.getGender());
        result.put("issueDate", driverLicense.getIssueDate());
        result.put("issuingCountry", driverLicense.getIssuingCountry());
        result.put("lastName", driverLicense.getLastName());
        result.put("licenseNumber", driverLicense.getLicenseNumber());
        result.put("middleName", driverLicense.getMiddleName());

        return result;
    }

    private static JSObject extractEmailProperties(Barcode.Email email) {
        JSObject emailObj = new JSObject();
        emailObj.put("address", email.getAddress());
        emailObj.put("body", email.getBody());
        emailObj.put("subject", email.getSubject());
        emailObj.put("type", email.getType());

        return emailObj;
    }

    private static JSArray extractEmails(List<Barcode.Email> emails) {
        JSArray emailArray = new JSArray();
        for (Barcode.Email email : emails) {
            JSObject emailObj = extractEmailProperties(email);
            emailArray.put(emailObj);
        }

        return emailArray;
    }

    private static JSObject extractGeoPointProperties(Barcode.GeoPoint geoPoint) {
        JSObject geoPointObj = new JSObject();
        geoPointObj.put("latitude", geoPoint.getLat());
        geoPointObj.put("longitude", geoPoint.getLng());

        return geoPointObj;
    }

    private static JSObject extractPersonNameProperties(Barcode.PersonName personName) {
        JSObject result = new JSObject();

        result.put("first", personName.getFirst());
        result.put("formattedName", personName.getFormattedName());
        result.put("last", personName.getLast());
        result.put("middle", personName.getMiddle());
        result.put("prefix", personName.getPrefix());
        result.put("pronunciation", personName.getPronunciation());
        result.put("suffix", personName.getSuffix());

        return result;
    }

    private static JSObject extractPhoneProperties(Barcode.Phone phone) {
        JSObject result = new JSObject();
        result.put("number", phone.getNumber());
        result.put("type", phone.getType());

        return result;
    }

    private static JSArray extractPhones(List<Barcode.Phone> phones) {
        JSArray phoneArray = new JSArray();
        for (Barcode.Phone phone : phones) {
            JSObject phoneObj = extractPhoneProperties(phone);
            phoneArray.put(phoneObj);
        }

        return phoneArray;
    }

    private static JSObject extractSmsProperties(Barcode.Sms sms) {
        JSObject result = new JSObject();
        result.put("message", sms.getMessage());
        result.put("phoneNumber", sms.getPhoneNumber());
        return result;
    }

    private static JSObject extractUrlBookmark(Barcode.UrlBookmark urlBookmark) {
        JSObject result = new JSObject();
        result.put("title", urlBookmark.getTitle());
        result.put("url", urlBookmark.getUrl());
        return result;
    }

    private static JSArray extractUrls(List<String> urls) {
        JSArray urlArray = new JSArray();
        for (String url : urls) {
            urlArray.put(url);
        }

        return urlArray;
    }

    private static JSObject extractWifiProperties(Barcode.WiFi wifi) {
        JSObject result = new JSObject();
        result.put("encryptionType", wifi.getEncryptionType());
        result.put("password", wifi.getPassword());
        result.put("ssid", wifi.getSsid());

        return result;
    }

    private static Point[] normalizeCornerPoints(@NonNull Point[] cornerPoints, @NonNull Point imageSize, @NonNull Point screenSize) {
        // Log corner points
        // Logger.debug("Corner points: " + cornerPoints[0] + ", " + cornerPoints[1] + ", " + cornerPoints[2] + ", " + cornerPoints[3]);
        double screenWidth = screenSize.x;
        double screenHeight = screenSize.y;
        double imageWidth = imageSize.x;
        double imageHeight = imageSize.y;
        // Swap the image dimensions if the image is in landscape mode
        if (screenWidth > screenHeight) {
            imageWidth = imageSize.y;
            imageHeight = imageSize.x;
        }
        // Calculate the scale of the image
        double scale = Math.max(screenHeight / imageWidth, screenWidth / imageHeight);
        // Calculate the invisible area of the image
        double invisibleWidth = imageHeight * scale - screenWidth;
        double invisibleHeight = imageWidth * scale - screenHeight;
        Point[] normalizedCornerPoints = new Point[cornerPoints.length];
        for (int i = 0; i < cornerPoints.length; i++) {
            // Scale the points and move them to the center of the screen
            int x = (int) ((cornerPoints[i].x * scale) - (invisibleWidth / 2));
            int y = (int) ((cornerPoints[i].y * scale) - (invisibleHeight / 2));
            normalizedCornerPoints[i] = new Point(x, y);
        }
        // Log normalized corner points
        // Logger.debug("Normalized corner points: " + normalizedCornerPoints[0] + ", " + normalizedCornerPoints[1] + ", " + normalizedCornerPoints[2] + ", " + normalizedCornerPoints[3]);
        return normalizedCornerPoints;
    }

    public static Size convertIntegerToResolution(Integer resolution) {
        return switch (resolution) {
            case 0 -> new Size(640, 480);
            case 2 -> new Size(1920, 1080);
            case 3 -> new Size(3840, 2160);
            default -> new Size(1280, 720);
        };
    }
}
