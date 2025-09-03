package automaton.text.tokens.mixins

import com.itextpdf.text.Element
import automaton.text.tokens.Token

private[tokens] trait Center {
  self: Token =>

  override def alignment: Int = Element.ALIGN_CENTER
}
