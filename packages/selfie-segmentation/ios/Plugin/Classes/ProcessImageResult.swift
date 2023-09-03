import Foundation
import Capacitor
import MLKitVision
import MLKitSegmentationSelfie

@objc class ProcessImageResult: NSObject {
    let segmentationMask: SegmentationMask

    init(segmentationMask: SegmentationMask) {
        self.segmentationMask = segmentationMask
    }

    func toJSObject() -> JSObject {
        let (maskResult, maskWidth, maskHeight) = createMaskResult(mask: segmentationMask)

        var result = JSObject()
        result["mask"] = maskResult
        result["width"] = maskWidth
        result["height"] = maskHeight

        return result
    }

    private func createMaskResult(mask: SegmentationMask) -> (JSArray, Int, Int) {
        var result = JSArray()

        let maskWidth = CVPixelBufferGetWidth(mask.buffer)
        let maskHeight = CVPixelBufferGetHeight(mask.buffer)

        CVPixelBufferLockBaseAddress(mask.buffer, CVPixelBufferLockFlags.readOnly)
        let maskBytesPerRow = CVPixelBufferGetBytesPerRow(mask.buffer)
        var maskAddress =
            CVPixelBufferGetBaseAddress(mask.buffer)!.bindMemory(
                to: Float32.self, capacity: maskBytesPerRow * maskHeight)

        for _ in 0...(maskHeight - 1) {
            for col in 0...(maskWidth - 1) {
                // Gets the confidence of the pixel in the mask being in the foreground.
                let foregroundConfidence: Float32 = maskAddress[col]
                result.append(foregroundConfidence)
            }
            maskAddress += maskBytesPerRow / MemoryLayout<Float32>.size
        }

        return (result, maskWidth, maskHeight)
    }
}
