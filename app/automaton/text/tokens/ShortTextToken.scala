package automaton.text.tokens

import automaton.text.tokens.mixins.{FontBold, SizeNormal}

/**
 * Name
 *
 * @param value text for this token value
 */
case class ShortTextToken (
  value: String,
) extends Token with SizeNormal with FontBold {

}
