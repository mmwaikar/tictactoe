package com.codionics.tictactoe.models

import play.api.libs.json.Format
import play.api.libs.json.Json
import com.codionics.tictactoe.models.enums.CellState

case class Move(state: GameState, position: CellPosition)

object Move {
  implicit val moveFormat: Format[Move] = Json.format[Move]

  def playerXMoves(state: GameState, position: CellPosition): Either[String, GameState] = {
    GameState.playerXMoves(state, position)
  }

  def playerOMoves(state: GameState, position: CellPosition): Either[String, GameState] = {
    GameState.playerOMoves(state, position)
  }
}
