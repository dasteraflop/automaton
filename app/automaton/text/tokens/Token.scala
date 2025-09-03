package automaton.text.tokens

import com.itextpdf.text.pdf.draw.LineSeparator
import com.itextpdf.text.{BaseColor, Element, FontFactory}

/**
 * Token with formatting options that
 * represents a line of text
 */
trait Token {

  /**
   * Actual text
   *
   * @return
   */
  def value: String

  /**
   * Font size
   *
   * @return
   */
  def size: Float

  /**
   * @see [[com.itextpdf.text.Element]]
   * @return
   */
  def alignment: Int = {
    Element.ALIGN_LEFT
  }

  /**
   * @see [[com.itextpdf.text.FontFactory]]
   * @return
   */
  def font: String = {
    FontFactory.HELVETICA
  }

  /**
   * @see [[com.itextpdf.text.BaseColor]]
   * @return
   */
  def color: BaseColor = {
    BaseColor.BLACK
  }

  /**
   * Optional separator after this token
   *
   * @return
   */
  def separator: Option[LineSeparator] = {
    None
  }
}
