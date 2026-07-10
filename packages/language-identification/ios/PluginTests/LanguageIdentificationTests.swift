import XCTest
@testable import Plugin

class LanguageIdentificationTests: XCTestCase {

    func testInitialization() {
        let implementation = LanguageIdentification()

        XCTAssertNotNil(implementation)
    }
}
