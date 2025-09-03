package automaton

import automaton.gpt.cache.Cache
import automaton.gpt.client.ChatGPTGenerator
import automaton.pdf.Writer
import automaton.text.Tokenizer

object Sandbox {

  def main(args: Array[String]): Unit = {
    sys.env.get("AUTOMATON_RESPONSE_TIME") match {
      case Some(v) => fromCache(v)
      case None => fromResponse()
    }
  }

  private def fromCache(str: String): Unit = {
    run(Cache.default().get(str, Cache.output))
  }

  private def fromResponse(): Unit = {
    run(ChatGPTGenerator.default().execute())
  }

  def run(text: String): Unit = {
    val tokens = Tokenizer.default().tokens(text)
    Writer.default().write(
      tokens,
      sys.env.getOrElse(
        "AUTOMATON_OUTPUT_LOCATION",
        throw new IllegalArgumentException()
      )
    )
    tokens.foreach(println)
  }
}