/**
 * Copyright (c) 2023 Robin Genz
 */
import Foundation

@objc public class SetFocusPointOptions: NSObject {
    private let x: Float
    private let y: Float

    init(x: Float, y: Float) {
        self.x = x
        self.y = y
    }

    func getX() -> Float {
        return x
    }

    func getY() -> Float {
        return y
    }
} 