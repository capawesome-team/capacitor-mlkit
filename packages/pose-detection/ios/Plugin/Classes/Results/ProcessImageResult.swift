import Foundation
import Capacitor
import MLKitPoseDetectionCommon

@objc public class ProcessImageResult: NSObject, Result {
    private static let landmarkTypeMap: [PoseLandmarkType: String] = [
        .nose: "NOSE",
        .leftEyeInner: "LEFT_EYE_INNER",
        .leftEye: "LEFT_EYE",
        .leftEyeOuter: "LEFT_EYE_OUTER",
        .rightEyeInner: "RIGHT_EYE_INNER",
        .rightEye: "RIGHT_EYE",
        .rightEyeOuter: "RIGHT_EYE_OUTER",
        .leftEar: "LEFT_EAR",
        .rightEar: "RIGHT_EAR",
        .mouthLeft: "LEFT_MOUTH",
        .mouthRight: "RIGHT_MOUTH",
        .leftShoulder: "LEFT_SHOULDER",
        .rightShoulder: "RIGHT_SHOULDER",
        .leftElbow: "LEFT_ELBOW",
        .rightElbow: "RIGHT_ELBOW",
        .leftWrist: "LEFT_WRIST",
        .rightWrist: "RIGHT_WRIST",
        .leftPinkyFinger: "LEFT_PINKY",
        .rightPinkyFinger: "RIGHT_PINKY",
        .leftIndexFinger: "LEFT_INDEX",
        .rightIndexFinger: "RIGHT_INDEX",
        .leftThumb: "LEFT_THUMB",
        .rightThumb: "RIGHT_THUMB",
        .leftHip: "LEFT_HIP",
        .rightHip: "RIGHT_HIP",
        .leftKnee: "LEFT_KNEE",
        .rightKnee: "RIGHT_KNEE",
        .leftAnkle: "LEFT_ANKLE",
        .rightAnkle: "RIGHT_ANKLE",
        .leftHeel: "LEFT_HEEL",
        .rightHeel: "RIGHT_HEEL",
        .leftToe: "LEFT_FOOT_INDEX",
        .rightToe: "RIGHT_FOOT_INDEX"
    ]

    let poses: [Pose]

    init(poses: [Pose]) {
        self.poses = poses
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["poses"] = createPosesResult()
        return result as AnyObject
    }

    private func createPosesResult() -> JSArray {
        var result = JSArray()
        for pose in poses {
            result.append(createPoseResult(pose))
        }
        return result
    }

    private func createPoseResult(_ pose: Pose) -> JSObject {
        var result = JSObject()
        result["landmarks"] = createLandmarksResult(pose.landmarks)
        return result
    }

    private func createLandmarksResult(_ landmarks: [PoseLandmark]) -> JSArray {
        var result = JSArray()
        for landmark in landmarks {
            result.append(createLandmarkResult(landmark))
        }
        return result
    }

    private func createLandmarkResult(_ landmark: PoseLandmark) -> JSObject {
        var result = JSObject()
        result["type"] = ProcessImageResult.landmarkTypeMap[landmark.type] ?? ""
        result["x"] = Double(landmark.position.x)
        result["y"] = Double(landmark.position.y)
        result["z"] = Double(landmark.position.z)
        result["inFrameLikelihood"] = Double(landmark.inFrameLikelihood)
        return result
    }
}
