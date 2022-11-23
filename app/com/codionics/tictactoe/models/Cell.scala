package com.codionics.tictactoe.models

import com.codionics.tictactoe.models.enums.CellState
import play.api.libs.json.{Format, Json}

case class Cell(position: CellPosition, state: CellState)

object Cell {
  implicit val cellFormat: Format[Cell] = Json.format[Cell]

  val NULL_OBJECT: Cell = Cell(CellPosition.LeftTop, CellState.Empty)
}
