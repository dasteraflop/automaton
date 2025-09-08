package automaton.gpt.query

import com.openai.models.ChatModel

/**
 * Builds a query a ChatGPT generator
 * can execute
 */
trait ChatGPTQuery {

  /**
   * Text we will ultimately send
   *
   * @return
   */
  def text: String

  /**
   * Model we'll use
   *
   * @return
   */
  def model: ChatModel
}

object ChatGPTQuery {

  def resume(
    resume:      String,
    description: String,
    attributes:  ResumeAttributes,
    model:       ChatModel = ChatModel.GPT_5_CHAT_LATEST,
  ): ResumeQuery = {
    ResumeQuery(
      resume, description, attributes, model
    )
  }
}