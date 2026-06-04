import { registerPlugin } from '@capacitor/core';
var BarcodeScanner = registerPlugin('BarcodeScanner', {
    web: function () { return import('./web').then(function (m) { return new m.BarcodeScannerWeb(); }); },
});
export * from './definitions';
export { BarcodeScanner };
//# sourceMappingURL=index.js.map