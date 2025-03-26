/**
 * Copyright (c) 2023 Robin Genz
 */
import Foundation
import Capacitor
import MLKitBarcodeScanning
import AVFoundation

// swiftlint:disable cyclomatic_complexity
public class BarcodeScannerHelper {
    // swiftlint:disable identifier_name
    public static func normalizeCornerPoints(cornerPoints: [NSValue], imageSize: CGSize, screenSize: CGSize, videoOrientation: AVCaptureVideoOrientation?) -> [NSValue] {
        // Log corner points
        // CAPLog.print("Corner points (\(cornerPoints[0].cgPointValue.x), \(cornerPoints[0].cgPointValue.y)), (\(cornerPoints[1].cgPointValue.x), \(cornerPoints[1].cgPointValue.y)), (\(cornerPoints[2].cgPointValue.x), \(cornerPoints[2].cgPointValue.y)), (\(cornerPoints[3].cgPointValue.x), \(cornerPoints[3].cgPointValue.y))")
        let screenWidth = screenSize.width
        let screenHeight = screenSize.height
        var imageWidth = imageSize.width
        var imageHeight = imageSize.height
        // Swap the image dimensions if the image is in landscape mode
        if screenWidth > screenHeight {
            imageWidth = imageSize.height
            imageHeight = imageSize.width
        }
        // Calculate the scale of the image
        let scale = max(screenHeight / CGFloat(imageWidth), screenWidth / CGFloat(imageHeight))
        // Calculate the invisible area of the image
        let invisibleWidth = imageHeight * scale - screenWidth
        let invisibleHeight = imageWidth * scale - screenHeight
        var normalizedCornerPoints = [NSValue]()
        for cornerPoint in cornerPoints {
            var x: CGFloat
            var y: CGFloat
            switch videoOrientation {
            case .portrait, .portraitUpsideDown:
                x = ((imageHeight - cornerPoint.cgPointValue.y) * scale) - (invisibleWidth / 2)
                y = (cornerPoint.cgPointValue.x * scale) - (invisibleHeight / 2)
            case .landscapeLeft:
                x = ((imageHeight - cornerPoint.cgPointValue.x) * scale) - (invisibleWidth / 2)
                y = ((imageWidth - cornerPoint.cgPointValue.y) * scale) - (invisibleHeight / 2)
            default:
                x = (cornerPoint.cgPointValue.x * scale) - (invisibleWidth / 2)
                y = (cornerPoint.cgPointValue.y * scale) - (invisibleHeight / 2)
            }
            let point = CGPoint(x: Int(x), y: Int(y))
            let value = NSValue(cgPoint: point)
            normalizedCornerPoints.append(value)
        }
        // If the video orientation is not in landscape right mode, the corner points need to be rotated
        switch videoOrientation {
        case .portrait:
            let lastNormalizedCornerPoints = normalizedCornerPoints.removeLast()
            normalizedCornerPoints.insert(lastNormalizedCornerPoints, at: 0)
        case .landscapeLeft:
            var lastNormalizedCornerPoints = normalizedCornerPoints.removeLast()
            normalizedCornerPoints.insert(lastNormalizedCornerPoints, at: 0)
            lastNormalizedCornerPoints = normalizedCornerPoints.removeLast()
            normalizedCornerPoints.insert(lastNormalizedCornerPoints, at: 0)
        default:
            break
        }
        // Log normalized corner points
        // CAPLog.print("Normalized corner points (\(normalizedCornerPoints[0].cgPointValue.x), \(normalizedCornerPoints[0].cgPointValue.y)), (\(normalizedCornerPoints[1].cgPointValue.x), \(normalizedCornerPoints[1].cgPointValue.y)), (\(normalizedCornerPoints[2].cgPointValue.x), \(normalizedCornerPoints[2].cgPointValue.y)), (\(normalizedCornerPoints[3].cgPointValue.x), \(normalizedCornerPoints[3].cgPointValue.y))")
        return normalizedCornerPoints
    }

