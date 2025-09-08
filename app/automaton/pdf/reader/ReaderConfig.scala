package automaton.pdf.reader

import automaton.utils.CommonUtils.readEnv

case class ReaderConfig(
  inputLocation: String
)

object ReaderConfig {

  def default(): ReaderConfig = {
    ReaderConfig(
      readEnv("AUTOMATON_ORIGINAL_LOCATION")
    )
  }
}