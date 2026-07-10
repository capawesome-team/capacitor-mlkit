import Foundation
import Capacitor
import MLKitTextRecognition

@objc public class ProcessImageResult: NSObject, Result {
    let text: Text

    init(text: Text) {
        self.text = text
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["text"] = text.text
        result["blocks"] = createBlocksResult(text.blocks)
        return result as AnyObject
    }

    private func createBlocksResult(_ blocks: [TextBlock]) -> JSArray {
        var result = JSArray()
        for block in blocks {
            var blockResult = JSObject()
            blockResult["text"] = block.text
            blockResult["boundingBox"] = createBoundingBoxResult(block.frame)
            blockResult["cornerPoints"] = createCornerPointsResult(block.cornerPoints)
            if let recognizedLanguage = createRecognizedLanguageResult(block.recognizedLanguages) {
                blockResult["recognizedLanguage"] = recognizedLanguage
            }
            blockResult["lines"] = createLinesResult(block.lines)
            result.append(blockResult)
        }
        return result
    }

    private func createLinesResult(_ lines: [TextLine]) -> JSArray {
        var result = JSArray()
        for line in lines {
            var lineResult = JSObject()
            lineResult["text"] = line.text
            lineResult["boundingBox"] = createBoundingBoxResult(line.frame)
            lineResult["cornerPoints"] = createCornerPointsResult(line.cornerPoints)
            if let recognizedLanguage = createRecognizedLanguageResult(line.recognizedLanguages) {
                lineResult["recognizedLanguage"] = recognizedLanguage
            }
            lineResult["elements"] = createElementsResult(line.elements)
            result.append(lineResult)
        }
        return result
    }

    private func createElementsResult(_ elements: [TextElement]) -> JSArray {
        var result = JSArray()
        for element in elements {
            var elementResult = JSObject()
            elementResult["text"] = element.text
            elementResult["boundingBox"] = createBoundingBoxResult(element.frame)
            elementResult["cornerPoints"] = createCornerPointsResult(element.cornerPoints)
            result.append(elementResult)
        }
        return result
    }

    private func createBoundingBoxResult(_ frame: CGRect) -> JSObject {
        var result = JSObject()
        result["left"] = Int(frame.origin.x)
        result["top"] = Int(frame.origin.y)
        result["right"] = Int(frame.origin.x + frame.size.width)
        result["bottom"] = Int(frame.origin.y + frame.size.height)
        return result
    }

    private func createCornerPointsResult(_ cornerPoints: [NSValue]) -> JSArray {
        var result = JSArray()
        for cornerPoint in cornerPoints {
            let point = cornerPoint.cgPointValue
            var pointResult = JSObject()
            pointResult["x"] = Int(point.x)
            pointResult["y"] = Int(point.y)
            result.append(pointResult)
        }
        return result
    }

    private func createRecognizedLanguageResult(_ recognizedLanguages: [TextRecognizedLanguage]) -> String? {
        for recognizedLanguage in recognizedLanguages {
            if let languageCode = recognizedLanguage.languageCode, !languageCode.isEmpty {
                return languageCode
            }
        }
        return nil
    }
}
