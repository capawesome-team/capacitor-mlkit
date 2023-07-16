/**
 * Copyright (c) 2023 Robin Genz
 */
import Foundation
import Capacitor
import AVFoundation
import MLKitBarcodeScanning

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(BarcodeScannerPlugin)
public class BarcodeScannerPlugin: CAPPlugin {
    public let errorPathMissing = "path must be provided."
    public let errorFileNotExist = "File does not exist."
    public let errorInvalidImage = "The file is no valid image."
    public let errorNoCaptureDeviceAvailable = "No capture device available."
    public let errorCannotAddCaptureInput = "Cannot add input to capture session."
    public let errorCannotAddCaptureOutput = "Cannot add output to capture session."
    public let errorScanCanceled = "scan canceled."
    public let errorPermissionDenied = "User denied access to camera."
    public let errorOpenSettingsFailed = "Cannot open settings."
    public let barcodeScannedEvent = "barcodeScanned"

    private var implementation: BarcodeScanner?

    override public func load() {
        implementation = BarcodeScanner(plugin: self)
    }

    @objc func startScan(_ call: CAPPluginCall) {
        let formatsOption = call.getArray("formats") as? [String]
        let formats = BarcodeScannerHelper.convertStringsToBarcodeScannerFormats(formatsOption ?? [])
        let lensFacingOption = call.getString("lensFacing", "BACK")
        let lensFacing = lensFacingOption == "FRONT" ? AVCaptureDevice.Position.front : AVCaptureDevice.Position.back

        let settings = ScanSettings()
        settings.showUIElements = false
        settings.formats = formats
        settings.lensFacing = lensFacing

        self.implementation?.requestCameraPermissionIfNotDetermined(completion: { error in
            if let error = error {
                call.reject(error.localizedDescription)
                return
            }
            self.implementation?.startScan(settings: settings, completion: { errorMessage in
                if let errorMessage = errorMessage {
                    call.reject(errorMessage)
                    return
                }
                call.resolve()
            })
        })
    }

    @objc func stopScan(_ call: CAPPluginCall) {
        implementation?.stopScan()
        call.resolve()
    }

    @objc func readBarcodesFromImage(_ call: CAPPluginCall) {
        let formatsOption = call.getArray("formats") as? [String]
        let formats = BarcodeScannerHelper.convertStringsToBarcodeScannerFormats(formatsOption ?? [])
        guard let path = call.getString("path") else {
            call.reject(errorPathMissing)
            return
        }
        guard let url = implementation?.getFileUrlByPath(path) else {
            call.reject(errorFileNotExist)
            return
        }

        let settings = ScanSettings()
        settings.formats = formats

        implementation?.readBarcodesFromImage(imageUrl: url, settings: settings, completion: { barcodes, errorMessage in
            if let errorMessage = errorMessage {
                call.reject(errorMessage)
                return
            }
            var barcodeResults = JSArray()
            for barcode in barcodes ?? [] {
                barcodeResults.append(BarcodeScannerHelper.createBarcodeResultForBarcode(barcode, imageSize: nil))
            }
            call.resolve([
                "barcodes": barcodeResults
            ])
        })
    }

    @objc func scan(_ call: CAPPluginCall) {
        let formatsOption = call.getArray("formats") as? [String]
        let formats = BarcodeScannerHelper.convertStringsToBarcodeScannerFormats(formatsOption ?? [])
        let lensFacingOption = call.getString("lensFacing", "BACK")
        let lensFacing = lensFacingOption == "FRONT" ? AVCaptureDevice.Position.front : AVCaptureDevice.Position.back

        let settings = ScanSettings()
        settings.showUIElements = true
        settings.formats = formats
        settings.lensFacing = lensFacing

        self.implementation?.requestCameraPermissionIfNotDetermined(completion: { error in
            if let error = error {
                call.reject(error.localizedDescription)
                return
            }
            self.implementation?.scan(settings: settings, completion: { barcodes, errorMessage in
                if let errorMessage = errorMessage {
                    call.reject(errorMessage)
                    return
                }
                var barcodeResults = JSArray()
                for barcode in barcodes ?? [] {
                    barcodeResults.append(BarcodeScannerHelper.createBarcodeResultForBarcode(barcode, imageSize: nil, scale: 1))
                }
                call.resolve([
                    "barcodes": barcodeResults
                ])
            })
        })
    }

    @objc func isSupported(_ call: CAPPluginCall) {
        call.resolve([
            "supported": implementation?.isSupported() ?? false
        ])
    }

    @objc func enableTorch(_ call: CAPPluginCall) {
        implementation?.enableTorch()
        call.resolve()
    }

    @objc func disableTorch(_ call: CAPPluginCall) {
        implementation?.disableTorch()
        call.resolve()
    }

    @objc func toggleTorch(_ call: CAPPluginCall) {
        implementation?.toggleTorch()
        call.resolve()
    }

    @objc func isTorchEnabled(_ call: CAPPluginCall) {
        call.resolve([
            "enabled": implementation?.isTorchEnabled() ?? false
        ])
    }

    @objc func isTorchAvailable(_ call: CAPPluginCall) {
        call.resolve([
            "available": implementation?.isTorchAvailable() ?? false
        ])
    }

    @objc func openSettings(_ call: CAPPluginCall) {
        implementation?.openSettings(completion: { error in
            if let error = error {
                call.reject(error.localizedDescription)
                return
            }
            call.resolve()
        })
    }

    @objc func isGoogleBarcodeScannerModuleAvailable(_ call: CAPPluginCall) {
        call.reject("Not available on iOS")
    }

    @objc func installGoogleBarcodeScannerModule(_ call: CAPPluginCall) {
        call.reject("Not available on iOS")
    }

    @objc override public func checkPermissions(_ call: CAPPluginCall) {
        call.resolve([
            "camera": AVCaptureDevice.authorizationStatus(for: .video).authorizationState
        ])
    }

    @objc override public func requestPermissions(_ call: CAPPluginCall) {
        AVCaptureDevice.requestAccess(for: .video) { _ in
            self.checkPermissions(call)
        }
    }

    @objc func notifyBarcodeScannedListener(barcode: Barcode, imageSize: CGSize) {
        var result = JSObject()
        result["barcode"] = BarcodeScannerHelper.createBarcodeResultForBarcode(barcode, imageSize: imageSize)
        notifyListeners(barcodeScannedEvent, data: result)
    }
}

extension AVAuthorizationStatus {
    var authorizationState: String {
        switch self {
        case .denied, .restricted:
            return "denied"
        case .authorized:
            return "granted"
        case .notDetermined:
            fallthrough
        @unknown default:
            return "prompt"
        }
    }
}

struct RuntimeError: Error, LocalizedError {
    let errorDescription: String?

    init(_ description: String) {
        errorDescription = description
    }
}
