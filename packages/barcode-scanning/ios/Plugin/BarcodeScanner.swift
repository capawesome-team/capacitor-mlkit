import Foundation

@objc public class BarcodeScanner: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
