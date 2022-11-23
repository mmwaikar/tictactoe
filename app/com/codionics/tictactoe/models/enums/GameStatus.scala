package com.codionics.tictactoe.models.enums

import enumeratum._

sealed trait GameStatus extends EnumEntry

object GameStatus extends PlayEnum[GameStatus] {

  /*
   `findValues` is a protected method that invokes a macro to find all `Greeting` object declarations inside an `Enum`

   You use it to implement the `val values` member
   */
  val values = findValues

  case object InProgress extends GameStatus
  case object PlayerXWon extends GameStatus
  case object PlayerOWon extends GameStatus
  case object Tie        extends GameStatus
}
