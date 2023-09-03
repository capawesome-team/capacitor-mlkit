import Foundation
import MLKitVision

@objc class ProcessImageOptions: NSObject {
    private var visionImage: VisionImage
    private var enableRawSizeMask: Bool

    init(
        visionImage: VisionImage,
        enableRawSizeMask: Bool
    ) {
        self.visionImage = visionImage
        self.enableRawSizeMask = enableRawSizeMask
    }

    func getVisionImage() -> VisionImage {
        return visionImage
    }

    func shouldEnableRawSizeMask() -> Bool {
        return enableRawSizeMask
    }
}
