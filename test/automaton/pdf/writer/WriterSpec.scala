package automaton.pdf.writer

import automaton.text.Tokenizer
import automaton.text.tokens.{LineItemToken, NameToken, ParagraphToken, TextToken}
import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatestplus.junit.JUnitRunner

import java.io.File
import java.nio.file.Paths

@RunWith(classOf[JUnitRunner])
class WriterSpec extends AnyFlatSpec {

  private val workDir =
    Paths.get(s"./temp/").toAbsolutePath.normalize.toString

  "Writer" should "write tokens to PDF file" in {
    val tokens = Tokenizer.default().tokens(text)
    val writer = new DefaultWriter(WriterConfig(workDir))
    val p = writer.write(tokens)
    val f = new File(s"$p/resume.pdf")
    //TODO: actually test
    assert(f.exists())
    assert(f.delete())
  }

  private lazy val text =
    """
      |text
      |
      |---
      |
      |# stuff
      |blah
      |
      |""".stripMargin
}
