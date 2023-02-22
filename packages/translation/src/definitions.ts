export interface TranslationPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
