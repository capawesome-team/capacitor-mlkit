import XCTest
@testable import Plugin

class GenAiRewritingTests: XCTestCase {

    func testPluginInitialization() {
        let plugin = GenAiRewritingPlugin()

        XCTAssertNotNil(plugin)
    }
}
