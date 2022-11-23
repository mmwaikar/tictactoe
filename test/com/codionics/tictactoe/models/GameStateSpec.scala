package com.codionics.tictactoe.models

import com.codionics.tictactoe.BaseSpec
import play.api.Logger

class GameStateSpec extends BaseSpec {
  val logger = Logger(this.getClass())

  "Given, GameState" when {
    "the START game state is examined, it" should {
      "result in an empty game state" in {
        val start = GameState.START
        logger.debug(s"start game state: $start")
        start.isEmpty should be(true)
      }
    }
  }
}
