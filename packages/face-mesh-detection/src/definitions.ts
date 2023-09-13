export interface FaceMeshDetectionPlugin {
  processImage(options: ProcessImageOptions): Promise<ProcessImageResult>;
}

export interface ProcessImageOptions {
  path: string;
}

export interface ProcessImageResult {
  faceMeshes: any[];
}
