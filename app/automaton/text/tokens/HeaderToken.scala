package automaton.text.tokens

import automaton.text.tokens.mixins.{Center, Separator, SizeLarge}

/**
 * Headers for a section
 *
 * @param value text for this token
 */
case class HeaderToken(
  value: String,
  size:  Int
) extends Token with Center with SizeLarge {

}
