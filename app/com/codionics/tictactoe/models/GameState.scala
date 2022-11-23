package com.codionics.tictactoe.models

import play.api.libs.json.{Format, Json}
import com.codionics.tictactoe.models.enums.{CellState, HorizontalPosition, VerticalPosition}
import com.codionics.tictactoe.models.CellPosition._

case class GameState(cells: Seq[Cell]) {

  def isEmpty: Boolean = {
    cells.forall(_.state == CellState.Empty)
  }
}

object GameState {
  implicit val gameStateFormat: Format[GameState] = Json.format[GameState]

  val START: GameState = startGame()

  val WinningXPositions: Seq[Seq[Cell]] = getWinningPositions(CellState.X)

  val WinningOPositions: Seq[Seq[Cell]] = getWinningPositions(CellState.O)

  def startGame(): GameState = {
    val cellPositions = TopRow ++ CenterRow ++ BottomRow
    val cells         = cellPositions.map(cp => Cell(cp, CellState.Empty))
    GameState(cells)
  }

  def getWinningPositions(state: CellState): Seq[Seq[Cell]] = {
    WinningPositions.map(wp => wp.map(cp => Cell(cp, state)))
  }
}
