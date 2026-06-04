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

var BarcodeScanner = core.registerPlugin('BarcodeScanner', {
    web: function () { return Promise.resolve().then(function () { return web; }).then(function (m) { return new m.BarcodeScannerWeb(); }); },
});

var __extends = (undefined && undefined.__extends) || (function () {
    var extendStatics = function (d, b) {
        extendStatics = Object.setPrototypeOf ||
            ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
            function (d, b) { for (var p in b) if (Object.prototype.hasOwnProperty.call(b, p)) d[p] = b[p]; };
        return extendStatics(d, b);
    };
    return function (d, b) {
        if (typeof b !== "function" && b !== null)
            throw new TypeError("Class extends value " + String(b) + " is not a constructor or null");
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var __awaiter = (undefined && undefined.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
var __generator = (undefined && undefined.__generator) || function (thisArg, body) {
    var _ = { label: 0, sent: function() { if (t[0] & 1) throw t[1]; return t[1]; }, trys: [], ops: [] }, f, y, t, g = Object.create((typeof Iterator === "function" ? Iterator : Object).prototype);
    return g.next = verb(0), g["throw"] = verb(1), g["return"] = verb(2), typeof Symbol === "function" && (g[Symbol.iterator] = function() { return this; }), g;
    function verb(n) { return function (v) { return step([n, v]); }; }
    function step(op) {
        if (f) throw new TypeError("Generator is already executing.");
        while (g && (g = 0, op[0] && (_ = 0)), _) try {
            if (f = 1, y && (t = op[0] & 2 ? y["return"] : op[0] ? y["throw"] || ((t = y["return"]) && t.call(y), 0) : y.next) && !(t = t.call(y, op[1])).done) return t;
            if (y = 0, t) op = [op[0] & 2, t.value];
            switch (op[0]) {
                case 0: case 1: t = op; break;
                case 4: _.label++; return { value: op[1], done: false };
                case 5: _.label++; y = op[1]; op = [0]; continue;
                case 7: op = _.ops.pop(); _.trys.pop(); continue;
                default:
                    if (!(t = _.trys, t = t.length > 0 && t[t.length - 1]) && (op[0] === 6 || op[0] === 2)) { _ = 0; continue; }
                    if (op[0] === 3 && (!t || (op[1] > t[0] && op[1] < t[3]))) { _.label = op[1]; break; }
                    if (op[0] === 6 && _.label < t[1]) { _.label = t[1]; t = op; break; }
                    if (t && _.label < t[2]) { _.label = t[2]; _.ops.push(op); break; }
                    if (t[2]) _.ops.pop();
                    _.trys.pop(); continue;
            }
            op = body.call(thisArg, _);
        } catch (e) { op = [6, e]; y = 0; } finally { f = t = 0; }
        if (op[0] & 5) throw op[1]; return { value: op[0] ? op[1] : void 0, done: true };
    }
};
var BarcodeScannerWeb = /** @class */ (function (_super) {
    __extends(BarcodeScannerWeb, _super);
    function BarcodeScannerWeb() {
        var _this = _super !== null && _super.apply(this, arguments) || this;
        _this._isSupported = 'BarcodeDetector' in window;
        _this.errorVideoElementMissing = 'videoElement must be provided.';
        _this.eventBarcodesScanned = 'barcodesScanned';
        return _this;
    }
    BarcodeScannerWeb.prototype.startScan = function (options) {
        return __awaiter(this, void 0, void 0, function () {
            var _a, barcodeDetector;
            var _this = this;
            return __generator(this, function (_b) {
                switch (_b.label) {
                    case 0:
                        if (!this._isSupported) {
                            throw this.createUnimplementedException();
                        }
                        if (!(options === null || options === void 0 ? void 0 : options.videoElement)) {
                            throw new Error(this.errorVideoElementMissing);
                        }
                        this.videoElement = options.videoElement;
                        _a = this;
                        return [4 /*yield*/, navigator.mediaDevices.getUserMedia({
                                video: {
                                    facingMode: {
                                        ideal: (options === null || options === void 0 ? void 0 : options.lensFacing) === exports.LensFacing.Front ? 'user' : 'environment',
                                    },
                                },
                                audio: false,
                            })];
                    case 1:
                        _a.stream = _b.sent();
                        options.videoElement.srcObject = this.stream;
                        return [4 /*yield*/, options.videoElement.play()];
                    case 2:
                        _b.sent();
                        barcodeDetector = new BarcodeDetector();
                        this.intervalId = window.setInterval(function () { return __awaiter(_this, void 0, void 0, function () {
                            var barcodes;
                            return __generator(this, function (_a) {
                                switch (_a.label) {
                                    case 0:
                                        if (!options.videoElement) {
                                            return [2 /*return*/];
                                        }
                                        return [4 /*yield*/, barcodeDetector.detect(options.videoElement)];
                                    case 1:
                                        barcodes = _a.sent();
                                        if (barcodes.length === 0) {
                                            return [2 /*return*/];
                                        }
                                        else {
                                            this.handleScannedBarcodes(barcodes);
                                        }
                                        return [2 /*return*/];
                                }
                            });
                        }); }, 500);
                        return [2 /*return*/];
                }
            });
        });
    };
    BarcodeScannerWeb.prototype.stopScan = function () {
        return __awaiter(this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                if (!this._isSupported) {
                    throw this.createUnimplementedException();
                }
                if (this.intervalId) {
                    clearInterval(this.intervalId);
                    this.intervalId = undefined;
                }
                if (this.stream) {
                    this.stream.getTracks().forEach(function (track) { return track.stop(); });
                    this.stream = undefined;
                }
                if (this.videoElement) {
                    this.videoElement.srcObject = null;
                    this.videoElement = undefined;
                }
                return [2 /*return*/];
            });
        });
    };
    BarcodeScannerWeb.prototype.pauseScan = function () {
        return __awaiter(this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                if (this.intervalId) {
                    clearInterval(this.intervalId);
                    this.intervalId = undefined;
                }
                return [2 /*return*/];
            });
        });
    };
    BarcodeScannerWeb.prototype.resumeScan = function () {
        return __awaiter(this, void 0, void 0, function () {
            var barcodeDetector;
            var _this = this;
            return __generator(this, function (_a) {
                if (!this.videoElement) {
                    throw new Error(this.errorVideoElementMissing);
                }
                if (!this.stream) {
                    throw new Error('Scan was not started. Call startScan() first.');
                }
                if (this.intervalId) {
                    return [2 /*return*/];
                }
                if (!('BarcodeDetector' in window)) {
                    throw new core.CapacitorException('BarcodeDetector Web API is not available in this browser.', core.ExceptionCode.Unavailable);
                }
                barcodeDetector = new BarcodeDetector();
                this.intervalId = window.setInterval(function () { return __awaiter(_this, void 0, void 0, function () {
                    var barcodes, err_1;
                    return __generator(this, function (_a) {
                        switch (_a.label) {
                            case 0:
                                if (!this.videoElement)
                                    return [2 /*return*/];
                                _a.label = 1;
                            case 1:
                                _a.trys.push([1, 3, , 4]);
                                return [4 /*yield*/, barcodeDetector.detect(this.videoElement)];
                            case 2:
                                barcodes = _a.sent();
                                if (barcodes.length > 0) {
                                    this.handleScannedBarcodes(barcodes);
                                }
                                return [3 /*break*/, 4];
                            case 3:
                                err_1 = _a.sent();
                                console.error('Barcode detection failed', err_1);
                                return [3 /*break*/, 4];
                            case 4: return [2 /*return*/];
                        }
                    });
                }); }, 300);
                return [2 /*return*/];
            });
        });
    };
    BarcodeScannerWeb.prototype.readBarcodesFromImage = function (_options) {
        return __awaiter(this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                throw this.createUnimplementedException();
            });
        });
    };
    BarcodeScannerWeb.prototype.scan = function () {
        return __awaiter(this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                throw this.createUnimplementedException();
            });
        });
    };
    BarcodeScannerWeb.prototype.isSupported = function () {
        return __awaiter(this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                return [2 /*return*/, { supported: this._isSupported }];
            });
        });
    };
    BarcodeScannerWeb.prototype.enableTorch = function () {
        return __awaiter(this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                throw this.createUnimplementedException();
            });
        });
    };
    BarcodeScannerWeb.prototype.disableTorch = function () {
        return __awaiter(this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                throw this.createUnimplementedException();
            });
        });
    };
    BarcodeScannerWeb.prototype.toggleTorch = function () {
        return __awaiter(this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                throw this.createUnimplementedException();
            });
        });
    };
    BarcodeScannerWeb.prototype.isTorchEnabled = function () {
        return __awaiter(this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                throw this.createUnimplementedException();
            });
        });
    };
    BarcodeScannerWeb.prototype.isTorchAvailable = function () {
        return __awaiter(this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                return [2 /*return*/, { available: false }];
            });
        });
    };
    BarcodeScannerWeb.prototype.setZoomRatio = function (_options) {
        return __awaiter(this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                throw this.createUnimplementedException();
            });
        });
    };
    BarcodeScannerWeb.prototype.getZoomRatio = function () {
        return __awaiter(this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                throw this.createUnimplementedException();
            });
        });
    };
    BarcodeScannerWeb.prototype.getMinZoomRatio = function () {
        return __awaiter(this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                throw this.createUnimplementedException();
            });
        });
    };
    BarcodeScannerWeb.prototype.getMaxZoomRatio = function () {
        return __awaiter(this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                throw this.createUnimplementedException();
            });
        });
    };
    BarcodeScannerWeb.prototype.openSettings = function () {
        return __awaiter(this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                throw this.createUnimplementedException();
            });
        });
    };
    BarcodeScannerWeb.prototype.isGoogleBarcodeScannerModuleAvailable = function () {
        return __awaiter(this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                throw this.createUnimplementedException();
            });
        });
    };
    BarcodeScannerWeb.prototype.installGoogleBarcodeScannerModule = function () {
        return __awaiter(this, void 0, void 0, function () {
            return __generator(this, function (_a) {
                throw this.createUnimplementedException();
            });
        });
    };
    BarcodeScannerWeb.prototype.checkPermissions = function () {
        return __awaiter(this, void 0, void 0, function () {
            var result;
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0:
                        _a.trys.push([0, 2, , 3]);
                        return [4 /*yield*/, navigator.permissions.query({
                                name: 'camera',
                            })];
                    case 1:
                        result = _a.sent();
                        return [2 /*return*/, {
                                camera: result.state,
                            }];
                    case 2:
                        _a.sent();
                        return [2 /*return*/, {
                                camera: 'prompt',
                            }];
                    case 3: return [2 /*return*/];
                }
            });
        });
    };
    BarcodeScannerWeb.prototype.requestPermissions = function () {
        return __awaiter(this, void 0, void 0, function () {
            var stream;
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0:
                        _a.trys.push([0, 2, , 3]);
                        return [4 /*yield*/, navigator.mediaDevices.getUserMedia({ video: true })];
                    case 1:
                        stream = _a.sent();
                        stream.getTracks().forEach(function (track) { return track.stop(); });
                        return [2 /*return*/, {
                                camera: 'granted',
                            }];
                    case 2:
                        _a.sent();
                        return [2 /*return*/, {
                                camera: 'denied',
                            }];
                    case 3: return [2 /*return*/];
                }
            });
        });
    };
    BarcodeScannerWeb.prototype.createUnimplementedException = function () {
        return new core.CapacitorException('This method is not implemented on web.', core.ExceptionCode.Unimplemented);
    };
    BarcodeScannerWeb.prototype.handleScannedBarcodes = function (barcodes) {
        var result = {
            barcodes: barcodes.map(function (barcode) { return ({
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
            }); }),
        };
        this.notifyListeners(this.eventBarcodesScanned, result);
    };
    return BarcodeScannerWeb;
}(core.WebPlugin));

var web = /*#__PURE__*/Object.freeze({
    __proto__: null,
    BarcodeScannerWeb: BarcodeScannerWeb
});

exports.BarcodeScanner = BarcodeScanner;
//# sourceMappingURL=plugin.cjs.js.map
