package automaton.text

import automaton.text.tokens.*

import scala.annotation.tailrec

/**
 * Naive implementation that works when making some
 * formatting assumptions
 *
 */
private[text] class SimpleTokenizer extends Tokenizer {

  private def isLineItem(lines: Seq[String], i: Int): Boolean = {
    lines(i).startsWith("- ")
  }

  private def isBreak(lines: Seq[String], i: Int): Boolean = {
    lines(i).isEmpty
  }

  private def isLongText(lines: Seq[String], i: Int): Boolean = {
    //approximately (almost) one line
    lines(i).length > 125
  }

  private def isSectionHeader(lines: Seq[String], i: Int): Boolean = {
    lines(i).matches("^[A-Z ]*$")
      && lines(i - 1).isEmpty
      && lines(i + 1).isEmpty
  }

  private def isParagraphHeader(lines: Seq[String], i: Int): Boolean = {
    lines(i).matches("^[A-Z ]*$") && lines(i - 1).isEmpty
  }

  @tailrec
  private def asTokens(lines: Seq[String], idx: Int, tokens: Seq[Token]): Seq[Token] = {
    if (idx >= lines.size)
      return tokens
    if (isParagraphHeader(lines, idx))
      asTokens(lines, idx + 1, tokens ++ Seq(ParagraphHeaderToken(lines(idx))))
    else if (isSectionHeader(lines, idx))
      asTokens(lines, idx + 1, tokens ++ Seq(SectionHeaderToken(lines(idx))))
    else if (isLineItem(lines, idx))
      asTokens(lines, idx + 1, tokens ++ Seq(LineItemToken(lines(idx))))
    else if (isBreak(lines, idx))
      asTokens(lines, idx + 1, tokens ++ Seq(BreakToken(lines(idx))))
    else if (isLongText(lines, idx))
      asTokens(lines, idx + 1, tokens ++ Seq(LongTextToken(lines(idx))))
    else
      asTokens(lines, idx + 1, tokens ++ Seq(ShortTextToken(lines(idx))))

  }

  def tokens(text: String): Seq[Token] = {
    val lines  = text.split("\n").toIndexedSeq
    //name, header, address line seem to always be first 3
    val tokens = Seq(
      NameToken(lines(0)),
      ShortTextToken(lines(1)),
      ShortTextToken(lines(2)),
    )

    asTokens(lines, 3, tokens)
  }

}
