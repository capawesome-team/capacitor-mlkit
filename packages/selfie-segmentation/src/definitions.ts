export interface SelfieSegmentationPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
