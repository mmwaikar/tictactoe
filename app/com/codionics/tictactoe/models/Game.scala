package com.codionics.tictactoe.models

import com.codionics.tictactoe.models.enums.GameStatus
import play.api.libs.json.{Format, Json}

case class Game(state: GameState, private val status: GameStatus, private val remainingMoves: Seq[Cell])

object Game {
  implicit val gameFormat: Format[Game] = Json.format[Game]

  def apply(state: GameState): Game = {
    val status         = GameState.getGameStatus(state)
    val remainingMoves = if (status == GameStatus.InProgress) state.getRemainingMoves else Seq.empty
    Game(state, status, remainingMoves)
  }

  val START = Game(GameState.START)
}
