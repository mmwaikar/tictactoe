package com.codionics.tictactoe.utils

object IntUtils {

  implicit class IntOps(private val i: Int) extends AnyVal {

    def between(lower: Int, upper: Int) = {
      lower < i & i < upper
    }

    def betweenInclusiveLower(lower: Int, upper: Int) = {
      lower <= i & i < upper
    }

    def betweenInclusiveUpper(lower: Int, upper: Int) = {
      lower < i & i <= upper
    }

    def betweenInclusiveBoth(lower: Int, upper: Int) = {
      lower <= i & i <= upper
    }
  }
}
