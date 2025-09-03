package automaton.gpt.client

import automaton.gpt.cache.Cache
import automaton.gpt.client.{ChatGPTGenerateConfig, DefaultChatGPTGenerator}

trait ChatGPTGenerator {

  /**
   * Executes a request and extract text
   * response containing new generated resume
   *
   * @return
   */
  def execute(): String
}

object ChatGPTGenerator {

  def default(): ChatGPTGenerator = {
    new DefaultChatGPTGenerator(
      ChatGPTGenerateConfig.local(),
      Cache.default()
    )
  }

  def fromConfig(
    config: ChatGPTGenerateConfig,
    cache:  Cache = Cache.default()
  ): ChatGPTGenerator = {
    new DefaultChatGPTGenerator(config, cache)
  }
}
