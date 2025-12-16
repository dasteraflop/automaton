package automaton.text.tokens

import automaton.text.tokens.mixins.{Center, SizeVeryLarge}

/**
 * Name
 *
 * @param value text for this token value
 */
case class NameToken(
  value: String,
) extends Token with Center with SizeVeryLarge {

}
