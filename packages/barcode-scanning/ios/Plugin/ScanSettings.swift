/**
 * Copyright (c) 2023 Robin Genz
 */
import Foundation
import MLKitBarcodeScanning
import AVFoundation

@objc public class ScanSettings: NSObject {
    public var showUIElements: Bool = false
    public var formats: [BarcodeFormat] = []
    public var lensFacing: AVCaptureDevice.Position = .back
    public var resolution: AVCaptureSession.Preset = AVCaptureSession.Preset.hd1280x720
    public var enableMultitaskingCameraAccess: Bool = false
}
