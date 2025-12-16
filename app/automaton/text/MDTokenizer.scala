package automaton.text

import automaton.text.tokens.*

import scala.annotation.tailrec

/**
 * Naive implementation that works when making some
 * formatting assumptions
 *
 */
private[text] class MDTokenizer extends Tokenizer {

  private val removeChars = Set('*', '#')

  override def tokens(text: String): Seq[Token] = {
    text.split(
      "\n"
    ).map(
      {
        case "---" => ParagraphToken()
        case "" => BreakToken()
        case s: String if isHeader(s) =>
          HeaderToken(
            cleanup(s),
            s.takeWhile(_ == '#').length
          )
        case s: String if isLineItem(s) =>
          LineItemToken(cleanup(s))
        //FIXME: ugh...
        case s: String if isBold(s) =>
          BoldTextToken(
            cleanup(s),
          )
        case default =>
          TextToken(cleanup(default))
      }
    ).filterNot(
      t => {
        val text = t.value.toLowerCase
        text.contains('`') ||
          text.contains("ats") ||
          text.contains("keywords") ||
          text.contains("markdown")
      }
    ).reverse.dropWhile(
      //drop tailing separators
      {
        case p: ParagraphToken => true
        case p: BreakToken => true
        case default => false
      }
    ).reverse.dropWhile(
      //...and then leading separators, if any
      {
        case p: ParagraphToken => true
        case p: BreakToken => true
        case default => false
      }
    )
  }

  private def isLineItem(s: String): Boolean = {
    s.startsWith("-")
  }

  private def isHeader(s: String): Boolean = {
    s.startsWith("#")
  }

  private def isBold(s: String): Boolean = {
    s.startsWith("*") && s.endsWith("*")
  }

  @tailrec
  private def group(tokens: Iterator[Token], groups: Seq[Seq[Token]]): Seq[Seq[Token]] = {
    if (tokens.isEmpty)
      return groups
    val g = tokens.takeWhile(
      {
        case t: ParagraphToken => false
        case default => true
      }
    )
    group(tokens, groups ++ Seq(g.toSeq ++ Seq(ParagraphToken())))
  }

  private def cleanup(s: String): String = {
    s.filterNot(removeChars.contains).trim
  }

}