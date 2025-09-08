package automaton.text.tokens

import automaton.text.tokens.mixins.{Center, SizeNormal}

/**
 * Line break
 *
 * @param value text for this token value
 */
case class BreakToken (
  value: String,
) extends Token with Center with SizeNormal {

}
