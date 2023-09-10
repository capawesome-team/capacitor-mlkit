import Foundation
import Capacitor

@objc class ProcessImageResult: NSObject {
    let image: UIImage

    init(image: UIImage) {
        self.image = image
    }

    func toJSObject() -> JSObject {
        var result = JSObject()

        if let data = image.pngData() {
            do {
                let path = FileManager.default.urls(for: .cachesDirectory, in: .userDomainMask)[0]
                let name = "photo-"+UUID().uuidString+".png"
                let url = path.appendingPathComponent(name)
                try data.write(to: url)

                result["path"] = url.absoluteString
            } catch {
                result["path"] = "data:image/png;base64," + data.base64EncodedString()
            }

            result["width"] = Int(image.size.width)
            result["height"] = Int(image.size.height)
        }

        return result
    }
}
