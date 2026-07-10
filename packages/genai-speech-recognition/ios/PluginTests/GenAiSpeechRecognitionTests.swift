import XCTest
@testable import Plugin

class GenAiSpeechRecognitionTests: XCTestCase {

    func testPluginInitialization() {
        let plugin = GenAiSpeechRecognitionPlugin()

        XCTAssertNotNil(plugin)
    }
}
