package com.codionics.tictactoe.models

import com.codionics.tictactoe.BaseSpec
import play.api.Logger
import com.codionics.tictactoe.models.enums.CellState

class GameStateSpec extends BaseSpec {
  val logger: Logger = Logger(this.getClass())

  val start = GameState.START

  "Given, GameState" when {
    "the START game state is examined, it" should {
      "result in an empty game state" in {
        logger.debug(s"start game state: $start")
        start.isEmpty should be(true)
      }
    }

    "player X moves to the left top position, it" should {
      "result in an updated state" in {
        val maybeUpdated = GameState.playerXMoves(CellPosition.LeftTop, start)
        maybeUpdated should not be empty

        val updated = maybeUpdated.get
        logger.debug(s"updated game state (after X moves): $updated")

        updated should not be start
        updated.isEmpty should be(false)
        updated.cells.head.state should be(CellState.X)
      }
    }

    "player O moves to the right bottom position, it" should {
      "result in an updated state" in {
        val maybeUpdated = GameState.playerOMoves(CellPosition.RightBottom, start)
        maybeUpdated should not be empty

        val updated = maybeUpdated.get
        logger.debug(s"updated game state (after O moves): $updated")

        updated should not be start
        updated.isEmpty should be(false)
        updated.cells.last.state should be(CellState.O)
      }
    }

    "player X moves to the left top position and player O moves to the right bottom position, it" should {
      "result in an updated state" in {
        val maybeAfterX  = GameState.playerXMoves(CellPosition.LeftTop, start)
        val maybeAfterXO = maybeAfterX.flatMap(afterX => GameState.playerOMoves(CellPosition.RightBottom, afterX))
        maybeAfterXO should not be empty

        val afterXO = maybeAfterXO.get
        logger.debug(s"updated game state (after both X & O move): $afterXO")

        afterXO should not be start
        afterXO.isEmpty should be(false)
        afterXO.cells.head.state should be(CellState.X)
        afterXO.cells.last.state should be(CellState.O)
      }
    }

    "certain moves are made so that the player X has Xs in straight lines, it" should {
      "result in player X winning" in {
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

        maybeAfterX3O2 should not be empty
        val afterX3O2 = maybeAfterX3O2.get
        logger.debug(s"after 5 moves, game state: $afterX3O2")

        afterX3O2 should not be start
        afterX3O2.isEmpty should be(false)
        afterX3O2.hasPlayerOWon should be(false) withClue { "Player O won" }
        afterX3O2.hasPlayerXWon should be(true) withClue { "Player X lost" }
      }
    }

    "neither player has won and there are no more empty cells, it" should {
      "result in a game which is not in progress" in {
        val tiedGame = GameState.getSampleTiedGame()
        tiedGame.isInProgress should be(false)
      }

      "result in a tied game" in {
        val tiedGame = GameState.getSampleTiedGame()
        tiedGame.isEmpty should be(false)
        tiedGame.hasPlayerXWon should be(false)
        tiedGame.hasPlayerOWon should be(false)
        tiedGame.isTied should be(true)
      }
    }
  }
}
