package automaton.gpt.client

import automaton.gpt.query.ChatGPTQuery
import automaton.gpt.response.{ChatGPTResponse, TextChatGPTResponse}
import com.openai.client.okhttp.OpenAIOkHttpClient
import com.openai.models.responses.ResponseCreateParams

import scala.jdk.CollectionConverters.*

private[gpt] class DefaultChatGPTGenerator(
  config: ChatGPTClientConfig
) extends ChatGPTGenerator {

  override def execute(query: ChatGPTQuery): ChatGPTResponse = {
    val requestText = query.text
    val client      = OpenAIOkHttpClient.builder().apiKey(
      config.token
    ).build()
    val params      = ResponseCreateParams
      .builder
      .input(requestText)
      .model(query.model)
      .build
    val response    = client.responses.create(params)
    val outputs     = response.output().asScala
    println("*******requestText")
    println(requestText)
    println("^^^^^^^requestText")

    println("*******outputs")
    outputs.foreach(println)
    println("^^^^^^^outputs")

    val content = outputs.find(
      _.isMessage
    ).map(
      _.asMessage()._content()
    ).get.asArray().get().asScala
    val s       = content.head.asObject().get().get("text")
    val result  = s.asString().get
    TextChatGPTResponse(result)
  }
}
