package automaton.text.tokens.mixins

import automaton.text.tokens.Token
import com.itextpdf.text.Element

private[tokens] trait SizeNormal {
  self: Token =>

  override def fontSize: Float = 8
}
