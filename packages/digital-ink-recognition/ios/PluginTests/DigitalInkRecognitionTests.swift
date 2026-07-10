import XCTest
@testable import Plugin

class DigitalInkRecognitionTests: XCTestCase {

    func testInitialization() {
        let implementation = DigitalInkRecognition(plugin: DigitalInkRecognitionPlugin())

        XCTAssertNotNil(implementation)
    }
}
