import XCTest
@testable import Plugin

class GenAiSummarizationTests: XCTestCase {

    func testPluginInitialization() {
        let plugin = GenAiSummarizationPlugin()

        XCTAssertNotNil(plugin)
    }
}
