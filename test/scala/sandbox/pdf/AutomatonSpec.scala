package automaton.pdf

import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatestplus.junit.JUnitRunner
import automaton.gpt.client.{ChatGPTGenerator, ChatGPTGenerateConfig}
import automaton.text.{SimpleTokenizer, Tokenizer}

@RunWith(classOf[JUnitRunner])
class AutomatonSpec extends AnyFlatSpec {

  it should "work" in {
    val text   = ChatGPTGenerator.default().execute()
    val tokens = Tokenizer.default().tokens(text)
    Writer.default().write(
      tokens,
      "/Users/sandbox/pdf/test.pdf"
    )
    tokens.foreach(println)
  }


}
