import XCTest
@testable import Plugin

class GenAiImageDescriptionTests: XCTestCase {

    func testPluginInitialization() {
        let plugin = GenAiImageDescriptionPlugin()

        XCTAssertNotNil(plugin)
    }
}
