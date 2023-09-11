import Foundation
import UIKit

extension UIImage {
    public func scaledImage(width: Int?, height: Int?) -> UIImage {
        let newWidth: CGFloat
        let newHeight: CGFloat

        if let width = width {
            newWidth = CGFloat(width)
            if let height = height {
                newHeight = CGFloat(height)
            } else {
                let scaleFactor = newWidth / self.size.width
                newHeight = self.size.height * scaleFactor
            }
        } else
        if let height = height {
            newHeight = CGFloat(height)
            if let width = width {
                newWidth = CGFloat(width)
            } else {
                let scaleFactor = newHeight / self.size.height
                newWidth = self.size.width * scaleFactor
            }
        } else {
            return self
        }

        let newSize = CGSize(width: newWidth, height: newHeight)

        if newSize.width >= size.width && newSize.height >= size.height {
            return self
        }

        UIGraphicsBeginImageContextWithOptions(newSize, false, scale)
        defer { UIGraphicsEndImageContext() }
        draw(in: CGRect(origin: .zero, size: newSize))
        return UIGraphicsGetImageFromCurrentImageContext() ?? self
    }
}

@objc class ProcessImageOptions: NSObject {
    private var image: UIImage
    private var confidence: CGFloat

    init(
        image: UIImage,
        width: Int?,
        height: Int?,
        confidence: CGFloat
    ) {
        self.image = image.scaledImage(width: width, height: height)
        self.confidence = confidence
    }

    func getImage() -> UIImage {
        return image
    }

    func getConfidence() -> CGFloat {
        return confidence
    }
}
