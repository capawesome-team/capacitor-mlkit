import Foundation
import MLKitVision
import MLKitSegmentationSelfie

@objc public class SelfieSegmentation: NSObject {
    public let plugin: SelfieSegmentationPlugin

    init(plugin: SelfieSegmentationPlugin) {
        self.plugin = plugin
    }

    @objc func createImageFromFilePath(_ path: String) -> UIImage? {
        guard let url = URL.init(string: path) else {
            return nil
        }
        if FileManager.default.fileExists(atPath: url.path) {
            return UIImage.init(contentsOfFile: url.path)
        } else {
            return nil
        }
    }

    enum ProcessError: Error {
        case createImageBuffer
    }

    private var segmenter: Segmenter?

    @objc func processImage(_ options: ProcessImageOptions, completion: @escaping (ProcessImageResult?, Error?) -> Void) {
        let image = options.getImage()
        let threshold = options.getConfidence()

        let visionImage = VisionImage.init(image: image)
        visionImage.orientation = image.imageOrientation

        let selfieSegmenterOptions: SelfieSegmenterOptions = SelfieSegmenterOptions()
        selfieSegmenterOptions.segmenterMode = .singleImage

        segmenter = Segmenter.segmenter(
            options: selfieSegmenterOptions
        )

        segmenter?.process(visionImage) { mask, error in
            self.segmenter = nil

            guard error == nil, let mask = mask else {
                return completion(nil, error)
            }

            do {
                guard let imageBuffer = self.createImageBuffer(from: image) else {
                    throw ProcessError.createImageBuffer
                }

                self.applySegmentationMask(
                    mask: mask, to: imageBuffer, threshold: threshold
                )

                let image = self.createImage(from: imageBuffer)
                let result = ProcessImageResult(image: image)

                completion(result, nil)
            } catch {
                completion(nil, error)
            }
        }
    }

    func createImageBuffer(from image: UIImage) -> CVImageBuffer? {
        guard let cgImage = image.cgImage else { return nil }
        let width = cgImage.width
        let height = cgImage.height

        var buffer: CVPixelBuffer?
        CVPixelBufferCreate(
            kCFAllocatorDefault,
            width,
            height,
            kCVPixelFormatType_32BGRA,
            nil,
            &buffer)
        guard let imageBuffer = buffer else { return nil }

        let flags = CVPixelBufferLockFlags(rawValue: 0)
        CVPixelBufferLockBaseAddress(imageBuffer, flags)
        let baseAddress = CVPixelBufferGetBaseAddress(imageBuffer)
        let colorSpace = CGColorSpaceCreateDeviceRGB()
        let bytesPerRow = CVPixelBufferGetBytesPerRow(imageBuffer)
        let context = CGContext(
            data: baseAddress,
            width: width,
            height: height,
            bitsPerComponent: 8,
            bytesPerRow: bytesPerRow,
            space: colorSpace,
            bitmapInfo: (CGImageAlphaInfo.premultipliedFirst.rawValue
                            | CGBitmapInfo.byteOrder32Little.rawValue))

        if let context = context {
            let rect = CGRect.init(x: 0, y: 0, width: width, height: height)
            context.draw(cgImage, in: rect)
            CVPixelBufferUnlockBaseAddress(imageBuffer, flags)
            return imageBuffer
        } else {
            CVPixelBufferUnlockBaseAddress(imageBuffer, flags)
            return nil
        }
    }

    //    func createSampleBuffer(with imageBuffer: CVImageBuffer) -> CMSampleBuffer? {
    //        var timingInfo = CMSampleTimingInfo()
    ////        guard CMSampleBufferGetSampleTimingInfo(sampleBuffer, at: 0, timingInfoOut: &timingInfo) == 0 else {
    ////            return nil
    ////        }
    //        var outputSampleBuffer: CMSampleBuffer?
    //        var newFormatDescription: CMFormatDescription?
    //        CMVideoFormatDescriptionCreateForImageBuffer(allocator: nil, imageBuffer: imageBuffer, formatDescriptionOut: &newFormatDescription)
    //        guard let formatDescription = newFormatDescription else {
    //            return nil
    //        }
    //        CMSampleBufferCreateReadyWithImageBuffer(allocator: nil, imageBuffer: imageBuffer, formatDescription: formatDescription, sampleTiming: &timingInfo, sampleBufferOut: &outputSampleBuffer)
    //        guard let buffer = outputSampleBuffer else {
    //            return nil
    //        }
    //        return buffer
    //    }

