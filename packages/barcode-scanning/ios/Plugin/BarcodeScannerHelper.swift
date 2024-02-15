/**
 * Copyright (c) 2023 Robin Genz
 */
import Foundation
import Capacitor
import MLKitBarcodeScanning

// swiftlint:disable cyclomatic_complexity
public class BarcodeScannerHelper {
    // swiftlint:disable identifier_name
    public static func normalizeCornerPoints(cornerPoints: [NSValue], imageSize: CGSize) -> [NSValue] {
        CAPLog.print("Corner points (\(cornerPoints[0].cgPointValue.x), \(cornerPoints[0].cgPointValue.y)), (\(cornerPoints[1].cgPointValue.x), \(cornerPoints[1].cgPointValue.y)), (\(cornerPoints[2].cgPointValue.x), \(cornerPoints[2].cgPointValue.y)), (\(cornerPoints[3].cgPointValue.x), \(cornerPoints[3].cgPointValue.y))")
        let screenWidth = UIScreen.main.bounds.width
        let screenHeight = UIScreen.main.bounds.height
        var imageWidth = imageSize.width
        var imageHeight = imageSize.height
        
        if screenWidth > screenHeight {
            imageWidth = imageSize.height
            imageHeight = imageSize.width
        }
        
        let scale = max(screenHeight / CGFloat(imageWidth), screenWidth / CGFloat(imageHeight))
        
        let isPortrait = UIDevice.current.orientation == .portrait || UIDevice.current.orientation == .portraitUpsideDown
        let invisibleWidth = imageHeight * scale - screenWidth
        let invisibleHeight = imageWidth * scale - screenHeight
        var normalizedCornerPoints = [NSValue]()
        for cornerPoint in cornerPoints {
            var x = Int(((cornerPoint.cgPointValue.x * scale) - (invisibleWidth / 2)) * UIScreen.main.scale)
            var y = Int(((cornerPoint.cgPointValue.y * scale) - (invisibleHeight / 2)) * UIScreen.main.scale)
            if isPortrait {
                x = Int((((imageHeight - cornerPoint.cgPointValue.y) * scale) - (invisibleWidth / 2)) * UIScreen.main.scale)
                y = Int(((cornerPoint.cgPointValue.x * scale) - (invisibleHeight / 2)) * UIScreen.main.scale)
            }
            let point = CGPoint(x: x, y: y)
            let value = NSValue(cgPoint: point)
            normalizedCornerPoints.append(value)
        }
        if isPortrait {
            let lastNormalizedCornerPoints = normalizedCornerPoints.removeLast()
            normalizedCornerPoints.insert(lastNormalizedCornerPoints, at: 0)
        }
        CAPLog.print("Normalized corner points (\(normalizedCornerPoints[0].cgPointValue.x), \(normalizedCornerPoints[0].cgPointValue.y)), (\(normalizedCornerPoints[1].cgPointValue.x), \(normalizedCornerPoints[1].cgPointValue.y)), (\(normalizedCornerPoints[2].cgPointValue.x), \(normalizedCornerPoints[2].cgPointValue.y)), (\(normalizedCornerPoints[3].cgPointValue.x), \(normalizedCornerPoints[3].cgPointValue.y))")
        return normalizedCornerPoints
    }

    public static func createBarcodeResultForBarcode(_ barcode: Barcode, imageSize: CGSize?, scale222: CGFloat = UIScreen.main.scale) -> JSObject {
        var cornerPointsResult = [[Int]]()
        if let cornerPoints = barcode.cornerPoints, let imageSize = imageSize {
            let normalizedCornerPoints = normalizeCornerPoints(cornerPoints: cornerPoints, imageSize: imageSize)
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
