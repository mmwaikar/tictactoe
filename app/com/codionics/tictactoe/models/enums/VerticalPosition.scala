package com.codionics.tictactoe.models.enums

import enumeratum._

sealed trait VerticalPosition extends EnumEntry

object VerticalPosition extends PlayEnum[VerticalPosition] {

  /*
   `findValues` is a protected method that invokes a macro to find all `Greeting` object declarations inside an `Enum`

   You use it to implement the `val values` member
   */
  val values = findValues

  case object Top     extends VerticalPosition
  case object VCenter extends VerticalPosition
  case object Bottom  extends VerticalPosition
}
