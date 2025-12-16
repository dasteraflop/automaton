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

  def keywords(
    resume: String,
    model:  ChatModel = ChatModel.GPT_5_CHAT_LATEST,

  ): KeywordQuery = {
    KeywordQuery(
      resume,
      model
    )
  }

  def resume(
    resume:      String,
    description: String,
    include:     Seq[String],
    exclude:     Seq[String],
    attributes:  ResumeAttributes,
    model:       ChatModel = ChatModel.GPT_5_CHAT_LATEST,
  ): ResumeQuery = {
    ResumeQuery(
      resume, description, include, exclude, attributes, model
    )
  }
}