    public static func createBarcodeResultForBarcode(_ barcode: Barcode, imageSize: CGSize?, videoOrientation: AVCaptureVideoOrientation?) -> JSObject {
        var cornerPointsResult = [[Int]]()
        if let cornerPoints = barcode.cornerPoints, let imageSize = imageSize {
            let screenSize = CGSize(width: UIScreen.main.bounds.width * UIScreen.main.scale, height: UIScreen.main.bounds.height * UIScreen.main.scale)
            let normalizedCornerPoints = normalizeCornerPoints(cornerPoints: cornerPoints, imageSize: imageSize, screenSize: screenSize, videoOrientation: videoOrientation)
            for cornerPoint in normalizedCornerPoints {
                var value = [Int]()
                value.append(Int(cornerPoint.cgPointValue.x))
                value.append(Int(cornerPoint.cgPointValue.y))
                cornerPointsResult.append(value)
            }
        } else if let cornerPoints = barcode.cornerPoints, videoOrientation == nil {
            for cornerPoint in cornerPoints {
                var value = [Int]()
                value.append(Int(cornerPoint.cgPointValue.x))
                value.append(Int(cornerPoint.cgPointValue.y))
                cornerPointsResult.append(value)
            }
        }

        var result = JSObject()
        if let rawData = barcode.rawData {
            result["bytes"] = convertDataToJsonArray(rawData)
        }
        result["cornerPoints"] = cornerPointsResult
        if let calendarEvent = barcode.calendarEvent {
            result["calendarEvent"] = extractCalendarEventProperties(calendarEvent)
        }
        if let contactInfo = barcode.contactInfo {
            result["contactInfo"] = extractContactInfoProperties(contactInfo)
        }
        result["displayValue"] = barcode.displayValue
        if let driverLicense = barcode.driverLicense {
            result["driverLicense"] = extractDriverLicenseProperties(driverLicense)
        }
        if let email = barcode.email {
            result["email"] = extractEmailProperties(email)
        }
        result["format"] = convertBarcodeScannerFormatToString(barcode.format)
        if let geoPoint = barcode.geoPoint {
            result["geoPoint"] = extractGeoPointProperties(geoPoint)
        }
        if let phone = barcode.phone {
            result["phone"] = extractPhoneProperties(phone)
        }
        result["rawValue"] = barcode.rawValue ?? ""
        if let sms = barcode.sms {
            result["sms"] = extractSmsProperties(sms)
        }
        if let url = barcode.url {
            result["url"] = extractUrlBookmark(url)
        }
        result["valueType"] = convertBarcodeValueTypeToString(barcode.valueType)
        if let wifi = barcode.wifi {
            result["wifi"] = extractWifiProperties(wifi)
        }
        return result
    }

    public static func convertStringsToBarcodeScannerFormats(_ values: [String]) -> [BarcodeFormat] {
        var formats: [BarcodeFormat] = []
        for value in values {
            let format = convertStringToBarcodeScannerFormat(value)
            guard let format = format else {
                continue
            }
            formats.append(format)
        }
        return formats
    }

    private static func convertStringToBarcodeScannerFormat(_ value: String) -> BarcodeFormat? {
        switch value {
        case "AZTEC":
            return BarcodeFormat.aztec
        case "CODABAR":
            return BarcodeFormat.codaBar
        case "CODE_39":
            return BarcodeFormat.code39
        case "CODE_93":
            return BarcodeFormat.code93
        case "CODE_128":
            return BarcodeFormat.code128
        case "DATA_MATRIX":
            return BarcodeFormat.dataMatrix
        case "EAN_8":
            return BarcodeFormat.EAN8
        case "EAN_13":
            return BarcodeFormat.EAN13
        case "ITF":
            return BarcodeFormat.ITF
        case "PDF_417":
            return BarcodeFormat.PDF417
        case "QR_CODE":
            return BarcodeFormat.qrCode
        case "UPC_A":
            return BarcodeFormat.UPCA
        case "UPC_E":
            return BarcodeFormat.UPCE
        default:
            return nil
        }
    }

    private static func convertBarcodeScannerFormatToString(_ format: BarcodeFormat) -> String? {
        switch format {
        case BarcodeFormat.aztec:
            return "AZTEC"
        case BarcodeFormat.codaBar:
            return "CODABAR"
        case BarcodeFormat.code39:
            return "CODE_39"
        case BarcodeFormat.code93:
            return "CODE_93"
        case BarcodeFormat.code128:
            return "CODE_128"
        case BarcodeFormat.dataMatrix:
            return "DATA_MATRIX"
        case BarcodeFormat.EAN8:
            return "EAN_8"
        case BarcodeFormat.EAN13:
            return "EAN_13"
        case BarcodeFormat.ITF:
            return "ITF"
        case BarcodeFormat.PDF417:
            return "PDF_417"
        case BarcodeFormat.qrCode:
            return "QR_CODE"
        case BarcodeFormat.UPCA:
            return "UPC_A"
        case BarcodeFormat.UPCE:
            return "UPC_E"
        default:
            return nil
        }
    }

    private static func convertBarcodeValueTypeToString(_ type: BarcodeValueType) -> String {
        switch type {
        case BarcodeValueType.calendarEvent:
            return "CALENDAR_EVENT"
        case BarcodeValueType.contactInfo:
            return "CONTACT_INFO"
        case BarcodeValueType.driversLicense:
            return "DRIVERS_LICENSE"
        case BarcodeValueType.email:
            return "EMAIL"
        case BarcodeValueType.geographicCoordinates:
            return "GEO"
        case BarcodeValueType.ISBN:
            return "ISBN"
        case BarcodeValueType.phone:
            return "PHONE"
        case BarcodeValueType.product:
            return "PRODUCT"
        case BarcodeValueType.SMS:
            return "SMS"
        case BarcodeValueType.text:
            return "TEXT"
        case BarcodeValueType.URL:
            return "URL"
        case BarcodeValueType.wiFi:
            return "WIFI"
        default:
            return "UNKNOWN"
        }
    }

