package com.codionics.tictactoe.models

import play.api.libs.json.{Format, Json}
import com.codionics.tictactoe.models.enums.{CellState, HorizontalPosition, VerticalPosition}
import com.codionics.tictactoe.models.CellPosition._
import play.api.Logger

case class GameState(cells: Seq[Cell]) {

  def isEmpty: Boolean = {
    cells.forall(_.state == CellState.Empty)
  }
}

object GameState {
  implicit val gameStateFormat: Format[GameState] = Json.format[GameState]

  val logger: Logger = Logger(this.getClass())

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

  def playerXMoves(position: CellPosition, state: GameState): Option[GameState] = {
    val xCell = Cell.getXCell(position)
    play(xCell, state)
  }

  def playerOMoves(position: CellPosition, state: GameState): Option[GameState] = {
    val oCell = Cell.getOCell(position)
    play(oCell, state)
  }

  private def play(cell: Cell, state: GameState): Option[GameState] = {
    val position            = cell.position
    val maybeCellIndexTuple = state.cells.zipWithIndex.find(cellIndexTuple => cellIndexTuple._1.position == position)
    val maybeUpdatedCells   = maybeCellIndexTuple.map(ciTuple => state.cells.updated(ciTuple._2, cell))

    if (maybeUpdatedCells.isEmpty) logger.warn(s"unable to update the new position: $position")
    else logger.debug(s"updated the new position: $position")

    maybeUpdatedCells.map(updatedCells => state.copy(cells = updatedCells))
  }
}
