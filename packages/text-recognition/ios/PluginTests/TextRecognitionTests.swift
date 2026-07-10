import XCTest
@testable import Plugin

class TextRecognitionTests: XCTestCase {

    func testPluginInstantiation() {
        let plugin = TextRecognitionPlugin()
        XCTAssertNotNil(plugin)
    }
}
