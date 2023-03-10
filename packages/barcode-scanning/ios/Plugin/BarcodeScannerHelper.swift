/**
 * Copyright (c) 2022 Robin Genz
 */
import Foundation
import Capacitor
import MLKitBarcodeScanning

public class BarcodeScannerHelper {
    public static func createBarcodeResultForBarcode(_ barcode: Barcode, imageWidth: Int?, imageHeight: Int?) -> JSObject {
        let screenSize: CGRect = UIScreen.main.bounds
        var cornerPointsResult = [[Int]]()
        if let cornerPoints = barcode.cornerPoints, let imageWidth = imageWidth, let imageHeight = imageHeight {
            for cornerPoint in cornerPoints {
                var value = [Int]()
                switch UIDevice.current.orientation {
                case .portrait:
                    value.append(Int((1 - (cornerPoint.cgPointValue.y / CGFloat(imageHeight))) * screenSize.width))
                    value.append(Int((cornerPoint.cgPointValue.x / CGFloat(imageWidth)) * screenSize.height))
                    break
                default:
                    value.append(Int((cornerPoint.cgPointValue.x / CGFloat(imageWidth)) * screenSize.width))
                    value.append(Int((cornerPoint.cgPointValue.y / CGFloat(imageHeight)) * screenSize.height))
                }
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
