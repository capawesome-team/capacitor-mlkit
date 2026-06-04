/**
 * @since 0.0.1
 */
export var BarcodeFormat;
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
})(BarcodeFormat || (BarcodeFormat = {}));
/**
 * @since 0.0.1
 */
export var BarcodeValueType;
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
})(BarcodeValueType || (BarcodeValueType = {}));
/**
 * @since 7.0.0
 */
export var Resolution;
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
})(Resolution || (Resolution = {}));
/**
 * @since 0.0.1
 */
export var LensFacing;
(function (LensFacing) {
    /**
     * @since 0.0.1
     */
    LensFacing["Front"] = "FRONT";
    /**
     * @since 0.0.1
     */
    LensFacing["Back"] = "BACK";
})(LensFacing || (LensFacing = {}));
/**
 * @since 5.1.0
 */
export var GoogleBarcodeScannerModuleInstallState;
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
})(GoogleBarcodeScannerModuleInstallState || (GoogleBarcodeScannerModuleInstallState = {}));
/**
 * @since 7.0.0
 */
export var AddressType;
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
})(AddressType || (AddressType = {}));
/**
 * @since 7.0.0
 */
export var EmailFormatType;
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
})(EmailFormatType || (EmailFormatType = {}));
/**
 * @since 7.0.0
 */
export var PhoneFormatType;
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
})(PhoneFormatType || (PhoneFormatType = {}));
/**
 * @since 7.0.0
 */
export var WifiEncryptionType;
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
})(WifiEncryptionType || (WifiEncryptionType = {}));
//# sourceMappingURL=definitions.js.map