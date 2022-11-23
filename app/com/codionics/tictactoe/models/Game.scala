package com.codionics.tictactoe.models

import com.codionics.tictactoe.models.enums.GameStatus
import play.api.libs.json.{Format, Json}

case class Game(state: GameState, status: GameStatus)

object Game {
  implicit val gameFormat: Format[Game] = Json.format[Game]

  val START = Game(GameState.START, GameStatus.InProgress)
}
