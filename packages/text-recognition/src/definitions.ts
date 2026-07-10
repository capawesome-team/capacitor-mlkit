export interface TextRecognitionPlugin {
  /**
   * Recognizes text in the supplied image.
   *
   * Only available on Android and iOS.
   *
   * @since 8.2.0
   */
  processImage(options: ProcessImageOptions): Promise<ProcessImageResult>;
}

/**
 * @since 8.2.0
 */
export interface ProcessImageOptions {
  /**
   * The local path to the image file.
   *
   * @since 8.2.0
   */
  path: string;
  /**
   * The script of the text to recognize.
   *
   * Each script requires a separate model that is bundled with the app.
   *
   * @since 8.2.0
   * @default Script.Latin
   */
  script?: Script;
}

/**
 * @since 8.2.0
 */
export enum Script {
  /**
   * The Latin script.
   *
   * @since 8.2.0
   */
  Latin = 'LATIN',
  /**
   * The Chinese script.
   *
   * @since 8.2.0
   */
  Chinese = 'CHINESE',
  /**
   * The Devanagari script.
   *
   * @since 8.2.0
   */
  Devanagari = 'DEVANAGARI',
  /**
   * The Japanese script.
   *
   * @since 8.2.0
   */
  Japanese = 'JAPANESE',
  /**
   * The Korean script.
   *
   * @since 8.2.0
   */
  Korean = 'KOREAN',
}

/**
 * @since 8.2.0
 */
export interface ProcessImageResult {
  /**
   * The full recognized text.
   *
   * @since 8.2.0
   */
  text: string;
  /**
   * The recognized blocks of text.
   *
   * @since 8.2.0
   */
  blocks: TextBlock[];
}

/**
 * Represents a block of text.
 *
 * A block is a contiguous set of text lines, such as a paragraph or a column.
 *
 * @since 8.2.0
 */
export interface TextBlock {
  /**
   * The recognized text of the block.
   *
   * @since 8.2.0
   */
  text: string;
  /**
   * The bounding box of the block.
   *
   * @since 8.2.0
   */
  boundingBox?: Rect;
  /**
   * The four corner points of the block in clockwise order,
   * starting with the top-left point relative to the image.
   *
   * @since 8.2.0
   */
  cornerPoints?: Point[];
  /**
   * The BCP-47 language code of the recognized language of the block.
   *
   * @since 8.2.0
   * @example 'en'
   */
  recognizedLanguage?: string;
  /**
   * The recognized lines of text within the block.
   *
   * @since 8.2.0
   */
  lines: TextLine[];
}

/**
 * Represents a line of text.
 *
 * @since 8.2.0
 */
export interface TextLine {
  /**
   * The recognized text of the line.
   *
   * @since 8.2.0
   */
  text: string;
  /**
   * The bounding box of the line.
   *
   * @since 8.2.0
   */
  boundingBox?: Rect;
  /**
   * The four corner points of the line in clockwise order,
   * starting with the top-left point relative to the image.
   *
   * @since 8.2.0
   */
  cornerPoints?: Point[];
  /**
   * The BCP-47 language code of the recognized language of the line.
   *
   * @since 8.2.0
   * @example 'en'
   */
  recognizedLanguage?: string;
  /**
   * The recognized elements of text within the line.
   *
   * @since 8.2.0
   */
  elements: TextElement[];
}

/**
 * Represents an element of text.
 *
 * An element is a contiguous set of characters, such as a word.
 *
 * @since 8.2.0
 */
export interface TextElement {
  /**
   * The recognized text of the element.
   *
   * @since 8.2.0
   */
  text: string;
  /**
   * The bounding box of the element.
   *
   * @since 8.2.0
   */
  boundingBox?: Rect;
  /**
   * The four corner points of the element in clockwise order,
   * starting with the top-left point relative to the image.
   *
   * @since 8.2.0
   */
  cornerPoints?: Point[];
  /**
   * The BCP-47 language code of the recognized language of the element.
   *
   * @since 8.2.0
   * @example 'en'
   */
  recognizedLanguage?: string;
}

/**
 * Represents a rectangle.
 *
 * @since 8.2.0
 */
export interface Rect {
  /**
   * The left coordinate of the rectangle.
   *
   * @since 8.2.0
   */
  left: number;
  /**
   * The top coordinate of the rectangle.
   *
   * @since 8.2.0
   */
  top: number;
  /**
   * The right coordinate of the rectangle.
   *
   * @since 8.2.0
   */
  right: number;
  /**
   * The bottom coordinate of the rectangle.
   *
   * @since 8.2.0
   */
  bottom: number;
}

/**
 * Represents a point.
 *
 * @since 8.2.0
 */
export interface Point {
  /**
   * The x coordinate of the point.
   *
   * @since 8.2.0
   */
  x: number;
  /**
   * The y coordinate of the point.
   *
   * @since 8.2.0
   */
  y: number;
}
