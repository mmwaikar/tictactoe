package com.codionics.tictactoe.models

import play.api.libs.json.{Format, Json}
import com.codionics.tictactoe.models.enums.{CellState, HorizontalPosition, VerticalPosition}
import com.codionics.tictactoe.models.CellPosition._
import play.api.Logger

case class GameState(cells: Seq[Cell]) {

  def isEmpty: Boolean = cells.forall(_.state == CellState.Empty)

  def isInProgress: Boolean = cells.exists(_.state == CellState.Empty)

  def hasPlayerXWon = GameState.hasPlayerWon(this, CellState.X)

  def hasPlayerOWon = GameState.hasPlayerWon(this, CellState.O)

  def isTied = !hasPlayerXWon && !hasPlayerOWon && !isInProgress
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

  def getSampleTiedGame(): GameState = {
    val cellPositions = TopRow ++ CenterRow ++ BottomRow
    val cells         = cellPositions.zipWithIndex.map { case (cp, index) =>
      val cellState = if (index % 2 == 0) CellState.X else CellState.O
      Cell(cp, cellState)
    }
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

  def hasPlayerWon(state: GameState, cellState: CellState) = {
    val cellsForState = state.cells.filter(c => c.state == cellState)
    logger.debug(s"cells for state: $cellState - $cellsForState")

    if (cellsForState.size < 3) false
    else {
      val positions           = cellsForState.map(_.position)
      val byTopRow            = positions.containsSlice(CellPosition.TopRow)
      val byCenterRow         = positions.containsSlice(CellPosition.CenterRow)
      val byBottomRow         = positions.containsSlice(CellPosition.BottomRow)
      val byLeftColumn        = positions.containsSlice(CellPosition.LeftColumn)
      val byCenterColumn      = positions.containsSlice(CellPosition.CenterColumn)
      val byRightColumn       = positions.containsSlice(CellPosition.RightColumn)
      val byTopBottomDiagonal = positions.containsSlice(CellPosition.TopBottomDiagonal)
      val byBottomTopDiagonal = positions.containsSlice(CellPosition.BottomTopDiagonal)

      val byRows      = byTopRow || byCenterRow || byBottomRow
      val byColumns   = byLeftColumn || byCenterColumn || byRightColumn
      val byDiagonals = byTopBottomDiagonal || byBottomTopDiagonal
      logger.debug(s"by rows: $byRows, by cols: $byColumns, by diagonals: $byDiagonals")

      byRows || byColumns || byDiagonals
    }
  }
}
