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
    val maybeAfterX1 = GameState.playerXMoves(CellPosition.LeftTop, start)
    // logger.debug(s"after 1 move, game state: ${maybeAfterX1.get}")

    val maybeAfterX1O1 = maybeAfterX1.flatMap(afterX => GameState.playerOMoves(CellPosition.LeftBottom, afterX))
    // logger.debug(s"after 2 moves, game state: ${maybeAfterX1O1.get}")

    val maybeAfterX2O1 = maybeAfterX1O1.flatMap(afterXO => GameState.playerXMoves(CellPosition.CenterTop, afterXO))
    // logger.debug(s"after 3 moves, game state: ${maybeAfterX2O1.get}")

    val maybeAfterX2O2 =
      maybeAfterX2O1.flatMap(afterXO => GameState.playerOMoves(CellPosition.CenterBottom, afterXO))
    // logger.debug(s"after 4 moves, game state: ${maybeAfterX2O2.get}")

    val maybeAfterX3O2 = maybeAfterX2O2.flatMap(afterXO => GameState.playerXMoves(CellPosition.RightTop, afterXO))
    // logger.debug(s"after 5 moves, game state: ${maybeAfterX3O2.get}")

    maybeAfterX3O2.getOrElse(start)
  }

  def getSampleOWonGame(): GameState = {
    val start        = GameState.startGame()
    val maybeAfterO1 = GameState.playerOMoves(CellPosition.LeftBottom, start)
    // logger.debug(s"after 1 move, game state: ${maybeAfterX1.get}")

    val maybeAfterO1X1 = maybeAfterO1.flatMap(afterO => GameState.playerXMoves(CellPosition.LeftTop, afterO))
    // logger.debug(s"after 2 moves, game state: ${maybeAfterX1O1.get}")

    val maybeAfterO2X1 = maybeAfterO1X1.flatMap(afterOX => GameState.playerOMoves(CellPosition.CenterCenter, afterOX))
    // logger.debug(s"after 3 moves, game state: ${maybeAfterX2O1.get}")

    val maybeAfterO2X2 =
      maybeAfterO2X1.flatMap(afterOX => GameState.playerXMoves(CellPosition.CenterTop, afterOX))
    // logger.debug(s"after 4 moves, game state: ${maybeAfterX2O2.get}")

    val maybeAfterO3X2 = maybeAfterO2X2.flatMap(afterOX => GameState.playerOMoves(CellPosition.RightTop, afterOX))
    // logger.debug(s"after 5 moves, game state: ${maybeAfterX3O2.get}")

    maybeAfterO3X2.getOrElse(start)
  }
}
