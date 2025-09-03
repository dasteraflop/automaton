package automaton.gpt.cache

import automaton.gpt.cache.SimpleDiskCache.timeFormat

import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}
import java.text.SimpleDateFormat

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
    tpe: String = Cache.output
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

object SimpleDiskCache {

  val timeFormat = new SimpleDateFormat("ddMMMHHmmss")

}
