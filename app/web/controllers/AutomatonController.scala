package web.controllers

import automaton.Sandbox
import automaton.gpt.client.{ChatGPTGenerateConfig, ChatGPTGenerator}
import play.api.mvc.*

import javax.inject.Inject

class AutomatonController @Inject()(
  val controllerComponents: ControllerComponents
) extends BaseController {

  private def message(text: String): String = {
    s"""{"msg": "$text"}"""
  }

  def index(): Action[AnyContent] = {
    Action { request =>
      request.body.asJson match {
        case Some(v) =>
          try {
            val text = ChatGPTGenerator.fromConfig(
              ChatGPTGenerateConfig.request(
                (v \ "text").getOrElse(throw new NoSuchElementException()).as[String]
              )
            ).execute()
            Sandbox.run(text)
            Ok(message("Ok"))
          } catch {
            case e: IllegalArgumentException =>
              ServiceUnavailable(message("something misconfigured"))
            case e: NoSuchElementException =>
              BadRequest(message("Require a text"))
            case e: Exception =>
              InternalServerError(message("Its all bad"))
          }
        case None =>
          BadRequest(message("Require a body"))
      }
    }
  }
}