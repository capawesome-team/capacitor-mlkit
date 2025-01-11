// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorMlkitSelfieSegmentation",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "CapacitorMlkitSelfieSegmentation",
            targets: ["SelfieSegmentationPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "6.0.0")
    ],
    targets: [
        .target(
            name: "SelfieSegmentationPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/SelfieSegmentationPlugin"),
        .testTarget(
            name: "SelfieSegmentationPluginTests",
            dependencies: ["SelfieSegmentationPlugin"],
            path: "ios/Tests/SelfieSegmentationPluginTests")
    ]
)
