package models

import models.enums.{HorizontalPosition, VerticalPosition}
import play.api.libs.json.{Format, Json}

case class CellPosition(horizontalPosition: HorizontalPosition, verticalPosition: VerticalPosition)

object CellPosition {
  implicit val cellPositionFormat: Format[CellPosition] = Json.format[CellPosition]
}
