package automaton.text.tokens.mixins

import automaton.text.tokens.Token

private[tokens] trait SizeVeryLarge {
  self: Token =>

  override def size: Float = 16
}
