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
    val start         = GameState.startGame()
    val eitherAfterX1 = GameState.playerXMoves(start, CellPosition.LeftTop)
    // logger.debug(s"after 1 move, game state: ${eitherAfterX1.get}")

    val eitherAfterX1O1 = eitherAfterX1.flatMap(afterX => GameState.playerOMoves(afterX, CellPosition.LeftBottom))
    // logger.debug(s"after 2 moves, game state: ${eitherAfterX1O1.get}")

    val eitherAfterX2O1 = eitherAfterX1O1.flatMap(afterXO => GameState.playerXMoves(afterXO, CellPosition.CenterTop))
    // logger.debug(s"after 3 moves, game state: ${eitherAfterX2O1.get}")

    val eitherAfterX2O2 =
      eitherAfterX2O1.flatMap(afterXO => GameState.playerOMoves(afterXO, CellPosition.CenterBottom))
    // logger.debug(s"after 4 moves, game state: ${eitherAfterX2O2.get}")

    val eitherAfterX3O2 = eitherAfterX2O2.flatMap(afterXO => GameState.playerXMoves(afterXO, CellPosition.RightTop))
    // logger.debug(s"after 5 moves, game state: ${eitherAfterX3O2.get}")

    eitherAfterX3O2.getOrElse(start)
  }

  def getSampleOWonGame(): GameState = {
    val start         = GameState.startGame()
    val eitherAfterO1 = GameState.playerOMoves(start, CellPosition.LeftBottom)
    // logger.debug(s"after 1 move, game state: ${eitherAfterX1.get}")

    val eitherAfterO1X1 = eitherAfterO1.flatMap(afterO => GameState.playerXMoves(afterO, CellPosition.LeftTop))
    // logger.debug(s"after 2 moves, game state: ${eitherAfterX1O1.get}")

    val eitherAfterO2X1 = eitherAfterO1X1.flatMap(afterOX => GameState.playerOMoves(afterOX, CellPosition.CenterCenter))
    // logger.debug(s"after 3 moves, game state: ${eitherAfterX2O1.get}")

    val eitherAfterO2X2 =
      eitherAfterO2X1.flatMap(afterOX => GameState.playerXMoves(afterOX, CellPosition.CenterTop))
    // logger.debug(s"after 4 moves, game state: ${eitherAfterX2O2.get}")

    val eitherAfterO3X2 = eitherAfterO2X2.flatMap(afterOX => GameState.playerOMoves(afterOX, CellPosition.RightTop))
    // logger.debug(s"after 5 moves, game state: ${eitherAfterX3O2.get}")

    eitherAfterO3X2.getOrElse(start)
  }

  def getSampleGameWithTwiceXMoves(): Either[String, GameState] = {
    val start         = GameState.startGame()
    val eitherAfterX1 = GameState.playerXMoves(start, CellPosition.LeftTop)
    val eitherAfterX2 = eitherAfterX1.flatMap(afterX => GameState.playerXMoves(afterX, CellPosition.LeftBottom))
    eitherAfterX2
  }

  def getSampleGameWithTwiceOMoves(): Either[String, GameState] = {
    val start         = GameState.startGame()
    val eitherAfterO1 = GameState.playerOMoves(start, CellPosition.LeftTop)
    val eitherAfterO2 = eitherAfterO1.flatMap(afterO => GameState.playerOMoves(afterO, CellPosition.LeftBottom))
    eitherAfterO2
  }

  def getSampleGameWithXOMoves(): GameState = {
    val start         = GameState.startGame()
    val eitherAfterX1 = GameState.playerXMoves(start, CellPosition.LeftTop)
    val eitherAfterO1 = eitherAfterX1.flatMap(afterX => GameState.playerOMoves(afterX, CellPosition.LeftBottom))
    eitherAfterO1.getOrElse(start)
  }
}
