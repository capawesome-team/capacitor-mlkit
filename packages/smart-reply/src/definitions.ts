export interface SmartReplyPlugin {
  /**
   * Generate smart replies for the given messages.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  smartReply(messages: [TextMessage]): Promise<{ value: [SmartReplySuggestion] }>;
}

/**
 * A single chat message, to be used as an array element for input to Smart Reply.
 * 
 * @since 0.0.1
 */
export interface TextMessage {
  /**
   * A single chat message, to be used as an array element for input to Smart Reply.
   *
   * @since 0.0.1
   */
  text: string;

  /**
   * Timestamp of the chat message.
   *
   * @since 0.0.1
   */
  timestamp: string | Date;

  /**
   * User ID of the message sender.
   *
   * @since 0.0.1
   */
  userID: string;

  /**
   * Whether this message is from the user that the suggestions are generated for.
   *
   * @since 0.0.1
   */
  isLocalUser: boolean;
}

/**
 * A sequence of chat messages
 * 
 * @since 0.0.1
 */
export interface Conversation {
  /**
   * Messages in the conversation.
   *
   * @since 0.0.1
   */
  messages: [TextMessage];
}


/**
 * A suggested reply for the given input text.
 * 
 * @since 0.0.1
 */
export interface SmartReplySuggestion {
  /**
   * String representation of the suggested reply.
   *
   * @since 0.0.1
   */
  text: string;
}
