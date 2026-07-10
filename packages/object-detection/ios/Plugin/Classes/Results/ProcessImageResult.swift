import Foundation
import Capacitor
import MLKitObjectDetection

@objc public class ProcessImageResult: NSObject, Result {
    let objects: [Object]

    init(objects: [Object]) {
        self.objects = objects
    }

    @objc public func toJSObject() -> AnyObject {
        let detectedObjectsResult = createDetectedObjectsResult()

        var result = JSObject()
        result["detectedObjects"] = detectedObjectsResult

        return result as AnyObject
    }

    private func createDetectedObjectsResult() -> JSArray {
        var result = JSArray()

        for object in objects {
            let detectedObjectResult = createDetectedObjectResult(object)
            result.append(detectedObjectResult)
        }

        return result
    }

    private func createDetectedObjectResult(_ object: Object) -> JSObject {
        var result = JSObject()
        result["boundingBox"] = createBoundingBoxResult(object.frame)
        if let trackingID = object.trackingID {
            result["trackingId"] = trackingID.intValue
        }
        result["labels"] = createLabelsResult(object.labels)
        return result
    }

    private func createBoundingBoxResult(_ frame: CGRect) -> JSObject {
        var result = JSObject()
        result["left"] = Int(frame.minX)
        result["top"] = Int(frame.minY)
        result["right"] = Int(frame.maxX)
        result["bottom"] = Int(frame.maxY)
        return result
    }

    private func createLabelsResult(_ labels: [ObjectLabel]) -> JSArray {
        var result = JSArray()

        for label in labels {
            var labelResult = JSObject()
            labelResult["index"] = label.index
            labelResult["text"] = label.text
            labelResult["confidence"] = label.confidence
            result.append(labelResult)
        }

        return result
    }
}
