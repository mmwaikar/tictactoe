package com.codionics.tictactoe.models

import com.codionics.tictactoe.models.CellPosition._
import com.codionics.tictactoe.models.enums.CellState
import com.codionics.tictactoe.models.enums.GameStatus
import com.codionics.tictactoe.models.enums.HorizontalPosition
import com.codionics.tictactoe.models.enums.VerticalPosition
import com.codionics.tictactoe.utils.IntUtils._
import play.api.Logger
import play.api.libs.json.Format
import play.api.libs.json.Json

case class GameState(cells: Seq[Cell]) {

  def isEmpty: Boolean = cells.forall(_.state == CellState.Empty)

  def isInProgress: Boolean = cells.exists(_.state == CellState.Empty)

  def hasPlayerXWon = GameState.hasPlayerWon(this, CellState.X)

  def hasPlayerOWon = GameState.hasPlayerWon(this, CellState.O)

  def isTied = !hasPlayerXWon && !hasPlayerOWon && !isInProgress

  def getRemainingMoves: Seq[Cell] = cells.filter(_.state == CellState.Empty)
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

  /** Determines if a GameState is valid or not. If at any time, there are two Xs or two Os i.e. if the difference
    * between the X and O cells is more than 1, then it's an invalid state.
    *
    * @param state
    *   the game state at any moment
    * @return
    *   a Left(reason) - if the state is not valid, else Right(state) - if the state is valid
    */
  def isValid(state: GameState): Either[String, GameState] = {
    val xCells = state.cells.filter(_.state == CellState.X)
    val oCells = state.cells.filter(_.state == CellState.O)

    val xSize       = xCells.size
    val oSize       = oCells.size
    val absDiff     = (xSize - oSize).abs
    val diffInRange = absDiff.betweenInclusiveBoth(0, 1)

    if (diffInRange) Right(state)
    else {
      if (xSize > oSize) Left("X has played more than once.")
      else Left("O has played more than once.")
    }
  }

  def hasPlayerWon(state: GameState, cellState: CellState) = {
    val cellsForState = state.cells.filter(c => c.state == cellState)
    logger.debug(s"cells for state: $cellState - $cellsForState")

    if (cellsForState.size < 3) false
    else {
      val positions           = cellsForState.map(_.position)
      val byTopRow            = positions.toSet == CellPosition.TopRow.toSet
      val byCenterRow         = positions.toSet == CellPosition.CenterRow.toSet
      val byBottomRow         = positions.toSet == CellPosition.BottomRow.toSet
      val byLeftColumn        = positions.toSet == CellPosition.LeftColumn.toSet
      val byCenterColumn      = positions.toSet == CellPosition.CenterColumn.toSet
      val byRightColumn       = positions.toSet == CellPosition.RightColumn.toSet
      val byTopBottomDiagonal = positions.toSet == CellPosition.TopBottomDiagonal.toSet
      val byBottomTopDiagonal = positions.toSet == CellPosition.BottomTopDiagonal.toSet

      val byRows      = byTopRow || byCenterRow || byBottomRow
      val byColumns   = byLeftColumn || byCenterColumn || byRightColumn
      val byDiagonals = byTopBottomDiagonal || byBottomTopDiagonal
      logger.debug(s"by rows: $byRows, by cols: $byColumns, by diagonals: $byDiagonals")

      byRows || byColumns || byDiagonals
    }
  }

  def getGameStatus(gameState: GameState) = {
    if (gameState.hasPlayerXWon) GameStatus.PlayerXWon
    else if (gameState.hasPlayerOWon) GameStatus.PlayerOWon
    else if (gameState.isInProgress) GameStatus.InProgress
    else GameStatus.Tie
  }

  def getWinningPositions(state: CellState): Seq[Seq[Cell]] = {
    WinningPositions.map(wp => wp.map(cp => Cell(cp, state)))
  }

  def playerXMoves(state: GameState, position: CellPosition): Either[String, GameState] = {
    val xCell        = Cell.getXCell(position)
    val eitherPlayed = play(state, xCell)
    eitherPlayed.flatMap(gs => GameState.isValid(gs))
  }

  def playerOMoves(state: GameState, position: CellPosition): Either[String, GameState] = {
    val oCell        = Cell.getOCell(position)
    val eitherPlayed = play(state, oCell)
    eitherPlayed.flatMap(gs => GameState.isValid(gs))
  }

  private def play(state: GameState, cell: Cell): Either[String, GameState] = {
    val position            = cell.position
    val maybeCellIndexTuple = state.cells.zipWithIndex.find { case (cell, index) =>
      cell.isEmpty && cell.position == position
    }

    maybeCellIndexTuple match {
      case None             => Left(s"Invalid cell position - either the position was not found or the cell was not empty.")
      case Some((_, index)) => {
        val updatedCells = state.cells.updated(index, cell)
        Right(state.copy(cells = updatedCells))
      }
    }
  }
}
