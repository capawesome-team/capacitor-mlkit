export interface SmartReplyPlugin {
  /**
   * Generate reply suggestions for a conversation.
   *
   * Smart Reply only supports conversations in English. If the conversation is
   * in another language or no suitable reply is found, the `status` will
   * reflect this and `suggestions` will be empty.
   *
   * Only available on Android and iOS.
   *
   * @since 8.2.0
   */
  suggestReplies(options: SuggestRepliesOptions): Promise<SuggestRepliesResult>;
}

/**
 * @since 8.2.0
 */
export interface SuggestRepliesOptions {
  /**
   * The recent messages of the conversation in chronological order (oldest first).
   *
   * Only the last 10 messages are considered by the model. The array must not be empty.
   *
   * @since 8.2.0
   */
  messages: TextMessage[];
}

/**
 * @since 8.2.0
 */
export interface TextMessage {
  /**
   * The text content of the message.
   *
   * @since 8.2.0
   */
  text: string;
  /**
   * The time the message was sent in milliseconds since epoch.
   *
   * @since 8.2.0
   * @example 1751990400000
   */
  timestamp: number;
  /**
   * Whether the message was sent by the local user.
   *
   * Reply suggestions are only generated for messages sent by remote users.
   *
   * @since 8.2.0
   */
  isLocalUser: boolean;
  /**
   * A stable identifier of the user who sent the message.
   *
   * Required when `isLocalUser` is `false`.
   *
   * @since 8.2.0
   */
  userId?: string;
}

/**
 * @since 8.2.0
 */
export interface SuggestRepliesResult {
  /**
   * The status of the suggestion generation.
   *
   * @since 8.2.0
   */
  status: SmartReplySuggestionResultStatus;
  /**
   * The generated reply suggestions.
   *
   * Contains up to 3 suggestions and is empty unless `status` is `SUCCESS`.
   *
   * @since 8.2.0
   */
  suggestions: string[];
}

/**
 * @since 8.2.0
 */
export enum SmartReplySuggestionResultStatus {
  /**
   * Suggestions were generated successfully.
   *
   * @since 8.2.0
   */
  Success = 'SUCCESS',
  /**
   * No suggestions were generated because the conversation language is not supported.
   *
   * @since 8.2.0
   */
  NotSupportedLanguage = 'NOT_SUPPORTED_LANGUAGE',
  /**
   * No suggestions were generated for the conversation.
   *
   * @since 8.2.0
   */
  NoReply = 'NO_REPLY',
}
