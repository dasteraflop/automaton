package automaton.pdf.reader

import automaton.utils.CommonUtils

private[pdf] class DefaultReader(
  config: ReaderConfig
) extends Reader {

  override def read(): String = {
    CommonUtils.readFile(config.inputLocation)
  }
}
