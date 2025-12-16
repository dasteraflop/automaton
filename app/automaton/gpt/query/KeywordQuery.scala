package automaton.gpt.query

import com.openai.models.ChatModel

case class KeywordQuery(
  resume: String,
  model:  ChatModel,
) extends ChatGPTQuery {

  override lazy val text: String =
    s"""
       |please extract top 40 relevant ATS keywords from following resume and respond in a JSON array:
       |
       |${resume}
       |""".stripMargin

}