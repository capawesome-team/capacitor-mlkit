'use strict';

var core = require('@capacitor/core');

/**
 * @since 0.0.1
 */
exports.BarcodeFormat = void 0;
(function (BarcodeFormat) {
    /**
     * Only available on Android and iOS.
     *
     * @since 0.0.1
     */
    BarcodeFormat["Aztec"] = "AZTEC";
    /**
     * Only available on Android and iOS.
     *
     * @since 0.0.1
     */
    BarcodeFormat["Codabar"] = "CODABAR";
    /**
     * Only available on Android and iOS.
     *
     * @since 0.0.1
     */
    BarcodeFormat["Code39"] = "CODE_39";
    /**
     * Only available on Android and iOS.
     *
     * @since 0.0.1
     */
    BarcodeFormat["Code93"] = "CODE_93";
    /**
     * Only available on Android and iOS.
     *
     * @since 0.0.1
     */
    BarcodeFormat["Code128"] = "CODE_128";
    /**
     * Only available on Android and iOS.
     *
     * @since 0.0.1
     */
    BarcodeFormat["DataMatrix"] = "DATA_MATRIX";
    /**
     * Only available on Android and iOS.
     *
     * @since 0.0.1
     */
    BarcodeFormat["Ean8"] = "EAN_8";
    /**
     * Only available on Android and iOS.
     *
     * @since 0.0.1
     */
    BarcodeFormat["Ean13"] = "EAN_13";
    /**
     * Only available on Android and iOS.
     *
     * @since 0.0.1
     */
    BarcodeFormat["Itf"] = "ITF";
    /**
     * Only available on Android and iOS.
     *
     * @since 0.0.1
     */
    BarcodeFormat["Pdf417"] = "PDF_417";
    /**
     * Only available on Android and iOS.
     *
     * @since 0.0.1
     */
    BarcodeFormat["QrCode"] = "QR_CODE";
    /**
     * Only available on Android and iOS.
     *
     * @since 0.0.1
     */
    BarcodeFormat["UpcA"] = "UPC_A";
    /**
     * Only available on Android and iOS.
     *
     * @since 0.0.1
     */
    BarcodeFormat["UpcE"] = "UPC_E";
})(exports.BarcodeFormat || (exports.BarcodeFormat = {}));
/**
 * @since 0.0.1
 */
exports.BarcodeValueType = void 0;
(function (BarcodeValueType) {
    /**
     * @since 0.0.1
     */
    BarcodeValueType["CalendarEvent"] = "CALENDAR_EVENT";
    /**
     * @since 0.0.1
     */
    BarcodeValueType["ContactInfo"] = "CONTACT_INFO";
    /**
     * @since 0.0.1
     */
    BarcodeValueType["DriversLicense"] = "DRIVERS_LICENSE";
    /**
     * @since 0.0.1
     */
    BarcodeValueType["Email"] = "EMAIL";
    /**
     * @since 0.0.1
     */
    BarcodeValueType["Geo"] = "GEO";
    /**
     * @since 0.0.1
     */
    BarcodeValueType["Isbn"] = "ISBN";
    /**
     * @since 0.0.1
     */
    BarcodeValueType["Phone"] = "PHONE";
    /**
     * @since 0.0.1
     */
    BarcodeValueType["Product"] = "PRODUCT";
    /**
     * @since 0.0.1
     */
    BarcodeValueType["Sms"] = "SMS";
    /**
     * @since 0.0.1
     */
    BarcodeValueType["Text"] = "TEXT";
    /**
     * @since 0.0.1
     */
    BarcodeValueType["Url"] = "URL";
    /**
     * @since 0.0.1
     */
    BarcodeValueType["Wifi"] = "WIFI";
    /**
     * @since 0.0.1
     */
    BarcodeValueType["Unknown"] = "UNKNOWN";
})(exports.BarcodeValueType || (exports.BarcodeValueType = {}));
/**
 * @since 7.0.0
 */
exports.Resolution = void 0;
(function (Resolution) {
    /**
     * @since 7.0.0
     */
    Resolution[Resolution["640x480"] = 0] = "640x480";
    /**
     * @since 7.0.0
     */
    Resolution[Resolution["1280x720"] = 1] = "1280x720";
    /**
     * @since 7.0.0
     */
    Resolution[Resolution["1920x1080"] = 2] = "1920x1080";
    /**
     * @since 7.2.0
     */
    Resolution[Resolution["3840x2160"] = 3] = "3840x2160";
})(exports.Resolution || (exports.Resolution = {}));
/**
 * @since 0.0.1
 */
exports.LensFacing = void 0;
(function (LensFacing) {
    /**
     * @since 0.0.1
     */
    LensFacing["Front"] = "FRONT";
    /**
     * @since 0.0.1
     */
    LensFacing["Back"] = "BACK";
})(exports.LensFacing || (exports.LensFacing = {}));
/**
 * @since 5.1.0
 */
