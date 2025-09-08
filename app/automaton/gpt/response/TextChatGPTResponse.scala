package automaton.gpt.response

/**
 * Simple response for now
 *
 * @param asString
 */
private[gpt] case class TextChatGPTResponse private[gpt](
  asString: String
) extends ChatGPTResponse {

}
