import { WebPlugin } from "@capacitor/core";

import type { DocumentScannerPlugin } from "./definitions";

export class DocumentScannerWeb
  extends WebPlugin
  implements DocumentScannerPlugin
{
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log("ECHO", options);
    return options;
  }
}
