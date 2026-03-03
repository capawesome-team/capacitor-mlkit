import Foundation
import Capacitor

@objc public class GetMaxZoomRatioResult: NSObject, Result {
    let zoomRatio: Float

    init(zoomRatio: CGFloat) {
        self.zoomRatio = Float(zoomRatio)
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["zoomRatio"] = zoomRatio
        return result as AnyObject
    }
}
