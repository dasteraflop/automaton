package automaton.pdf

import automaton.text.tokens.Token

/**
 * A PDF writer with pre-defined format, capable
 * of writing output of a GPT response to PDF
 */
trait Writer {

  /**
   * Writes a text extracted from a GPT response
   * as a PDF file with some predefined format
   *
   * @param tokens from a text file
   * @param path   what to write PDF to
   */
  def write(
    tokens: Seq[Token],
    path:   String
  ): Unit
}

object Writer {

  /**
   * Creates a new default instance
   *
   * @return
   */
  def default(): DefaultWriter = {
    new DefaultWriter()
  }
}