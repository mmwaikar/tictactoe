package models

import models.enums.{HorizontalPosition, VerticalPosition}
import play.api.libs.json.{Format, Json}

case class CellPosition(horizontalPosition: HorizontalPosition, verticalPosition: VerticalPosition)

object CellPosition {
  implicit val cellPositionFormat: Format[CellPosition] = Json.format[CellPosition]

  val LeftTop: CellPosition   = CellPosition(HorizontalPosition.Left, VerticalPosition.Top)
  val CenterTop: CellPosition = CellPosition(HorizontalPosition.HCenter, VerticalPosition.Top)
  val RightTop: CellPosition  = CellPosition(HorizontalPosition.Right, VerticalPosition.Top)

  val LeftCenter: CellPosition   = CellPosition(HorizontalPosition.Left, VerticalPosition.VCenter)
  val CenterCenter: CellPosition = CellPosition(HorizontalPosition.HCenter, VerticalPosition.VCenter)
  val RightCenter: CellPosition  = CellPosition(HorizontalPosition.Right, VerticalPosition.VCenter)

  val LeftBottom: CellPosition   = CellPosition(HorizontalPosition.Left, VerticalPosition.Bottom)
  val CenterBottom: CellPosition = CellPosition(HorizontalPosition.HCenter, VerticalPosition.Bottom)
  val RightBottom: CellPosition  = CellPosition(HorizontalPosition.Right, VerticalPosition.Bottom)
}
