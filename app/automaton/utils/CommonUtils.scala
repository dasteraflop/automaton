package automaton.utils

import java.text.SimpleDateFormat

object CommonUtils {

  val timeFormat = new SimpleDateFormat("ddMMMHHmmss")

  def readEnv(key: String): String = {
    val v = sys.env.getOrElse(
      key,
      throw new IllegalArgumentException(
        s"$key env var not configured"
      )
    )
    println(s"config: ${key} = ${v}")
    v
  }

  def readFile(path: String): String = {
    val src = scala.io.Source.fromFile(path)
    try {
      src.mkString
    } finally {
      src.close()
    }
  }

}
