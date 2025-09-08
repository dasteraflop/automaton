package automaton.pdf.writer

import automaton.text.tokens.{NameToken, ShortTextToken}
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
    val tokens = Seq(
      NameToken("Name"),
      ShortTextToken("things"),
    )

    val writer = new DefaultWriter(WriterConfig(workDir))
    val p = writer.write(tokens)
    val f = new File(s"$p/resume.pdf")
    assert(f.exists())
    assert(f.delete())
  }
}
