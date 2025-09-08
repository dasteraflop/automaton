package automaton.gpt.response

/**
 * An abstraction over query response,
 * that we might want to add metadata or
 * something to eventually.
 */
trait ChatGPTResponse {

  def asString: String
}
