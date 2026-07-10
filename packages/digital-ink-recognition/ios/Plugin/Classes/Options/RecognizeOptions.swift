import Foundation
import Capacitor
import MLKitDigitalInkRecognition

@objc public class RecognizeOptions: NSObject {
    let ink: Ink
    let languageTag: String
    let maxResultCount: Int
    let preContext: String?
    let writingArea: WritingArea?

    init(_ call: CAPPluginCall) throws {
        self.ink = try RecognizeOptions.getInkFromCall(call)
        self.languageTag = try RecognizeOptions.getLanguageTagFromCall(call)
        self.maxResultCount = call.getInt("maxResultCount", 5)
        self.preContext = call.getString("preContext")
        self.writingArea = RecognizeOptions.getWritingAreaFromCall(call)
    }

    // swiftlint:disable identifier_name
    private static func getInkFromCall(_ call: CAPPluginCall) throws -> Ink {
        guard let strokesOption = call.getArray("strokes") else {
            throw CustomError.strokesMissing
        }
        var strokes: [Stroke] = []
        for strokeOption in strokesOption {
            guard let strokeObject = strokeOption as? JSObject else {
                throw CustomError.strokesMissing
            }
            let pointObjects = strokeObject["points"] as? JSArray ?? []
            var points: [StrokePoint] = []
            for pointObject in pointObjects {
                guard let pointObject = pointObject as? JSObject,
                      let x = (pointObject["x"] as? NSNumber)?.floatValue,
                      let y = (pointObject["y"] as? NSNumber)?.floatValue else {
                    throw CustomError.strokesMissing
                }
                if let t = (pointObject["t"] as? NSNumber)?.intValue {
                    points.append(StrokePoint(x: x, y: y, t: t))
                } else {
                    points.append(StrokePoint(x: x, y: y))
                }
            }
            strokes.append(Stroke(points: points))
        }
        return Ink(strokes: strokes)
    }

    // swiftlint:enable identifier_name

    private static func getLanguageTagFromCall(_ call: CAPPluginCall) throws -> String {
        guard let languageTag = call.getString("languageTag") else {
            throw CustomError.languageTagMissing
        }
        return languageTag
    }

    private static func getWritingAreaFromCall(_ call: CAPPluginCall) -> WritingArea? {
        guard let writingAreaObject = call.getObject("writingArea"),
              let width = (writingAreaObject["width"] as? NSNumber)?.floatValue,
              let height = (writingAreaObject["height"] as? NSNumber)?.floatValue else {
            return nil
        }
        return WritingArea(width: width, height: height)
    }
}
