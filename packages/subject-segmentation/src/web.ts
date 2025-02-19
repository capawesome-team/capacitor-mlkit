import { WebPlugin } from "@capacitor/core";

import type { SubjectSegmentationPlugin } from "./definitions";

export class SubjectSegmentationWeb
  extends WebPlugin
  implements SubjectSegmentationPlugin
{
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log("ECHO", options);
    return options;
  }
}
