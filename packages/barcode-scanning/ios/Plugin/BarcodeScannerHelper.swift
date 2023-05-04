/**
 * Copyright (c) 2023 Robin Genz
 */
import Foundation
import Capacitor
import MLKitBarcodeScanning

// swiftlint:disable cyclomatic_complexity
public class BarcodeScannerHelper {
    // swiftlint:disable identifier_name
    public static func normalizeCornerPoints(cornerPoints: [NSValue], imageSize: CGSize, scale: CGFloat = UIScreen.main.scale) -> [NSValue] {
        let screenSize: CGRect = UIScreen.main.bounds
        let imageWidth = imageSize.width
        let imageHeight = imageSize.height
        let isPortrait = UIDevice.current.orientation == .portrait || UIDevice.current.orientation == .portraitUpsideDown
        var normalizedCornerPoints = [NSValue]()
        for cornerPoint in cornerPoints {
            var x = Int((cornerPoint.cgPointValue.x / CGFloat(imageWidth)) * screenSize.width * scale)
            var y = Int((cornerPoint.cgPointValue.y / CGFloat(imageHeight)) * screenSize.height * scale)
            if isPortrait {
                x = Int((1 - (cornerPoint.cgPointValue.y / CGFloat(imageHeight))) * screenSize.width * scale)
                y = Int((cornerPoint.cgPointValue.x / CGFloat(imageWidth)) * screenSize.height * scale)
            }
            let point = CGPoint(x: x, y: y)
            let value = NSValue(cgPoint: point)
            normalizedCornerPoints.append(value)
        }
        return normalizedCornerPoints
    }

    public static func createBarcodeResultForBarcode(_ barcode: Barcode, imageSize: CGSize?, scale: CGFloat = UIScreen.main.scale) -> JSObject {
        var cornerPointsResult = [[Int]]()
        if let cornerPoints = barcode.cornerPoints, let imageSize = imageSize {
            let normalizedCornerPoints = normalizeCornerPoints(cornerPoints: cornerPoints, imageSize: imageSize, scale: scale)
            for cornerPoint in normalizedCornerPoints {
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
        result["displayValue"] = barcode.displayValue
        result["format"] = convertBarcodeScannerFormatToString(barcode.format)
        result["rawValue"] = barcode.rawValue
        result["valueType"] = convertBarcodeValueTypeToString(barcode.valueType)
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
}
