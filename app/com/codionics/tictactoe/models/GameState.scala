package com.codionics.tictactoe.models

import play.api.libs.json.{Format, Json}
import com.codionics.tictactoe.models.enums.{CellState, HorizontalPosition, VerticalPosition}
import com.codionics.tictactoe.models.CellPosition._

case class GameState(cells: Seq[Cell])

object GameState {
  implicit val gameStateFormat: Format[GameState] = Json.format[GameState]

  val Start: GameState = startGame()

  def startGame(): GameState = {
    val cellPositions = TopRow ++ CenterRow ++ BottomRow
    val cells         = cellPositions.map(cp => Cell(cp, CellState.Empty))
    GameState(cells)
  }
}