    func createImage(
        from imageBuffer: CVImageBuffer
    ) -> UIImage {
        let ciImage = CIImage(cvPixelBuffer: imageBuffer)
        let context = CIContext(options: nil)
        let cgImage = context.createCGImage(ciImage, from: ciImage.extent)!
        return UIImage(cgImage: cgImage)
    }

    func applySegmentationMask(
        mask: SegmentationMask, to imageBuffer: CVImageBuffer, threshold: CGFloat
    ) {
        let bgraBytesPerPixel = 4

        assert(
            CVPixelBufferGetPixelFormatType(imageBuffer) == kCVPixelFormatType_32BGRA,
            "Image buffer must have 32BGRA pixel format type")

        let width = CVPixelBufferGetWidth(mask.buffer)
        let height = CVPixelBufferGetHeight(mask.buffer)
        assert(CVPixelBufferGetWidth(imageBuffer) == width, "Width must match")
        assert(CVPixelBufferGetHeight(imageBuffer) == height, "Height must match")

        let writeFlags = CVPixelBufferLockFlags(rawValue: 0)
        CVPixelBufferLockBaseAddress(imageBuffer, writeFlags)
        CVPixelBufferLockBaseAddress(mask.buffer, CVPixelBufferLockFlags.readOnly)

        let maskBytesPerRow = CVPixelBufferGetBytesPerRow(mask.buffer)
        var maskAddress =
            CVPixelBufferGetBaseAddress(mask.buffer)!.bindMemory(
                to: Float32.self, capacity: maskBytesPerRow * height)

        let imageBytesPerRow = CVPixelBufferGetBytesPerRow(imageBuffer)
        var imageAddress = CVPixelBufferGetBaseAddress(imageBuffer)!.bindMemory(
            to: UInt8.self, capacity: imageBytesPerRow * height)

        for _ in 0...(height - 1) {
            for col in 0...(width - 1) {
                let pixelOffset = col * bgraBytesPerPixel
                let blueOffset = pixelOffset
                let greenOffset = pixelOffset + 1
                let redOffset = pixelOffset + 2
                let alphaOffset = pixelOffset + 3

                let confidence: CGFloat = CGFloat(maskAddress[col])

                if confidence >= threshold {
                    let red = CGFloat(imageAddress[redOffset])
                    let green = CGFloat(imageAddress[greenOffset])
                    let blue = CGFloat(imageAddress[blueOffset])
                    // let alpha = CGFloat(imageAddress[alphaOffset])

                    imageAddress[redOffset] = UInt8(red * confidence)
                    imageAddress[greenOffset] = UInt8(green * confidence)
                    imageAddress[blueOffset] = UInt8(blue * confidence)
                    imageAddress[alphaOffset] = UInt8(0xff)
                } else {
                    imageAddress[redOffset] = UInt8(0x00)
                    imageAddress[greenOffset] = UInt8(0x00)
                    imageAddress[blueOffset] = UInt8(0x00)
                    imageAddress[alphaOffset] = UInt8(0x00)
                }
            }

            imageAddress += imageBytesPerRow / MemoryLayout<UInt8>.size
            maskAddress += maskBytesPerRow / MemoryLayout<Float32>.size
        }

        CVPixelBufferUnlockBaseAddress(imageBuffer, writeFlags)
        CVPixelBufferUnlockBaseAddress(mask.buffer, CVPixelBufferLockFlags.readOnly)
    }
}
