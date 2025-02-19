export interface SubjectSegmentationPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
