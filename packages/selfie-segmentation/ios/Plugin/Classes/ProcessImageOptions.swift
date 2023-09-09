import Foundation
import MLKitVision

@objc class ProcessImageOptions: NSObject {
    private var visionImage: VisionImage

    init(
        visionImage: VisionImage
    ) {
        self.visionImage = visionImage
    }

    func getVisionImage() -> VisionImage {
        return visionImage
    }
}
