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
  }
}
