import Foundation
import Capacitor

@objc public class SetZoomRatioOptions: NSObject {
    private var zoomRatio: CGFloat

    init(zoomRatio: Float) {
        self.zoomRatio = CGFloat(zoomRatio)
    }

    func getZoomRatio() -> CGFloat {
        return zoomRatio
    }
}
