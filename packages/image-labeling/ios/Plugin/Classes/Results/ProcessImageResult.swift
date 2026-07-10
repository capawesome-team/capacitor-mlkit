import Foundation
import Capacitor
import MLKitImageLabeling

@objc public class ProcessImageResult: NSObject, Result {
    let labels: [ImageLabel]

    init(labels: [ImageLabel]) {
        self.labels = labels
    }

    @objc public func toJSObject() -> AnyObject {
        let labelsResult = createLabelsResult()

        var result = JSObject()
        result["labels"] = labelsResult

        return result as AnyObject
    }

    private func createLabelsResult() -> JSArray {
        var result = JSArray()

        for label in labels {
            let labelResult = createLabelResult(label)
            result.append(labelResult)
        }

        return result
    }

    private func createLabelResult(_ label: ImageLabel) -> JSObject {
        var result = JSObject()
        result["index"] = label.index
        result["text"] = label.text
        result["confidence"] = label.confidence
        return result
    }
}
