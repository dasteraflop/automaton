package automaton.gpt.cache

/**
 * Caches a Chat GPT response with current time
 */
trait Cache {

  /**
   * Caches a response
   *
   * @param value response string
   * @param tpe   type - whether input or output
   * @param time  current time
   */
  def put(
    value: String,
    tpe:   String,
    time:  Long = System.currentTimeMillis()
  ): Unit

  /**
   * Gets a previous response
   *
   * @param time formatted time
   * @param tpe  type - whether input or output
   */
  def get(
    time: String,
    tpe:  String
  ): String
}


object Cache {

  val input  = "input"
  val output = "output"

  /**
   * Default implementation
   *
   * @return
   */
  def default(): Cache = {
    sys.env.get(
      "AUTOMATON_CACHE_LOCATION"
    ).map(
      s => {
        new SimpleDiskCache(s)
      }
    ).getOrElse(
      new NoOpCache()
    )
  }
}