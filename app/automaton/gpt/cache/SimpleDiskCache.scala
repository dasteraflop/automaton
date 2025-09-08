package automaton.gpt.cache


import automaton.utils.CommonUtils.timeFormat

import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}

class SimpleDiskCache(
  location: String
) extends Cache {

  /**
   * Caches a response
   *
   * @param value response string
   * @param tpe   type - whether input or output
   * @param time  current time
   */
  override def put(
    value: String,
    tpe:   String,
    time:  Long
  ): Unit = {
    val cachePath = s"$location/${timeFormat.format(time)}"
    println(s"caching ${tpe} at ${cachePath}")
    if (!new File(cachePath).exists()) {
      Files.createDirectory(
        Paths.get(s"$location/${timeFormat.format(time)}")
      )
    }
    Files.write(
      Paths.get(s"$location/${timeFormat.format(time)}/$tpe.txt"),
      value.getBytes(StandardCharsets.UTF_8)
    )

  }

  /**
   * Gets a previous response
   *
   * @param time formatted time
   * @param tpe  type - whether input or output
   */
  override def get(
    time: String,
    tpe:  String = Cache.output
  ): String = {
    val src = scala.io.Source.fromFile(
      s"$location/$time/$tpe.txt"
    )
    try {
      src.mkString
    } finally {
      src.close()
    }
  }
}
