package com.codionics.tictactoe.models

import com.codionics.tictactoe.models.CellPosition._
import com.codionics.tictactoe.models.enums.CellState
import play.api.Logger

object GameStateHelper {
  val logger: Logger = Logger(this.getClass())

  def getSampleTiedGame(): GameState = {
    val cellPositions = TopRow ++ CenterRow ++ BottomRow
    val cells         = cellPositions.zipWithIndex.map { case (cp, index) =>
      val cellState = if (index % 2 == 0) CellState.X else CellState.O
      Cell(cp, cellState)
    }
    GameState(cells)
  }

  def getSampleXWonGame(): GameState = {
    val start        = GameState.startGame()
    val maybeAfterX1 = GameState.playerXMoves(start, CellPosition.LeftTop)
    // logger.debug(s"after 1 move, game state: ${maybeAfterX1.get}")

    val maybeAfterX1O1 = maybeAfterX1.flatMap(afterX => GameState.playerOMoves(afterX, CellPosition.LeftBottom))
    // logger.debug(s"after 2 moves, game state: ${maybeAfterX1O1.get}")

    val maybeAfterX2O1 = maybeAfterX1O1.flatMap(afterXO => GameState.playerXMoves(afterXO, CellPosition.CenterTop))
    // logger.debug(s"after 3 moves, game state: ${maybeAfterX2O1.get}")

    val maybeAfterX2O2 =
      maybeAfterX2O1.flatMap(afterXO => GameState.playerOMoves(afterXO, CellPosition.CenterBottom))
    // logger.debug(s"after 4 moves, game state: ${maybeAfterX2O2.get}")

    val maybeAfterX3O2 = maybeAfterX2O2.flatMap(afterXO => GameState.playerXMoves(afterXO, CellPosition.RightTop))
    // logger.debug(s"after 5 moves, game state: ${maybeAfterX3O2.get}")

    maybeAfterX3O2.getOrElse(start)
  }

  def getSampleOWonGame(): GameState = {
    val start        = GameState.startGame()
    val maybeAfterO1 = GameState.playerOMoves(start, CellPosition.LeftBottom)
    // logger.debug(s"after 1 move, game state: ${maybeAfterX1.get}")

    val maybeAfterO1X1 = maybeAfterO1.flatMap(afterO => GameState.playerXMoves(afterO, CellPosition.LeftTop))
    // logger.debug(s"after 2 moves, game state: ${maybeAfterX1O1.get}")

    val maybeAfterO2X1 = maybeAfterO1X1.flatMap(afterOX => GameState.playerOMoves(afterOX, CellPosition.CenterCenter))
    // logger.debug(s"after 3 moves, game state: ${maybeAfterX2O1.get}")

    val maybeAfterO2X2 =
      maybeAfterO2X1.flatMap(afterOX => GameState.playerXMoves(afterOX, CellPosition.CenterTop))
    // logger.debug(s"after 4 moves, game state: ${maybeAfterX2O2.get}")

    val maybeAfterO3X2 = maybeAfterO2X2.flatMap(afterOX => GameState.playerOMoves(afterOX, CellPosition.RightTop))
    // logger.debug(s"after 5 moves, game state: ${maybeAfterX3O2.get}")

    maybeAfterO3X2.getOrElse(start)
  }

  def getSampleGameWithTwiceXMoves(): GameState = {
    val start        = GameState.startGame()
    val maybeAfterX1 = GameState.playerXMoves(start, CellPosition.LeftTop)
    val maybeAfterX2 = maybeAfterX1.flatMap(afterX => GameState.playerXMoves(afterX, CellPosition.LeftBottom))
    maybeAfterX2.getOrElse(start)
  }

  def getSampleGameWithTwiceOMoves(): GameState = {
    val start        = GameState.startGame()
    val maybeAfterO1 = GameState.playerOMoves(start, CellPosition.LeftTop)
    val maybeAfterO2 = maybeAfterO1.flatMap(afterO => GameState.playerOMoves(afterO, CellPosition.LeftBottom))
    maybeAfterO2.getOrElse(start)
  }

  def getSampleGameWithXOMoves(): GameState = {
    val start        = GameState.startGame()
    val maybeAfterX1 = GameState.playerXMoves(start, CellPosition.LeftTop)
    val maybeAfterO1 = maybeAfterX1.flatMap(afterX => GameState.playerOMoves(afterX, CellPosition.LeftBottom))
    maybeAfterO1.getOrElse(start)
  }
}
