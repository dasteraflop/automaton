package web.service

import automaton.gpt.cache.Cache
import automaton.gpt.client.ChatGPTGenerator
import automaton.gpt.query.{ChatGPTQuery, ResumeAttributes}
import automaton.pdf.reader.Reader
import automaton.pdf.writer.Writer
import automaton.text.Tokenizer
import automaton.text.tokens.{BreakToken, ShortTextToken}

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
    attributes:  ResumeAttributes
  ): Unit = {
    val time     = System.currentTimeMillis()
    val query    = ChatGPTQuery.resume(
      reader.read(),
      description,
      attributes,
    )
    val response = client.execute(query)

    cache.put(response.asString, Cache.output, time)
    cache.put(query.text, Cache.input, time)
    val tokens = tokenizer.tokens(
      response.asString
    ).filter(
      {
        //TODO: clean up all this stuff
        case t: BreakToken =>
          println(s"filtered ${t}")
          false
        case t: ShortTextToken =>
          val a = t.value.toCharArray.exists(_ != '-')
          println(s"filtered ${t} - $a")
          a
        case default =>
          val a = !default.value.toLowerCase.contains("ats")
          println(s"filtered ${default} - $a")
          a
      }
    )

    tokens.foreach(println)

    writer.write(tokens)
  }
}