exports.GoogleBarcodeScannerModuleInstallState = void 0;
(function (GoogleBarcodeScannerModuleInstallState) {
    /**
     * @since 5.1.0
     */
    GoogleBarcodeScannerModuleInstallState[GoogleBarcodeScannerModuleInstallState["UNKNOWN"] = 0] = "UNKNOWN";
    /**
     * @since 5.1.0
     */
    GoogleBarcodeScannerModuleInstallState[GoogleBarcodeScannerModuleInstallState["PENDING"] = 1] = "PENDING";
    /**
     * @since 5.1.0
     */
    GoogleBarcodeScannerModuleInstallState[GoogleBarcodeScannerModuleInstallState["DOWNLOADING"] = 2] = "DOWNLOADING";
    /**
     * @since 5.1.0
     */
    GoogleBarcodeScannerModuleInstallState[GoogleBarcodeScannerModuleInstallState["CANCELED"] = 3] = "CANCELED";
    /**
     * @since 5.1.0
     */
    GoogleBarcodeScannerModuleInstallState[GoogleBarcodeScannerModuleInstallState["COMPLETED"] = 4] = "COMPLETED";
    /**
     * @since 5.1.0
     */
    GoogleBarcodeScannerModuleInstallState[GoogleBarcodeScannerModuleInstallState["FAILED"] = 5] = "FAILED";
    /**
     * @since 5.1.0
     */
    GoogleBarcodeScannerModuleInstallState[GoogleBarcodeScannerModuleInstallState["INSTALLING"] = 6] = "INSTALLING";
    /**
     * @since 5.1.0
     */
    GoogleBarcodeScannerModuleInstallState[GoogleBarcodeScannerModuleInstallState["DOWNLOAD_PAUSED"] = 7] = "DOWNLOAD_PAUSED";
})(exports.GoogleBarcodeScannerModuleInstallState || (exports.GoogleBarcodeScannerModuleInstallState = {}));
/**
 * @since 7.0.0
 */
exports.AddressType = void 0;
(function (AddressType) {
    /**
     * @since 7.0.0
     */
    AddressType[AddressType["HOME"] = 0] = "HOME";
    /**
     * @since 7.0.0
     */
    AddressType[AddressType["UNKNOWN"] = 1] = "UNKNOWN";
    /**
     * @since 7.0.0
     */
    AddressType[AddressType["WORK"] = 2] = "WORK";
})(exports.AddressType || (exports.AddressType = {}));
/**
 * @since 7.0.0
 */
exports.EmailFormatType = void 0;
(function (EmailFormatType) {
    /**
     * @since 7.0.0
     */
    EmailFormatType[EmailFormatType["HOME"] = 0] = "HOME";
    /**
     * @since 7.0.0
     */
    EmailFormatType[EmailFormatType["UNKNOWN"] = 1] = "UNKNOWN";
    /**
     * @since 7.0.0
     */
    EmailFormatType[EmailFormatType["WORK"] = 2] = "WORK";
})(exports.EmailFormatType || (exports.EmailFormatType = {}));
/**
 * @since 7.0.0
 */
exports.PhoneFormatType = void 0;
(function (PhoneFormatType) {
    /**
     * @since 7.0.0
     */
    PhoneFormatType[PhoneFormatType["FAX"] = 0] = "FAX";
    /**
     * @since 7.0.0
     */
    PhoneFormatType[PhoneFormatType["HOME"] = 1] = "HOME";
    /**
     * @since 7.0.0
     */
    PhoneFormatType[PhoneFormatType["MOBILE"] = 2] = "MOBILE";
    /**
     * @since 7.0.0
     */
    PhoneFormatType[PhoneFormatType["UNKNOWN"] = 3] = "UNKNOWN";
    /**
     * @since 7.0.0
     */
    PhoneFormatType[PhoneFormatType["WORK"] = 4] = "WORK";
})(exports.PhoneFormatType || (exports.PhoneFormatType = {}));
/**
 * @since 7.0.0
 */
exports.WifiEncryptionType = void 0;
(function (WifiEncryptionType) {
    /**
     * @since 7.0.0
     */
    WifiEncryptionType[WifiEncryptionType["OPEN"] = 1] = "OPEN";
    /**
     * @since 7.0.0
     */
    WifiEncryptionType[WifiEncryptionType["WEP"] = 2] = "WEP";
    /**
     * @since 7.0.0
     */
    WifiEncryptionType[WifiEncryptionType["WPA"] = 3] = "WPA";
})(exports.WifiEncryptionType || (exports.WifiEncryptionType = {}));

const BarcodeScanner = core.registerPlugin('BarcodeScanner', {
    web: () => Promise.resolve().then(function () { return web; }).then(m => new m.BarcodeScannerWeb()),
});

