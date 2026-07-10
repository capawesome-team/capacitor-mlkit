import XCTest
@testable import Plugin

class SmartReplyTests: XCTestCase {

    func testInitialization() {
        let implementation = SmartReply()

        XCTAssertNotNil(implementation)
    }
}
