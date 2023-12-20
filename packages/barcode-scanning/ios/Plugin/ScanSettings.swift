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
}
