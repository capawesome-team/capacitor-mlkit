import XCTest
@testable import Plugin

class GenAiProofreadingTests: XCTestCase {

    func testPluginInitialization() {
        let plugin = GenAiProofreadingPlugin()

        XCTAssertNotNil(plugin)
    }
}
