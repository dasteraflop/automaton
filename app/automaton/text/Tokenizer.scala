package automaton.text

import automaton.text.tokens.Token

trait Tokenizer {

  /**
   * Breaks up text to printable tokens
   *
   * @param text to tokenize
   * @return
   */
  def tokens(text: String): Seq[Token]
}

object Tokenizer {

  /**
   * Creates a new default instance
   *
   * @return
   */
  def default(): Tokenizer = {
    new MDTokenizer
  }
}