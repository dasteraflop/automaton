package automaton.text

import automaton.text.Tokenizer
import automaton.text.tokens.*
import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MDTokenizerSpec extends AnyFlatSpec {

  "MDTokenizer" should "tokenize good markdown text" in {
    val text =
      """
        |# Jimmy Bob
        |**title or what ever**
        |another text
        |
        |---
        |
        |## Profile
        |Technical leader blah blah
        |
        |---
        |
        |## Core Competencies
        |- item 1
        |- item2
        |- item3
        |
        |---
        |
        |## Professional Experience
        |
        |### title
        |*date – Present*
        |some text
        |- did things
        |- built stuff
        |
        |---
        |
        |### another job
        |*date – another date*
        |- other things
        |- and wrote some other stuff
        |""".stripMargin

    val expected = Seq(
      HeaderToken("Jimmy Bob", 1),
      BoldTextToken("title or what ever"),
      TextToken("another text"),
      BreakToken(""),
      ParagraphToken(),
      BreakToken(""),
      HeaderToken("Profile", 2),
      TextToken("Technical leader blah blah"),
      BreakToken(""),
      ParagraphToken(),
      BreakToken(""),
      HeaderToken("Core Competencies", 2),
      LineItemToken("- item 1"),
      LineItemToken("- item2"),
      LineItemToken("- item3"),
      BreakToken(""),
      ParagraphToken(),
      BreakToken(""),
      HeaderToken("Professional Experience", 2),
      BreakToken(""),
      HeaderToken("title", 3),
      BoldTextToken("date – Present"),
      TextToken("some text"),
      LineItemToken("- did things"),
      LineItemToken("- built stuff"),
      BreakToken(""),
      ParagraphToken(),
      BreakToken(""),
      HeaderToken("another job", 3),
      BoldTextToken("date – another date"),
      LineItemToken("- other things"),
      LineItemToken("- and wrote some other stuff"),
    )
    val tokens   = Tokenizer.default().tokens(text)
    tokens.foreach(println)
    assert(tokens == expected)

  }


}
