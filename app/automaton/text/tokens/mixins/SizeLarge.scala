package automaton.text.tokens.mixins

import automaton.text.tokens.Token
import com.itextpdf.text.Element

private[tokens] trait SizeLarge {
  self: Token =>

  override def size: Float = 12
}
