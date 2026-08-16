// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorMlkitFaceDetection",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "CapacitorMlkitFaceDetection",
            targets: ["FaceDetectionPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0"),
        .package(url: "https://github.com/d-date/google-mlkit-swiftpm.git", .upToNextMinor(from: "9.0.0"))
    ],
    targets: [
        .target(
            name: "FaceDetectionPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "MLKitFaceDetection", package: "google-mlkit-swiftpm")
            ],
            path: "ios/Plugin"),
        .testTarget(
            name: "FaceDetectionPluginTests",
            dependencies: ["FaceDetectionPlugin"],
            path: "ios/PluginTests")
    ]
)
