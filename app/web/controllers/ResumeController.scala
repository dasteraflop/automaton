package web.controllers

import automaton.gpt.query.ResumeAttributes
import play.api.mvc.*
import web.service.ResumeService

import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

class ResumeController @Inject()(
  val controllerComponents: ControllerComponents,
  val service:              ResumeService
) extends BaseController {

  def index(
    atsFriendly: Option[Boolean],
    relevance:   Option[Boolean],
    education:   Option[Boolean],
  ): Action[AnyContent] = {
    Action { request =>
      request.body.asJson match {
        case Some(v) =>
          try {
            //TODO: cleanup
            service.resume(
              (v \ "text").getOrElse(throw new NoSuchElementException()).as[String],
              (v \ "include").asOpt[Seq[String]].getOrElse(Seq()),
              (v \ "exclude").asOpt[Seq[String]].getOrElse(Seq()),
              ResumeAttributes(
                atsFriendly, relevance, education
              )
            )
            Ok(message("Ok"))
          } catch {
            case e: IllegalArgumentException =>
              e.printStackTrace()
              ServiceUnavailable(message("something misconfigured"))
            case e: NoSuchElementException =>
              e.printStackTrace()
              BadRequest(message("Require a text"))
            case e: Exception =>
              e.printStackTrace()
              InternalServerError(message("Its all bad"))
          }
        case None =>
          BadRequest(message("Require a body"))
      }
    }
  }

  private def message(text: String): String = {
    s"""{"msg": "$text"}"""
  }
}