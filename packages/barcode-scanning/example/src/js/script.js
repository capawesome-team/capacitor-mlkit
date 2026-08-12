import 'barcode-detector/polyfill';

import {
  BarcodeScanner,
  GoogleBarcodeScannerModuleInstallState,
} from '@capacitor-mlkit/barcode-scanning';
import { Capacitor } from '@capacitor/core';
import { FilePicker } from '@capawesome/capacitor-file-picker';

const isWeb = Capacitor.getPlatform() === 'web';

let pickedFile;

const setResult = value => {
  document.querySelector('#result').textContent = `Result: ${value}`;
};

const setFile = value => {
  document.querySelector('#file').textContent = `File: ${value}`;
};

const setScannerResult = value => {
  document.querySelector('#scanner-result').textContent = value;
};

const runWithResult = async callback => {
  try {
    await callback();
  } catch (error) {
    setResult(error.message || error);
  }
};

const formatBarcodes = barcodes =>
  barcodes.length === 0
    ? 'No barcodes detected.'
    : barcodes
        .map(
          barcode =>
            `${barcode.format}: ${barcode.displayValue} (${barcode.valueType})`,
        )
        .join(' | ');

const getFormats = () => {
  const formats = document.querySelector('#formats').value;
  return formats?.length ? formats : undefined;
};

const showScanner = isVisible => {
  document.querySelector('#scanner').hidden = !isVisible;
  document.body.classList.toggle('barcode-scanner-active', isVisible);
};

const updateTorchButton = async () => {
  const button = document.querySelector('#toggle-torch');
  try {
    const { available } = await BarcodeScanner.isTorchAvailable();
    const { enabled } = await BarcodeScanner.isTorchEnabled();
    button.disabled = !available;
    button.textContent = `Torch: ${enabled ? 'On' : 'Off'}`;
  } catch {
    button.disabled = true;
    button.textContent = 'Torch: unavailable';
  }
};

const updateZoomRange = async () => {
  const range = document.querySelector('#zoom-ratio');
  try {
    const { zoomRatio: minZoomRatio } = await BarcodeScanner.getMinZoomRatio();
    const { zoomRatio: maxZoomRatio } = await BarcodeScanner.getMaxZoomRatio();
    const { zoomRatio } = await BarcodeScanner.getZoomRatio();
    range.min = minZoomRatio;
    range.max = maxZoomRatio;
    range.value = zoomRatio;
    range.disabled = false;
  } catch {
    range.disabled = true;
  }
};

const startScan = async () => {
  const options = {
    formats: getFormats(),
    lensFacing: document.querySelector('#lens-facing').value,
    resolution: Number(document.querySelector('#resolution').value),
  };
  if (isWeb) {
    options.videoElement = document.querySelector('#video');
  }
  setScannerResult('Waiting for barcodes...');
  showScanner(true);
  try {
    await BarcodeScanner.startScan(options);
  } catch (error) {
    showScanner(false);
    throw error;
  }
  await updateTorchButton();
  await updateZoomRange();
};

const stopScan = async () => {
  showScanner(false);
  await BarcodeScanner.stopScan();
};

void BarcodeScanner.addListener('barcodesScanned', event => {
  setScannerResult(formatBarcodes(event.barcodes));
});

void BarcodeScanner.addListener('scanError', event => {
  setScannerResult(event.message);
});

void BarcodeScanner.addListener(
  'googleBarcodeScannerModuleInstallProgress',
  event => {
    const state = GoogleBarcodeScannerModuleInstallState[event.state];
    setResult(`Module installation: ${state} (${event.progress ?? 0}%)`);
  },
);

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#video').hidden = !isWeb;

  document
    .querySelector('#start-scan')
    .addEventListener('click', () => runWithResult(startScan));
  document
    .querySelector('#stop-scan')
    .addEventListener('click', () => runWithResult(stopScan));
  document.querySelector('#scan').addEventListener('click', () =>
    runWithResult(async () => {
      const { barcodes } = await BarcodeScanner.scan({
        formats: getFormats(),
        autoZoom: document.querySelector('#auto-zoom').checked,
      });
      setResult(formatBarcodes(barcodes));
    }),
  );
  document.querySelector('#pick-image').addEventListener('click', () =>
    runWithResult(async () => {
      const { files } = await FilePicker.pickImages({ limit: 1 });
      pickedFile = files[0];
      setFile(pickedFile.name);
    }),
  );
  document
    .querySelector('#read-barcodes-from-image')
    .addEventListener('click', () =>
      runWithResult(async () => {
        if (!pickedFile) {
          setResult('Please pick an image first.');
          return;
        }
        const { barcodes } = await BarcodeScanner.readBarcodesFromImage({
          blob: pickedFile.blob,
          formats: getFormats(),
          path: pickedFile.path,
        });
        setResult(formatBarcodes(barcodes));
      }),
    );
  document.querySelector('#toggle-torch').addEventListener('click', () =>
    runWithResult(async () => {
      await BarcodeScanner.toggleTorch();
      await updateTorchButton();
    }),
  );
  document.querySelector('#zoom-ratio').addEventListener('ionChange', event =>
    runWithResult(async () => {
      await BarcodeScanner.setZoomRatio({ zoomRatio: event.detail.value });
    }),
  );
  document
    .querySelector('#is-google-barcode-scanner-module-available')
    .addEventListener('click', () =>
      runWithResult(async () => {
        const { available } =
          await BarcodeScanner.isGoogleBarcodeScannerModuleAvailable();
        setResult(`Module available: ${available}`);
      }),
    );
  document
    .querySelector('#install-google-barcode-scanner-module')
    .addEventListener('click', () =>
      runWithResult(async () => {
        await BarcodeScanner.installGoogleBarcodeScannerModule();
        setResult('Module installation started.');
      }),
    );
  document.querySelector('#is-supported').addEventListener('click', () =>
    runWithResult(async () => {
      const { supported } = await BarcodeScanner.isSupported();
      setResult(`Supported: ${supported}`);
    }),
  );
  document.querySelector('#check-permissions').addEventListener('click', () =>
    runWithResult(async () => {
      const { camera } = await BarcodeScanner.checkPermissions();
      setResult(`Camera permission: ${camera}`);
    }),
  );
  document.querySelector('#request-permissions').addEventListener('click', () =>
    runWithResult(async () => {
      const { camera } = await BarcodeScanner.requestPermissions();
      setResult(`Camera permission: ${camera}`);
    }),
  );
  document
    .querySelector('#open-settings')
    .addEventListener('click', () =>
      runWithResult(() => BarcodeScanner.openSettings()),
    );
});
