package automaton.text.tokens.mixins

import automaton.text.tokens.Token
import com.itextpdf.text.FontFactory
import com.itextpdf.text.pdf.draw.LineSeparator

private[tokens] trait Separator {
  self: Token =>

  override def separator: Option[LineSeparator] = {
    Some(
      new LineSeparator(
        FontFactory.getFont(font, size, color)
      )
    )
  }
}
