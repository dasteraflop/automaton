package web.controllers

import automaton.gpt.query.ResumeAttributes
import play.api.libs.json.*
import play.api.mvc.*
import web.service.KeywordService

import javax.inject.Inject

class KeywordController @Inject()(
  val controllerComponents: ControllerComponents,
  val service:              KeywordService
) extends BaseController {

  def get(): Action[AnyContent] = {
    Action { request =>
      request.body.asJson match {
        case Some(v) =>
          val s = service.getKeywords(
            (v \ "text")
              .getOrElse(throw new NoSuchElementException())
              .as[String]
          )
          Json.parse(s).as[Seq[String]]
          Ok(s)
        case None =>
          BadRequest(message("Require a body"))
      }
    }
  }

  private def message(text: String): String = {
    s"""{"msg": "$text"}"""
  }
}