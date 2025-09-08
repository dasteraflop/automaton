package automaton.text

import automaton.text.tokens.*

import scala.annotation.tailrec

/**
 * Naive implementation that works when making some
 * formatting assumptions
 *
 */
private[text] class MDTokenizer extends Tokenizer {

  /**
   * Initial clean up etc
   * @param lines
   * @return
   */
  private def transform(lines: Seq[String]): Seq[String] = {
    lines.filterNot(_.toLowerCase.contains("ats"))
  }

  def tokens(text: String): Seq[Token] = {
    val lines = text.split("\n").toIndexedSeq
    //name should probably always be first
    asTokens(transform(lines), 0, Seq())
  }

  private def isLineItem(lines: Seq[String], i: Int): Boolean = {
    lines(i).startsWith("- ")
  }

  private def isBreak(lines: Seq[String], i: Int): Boolean = {
    lines(i).isEmpty
  }

  private def isName(lines: Seq[String], i: Int): Boolean = {
    lines(i).startsWith("# ")
  }

  private def isLongText(lines: Seq[String], i: Int): Boolean = {
    //approximately (almost) one line
    lines(i).length > 125
  }

  private def isSectionHeader(lines: Seq[String], i: Int): Boolean = {
    lines(i).startsWith("## ")
  }

  private def isParagraphHeader(lines: Seq[String], i: Int): Boolean = {
    lines(i).startsWith("### ")
  }

  private val removeChars = Set('*', '#')


  private def cleanup(string: String): String = {
    string.filterNot(removeChars.contains).mkString.trim
  }

  @tailrec
  private def group(tokens: Iterator[Token], groups: Seq[Seq[Token]]): Seq[Seq[Token]] = {
    if (tokens.isEmpty)
      return groups
    val g = tokens.takeWhile(
      {
        case t: ShortTextToken if (t.value == "---") => false
        case default => true
      }
    )
    group(tokens, groups ++ Seq(g.toSeq))
  }

  private def filter(tokens: Seq[Token]): Seq[Token] = {
    val g = group(
      tokens.iterator, Seq()
    )
    g.filter(
      g => g.nonEmpty &&
        g.exists {
          case _: NameToken => true
          case _: ParagraphHeaderToken => true
          case _: SectionHeaderToken => true
          case default => false
        }
    ).flatten
  }

  @tailrec
  private def asTokens(lines: Seq[String], idx: Int, tokens: Seq[Token]): Seq[Token] = {
    if (idx >= lines.size) {
      return filter(tokens)
    }
    if (isParagraphHeader(lines, idx))
      asTokens(lines, idx + 1, tokens ++ Seq(ParagraphHeaderToken(cleanup(lines(idx)))))
    else if (isSectionHeader(lines, idx))
      asTokens(lines, idx + 1, tokens ++ Seq(SectionHeaderToken(cleanup(lines(idx)))))
    else if (isLineItem(lines, idx))
      asTokens(lines, idx + 1, tokens ++ Seq(LineItemToken(cleanup(lines(idx)))))
    else if (isBreak(lines, idx))
      asTokens(lines, idx + 1, tokens ++ Seq(BreakToken(cleanup(lines(idx)))))
    else if (isName(lines, idx))
      asTokens(lines, idx + 1, tokens ++ Seq(NameToken(cleanup(lines(idx)))))
    else if (isLongText(lines, idx))
      asTokens(lines, idx + 1, tokens ++ Seq(LongTextToken(cleanup(lines(idx)))))
    else
      asTokens(lines, idx + 1, tokens ++ Seq(ShortTextToken(cleanup(lines(idx)))))

  }

}
