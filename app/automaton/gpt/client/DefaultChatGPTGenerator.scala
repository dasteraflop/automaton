package automaton.gpt.client

import com.openai.client.okhttp.OpenAIOkHttpClient
import com.openai.models.ChatModel
import com.openai.models.responses.ResponseCreateParams
import automaton.gpt.cache.Cache

import scala.jdk.CollectionConverters.*

private[gpt] class DefaultChatGPTGenerator(
  config: ChatGPTGenerateConfig,
  cache:  Cache
) extends ChatGPTGenerator {

  private val requestText: String =
    s"""
       |please rewrite following resume and capitalize section headers:
       |
       |${config.resume}
       |
       |For following job description:
       |
       |${config.description}
       |""".stripMargin

  override def execute(): String = {
    val client   = OpenAIOkHttpClient.builder().apiKey(
      config.token
    ).build()
    val params   = ResponseCreateParams
      .builder
      .input(requestText)
      .model(ChatModel.GPT_5)
      .build
    val response = client.responses.create(params)
    val outputs  = response.output().asScala
    println("*******requestText")
    println(requestText)
    println("^^^^^^^requestText")

    println("*******outputs")
    outputs.foreach(println)
    println("^^^^^^^outputs")

    val content  = outputs.find(
      _.isMessage
    ).map(
      _.asMessage()._content()
    ).get.asArray().get().asScala
    val s = content.head.asObject().get().get("text")
    val result = s.asString().get

    val time = System.currentTimeMillis()
    cache.put(result, Cache.output, time)
    cache.put(config.description, Cache.input, time)

    result
  }
}
