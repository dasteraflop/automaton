package automaton.text.tokens.mixins

import com.itextpdf.text.{Element, FontFactory}
import automaton.text.tokens.Token

private[tokens] trait FontBold {
  self: Token =>

  override def font: String = {
    FontFactory.HELVETICA_BOLD
  }

}
