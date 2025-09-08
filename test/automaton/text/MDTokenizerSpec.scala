package automaton.text

import automaton.text.Tokenizer
import automaton.text.tokens.*
import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MDTokenizerSpec extends AnyFlatSpec {

  "MDTokenizer" should "tokenize good markdown text" in {
    val text   =
      """
        |# Name
        |things
        |and
        |stuff
        |
        |---
        |
        |## Profile
        |Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies
        |
        |---
        |
        |## Skills
        |- item 1
        |- item 2
        |- item 3
        |
        |---
        |✅ this line is not good
        |""".stripMargin
    val tokens = Tokenizer.default().tokens(text)
    assert(
      tokens == Seq(
        BreakToken(""),
        NameToken("Name"),
        ShortTextToken("things"),
        ShortTextToken("and"),
        ShortTextToken("stuff"),
        BreakToken(""),
        BreakToken(""),
        SectionHeaderToken("Profile"),
        LongTextToken("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies"),
        BreakToken(""),
        BreakToken(""),
        SectionHeaderToken("Skills"),
        LineItemToken("- item 1"),
        LineItemToken("- item 2"),
        LineItemToken("- item 3"),
        BreakToken("")

      )
    )
  }


}
