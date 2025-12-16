package web.service

import automaton.gpt.cache.Cache
import automaton.gpt.client.ChatGPTGenerator
import automaton.gpt.query.{ChatGPTQuery, ResumeAttributes}
import automaton.pdf.reader.Reader
import automaton.pdf.writer.Writer
import automaton.text.Tokenizer
import automaton.text.tokens.{BreakToken, TextToken}

import javax.inject.Inject

class ResumeService @Inject()(
  private val client:    ChatGPTGenerator,
  private val cache:     Cache,
  private val tokenizer: Tokenizer,
  private val writer:    Writer,
  private val reader:    Reader
) {

  def resume(
    description: String,
    include:     Seq[String],
    exclude:     Seq[String],
    attributes:  ResumeAttributes
  ): Unit = {
    val time     = System.currentTimeMillis()
    val query    = ChatGPTQuery.resume(
      reader.read(),
      description,
      include,
      exclude,
      attributes,
    )
    val response = client.execute(query)

    cache.put(response.asString, Cache.output, time)
    cache.put(query.text, Cache.input, time)
    val tokens = tokenizer.tokens(response.asString)
    println("tokens = ")
    tokens.foreach(println)
    println(s"completed write at '${writer.write(tokens)}'")
  }
}
