package automaton.pdf.writer

import automaton.utils.CommonUtils.readEnv

case class WriterConfig(
  outputLocation: String
)

object WriterConfig {

  def default(): WriterConfig = {
    WriterConfig(
      readEnv("AUTOMATON_OUTPUT_LOCATION")
    )
  }
}