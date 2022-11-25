package com.codionics.tictactoe.utils

import com.codionics.tictactoe.BaseSpec

import play.api.Logger
import com.codionics.tictactoe.utils.IntUtils._

class IntUtilsSpec extends BaseSpec {
  val logger: Logger = Logger(this.getClass())

  "Given, IntUtils" when {

    "between method is called, it" should {
      "return true for a value in between the two values" in {
        val i      = 2
        val result = i.between(1, 3)
        result should be(true)
      }

      "return false for a value outside the two values" in {
        val i      = 2
        val result = i.between(4, 10)
        result should be(false)
      }
    }

    "betweenInclusiveBoth method is called, it" should {
      "return true for a value in between the two values" in {
        val i      = 4
        val result = i.betweenInclusiveBoth(1, 6)
        result should be(true)
      }

      "return true for a value in between or matching any of the values" in {
        val i      = 1
        val result = i.betweenInclusiveBoth(0, 1)
        result should be(true)
      }

      "return false for a value outside the two values" in {
        val i      = 2
        val result = i.betweenInclusiveBoth(4, 10)
        result should be(false)
      }
    }
  }
}
