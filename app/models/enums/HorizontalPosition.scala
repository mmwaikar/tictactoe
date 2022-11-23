package models.enums

import enumeratum._

sealed trait HorizontalPosition extends EnumEntry

object HorizontalPosition extends PlayEnum[HorizontalPosition] {

  /*
   `findValues` is a protected method that invokes a macro to find all `Greeting` object declarations inside an `Enum`

   You use it to implement the `val values` member
   */
  val values = findValues

  case object Left    extends HorizontalPosition
  case object HCenter extends HorizontalPosition
  case object Right   extends HorizontalPosition
}
