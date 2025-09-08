package automaton.text.tokens

import automaton.text.tokens.mixins.{FontBold, SizeNormal}

/**
 * Headers for a paragraph
 *
 * @param value text for this token value
 */
case class ParagraphHeaderToken(
  value: String,
) extends Token with SizeNormal with FontBold {

}