    private static func convertDataToJsonArray(_ data: Data) -> [UInt8] {
        return [UInt8](data)
    }

    private static func extractAddressProperties(_ address: BarcodeAddress) -> JSObject {
        var result = JSObject()
        result["addressLines"] = address.addressLines
        result["type"] = address.type.rawValue
        return result
    }

    private static func extractCalendarEventProperties(_ calendarEvent: BarcodeCalendarEvent) -> JSObject {
        var result = JSObject()
        result["description"] = calendarEvent.eventDescription
        result["end"] = calendarEvent.end
        result["location"] = calendarEvent.location
        result["organizer"] = calendarEvent.organizer
        result["start"] = calendarEvent.start
        result["status"] = calendarEvent.status
        result["summary"] = calendarEvent.summary
        return result
    }

    private static func extractContactInfoProperties(_ contactInfo: BarcodeContactInfo) -> JSObject {
        var result = JSObject()
        result["addresses"] = (contactInfo.addresses ?? []).map { address in extractAddressProperties(address) }
        result["emails"] = (contactInfo.emails ?? []).map { email in extractEmailProperties(email) }
        result["name"] = extractPersonNameProperties(contactInfo.name)
        result["organization"] = contactInfo.organization
        result["phones"] = (contactInfo.phones ?? []).map { phone in
            extractPhoneProperties(phone)
        }
        result["title"] = contactInfo.jobTitle
        result["urls"] = JSArray(contactInfo.urls ?? [])
        return result
    }

    private static func extractDriverLicenseProperties(_ driverLicense: BarcodeDriverLicense) -> JSObject {
        var result = JSObject()
        result["addressCity"] = driverLicense.addressCity
        result["addressState"] = driverLicense.addressState
        result["addressStreet"] = driverLicense.addressStreet
        result["addressZip"] = driverLicense.addressZip
        result["birthDate"] = driverLicense.birthDate
        result["documentType"] = driverLicense.documentType
        result["expiryDate"] = driverLicense.expiryDate
        result["firstName"] = driverLicense.firstName
        result["gender"] = driverLicense.gender
        result["issuingDate"] = driverLicense.issuingDate
        result["issuingCountry"] = driverLicense.issuingCountry
        result["lastName"] = driverLicense.lastName
        result["licenseNumber"] = driverLicense.licenseNumber
        result["middleName"] = driverLicense.middleName
        return result
    }

    private static func extractEmailProperties(_ email: BarcodeEmail) -> JSObject {
        var result = JSObject()
        result["address"] = email.address
        result["body"] = email.body
        result["subject"] = email.subject
        result["type"] = email.type.rawValue
        return result
    }

    private static func extractGeoPointProperties(_ geoPoint: BarcodeGeoPoint) -> JSObject {
        var result = JSObject()
        result["latitude"] = geoPoint.latitude
        result["longitude"] = geoPoint.longitude
        return result
    }

    private static func extractPersonNameProperties(_ personName: BarcodePersonName?) -> JSObject {
        var result = JSObject()
        result["first"] = personName?.first
        result["formattedName"] = personName?.formattedName
        result["last"] = personName?.last
        result["middle"] = personName?.middle
        result["prefix"] = personName?.prefix
        result["pronunciation"] = personName?.pronunciation
        result["suffix"] = personName?.suffix
        return result
    }

    private static func extractPhoneProperties(_ phone: BarcodePhone) -> JSObject {
        var result = JSObject()
        result["number"] = phone.number
        result["type"] = phone.type.rawValue
        return result
    }

    private static func extractSmsProperties(_ sms: BarcodeSMS) -> JSObject {
        var result = JSObject()
        result["message"] = sms.message
        result["phoneNumber"] = sms.phoneNumber
        return result
    }

    private static func extractUrlBookmark(_ bookmark: BarcodeURLBookmark) -> JSObject {
        var result = JSObject()
        result["title"] = bookmark.title
        result["url"] = bookmark.url
        return result
    }

    private static func extractWifiProperties(_ wifi: BarcodeWifi) -> JSObject {
        var result = JSObject()
        result["encryptionType"] = wifi.type.rawValue
        result["ssid"] = wifi.ssid
        result["password"] = wifi.password
        return result
    }

    public static func convertIntToCapturePreset(_ resolution: Int) -> AVCaptureSession.Preset {
        switch resolution {
        case 0:
            return AVCaptureSession.Preset.vga640x480
        case 2:
            return AVCaptureSession.Preset.hd1920x1080
        case 3:
            return AVCaptureSession.Preset.hd4K3840x2160
        default:
            return AVCaptureSession.Preset.hd1280x720
        }
    }

}
