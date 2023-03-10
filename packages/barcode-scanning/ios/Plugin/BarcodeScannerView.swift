/**
 * Copyright (c) 2022 Robin Genz
 */
import Foundation
import AVFoundation
import UIKit
import Capacitor
import MLKitBarcodeScanning
import MLKitVision

public protocol BarcodeScannerViewDelegate {
    func onBarcodesDetected(barcodes: [Barcode], imageWidth: Int, imageHeight: Int)
    func onCancel()
    func onTorchToggle()
}

@objc public class BarcodeScannerView: UIView, AVCaptureVideoDataOutputSampleBufferDelegate {
    public var delegate: BarcodeScannerViewDelegate?

    private let implementation: BarcodeScanner
    private let settings: ScanSettings
    private var captureSession: AVCaptureSession?
    private var barcodeScannerInstance: MLKitBarcodeScanner?
    private var videoPreviewLayer: AVCaptureVideoPreviewLayer?
    private var cancelButton: UIButton?
    private var torchButton: UIButton?

    init (implementation: BarcodeScanner, settings: ScanSettings) throws {
        self.implementation = implementation
        self.settings = settings

        super.init(frame: UIScreen.main.bounds)

        let captureSession = AVCaptureSession()
        captureSession.beginConfiguration()
        captureSession.sessionPreset = AVCaptureSession.Preset.hd1280x720

        let captureDevice = AVCaptureDevice.default(.builtInWideAngleCamera, for: AVMediaType.video, position: settings.lensFacing)
        guard let captureDevice = captureDevice else {
            throw implementation.plugin.errorNoCaptureDeviceAvailable
        }
        var deviceInput: AVCaptureDeviceInput
        deviceInput = try AVCaptureDeviceInput(device: captureDevice)
        if captureSession.canAddInput(deviceInput) {
            captureSession.addInput(deviceInput)
        } else {
            throw implementation.plugin.errorCannotAddCaptureInput
        }
        let deviceOutput = AVCaptureVideoDataOutput()
        deviceOutput.videoSettings = [kCVPixelBufferPixelFormatTypeKey as String: Int(kCVPixelFormatType_32BGRA)]
        deviceOutput.alwaysDiscardsLateVideoFrames = true
        let outputQueue = DispatchQueue(label: "com.google.mlkit.visiondetector.VideoDataOutputQueue")
        deviceOutput.setSampleBufferDelegate(self, queue: outputQueue)
        if captureSession.canAddOutput(deviceOutput) {
            captureSession.addOutput(deviceOutput)
        } else {
            throw implementation.plugin.errorCannotAddCaptureOutput
        }
        captureSession.commitConfiguration()

        DispatchQueue.global(qos: .background).async {
            captureSession.startRunning()
        }
        self.captureSession = captureSession
        let formats = settings.formats.count == 0 ? BarcodeFormat.all : BarcodeFormat(settings.formats)
        self.barcodeScannerInstance = MLKitBarcodeScanner.barcodeScanner(options: BarcodeScannerOptions(formats: formats))

        self.setVideoPreviewLayer(AVCaptureVideoPreviewLayer(session: captureSession))

        if settings.showUIElements {
            self.addCancelButton()
            if implementation.isTorchAvailable() {
                self.addTorchButton()
            }
        }
    }

    required init?(coder: NSCoder) {
        fatalError("coder initialization not supported.")
    }

    deinit {
        self.videoPreviewLayer?.removeFromSuperlayer()
        self.videoPreviewLayer = nil
        self.captureSession?.stopRunning()
        self.captureSession = nil
        self.barcodeScannerInstance = nil
        self.cancelButton = nil
        self.torchButton = nil
    }

    override public func layoutSubviews() {
        super.layoutSubviews()

        self.frame = UIScreen.main.bounds
        self.videoPreviewLayer?.frame = self.bounds
        if self.settings.showUIElements {
            self.removeCancelButton()
            self.addCancelButton()
            self.removeTorchButton()
            self.addTorchButton()
        }

        if let interfaceOrientation = UIApplication.shared.windows.first(where: { $0.isKeyWindow })?.windowScene?.interfaceOrientation {
            self.videoPreviewLayer?.connection?.videoOrientation = interfaceOrientationToVideoOrientation(interfaceOrientation)
        }
    }

    public func captureOutput(_ output: AVCaptureOutput, didOutput sampleBuffer: CMSampleBuffer, from connection: AVCaptureConnection) {
        guard let barcodeScannerInstance = self.barcodeScannerInstance else {
            return
        }
        let visionImage = VisionImage(buffer: sampleBuffer)
        visionImage.orientation = imageOrientation(
            deviceOrientation: UIDevice.current.orientation,
            cameraPosition: AVCaptureDevice.Position.back)
        var barcodes: [Barcode] = []
        do {
            barcodes = try barcodeScannerInstance.results(in: visionImage)
        } catch let error {
            CAPLog.print(error)
        }
        if barcodes.isEmpty {
            return
        }
        guard let imageBuffer = CMSampleBufferGetImageBuffer(sampleBuffer) else {
            CAPLog.print("imageBuffer is nil")
            return
        }
        let imageWidth = CVPixelBufferGetWidth(imageBuffer)
        let imageHeight = CVPixelBufferGetHeight(imageBuffer)
        onBarcodesDetected(barcodes: barcodes, imageWidth: imageWidth, imageHeight: imageHeight)
    }

