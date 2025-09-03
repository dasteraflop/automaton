package automaton.text.tokens

import automaton.text.tokens.mixins.SizeNormal

/**
 * General item in a paragraph
 *
 * @param value text for this token value
 */
case class LineItemToken private[text](
  value: String,
) extends Token with SizeNormal {

}
