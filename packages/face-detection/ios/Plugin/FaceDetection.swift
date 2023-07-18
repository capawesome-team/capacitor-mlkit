import Foundation
import MLKitVision
import MLKitFaceDetection

@objc public class FaceDetection: NSObject {
    public let plugin: FaceDetectionPlugin

    init(plugin: FaceDetectionPlugin) {
        self.plugin = plugin
    }

    @objc public func processImage(_ image: VisionImage, _ options: FaceDetectorOptions, completion: @escaping ([Any]?, Error?) -> Void) {
        // Returns a face detector with the given options.
        let faceDetector = FaceDetector.faceDetector(
            // Options for configuring the face detector.
            options: options
        )

        // Array of face results in the given image.
        let faces: [Face]
        do {
            // Returns face results in the given image
            faces = try faceDetector.results(
                // The image to get results in.
                in: image
            )
        } catch let error {
            completion(nil, error)
            return
        }

        var facesArray = [Any]()

        for face in faces {
            // A human face detected in an image.

            var faceObject = [String: Any]()

            do { // Bounds
                // The rectangle containing the detected face relative to the image in the view coordinate system.
                let bounds = face.frame

                var boundsObject = [String: Any]()

                boundsObject["left"] = bounds.origin.x
                boundsObject["top"] = bounds.origin.y
                boundsObject["right"] = bounds.origin.x + bounds.size.width
                boundsObject["bottom"] = bounds.origin.y + bounds.size.height

                faceObject["bounds"] = boundsObject
            }

            do { // Landmarks
                var landmarksArray = [Any]()

                // An array of all the landmarks in the detected face.
                for landmark in face.landmarks {
                    // A landmark on a human face detected in an image.

                    // 2D position of the facial landmark.
                    let point: VisionPoint = landmark.position

                    var landmarkObject = [String: Any]()

                    // The type of the facial landmark.
                    landmarkObject["type"] = landmarkType(type: landmark.type)
                    landmarkObject["position"] = pointHelper(point: point)

                    landmarksArray.append(landmarkObject)
                }

                if landmarksArray.count > 0 {
                    faceObject["landmarks"] = landmarksArray
                }
            }

            do { // Contours
                var contoursArray = [Any]()

                // An array of all the contours in the detected face.
                for contour in face.contours {
                    // A contour on a human face detected in an image.

                    // An array of 2D points that make up the facial contour.
                    let points: [VisionPoint] = contour.points

                    var pointsArray = [Any]()

                    for point in points {
                        pointsArray.append(pointHelper(point: point))
                    }

                    var contourObject = [String: Any]()

                    // The facial contour type.
                    contourObject["type"] = contourType(type: contour.type)
                    contourObject["points"] = pointsArray

                    contoursArray.append(contourObject)
                }

                if contoursArray.count > 0 {
                    faceObject["contours"] = contoursArray
                }
            }

            // Indicates whether the face has a tracking ID.
            if face.hasTrackingID {
                // The tracking identifier of the face.
                faceObject["trackingId"] = face.trackingID
            }

            // Indicates whether the detector found the head x euler angle.
            if face.hasHeadEulerAngleX {
                // Indicates the rotation of the face about the horizontal axis of the image. Positive x euler angle is when the face is turned upward in the image that is being processed.
                faceObject["headEulerAngleX"] = face.headEulerAngleX
            }
            // Indicates whether the detector found the head y euler angle.
            if face.hasHeadEulerAngleY {
                // Indicates the rotation of the face about the vertical axis of the image. Positive y euler angle is when the face is turned towards the right side of the image that is being processed.
                faceObject["headEulerAngleY"] = face.headEulerAngleY
            }
            // Indicates whether the detector found the head z euler angle.
            if face.hasHeadEulerAngleZ {
                // Indicates the rotation of the face about the axis pointing out of the image. Positive z euler angle is a counter-clockwise rotation within the image plane.
                faceObject["headEulerAngleZ"] = face.headEulerAngleZ
            }

            // Indicates whether a smiling probability is available.
            if face.hasSmilingProbability {
                // Probability that the face is smiling.
                faceObject["smilingProbability"] = face.smilingProbability
            }
            // Indicates whether a smiling probability is available.
            if face.hasLeftEyeOpenProbability {
                // Probability that the face's left eye is open.
                faceObject["leftEyeOpenProbability"] = face.leftEyeOpenProbability
            }
            // Indicates whether a right eye open probability is available.
            if face.hasRightEyeOpenProbability {
                // Probability that the face's right eye is open.
                faceObject["rightEyeOpenProbability"] = face.rightEyeOpenProbability
            }

            facesArray.append(faceObject)
        }

        completion(facesArray, nil)
    }

    private func landmarkType(
        type: FaceLandmarkType
    ) -> Int? {
        switch type {
        // The center of the bottom lip.
        case .mouthBottom: return 0
        // The left cheek.
        case .leftCheek: return 1
        // The midpoint of the left ear tip and left ear lobe.
        case .leftEar: return 3
        // The left eye.
        case .leftEye: return 4
        // The left corner of the mouth
        case .mouthLeft: return 5
        // The midpoint between the nostrils where the nose meets the face.
        case .noseBase: return 6
        // The right cheek.
        case .rightCheek: return 7
        // The midpoint of the right ear tip and right ear lobe.
        case .rightEar: return 9
        // The right eye.
        case .rightEye: return 10
        // The right corner of the mouth
        case .mouthRight: return 11
        default:
            return nil
        }
    }

    private func contourType(
        type: FaceContourType
    ) -> Int? {
        switch type {
        // A set of points that outline the face oval.
        case .face: return 1
        // A set of points that outline the top of the left eyebrow.
        case .leftEyebrowTop: return 2
        // A set of points that outline the bottom of the left eyebrow.
        case .leftEyebrowBottom: return 3
        // A set of points that outline the top of the right eyebrow.
        case .rightEyebrowTop: return 4
        // A set of points that outline the bottom of the right eyebrow.
        case .rightEyebrowBottom: return 5
        // A set of points that outline the left eye.
        case .leftEye: return 6
        // A set of points that outline the right eye.
        case .rightEye: return 7
        // A set of points that outline the top of the upper lip.
        case .upperLipTop: return 8
        // A set of points that outline the bottom of the upper lip.
        case .upperLipBottom: return 9
        // A set of points that outline the top of the lower lip.
        case .lowerLipTop: return 10
        // A set of points that outline the bottom of the lower lip.
        case .lowerLipBottom: return 11
        // A set of points that outline the nose bridge.
        case .noseBridge: return 12
        // A set of points that outline the bottom of the nose.
        case .noseBottom: return 13
        // A center point on the left cheek.
        case .leftCheek: return 14
        // A center point on the right cheek.
        case .rightCheek: return 15
        default:
            return nil
        }
    }

    private func pointHelper(
        // A two-dimensional point in an image.
        point: VisionPoint
    ) -> [String: Any] {
        var pointObject = [String: Any]()

        // The x-coordinate of the point.
        pointObject["x"] = point.x
        // The y-coordinate of the point.
        pointObject["y"] = point.y

        return pointObject
    }

    @objc public func getFileUrlByPath(_ path: String) -> URL? {
        guard let url = URL.init(string: path) else {
            return nil
        }
        if FileManager.default.fileExists(atPath: url.path) {
            return url
        } else {
            return nil
        }
    }
}
