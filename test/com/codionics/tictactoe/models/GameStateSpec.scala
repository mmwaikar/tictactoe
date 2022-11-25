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
        val maybeUpdated = GameState.playerXMoves(start, CellPosition.LeftTop)
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
        val maybeUpdated = GameState.playerOMoves(start, CellPosition.RightBottom)
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
        val maybeAfterX  = GameState.playerXMoves(start, CellPosition.LeftTop)
        val maybeAfterXO = maybeAfterX.flatMap(afterX => GameState.playerOMoves(afterX, CellPosition.RightBottom))
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
        val xWinning = GameStateHelper.getSampleXWonGame()

        xWinning should not be start
        xWinning.isEmpty should be(false)
        xWinning.hasPlayerOWon should be(false) withClue { "Player O won" }
        xWinning.hasPlayerXWon should be(true) withClue { "Player X lost" }
      }
    }

    "certain moves are made so that the player O has Os in straight lines, it" should {
      "result in player O winning" in {
        val oWinning = GameStateHelper.getSampleOWonGame()

        oWinning should not be start
        oWinning.isEmpty should be(false)
        oWinning.hasPlayerXWon should be(false) withClue { "Player X won" }
        oWinning.hasPlayerOWon should be(true) withClue { "Player O lost" }
      }
    }

    "neither player has won and there are no more empty cells, it" should {
      val tiedGame = GameStateHelper.getSampleTiedGame()

      "result in a game which is not in progress" in {
        tiedGame.isInProgress should be(false)
      }

      "result in a tied game" in {
        tiedGame.isEmpty should be(false)
        tiedGame.hasPlayerXWon should be(false)
        tiedGame.hasPlayerOWon should be(false)
        tiedGame.isTied should be(true)
      }
    }

    "player X moves twice, it" should {
      "result in an invalid state with the correct reason" in {
        val twiceXs = GameStateHelper.getSampleGameWithTwiceXMoves()
        twiceXs should not be start
        twiceXs.isEmpty should be(false)

        val (reason, valid) = GameState.isValid(twiceXs)
        logger.debug(s"reason: $reason, valid: $valid")
        reason should not be (empty)
        reason.get should startWith("X")
        valid should be(false)
      }
    }

    "player O moves twice, it" should {
      "result in an invalid state with the correct reason" in {
        val twiceOs = GameStateHelper.getSampleGameWithTwiceOMoves()
        twiceOs should not be start
        twiceOs.isEmpty should be(false)

        val (reason, valid) = GameState.isValid(twiceOs)
        logger.debug(s"reason: $reason, valid: $valid")
        reason should not be (empty)
        reason.get should startWith("O")
        valid should be(false)
      }
    }

    "player X and O move, it" should {
      "result in an invalid state with the correct reason" in {
        val afterXO = GameStateHelper.getSampleGameWithXOMoves()
        afterXO should not be start
        afterXO.isEmpty should be(false)

        val (reason, valid) = GameState.isValid(afterXO)
        logger.debug(s"reason: $reason, valid: $valid")
        reason should be(empty)
        valid should be(true)
      }
    }
  }
}
