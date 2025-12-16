package web.service

import automaton.gpt.cache.Cache
import automaton.gpt.client.ChatGPTGenerator
import automaton.gpt.query.{ChatGPTQuery, ResumeAttributes}
import automaton.pdf.reader.Reader
import automaton.pdf.writer.Writer
import automaton.text.Tokenizer
import automaton.text.tokens.{BreakToken, TextToken}

import javax.inject.Inject

class KeywordService @Inject()(
  private val client: ChatGPTGenerator,
) {

  def getKeywords(
    resume: String,
  ): String = {
    val time     = System.currentTimeMillis()
    val query    = ChatGPTQuery.keywords(resume)
    val response = client.execute(query)
                         .asString
                         .dropWhile(_ != '[')
                         .filterNot(_ == '`')
    println("response = " + response)
    response
  }
}