class BarcodeScannerWeb extends core.WebPlugin {
    constructor() {
        super(...arguments);
        this._isSupported = 'BarcodeDetector' in window;
        this.errorVideoElementMissing = 'videoElement must be provided.';
        this.eventBarcodesScanned = 'barcodesScanned';
    }
    async startScan(options) {
        if (!this._isSupported) {
            throw this.createUnavailableException();
        }
        if (!(options === null || options === undefined ? undefined : options.videoElement)) {
            throw new Error(this.errorVideoElementMissing);
        }
        this.videoElement = options.videoElement;
        this.stream = await navigator.mediaDevices.getUserMedia({
            video: {
                facingMode: {
                    ideal: (options === null || options === undefined ? undefined : options.lensFacing) === exports.LensFacing.Front ? 'user' : 'environment',
                },
            },
            audio: false,
        });
        options.videoElement.srcObject = this.stream;
        await options.videoElement.play();
        const barcodeDetector = new BarcodeDetector();
        this.intervalId = window.setInterval(async () => {
            if (!options.videoElement) {
                return;
            }
            const barcodes = await barcodeDetector.detect(options.videoElement);
            if (barcodes.length === 0) {
                return;
            }
            else {
                this.handleScannedBarcodes(barcodes);
            }
        }, 500);
    }
    async stopScan() {
        if (!this._isSupported) {
            throw this.createUnavailableException();
        }
        if (this.intervalId) {
            clearInterval(this.intervalId);
            this.intervalId = undefined;
        }
        if (this.stream) {
            this.stream.getTracks().forEach(track => track.stop());
            this.stream = undefined;
        }
        if (this.videoElement) {
            this.videoElement.srcObject = null;
            this.videoElement = undefined;
        }
    }
    async pauseScan() {
        if (this.intervalId) {
            clearInterval(this.intervalId);
            this.intervalId = undefined;
        }
    }
    async resumeScan() {
        if (!this._isSupported) {
            throw this.createUnavailableException();
        }
        if (!this.videoElement) {
            throw new Error(this.errorVideoElementMissing);
        }
        if (!this.stream) {
            throw new Error('Stream is not available, call startScan first.');
        }
        const barcodeDetector = new BarcodeDetector();
        this.intervalId = window.setInterval(async () => {
            if (!this.videoElement) {
                return;
            }
            const barcodes = await barcodeDetector.detect(this.videoElement);
            if (barcodes.length > 0) {
                this.handleScannedBarcodes(barcodes);
            }
        }, 500);
    }
    async readBarcodesFromImage(_options) {
        throw this.createUnavailableException();
    }
    async scan() {
        throw this.createUnavailableException();
    }
    async isSupported() {
        return { supported: this._isSupported };
    }
    async enableTorch() {
        throw this.createUnavailableException();
    }
    async disableTorch() {
        throw this.createUnavailableException();
    }
    async toggleTorch() {
        throw this.createUnavailableException();
    }
    async isTorchEnabled() {
        throw this.createUnavailableException;
    }
    async isTorchAvailable() {
        return { available: false };
    }
    async setZoomRatio(_options) {
        throw this.createUnavailableException();
    }
    async getZoomRatio() {
        throw this.createUnavailableException();
    }
    async getMinZoomRatio() {
        throw this.createUnavailableException();
    }
    async getMaxZoomRatio() {
        throw this.createUnavailableException();
    }
    async openSettings() {
        throw this.createUnavailableException();
    }
    async isGoogleBarcodeScannerModuleAvailable() {
        throw this.createUnavailableException();
    }
    async installGoogleBarcodeScannerModule() {
        throw this.createUnavailableException();
    }
    async checkPermissions() {
        try {
            const result = await navigator.permissions.query({
                name: 'camera',
            });
            return {
                camera: result.state,
            };
        }
        catch (error) {
            return {
                camera: 'prompt',
            };
        }
    }
    async requestPermissions() {
        try {
            const stream = await navigator.mediaDevices.getUserMedia({ video: true });
            stream.getTracks().forEach(track => track.stop());
            return {
                camera: 'granted',
            };
        }
        catch (error) {
            return {
                camera: 'denied',
            };
        }
    }
    createUnavailableException() {
        return new core.CapacitorException('This plugin method is not available on this platform.', core.ExceptionCode.Unavailable);
    }
    handleScannedBarcodes(barcodes) {
        const result = {
            barcodes: barcodes.map(barcode => ({
                cornerPoints: [
                    [barcode.cornerPoints[0].x, barcode.cornerPoints[0].y],
                    [barcode.cornerPoints[1].x, barcode.cornerPoints[1].y],
                    [barcode.cornerPoints[2].x, barcode.cornerPoints[2].y],
                    [barcode.cornerPoints[3].x, barcode.cornerPoints[3].y],
                ],
                displayValue: barcode.rawValue,
                rawValue: barcode.rawValue,
                format: barcode.format.toUpperCase(),
                valueType: exports.BarcodeValueType.Unknown,
            })),
        };
        this.notifyListeners(this.eventBarcodesScanned, result);
    }
}

var web = /*#__PURE__*/Object.freeze({
    __proto__: null,
    BarcodeScannerWeb: BarcodeScannerWeb
});

exports.BarcodeScanner = BarcodeScanner;
//# sourceMappingURL=plugin.cjs.js.map
