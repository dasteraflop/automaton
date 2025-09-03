package automaton.text.tokens

import automaton.text.tokens.mixins.{Center, Separator, SizeLarge}

/**
 * Headers for a paragraph
 *
 * @param value text for this token value
 */
case class ParagraphHeaderToken private[text](
  value: String,
) extends Token with Center with SizeLarge with Separator {

}
