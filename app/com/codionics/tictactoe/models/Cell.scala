package com.codionics.tictactoe.models

import com.codionics.tictactoe.models.enums.CellState
import play.api.libs.json.{Format, Json}

case class Cell(position: CellPosition, state: CellState) {

  def isEmpty: Boolean = state == CellState.Empty

  def isOccupied: Boolean = !isEmpty
}

object Cell {
  implicit val cellFormat: Format[Cell] = Json.format[Cell]

  val NULL_OBJECT: Cell = Cell(CellPosition.LeftTop, CellState.Empty)

  def getXCell(position: CellPosition): Cell = Cell(position, CellState.X)

  def getOCell(position: CellPosition): Cell = Cell(position, CellState.O)
}
