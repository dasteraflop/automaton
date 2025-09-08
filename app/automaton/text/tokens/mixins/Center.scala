package automaton.text.tokens.mixins

import automaton.text.tokens.Token
import com.itextpdf.text.Element

private[tokens] trait Center {
  self: Token =>

  override def alignment: Int = Element.ALIGN_CENTER
}
