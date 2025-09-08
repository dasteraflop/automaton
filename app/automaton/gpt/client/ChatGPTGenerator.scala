package automaton.gpt.client

import automaton.gpt.cache.Cache
import automaton.gpt.client.{ChatGPTClientConfig, DefaultChatGPTGenerator}
import automaton.gpt.query.ChatGPTQuery
import automaton.gpt.response.ChatGPTResponse

trait ChatGPTGenerator {

  /**
   * Executes a request and extract text
   * response containing new generated resume
   *
   * @return a query to generate a response for
   */
  def execute(query: ChatGPTQuery): ChatGPTResponse
}

object ChatGPTGenerator {

  def default(): ChatGPTGenerator = {
    new DefaultChatGPTGenerator(
      ChatGPTClientConfig.default(),
    )
  }

  def fromConfig(
    config: ChatGPTClientConfig,
    cache:  Cache = Cache.default()
  ): ChatGPTGenerator = {
    new DefaultChatGPTGenerator(config)
  }
}
