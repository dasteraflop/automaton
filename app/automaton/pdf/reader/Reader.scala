package automaton.pdf.reader

/**
 * Reads original resume
 */
trait Reader {

  def read(): String
}

object Reader {

  def default(): Reader = {
    new DefaultReader(
      ReaderConfig.default()
    )
  }
}