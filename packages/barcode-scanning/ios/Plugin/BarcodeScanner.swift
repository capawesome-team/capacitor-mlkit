/**
 * Copyright (c) 2023 Robin Genz
 */
import Foundation
import AVFoundation
import Capacitor
import MLKitBarcodeScanning
import MLKitVision

typealias MLKitBarcodeScanner = MLKitBarcodeScanning.BarcodeScanner

@objc public class BarcodeScanner: NSObject {

    public let plugin: BarcodeScannerPlugin

    private var cameraView: BarcodeScannerView?
    private var scanCompletionHandler: (([Barcode]?, String?) -> Void)?

    init(plugin: BarcodeScannerPlugin) {
        self.plugin = plugin
    }

    @objc public func getFileUrlByPath(_ path: String) -> URL? {
        guard let url = URL.init(string: path) else {
            return nil
        }
        if FileManager.default.fileExists(atPath: url.path) {
            return url
        } else {
            return nil
        }
    }

    @objc public func startScan(settings: ScanSettings, completion: @escaping (String?) -> Void) {
        self.stopScan()

        guard let webView = self.plugin.webView else {
            return
        }

        DispatchQueue.main.async {
            do {
                let cameraView = try BarcodeScannerView(implementation: self, settings: settings)
                cameraView.delegate = self
                self.hideWebViewBackground()
                webView.superview?.insertSubview(cameraView, belowSubview: webView)
                self.cameraView = cameraView
            } catch let error {
                self.showWebViewBackground()
                CAPLog.print(error.localizedDescription, error)
                completion(error.localizedDescription)
                return
            }
        }
    }

    @objc public func stopScan() {
        DispatchQueue.main.async {
            self.showWebViewBackground()
            self.cameraView?.removeFromSuperview()
            self.cameraView = nil
        }
        self.scanCompletionHandler = nil
    }

    @objc public func readBarcodesFromImage(imageUrl: URL, settings: ScanSettings, completion: @escaping ([Barcode]?, String?) -> Void) {
        let image = UIImage.init(contentsOfFile: imageUrl.path)
        guard let image = image else {
            completion(nil, plugin.errorInvalidImage)
            return
        }
        let visionImage = VisionImage(image: image)
        let barcodeScannerInstance = MLKitBarcodeScanner.barcodeScanner(options: BarcodeScannerOptions(formats: BarcodeFormat(settings.formats)))
        barcodeScannerInstance.process(visionImage) { features, error in
            if let error = error {
                CAPLog.print(error.localizedDescription, error)
                completion(nil, error.localizedDescription)
                return
            }
            CAPLog.print("\(features?.count ?? 0)")
            guard let features = features, !features.isEmpty else {
                return
            }
            completion(features, nil)
        }
    }

    @objc public func scan(settings: ScanSettings, completion: @escaping (([Barcode]?, String?) -> Void)) {
        self.stopScan()

        guard let webView = self.plugin.webView else {
            return
        }
        self.scanCompletionHandler = completion

        DispatchQueue.main.async {
            do {
                let cameraView = try BarcodeScannerView(implementation: self, settings: settings)
                cameraView.delegate = self
                webView.superview?.insertSubview(cameraView, aboveSubview: webView)
                self.cameraView = cameraView
            } catch let error {
                CAPLog.print(error.localizedDescription, error)
                completion(nil, error.localizedDescription)
                return
            }
        }
    }

    @objc public func isSupported() -> Bool {
        return UIImagePickerController.isSourceTypeAvailable(.camera)
    }

    @objc public func enableTorch() {
        guard let device = AVCaptureDevice.default(for: AVMediaType.video) else { return }
        guard device.hasTorch else { return }
        do {
            try device.lockForConfiguration()
            do {
                try device.setTorchModeOn(level: 1.0)
            } catch {
                CAPLog.print("setTorchModeOn failed.", error.localizedDescription)
            }
            device.unlockForConfiguration()
        } catch {
            CAPLog.print("lockForConfiguration failed.", error.localizedDescription)
        }
    }

    @objc public func disableTorch() {
        guard let device = AVCaptureDevice.default(for: AVMediaType.video) else { return }
        guard device.hasTorch else { return }
        do {
            try device.lockForConfiguration()
            device.torchMode = AVCaptureDevice.TorchMode.off
            device.unlockForConfiguration()
        } catch {
            CAPLog.print("lockForConfiguration failed.", error.localizedDescription)
        }
    }

    @objc public func toggleTorch() {
        if self.isTorchEnabled() {
            self.disableTorch()
        } else {
            self.enableTorch()
        }
    }

    @objc public func isTorchEnabled() -> Bool {
        guard let device = AVCaptureDevice.default(for: AVMediaType.video) else { return false }
        guard device.hasTorch else { return false }
        return device.torchMode == AVCaptureDevice.TorchMode.on
    }

    @objc public func isTorchAvailable() -> Bool {
        guard let device = AVCaptureDevice.default(for: AVMediaType.video) else {
            return false
        }
        return device.hasTorch
    }

    /**
     * Must run on UI thread.
     */
    private func hideWebViewBackground() {
        guard let webView = self.plugin.webView else {
            return
        }
        webView.isOpaque = false
        webView.backgroundColor = UIColor.clear
        webView.scrollView.backgroundColor = UIColor.clear
    }

    /**
     * Must run on UI thread.
     */
    private func showWebViewBackground() {
        guard let webView = self.plugin.webView else {
            return
        }
        webView.isOpaque = true
        webView.backgroundColor = UIColor.white
        webView.scrollView.backgroundColor = UIColor.white
    }

    private func handleScannedBarcode(barcode: Barcode, imageWidth: Int, imageHeight: Int) {
        plugin.notifyBarcodeScannedListener(barcode: barcode, imageWidth: imageWidth, imageHeight: imageHeight)
    }

}

extension BarcodeScanner: BarcodeScannerViewDelegate {
    public func onBarcodesDetected(barcodes: [Barcode], imageWidth: Int, imageHeight: Int) {
        if let scanCompletionHandler = self.scanCompletionHandler {
            scanCompletionHandler(barcodes, nil)
            self.stopScan()
        } else {
            for barcode in barcodes {
                self.handleScannedBarcode(barcode: barcode, imageWidth: imageWidth, imageHeight: imageHeight)
            }
        }
    }

    public func onCancel() {
        if let scanCompletionHandler = self.scanCompletionHandler {
            scanCompletionHandler(nil, plugin.errorScanCanceled)
        }
        self.stopScan()
    }

    public func onTorchToggle() {
        toggleTorch()
    }
}
