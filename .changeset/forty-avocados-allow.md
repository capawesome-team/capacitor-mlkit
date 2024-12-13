---
'@capacitor-mlkit/barcode-scanning': minor
---

Introduce a serial DispatchQueue for managing AVCaptureSession operations in a FIFO (First In, First Out) sequence. Configuration tasks are encapsulated within a synchronous block to ensure complete setup before proceeding. Following this, startRunning() is scheduled asynchronously, guaranteeing it executes only after the configuration is fully committed.

This approach not only prevents the NSGenericException by ensuring proper sequence of operations but also maintains high performance and responsiveness of the application.

Related PR [fix(barcode-scanning): add delay before starting camera session #188](https://github.com/capawesome-team/capacitor-mlkit/pull/188)
