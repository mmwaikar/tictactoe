package com.codionics.tictactoe.controllers

import com.codionics.tictactoe.models.Game
import play.api._
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.mvc._

import javax.inject._

@Singleton
class GameController @Inject() (val controllerComponents: ControllerComponents) extends BaseController {

  def start() = {
    Action { implicit request: Request[AnyContent] =>
      val start = Game.START
      Ok(Json.toJson(start))
    }
  }

  // def playX(): Action[JsValue] = {
  //   Action { implicit request: Request[JsValue] =>
  //     request.body.validate
  //   }
  // }
}
