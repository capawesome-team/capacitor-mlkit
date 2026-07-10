import XCTest
@testable import Plugin

class EntityExtractionTests: XCTestCase {

    func testInitialization() {
        let implementation = EntityExtraction()

        XCTAssertNotNil(implementation)
    }
}
