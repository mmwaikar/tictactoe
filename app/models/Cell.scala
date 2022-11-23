package models

import models.enums.CellState
import play.api.libs.json.{Format, Json}

case class Cell(position: CellPosition, state: CellState)

object Cell {
  implicit val cellFormat: Format[Cell] = Json.format[Cell]
}
