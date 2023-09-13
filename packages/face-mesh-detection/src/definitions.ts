export interface FaceMeshDetectionPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
