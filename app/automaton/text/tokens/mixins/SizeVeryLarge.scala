package automaton.text.tokens.mixins

import com.itextpdf.text.Element
import automaton.text.tokens.Token

private[tokens] trait SizeVeryLarge {
  self: Token =>

  override def size: Float = 16
}
