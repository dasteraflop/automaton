package automaton.text.tokens

import automaton.text.tokens.mixins.{FontBold, SizeNormal}

/**
 *
 * @param value text for this token value
 */
case class BoldTextToken(
  value: String,
) extends Token with SizeNormal {

}
