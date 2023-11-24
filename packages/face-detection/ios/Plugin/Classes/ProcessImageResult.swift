import Foundation
import Capacitor
import MLKitVision
import MLKitFaceDetection

@objc class ProcessImageResult: NSObject {
    let faces: [Face]

    init(faces: [Face]) {
        self.faces = faces
    }

    func toJSObject() -> JSObject {
        let facesResult = createFacesResult()

        var result = JSObject()
        result["faces"] = facesResult

        return result
    }

    private func createFacesResult() -> JSArray {
        var result = JSArray()

        for face in faces {
            let faceResult = createFaceResult(face)
            result.append(faceResult)
        }

        return result
    }

    private func createFaceResult(_ face: Face) -> JSObject {
        var result = JSObject()

        let frame = face.frame
        let boundsResult = createBoundsResult(frame)
        result["bounds"] = boundsResult

        var landmarksResult = [[String: Any]]()
        for landmark in face.landmarks {
            let landmarkResult = createLandmarkResult(landmark)
            landmarksResult.append(landmarkResult)
        }
        if landmarksResult.count > 0 {
            result["landmarks"] = landmarksResult
        }

        var contoursResult = JSArray()
        for contour in face.contours {
            let pointsResult = createPointsResult(contour.points)
            if pointsResult.count > 0 {
                let contourResult = createContourResult(contour, pointsResult)
                contoursResult.append(contourResult)
            }
        }
        if contoursResult.count > 0 {
            result["contours"] = contoursResult
        }

        if face.hasTrackingID {
            result["trackingId"] = face.trackingID
        }

        if face.hasHeadEulerAngleX {
            result["headEulerAngleX"] = Float(face.headEulerAngleX)
        }

        if face.hasHeadEulerAngleY {
            result["headEulerAngleY"] = Float(face.headEulerAngleY)
        }

        if face.hasHeadEulerAngleZ {
            result["headEulerAngleZ"] = Float(face.headEulerAngleZ)
        }

        if face.hasSmilingProbability {
            result["smilingProbability"] = Float(face.smilingProbability)
        }

        if face.hasLeftEyeOpenProbability {
            result["leftEyeOpenProbability"] = Float(face.leftEyeOpenProbability)
        }

        if face.hasRightEyeOpenProbability {
            result["rightEyeOpenProbability"] = Float(face.rightEyeOpenProbability)
        }

        return result
    }

    private func createBoundsResult(_ frame: CGRect) -> JSObject {
        var result = JSObject()
        result["left"] = Float(frame.origin.x)
        result["top"] = Float(frame.origin.y)
        result["right"] = frame.origin.x + frame.size.width
        result["bottom"] = frame.origin.y + frame.size.height
        return result
    }

    private func createLandmarkResult(_ landmark: FaceLandmark) -> JSObject {
        let positionResult = createPositionResult(landmark.position)
        var result = JSObject()
        result["type"] = landmark.type.rawValue
        result["position"] = positionResult
        return result
    }

    private func createPositionResult(_ point: VisionPoint) -> JSObject {
        var result = JSObject()
        result["x"] = Float(point.x)
        result["y"] = Float(point.y)
        return result
    }

    private func createContourResult(_ contour: FaceContour, _ pointsResult: JSArray) -> JSObject {
        var result = JSObject()
        result["type"] = contour.type.rawValue
        result["points"] = pointsResult
        return result
    }

    private func createPointsResult(_ points: [VisionPoint]) -> JSArray {
        var result = JSArray()

        for point in points {
            let positionResult = createPositionResult(point)
            result.append(positionResult)
        }

        return result
    }
}
