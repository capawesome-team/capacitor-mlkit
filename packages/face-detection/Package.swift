// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorMlkitFaceDetection",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "CapacitorMlkitFaceDetection",
            targets: ["FaceDetectionPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "6.0.0")
    ],
    targets: [
        .target(
            name: "FaceDetectionPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/FaceDetectionPlugin"),
        .testTarget(
            name: "FaceDetectionPluginTests",
            dependencies: ["FaceDetectionPlugin"],
            path: "ios/Tests/FaceDetectionPluginTests")
    ]
)
