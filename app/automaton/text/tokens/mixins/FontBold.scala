package automaton.text.tokens.mixins

import automaton.text.tokens.Token
import com.itextpdf.text.{Element, FontFactory}

private[tokens] trait FontBold {
  self: Token =>

  override def font: String = {
    FontFactory.HELVETICA_BOLD
  }

}
