// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorMlkitSelfieSegmentation",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "CapacitorMlkitSelfieSegmentation",
            targets: ["SelfieSegmentationPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0"),
        .package(url: "https://github.com/d-date/google-mlkit-swiftpm.git", .upToNextMinor(from: "9.0.0"))
    ],
    targets: [
        .target(
            name: "SelfieSegmentationPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "MLKitSegmentationSelfie", package: "google-mlkit-swiftpm")
            ],
            path: "ios/Plugin"),
        .testTarget(
            name: "SelfieSegmentationPluginTests",
            dependencies: ["SelfieSegmentationPlugin"],
            path: "ios/PluginTests")
    ]
)
