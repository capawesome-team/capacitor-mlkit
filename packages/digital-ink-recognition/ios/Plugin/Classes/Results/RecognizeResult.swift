import Foundation
import Capacitor
import MLKitDigitalInkRecognition

@objc public class RecognizeResult: NSObject, Result {
    let candidates: [DigitalInkRecognitionCandidate]

    init(candidates: [DigitalInkRecognitionCandidate]) {
        self.candidates = candidates
    }

    @objc public func toJSObject() -> AnyObject {
        var candidatesResult = JSArray()
        for candidate in candidates {
            var candidateResult = JSObject()
            candidateResult["text"] = candidate.text
            if let score = candidate.score {
                candidateResult["score"] = score.floatValue
            }
            candidatesResult.append(candidateResult)
        }
        var result = JSObject()
        result["candidates"] = candidatesResult
        return result as AnyObject
    }
}
