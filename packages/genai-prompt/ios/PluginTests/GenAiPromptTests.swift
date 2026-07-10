import XCTest
@testable import Plugin

class GenAiPromptTests: XCTestCase {

    func testPluginInitialization() {
        let plugin = GenAiPromptPlugin()

        XCTAssertNotNil(plugin)
    }
}
