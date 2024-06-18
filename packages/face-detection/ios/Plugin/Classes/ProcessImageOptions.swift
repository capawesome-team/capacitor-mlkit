import Foundation
import MLKitVision

@objc class ProcessImageOptions: NSObject {
    private var visionImage: VisionImage
    private var performanceMode: Int
    private var landmarkMode: Int
    private var contourMode: Int
    private var classificationMode: Int
    private var minFaceSize: Float
    private var enableTracking: Bool

    init(
        visionImage: VisionImage,
        performanceMode: Int,
        landmarkMode: Int,
        contourMode: Int,
        classificationMode: Int,
        minFaceSize: Float,
        enableTracking: Bool
    ) {
        self.visionImage = visionImage
        self.performanceMode = performanceMode
        self.landmarkMode = landmarkMode
        self.contourMode = contourMode
        self.classificationMode = classificationMode
        self.minFaceSize = minFaceSize
        self.enableTracking = enableTracking
    }

    func getVisionImage() -> VisionImage {
        return visionImage
    }

    func getPerformanceMode() -> Int {
        return performanceMode
    }

    func getLandmarkMode() -> Int {
        return landmarkMode
    }

    func getContourMode() -> Int {
        return contourMode
    }

    func getClassificationMode() -> Int {
        return classificationMode
    }

    func getMinFaceSize() -> Float {
        return minFaceSize
    }

    func isTrackingEnabled() -> Bool {
        return enableTracking
    }
}
