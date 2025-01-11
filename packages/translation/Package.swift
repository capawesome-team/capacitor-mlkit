// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorMlkitTranslation",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "CapacitorMlkitTranslation",
            targets: ["TranslationPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "6.0.0")
    ],
    targets: [
        .target(
            name: "TranslationPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/TranslationPlugin"),
        .testTarget(
            name: "TranslationPluginTests",
            dependencies: ["TranslationPlugin"],
            path: "ios/Tests/TranslationPluginTests")
    ]
)
