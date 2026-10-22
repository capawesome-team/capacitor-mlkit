---
'@capacitor-mlkit/document-scanner': patch
---

fix(android): deliver the `scanDocument(...)` result via the `appRestoredResult` event if the app was destroyed while the scanner was open
