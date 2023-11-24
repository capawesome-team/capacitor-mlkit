import Foundation
import Capacitor

@objc class ProcessImageResult: NSObject {
    let image: UIImage

    init(image: UIImage) {
        self.image = image
    }

    func toJSObject() throws -> JSObject {
        var result = JSObject()

        if let data = image.pngData() {
            let uniqueFileNameWithExtension = UUID().uuidString + ".png"
            var directory = URL(fileURLWithPath: NSTemporaryDirectory())
            if let cachesDirectory = FileManager.default.urls(for: .cachesDirectory, in: .userDomainMask).first {
                directory = cachesDirectory
            }
            let url = directory.appendingPathComponent(uniqueFileNameWithExtension)
            try data.write(to: url)

            result["path"] = url.absoluteString
            result["width"] = Int(image.size.width)
            result["height"] = Int(image.size.height)
        }

        return result
    }
}
