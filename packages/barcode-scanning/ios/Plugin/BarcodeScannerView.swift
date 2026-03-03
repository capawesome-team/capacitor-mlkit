/**
 * Copyright (c) 2023 Robin Genz
 */
import Foundation
import AVFoundation
import UIKit
import Capacitor
import MLKitBarcodeScanning
import MLKitVision

// swiftlint:disable class_delegate_protocol
public protocol BarcodeScannerViewDelegate {
    func onBarcodesDetected(barcodes: [Barcode], imageSize: CGSize, videoOrientation: AVCaptureVideoOrientation?)
    func onCancel()
    func onTorchToggle()
}

@objc public class BarcodeScannerView: UIView, AVCaptureVideoDataOutputSampleBufferDelegate {
    public var delegate: BarcodeScannerViewDelegate?

    private let implementation: BarcodeScanner
    private let settings: ScanSettings
    private var captureDevice: AVCaptureDevice?
    private var captureSession: AVCaptureSession?
    private var barcodeScannerInstance: MLKitBarcodeScanner?
    private var videoPreviewLayer: AVCaptureVideoPreviewLayer?
    private var videoOrientation: AVCaptureVideoOrientation?
    private var cancelButton: UIButton?
    private var torchButton: UIButton?
    private var detectionAreaView: UIView?
    private var detectionAreaViewFrame: CGRect?

    init(implementation: BarcodeScanner, settings: ScanSettings) throws {
        self.implementation = implementation
        self.settings = settings

        super.init(frame: UIScreen.main.bounds)

        // Set autoresizing mask to fill the screen
        // This is necessary for the view to resize when the device orientation changes
        super.autoresizingMask = [.flexibleWidth, .flexibleHeight]

        // creates a serial DispatchQueue, which ensures operations are executed in a First In, First Out
        // (FIFO) order, meaning tasks are completed one at a time in the exact order they were added to
        // the queue.
        let captureSessionQueue = DispatchQueue(label: "com.google.mlkit.visiondetector.CaptureSessionQueue")
        var setupError: Error?

        let captureSession = AVCaptureSession()
        captureSession.beginConfiguration()

        // Prepare capture session and preview layer
        // It executes tasks one at a time in the order they are added (FIFO), ensuring that no other
        // tasks on the same queue can run simultaneously or out of order with respect to the synchronous
        // block
        captureSessionQueue.sync {
            do {
                captureDevice = AVCaptureDevice.default(.builtInTripleCamera, for: AVMediaType.video, position: settings.lensFacing)
                if captureDevice == nil {
                    captureDevice = AVCaptureDevice.default(.builtInWideAngleCamera, for: AVMediaType.video, position: settings.lensFacing)
                }
                guard let captureDevice = captureDevice else {
                    throw RuntimeError(implementation.plugin.errorNoCaptureDeviceAvailable)
                }

                // Check if the capture device supports the requested resolution (session preset)
                // and if the capture session can set the session preset
                // Otherwise, the default session preset will be used (which usually is .high)
                if captureDevice.supportsSessionPreset(settings.resolution)
                    && captureSession.canSetSessionPreset(settings.resolution) {
                    captureSession.sessionPreset = settings.resolution
                }

                var deviceInput: AVCaptureDeviceInput
                deviceInput = try AVCaptureDeviceInput(device: captureDevice)

                if captureSession.canAddInput(deviceInput) {
                    captureSession.addInput(deviceInput)
                } else {
                    throw RuntimeError(implementation.plugin.errorCannotAddCaptureInput)
                }

                let deviceOutput = AVCaptureVideoDataOutput()
                deviceOutput.videoSettings = [kCVPixelBufferPixelFormatTypeKey as String: Int(kCVPixelFormatType_32BGRA)]
                deviceOutput.alwaysDiscardsLateVideoFrames = true
                let outputQueue = DispatchQueue(label: "com.google.mlkit.visiondetector.VideoDataOutputQueue")
                deviceOutput.setSampleBufferDelegate(self, queue: outputQueue)

                if captureSession.canAddOutput(deviceOutput) {
                    captureSession.addOutput(deviceOutput)
                } else {
                    throw RuntimeError(implementation.plugin.errorCannotAddCaptureOutput)
                }
                // Configure capture device to set the correct focus mode and exposure mode.
                // This must be called before commiting the configuration to the capture session
                // and after adding the device input to the capture session.
                self.configureCaptureDevice(captureDevice)

                // Allow camera usage while in iPad multitasking if enableMultitaskingCameraAccess setting is enabled
                if #available(iOS 16.0, *) {
                    if settings.enableMultitaskingCameraAccess
                        && captureSession.isMultitaskingCameraAccessSupported {
                        captureSession.isMultitaskingCameraAccessEnabled = true
                    }
                }

                captureSession.commitConfiguration()
                self.captureSession = captureSession
            } catch {
                print("Failed to configure AVCaptureSession: \(error)")
                setupError = error
            }
        }

