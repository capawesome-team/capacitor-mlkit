---
'@capacitor-mlkit/barcode-scanning': minor
---

fix: moved video preview layer setup outside of async task to prevent race condition related crash on AVCaprtureSession.startRunning()
