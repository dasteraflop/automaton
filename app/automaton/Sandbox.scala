package automaton

import automaton.gpt.cache.Cache
import automaton.pdf.writer.Writer
import automaton.text.Tokenizer
import automaton.utils.CommonUtils

object Sandbox {

  def main(args: Array[String]): Unit = {
    val cache = Cache.default()
    val text = cache.get(
      CommonUtils.readEnv("AUTOMATON_RESPONSE_TIME"),
      Cache.output
    )

    Writer.default().write(
      Tokenizer.default().tokens(text)
    )
  }

}