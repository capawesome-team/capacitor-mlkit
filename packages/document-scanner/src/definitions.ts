export interface DocumentScannerPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
