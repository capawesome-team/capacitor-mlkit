// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorMlkitBarcodeScanning",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "CapacitorMlkitBarcodeScanning",
            targets: ["BarcodeScannerPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0"),
        .package(url: "https://github.com/d-date/google-mlkit-swiftpm.git", .upToNextMinor(from: "8.0.0"))
    ],
    targets: [
        .target(
            name: "BarcodeScannerPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "MLKitBarcodeScanning", package: "google-mlkit-swiftpm")
            ],
            path: "ios/Plugin"),
        .testTarget(
            name: "BarcodeScannerPluginTests",
            dependencies: ["BarcodeScannerPlugin"],
            path: "ios/PluginTests")
    ]
)
