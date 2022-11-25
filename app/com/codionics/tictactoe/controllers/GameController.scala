package com.codionics.tictactoe.controllers

import com.codionics.tictactoe.models.Game
import com.codionics.tictactoe.models.Move
import play.api._
import play.api.libs.json.JsError
import play.api.libs.json.Json
import play.api.mvc._

import javax.inject._
import scala.concurrent._

@Singleton
class GameController @Inject() (val controllerComponents: ControllerComponents)(implicit ec: ExecutionContext)
    extends BaseController {

  def start() = {
    Action { implicit request: Request[AnyContent] =>
      val start = Game.START
      Ok(Json.toJson(start))
    }
  }

  def playerXMoves() = {
    Action.async(parse.json) { implicit request =>
      request.body
        .validate[Move]
        .fold(
          errors => Future(BadRequest(JsError.toJson(errors).toString())),
          move => {
            val eitherState = Move.playerXMoves(move.state, move.position)
            eitherState match {
              case Left(error)  => Future(BadRequest(error))
              case Right(state) => Future(Created(Json.toJson(Game(state))))
            }
          }
        )
    }
  }
}
