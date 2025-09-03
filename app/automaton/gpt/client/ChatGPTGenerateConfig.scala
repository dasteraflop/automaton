package automaton.gpt.client

/**
 * Configuration for calling chat GPT features.
 *
 * Currently only text files
 *
 * @param token       access key
 * @param description job description content as text
 * @param resume      original resume content as text
 */
case class ChatGPTGenerateConfig(
  token:       String,
  description: String,
  resume:      String,
) {

}

object ChatGPTGenerateConfig {

  /**
   * Constructs config from environment vars
   *
   * @return
   */
  def local(): ChatGPTGenerateConfig = {
    ChatGPTGenerateConfig(
      get("AUTOMATON_GPT_TOKEN"),
      read(get("AUTOMATON_DESCRIPTION_LOCATION")),
      read(get("AUTOMATON_ORIGINAL_LOCATION")),
    )
  }

  /**
   * Constructs config with description text from
   * a request
   *
   * @return
   */
  def request(description: String): ChatGPTGenerateConfig = {
    ChatGPTGenerateConfig(
      get("AUTOMATON_GPT_TOKEN"),
      description,
      read(get("AUTOMATON_ORIGINAL_LOCATION")),
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

  private def get(key: String): String = {
    sys.env.getOrElse(
      key,
      throw new IllegalArgumentException(s"missing $key environment variable")
    )
  }
}