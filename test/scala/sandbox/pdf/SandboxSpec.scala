package automaton.pdf

import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatestplus.junit.JUnitRunner
import automaton.gpt.client.{ChatGPTGenerator, ChatGPTGenerateConfig}
import automaton.text.Tokenizer

@RunWith(classOf[JUnitRunner])
class SandboxSpec extends AnyFlatSpec {


  it should "work" in {
//    val tokens = Tokenizer.default().tokens(text)
//    Writer.default().write(
//      tokens,
//      "/Users/admin/sandbox/pdf/test.pdf"
//    )
    println("Accomplished backend and distributed systems engineer with a track record of building scalable, high-performance services and".length)
  }

  def text =
    """
      |""".stripMargin

}
