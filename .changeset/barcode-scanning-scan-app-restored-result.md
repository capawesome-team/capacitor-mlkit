---
'@capacitor-mlkit/barcode-scanning': patch
---

fix(android): deliver the `scan(...)` result via the `appRestoredResult` event if the app was destroyed while the scanner was open
