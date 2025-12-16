package automaton.gpt.cache

import automaton.utils.CommonUtils.timeFormat

import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}

class NoOpCache() extends Cache {

  override def put(
    value: String,
    tpe:   String,
    time:  Long
  ): Unit = {

  }

  override def get(
    time: String,
    tpe:  String = Cache.output
  ): String = {
    ""
  }
}
