package com.codionics.tictactoe.models.enums

import enumeratum._

sealed trait Player extends EnumEntry

object Player extends PlayEnum[Player] {

  /*
   `findValues` is a protected method that invokes a macro to find all `Greeting` object declarations inside an `Enum`

   You use it to implement the `val values` member
   */
  val values = findValues

  case object PlayerX extends Player
  case object PlayerO extends Player
}
