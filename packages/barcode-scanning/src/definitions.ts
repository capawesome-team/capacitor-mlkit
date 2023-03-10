export interface BarcodeScannerPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