        if let error = setupError {
            throw error
        }

        // Moved videoPreview setup outside async task in main dispatch queue.
        // This prevents inconsistent behavior when calling startScan() multiple times quickly.
        // See https://github.com/capawesome-team/capacitor-mlkit/issues/258 for details
        self.setVideoPreviewLayer(AVCaptureVideoPreviewLayer(session: captureSession))

        // Add Start task to the queue in the order, each task starts only after the previous task has
        // finished, ensuring captureSession.startRunning() starts after the sync block
        captureSessionQueue.async {
            captureSession.startRunning()
        }

        DispatchQueue.main.async {
            guard let captureDevice = self.captureDevice else { return }
            guard let captureSession = self.captureSession else { return }
            let formats = self.settings.formats.isEmpty ? BarcodeFormat.all : BarcodeFormat(self.settings.formats)
            self.barcodeScannerInstance = MLKitBarcodeScanner.barcodeScanner(options: BarcodeScannerOptions(formats: formats))

            if self.settings.showUIElements {
                self.addCancelButton()
                if self.implementation.isTorchAvailable() {
                    self.addTorchButton()
                }
            }
        }
    }

    required init?(coder: NSCoder) {
        fatalError("coder initialization not supported.")
    }

    deinit {
        self.removeDetectionAreaView()
        self.removeTorchButton()
        self.removeCancelButton()
        self.removeVideoPreviewLayer()
        self.captureSession?.stopRunning()
        self.captureSession = nil
        self.barcodeScannerInstance = nil
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
            self.removeDetectionAreaView()
            self.addDetectionAreaView()
        }

        if let interfaceOrientation = UIApplication.shared.windows.first(where: { $0.isKeyWindow })?.windowScene?.interfaceOrientation {
            let videoOrientation = interfaceOrientationToVideoOrientation(interfaceOrientation)
            self.videoPreviewLayer?.connection?.videoOrientation = videoOrientation
            self.videoOrientation = videoOrientation
        }
    }

    public func captureOutput(_ output: AVCaptureOutput, didOutput sampleBuffer: CMSampleBuffer, from connection: AVCaptureConnection) {
        guard let barcodeScannerInstance = self.barcodeScannerInstance else {
            return
        }
        let visionImage = VisionImage(buffer: sampleBuffer)
        visionImage.orientation = imageOrientation(
            videoOrientation: self.videoOrientation,
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
        let imageSize = CGSize(width: imageWidth, height: imageHeight)
        if let detectionAreaViewFrame = self.detectionAreaViewFrame {
            barcodes = filterBarcodesOutsideTheDetectionArea(barcodes, imageSize: imageSize,
                                                             detectionArea: detectionAreaViewFrame, videoOrientation: videoOrientation)
            if barcodes.isEmpty {
                return
            }
        }
        onBarcodesDetected(barcodes: barcodes, imageSize: imageSize, videoOrientation: videoOrientation)
    }

    public func getCaptureDevice() -> AVCaptureDevice? {
        return self.captureDevice
    }

    private func configureCaptureDevice(_ device: AVCaptureDevice) {
        do {
            try device.lockForConfiguration()

            // Set appropriate zoom factor for triple camera
            if device.deviceType == .builtInTripleCamera {
                device.videoZoomFactor = 2.0
            }

            // Set focus mode
            if device.isFocusModeSupported(.continuousAutoFocus) {
                device.focusMode = .continuousAutoFocus
            }

            // Set exposure mode
            if device.isExposureModeSupported(.continuousAutoExposure) {
                device.exposureMode = .continuousAutoExposure
            }

            device.unlockForConfiguration()
        } catch {
            // Silent failure is acceptable during setup
        }
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
        videoOrientation: AVCaptureVideoOrientation?,
        cameraPosition: AVCaptureDevice.Position
    ) -> UIImage.Orientation {
        switch videoOrientation {
        case .portrait:
            return cameraPosition == .front ? .leftMirrored : .right
        case .landscapeLeft:
            return cameraPosition == .front ? .downMirrored : .up
        case .portraitUpsideDown:
            return cameraPosition == .front ? .rightMirrored : .left
        case .landscapeRight:
            return cameraPosition == .front ? .upMirrored : .down
        case .none:
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

    private func removeVideoPreviewLayer() {
        self.videoPreviewLayer?.removeFromSuperlayer()
        self.videoPreviewLayer = nil
    }

    private func addCancelButton() {
        let interfaceOrientation = UIApplication.shared.windows.first(where: { $0.isKeyWindow })?
            .windowScene?.interfaceOrientation ?? UIInterfaceOrientation.portrait
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
        self.cancelButton = nil
    }

    private func addTorchButton() {
        let image = UIImage(systemName: "flashlight.off.fill")?.withTintColor(.white, renderingMode: .alwaysOriginal)
        let button = UIButton(type: .custom)
        button.frame = CGRect(x: (self.bounds.size.width / 2) - 25, y: self.bounds.size.height - 86, width: 60, height: 60)
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
        self.torchButton = nil
    }

    private func addDetectionAreaView() {
        let halfScreenWidth = UIScreen.main.bounds.width / 2
        let halfScreenHeight = UIScreen.main.bounds.height / 2
        let width = halfScreenWidth > halfScreenHeight ? halfScreenHeight : halfScreenWidth
        let topLeft = CGPoint(x: self.center.x - (width / 2), y: self.center.y - (width / 2))
        let view = UIView(frame: CGRect(x: topLeft.x, y: topLeft.y, width: width, height: width))
        view.backgroundColor = UIColor.clear
        view.layer.cornerRadius = 12
        view.layer.borderWidth = 4
        view.layer.borderColor = UIColor.white.cgColor
        self.addSubview(view)
        self.detectionAreaView = view
        self.detectionAreaViewFrame = view.frame
    }

    private func removeDetectionAreaView() {
        self.detectionAreaView?.removeFromSuperview()
        self.detectionAreaView = nil
        self.detectionAreaViewFrame = nil
    }

    private func filterBarcodesOutsideTheDetectionArea(_ barcodes: [Barcode], imageSize: CGSize?, detectionArea: CGRect, videoOrientation: AVCaptureVideoOrientation?) -> [Barcode] {
        return barcodes.filter { barcode in
            if let cornerPoints = barcode.cornerPoints, let imageSize = imageSize {
                let screenSize = CGSize(width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height)
                let normalizedCornerPoints = BarcodeScannerHelper.normalizeCornerPoints(cornerPoints: cornerPoints,
                                                                                        imageSize: imageSize, screenSize: screenSize, videoOrientation: videoOrientation)

                let topLeft = normalizedCornerPoints[0].cgPointValue
                let topRight = normalizedCornerPoints[1].cgPointValue
                let bottomRight = normalizedCornerPoints[2].cgPointValue
                let bottomLeft = normalizedCornerPoints[3].cgPointValue

                if topLeft.x < detectionArea.minX || topLeft.y < detectionArea.minY {
                    return false
                }
                if topRight.x > detectionArea.maxX || topRight.y < detectionArea.minY {
                    return false
                }
                if bottomRight.x > detectionArea.maxX || bottomRight.y > detectionArea.maxY {
                    return false
                }
                if bottomLeft.x < detectionArea.minX || bottomLeft.y > detectionArea.maxY {
                    return false
                }
            }
            return true
        }
    }

    private func onBarcodesDetected(barcodes: [Barcode], imageSize: CGSize, videoOrientation: AVCaptureVideoOrientation?) {
        self.delegate?.onBarcodesDetected(barcodes: barcodes, imageSize: imageSize, videoOrientation: videoOrientation)
    }

    @objc private func onCancel() {
        self.delegate?.onCancel()
    }

    @objc private func onTorchToggle() {
        self.delegate?.onTorchToggle()
    }
}
