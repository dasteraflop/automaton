package automaton.gpt.client

/**
 * Configuration for calling chat GPT features.
 *
 * Currently only text files
 *
 * @param token access key
 */
case class ChatGPTClientConfig(
  token: String,
) {

}

object ChatGPTClientConfig {

  /**
   * Constructs config from environment vars
   *
   * @return
   */
  def default(): ChatGPTClientConfig = {
    ChatGPTClientConfig(
      get("AUTOMATON_GPT_TOKEN"),
    )
  }

  private def get(key: String): String = {
    sys.env.getOrElse(
      key,
      throw new IllegalArgumentException(s"missing $key environment variable")
    )
  }

  private def read(path: String) = {
    val src = scala.io.Source.fromFile(path)
    try {
      src.mkString
    } finally {
      src.close()
    }
  }
}