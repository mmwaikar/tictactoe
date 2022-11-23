package com.codionics.tictactoe.models.enums

import enumeratum._

sealed trait CellState extends EnumEntry

object CellState extends PlayEnum[CellState] {

  /*
   `findValues` is a protected method that invokes a macro to find all `Greeting` object declarations inside an `Enum`

   You use it to implement the `val values` member
   */
  val values = findValues

  case object X     extends CellState
  case object O     extends CellState
  case object Empty extends CellState
}
