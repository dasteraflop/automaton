package automaton.text.tokens

import automaton.text.tokens.mixins.{Center, Separator, SizeLarge}

/**
 * Headers for a section
 *
 * @param value text for this token
 */
case class SectionHeaderToken(
  value: String,
) extends Token with Center with SizeLarge with Separator {

}
