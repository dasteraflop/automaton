package automaton.text.tokens

import automaton.text.tokens.mixins.{Center, SizeLarge}

/**
 * Headers for a section
 *
 * @param value text for this token
 */
case class SectionHeaderToken private[text](
  value: String,
) extends Token with Center with SizeLarge {

}
