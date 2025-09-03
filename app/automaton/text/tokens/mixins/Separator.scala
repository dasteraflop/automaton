package automaton.text.tokens.mixins

import com.itextpdf.text.FontFactory
import com.itextpdf.text.pdf.draw.LineSeparator
import automaton.text.tokens.Token

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
