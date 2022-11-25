package com.codionics.tictactoe.models

import com.codionics.tictactoe.models.enums.{HorizontalPosition, VerticalPosition}
import play.api.libs.json.{Format, Json}

case class CellPosition(horizontal: HorizontalPosition, vertical: VerticalPosition)

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

  val TopRow: Seq[CellPosition]    = Seq(LeftTop, CenterTop, RightTop)
  val CenterRow: Seq[CellPosition] = Seq(LeftCenter, CenterCenter, RightCenter)
  val BottomRow: Seq[CellPosition] = Seq(LeftBottom, CenterBottom, RightBottom)

  val LeftColumn: Seq[CellPosition]   = Seq(LeftTop, LeftCenter, LeftBottom)
  val CenterColumn: Seq[CellPosition] = Seq(CenterTop, CenterCenter, CenterBottom)
  val RightColumn: Seq[CellPosition]  = Seq(RightTop, RightCenter, RightBottom)

  val TopBottomDiagonal: Seq[CellPosition] = Seq(LeftTop, CenterCenter, RightBottom)
  val BottomTopDiagonal: Seq[CellPosition] = Seq(LeftBottom, CenterCenter, RightTop)

  val WinningPositions: Seq[Seq[CellPosition]] =
    Seq(TopRow, CenterRow, BottomRow, LeftColumn, CenterColumn, RightColumn, TopBottomDiagonal, BottomTopDiagonal)
}
