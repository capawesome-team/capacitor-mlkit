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
    private var scanCompletionHandler: (([Barcode]?, AVCaptureVideoOrientation?, String?) -> Void)?
    private var barcodeRawValueVotes = [String: Int]()

    init(plugin: BarcodeScannerPlugin) {
        self.plugin = plugin
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
                completion(nil)
            } catch let error {
                self.showWebViewBackground()
                CAPLog.print(error.localizedDescription, error)
                completion(error.localizedDescription)
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
        self.barcodeRawValueVotes.removeAll()
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
            completion(features, nil)
        }
    }

    public func scan(settings: ScanSettings, completion: @escaping (([Barcode]?, AVCaptureVideoOrientation?, String?) -> Void)) {
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
                completion(nil, nil, error.localizedDescription)
                return
            }
        }
    }

    @objc public func isSupported() -> Bool {
        return UIImagePickerController.isSourceTypeAvailable(.camera)
    }

    @objc public func enableTorch() {
        guard let device = cameraView?.getCaptureDevice() else { return }
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
        guard let device = cameraView?.getCaptureDevice() else { return }
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
        guard let device = cameraView?.getCaptureDevice() else { return false }
        guard device.hasTorch else { return false }
        return device.torchMode == AVCaptureDevice.TorchMode.on
    }

    @objc public func isTorchAvailable() -> Bool {
        guard let device = cameraView?.getCaptureDevice() else {
            return false
        }
        return device.hasTorch
    }

    @objc public func setZoomRatio(_ options: SetZoomRatioOptions) throws {
        let zoomRatio = options.getZoomRatio()

        guard let device = cameraView?.getCaptureDevice() else {
            return
        }
        try device.lockForConfiguration()
        device.videoZoomFactor = zoomRatio
        device.unlockForConfiguration()
    }

    @objc public func getZoomRatio() -> GetZoomRatioResult? {
        guard let device = cameraView?.getCaptureDevice() else {
            return nil
        }
        return GetZoomRatioResult(zoomRatio: device.videoZoomFactor)
    }

    @objc public func getMinZoomRatio() -> GetMinZoomRatioResult? {
        guard let device = cameraView?.getCaptureDevice() else {
            return nil
        }
        return GetMinZoomRatioResult(zoomRatio: device.minAvailableVideoZoomFactor)
    }

    @objc public func getMaxZoomRatio() -> GetMaxZoomRatioResult? {
        guard let device = cameraView?.getCaptureDevice() else {
            return nil
        }
        return GetMaxZoomRatioResult(zoomRatio: device.maxAvailableVideoZoomFactor)
    }

    @objc func openSettings(completion: @escaping (Error?) -> Void) {
        let url = URL(string: UIApplication.openSettingsURLString)
        DispatchQueue.main.async {
            if let url = url, UIApplication.shared.canOpenURL(url) {
                UIApplication.shared.open(url, completionHandler: { (_) in
                    completion(nil)
                })
            } else {
                completion(RuntimeError(self.plugin.errorOpenSettingsFailed))
            }
        }
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

    @objc public func getCameraPermission() -> AVAuthorizationStatus {
        return AVCaptureDevice.authorizationStatus(for: .video)
    }

    @objc public func requestCameraPermission(completion: @escaping () -> Void) {
        AVCaptureDevice.requestAccess(for: .video) { _ in
            completion()
        }
    }

    @objc public func requestCameraPermissionIfNotDetermined(completion: @escaping (Error?) -> Void) {
        let authorizationStatus = self.getCameraPermission()
        if authorizationStatus == .notDetermined {
            self.requestCameraPermission {
                let authorizationStatusAfterRequest = self.getCameraPermission()
                if authorizationStatusAfterRequest == .denied || authorizationStatusAfterRequest == .restricted {
                    completion(RuntimeError(self.plugin.errorPermissionDenied))
                } else {
                    completion(nil)
                }
            }
            return
        } else if authorizationStatus == .authorized {
            completion(nil)
        } else {
            completion(RuntimeError(self.plugin.errorPermissionDenied))
        }
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

    private func handleScannedBarcode(barcode: Barcode, imageSize: CGSize, videoOrientation: AVCaptureVideoOrientation?) {
        plugin.notifyBarcodeScannedListener(barcode: barcode, imageSize: imageSize, videoOrientation: videoOrientation)
    }

    private func handleScannedBarcodes(barcodes: [Barcode], imageSize: CGSize, videoOrientation: AVCaptureVideoOrientation?) {
        plugin.notifyBarcodesScannedListener(barcodes: barcodes, imageSize: imageSize, videoOrientation: videoOrientation)
    }

    private func voteForBarcode(barcode: Barcode) -> Int? {
        guard let rawValue = barcode.rawValue else {
            return nil
        }
        if let votes = self.barcodeRawValueVotes[rawValue] {
            self.barcodeRawValueVotes[rawValue] = votes + 1
        } else {
            self.barcodeRawValueVotes[rawValue] = 1
        }
        return self.barcodeRawValueVotes[rawValue] ?? 1
    }

    private func voteForBarcodes(barcodes: [Barcode]) -> [Barcode] {
        return barcodes.filter { barcode in
            if let votes = self.voteForBarcode(barcode: barcode) {
                return votes >= 10
            } else {
                // Do not filter out barcodes without raw value.
                return true
            }
        }
    }
}

extension BarcodeScanner: BarcodeScannerViewDelegate {
    public func onBarcodesDetected(barcodes: [Barcode], imageSize: CGSize, videoOrientation: AVCaptureVideoOrientation?) {
        if let scanCompletionHandler = self.scanCompletionHandler {
            scanCompletionHandler(barcodes, videoOrientation, nil)
            self.stopScan()
        } else {
            let barcodesWithEnoughVotes = self.voteForBarcodes(barcodes: barcodes)
            for barcode in barcodesWithEnoughVotes {
                self.handleScannedBarcode(barcode: barcode, imageSize: imageSize, videoOrientation: videoOrientation)
            }
            if barcodesWithEnoughVotes.count > 0 {
                self.handleScannedBarcodes(barcodes: barcodesWithEnoughVotes, imageSize: imageSize, videoOrientation: videoOrientation)
            }
        }
    }

    public func onCancel() {
        if let scanCompletionHandler = self.scanCompletionHandler {
            scanCompletionHandler(nil, nil, plugin.errorScanCanceled)
        }
        self.stopScan()
    }

    public func onTorchToggle() {
        toggleTorch()
    }
}
