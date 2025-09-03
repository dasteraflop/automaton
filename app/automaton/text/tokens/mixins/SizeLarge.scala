package automaton.text.tokens.mixins

import com.itextpdf.text.Element
import automaton.text.tokens.Token

private[tokens] trait SizeLarge {
  self: Token =>

  override def size: Float = 12
}
