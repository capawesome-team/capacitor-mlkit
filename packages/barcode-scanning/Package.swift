// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorMlkitBarcodeScanning",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "CapacitorMlkitBarcodeScanning",
            targets: ["BarcodeScannerPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "6.0.0")
    ],
    targets: [
        .target(
            name: "BarcodeScannerPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/BarcodeScannerPlugin"),
        .testTarget(
            name: "BarcodeScannerPluginTests",
            dependencies: ["BarcodeScannerPlugin"],
            path: "ios/Tests/BarcodeScannerPluginTests")
    ]
)