    private func interfaceOrientationToVideoOrientation(_ orientation: UIInterfaceOrientation) -> AVCaptureVideoOrientation {
        switch orientation {
        case UIInterfaceOrientation.portrait:
            return AVCaptureVideoOrientation.portrait
        case UIInterfaceOrientation.portraitUpsideDown:
            return AVCaptureVideoOrientation.portraitUpsideDown
        case UIInterfaceOrientation.landscapeLeft:
            return AVCaptureVideoOrientation.landscapeLeft
        case UIInterfaceOrientation.landscapeRight:
            return AVCaptureVideoOrientation.landscapeRight
        default:
            return AVCaptureVideoOrientation.portraitUpsideDown
        }
    }

    private func imageOrientation(
        deviceOrientation: UIDeviceOrientation,
        cameraPosition: AVCaptureDevice.Position
    ) -> UIImage.Orientation {
        switch deviceOrientation {
        case .portrait:
            return cameraPosition == .front ? .leftMirrored : .right
        case .landscapeLeft:
            return cameraPosition == .front ? .downMirrored : .up
        case .portraitUpsideDown:
            return cameraPosition == .front ? .rightMirrored : .left
        case .landscapeRight:
            return cameraPosition == .front ? .upMirrored : .down
        case .faceDown, .faceUp, .unknown:
            return .up
        @unknown default:
            return .up
        }
    }

    private func setVideoPreviewLayer(_ videoPreviewLayer: AVCaptureVideoPreviewLayer) {
        videoPreviewLayer.frame = self.bounds
        videoPreviewLayer.videoGravity = .resizeAspectFill
        self.layer.addSublayer(videoPreviewLayer)
        self.videoPreviewLayer = videoPreviewLayer
    }

    private func addCancelButton() {
        let interfaceOrientation = UIApplication.shared.windows.first(where: { $0.isKeyWindow })?.windowScene?.interfaceOrientation ?? UIInterfaceOrientation.portrait
        let image = UIImage(systemName: "xmark")?.withTintColor(.white, renderingMode: .alwaysOriginal)
        let button = UIButton(type: .custom)
        if interfaceOrientation.isPortrait {
            button.frame = CGRect(x: 20, y: 50, width: 50, height: 50)
        } else {
            button.frame = CGRect(x: 20, y: 20, width: 50, height: 50)
        }
        button.backgroundColor = .black.withAlphaComponent(0.5)
        button.setImage(image, for: .normal)
        button.layer.cornerRadius = 10
        button.contentVerticalAlignment = .fill
        button.contentHorizontalAlignment = .fill
        button.imageEdgeInsets = UIEdgeInsets(top: 15, left: 15, bottom: 15, right: 15)
        button.addTarget(self, action: #selector(onCancel), for: .touchUpInside)
        self.addSubview(button)
        self.cancelButton = button
    }

    private func removeCancelButton() {
        self.cancelButton?.removeFromSuperview()
    }

    private func addTorchButton() {
        let image = UIImage(systemName: "flashlight.off.fill")?.withTintColor(.white, renderingMode: .alwaysOriginal)
        let button = UIButton(type: .custom)
        button.frame = CGRect(x: (self.bounds.size.width / 2) - 25, y: self.bounds.size.height - 100, width: 60, height: 60)
        button.backgroundColor = .black.withAlphaComponent(0.5)
        button.setImage(image, for: .normal)
        button.layer.cornerRadius = button.bounds.size.width / 2
        button.contentVerticalAlignment = .fill
        button.contentHorizontalAlignment = .fill
        button.imageEdgeInsets = UIEdgeInsets(top: 15, left: 20, bottom: 15, right: 20)
        button.addTarget(self, action: #selector(onTorchToggle), for: .touchUpInside)
        self.addSubview(button)
        self.torchButton = button
    }

    private func removeTorchButton() {
        self.torchButton?.removeFromSuperview()
    }

    @objc private func onBarcodesDetected(barcodes: [Barcode], imageWidth: Int, imageHeight: Int) {
        self.delegate?.onBarcodesDetected(barcodes: barcodes, imageWidth: imageWidth, imageHeight: imageHeight)
    }

    @objc private func onCancel() {
        self.delegate?.onCancel()
    }

    @objc private func onTorchToggle() {
        self.delegate?.onTorchToggle()
    }
}

extension String: LocalizedError {
    public var localizedDescription: String? { return self }
}
