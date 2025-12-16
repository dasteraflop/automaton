package automaton.text.tokens

import automaton.text.tokens.mixins.{FontBold, SizeNormal}

/**
 * Headers for a paragraph
 *
 * @param value text for this token value
 */
case class ParagraphToken private(
  value: String,
) extends Token with SizeNormal with FontBold {

}

object ParagraphToken {

  private val instance = ParagraphToken("---")

  def apply(): ParagraphToken = instance
}
