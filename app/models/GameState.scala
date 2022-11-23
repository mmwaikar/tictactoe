package models

import play.api.libs.json.{Format, Json}
import models.enums.{CellState, HorizontalPosition, VerticalPosition}
import models.CellPosition._

case class GameState(cells: Seq[Cell])

object GameState {
  implicit val gameStateFormat: Format[GameState] = Json.format[GameState]

  val Start: GameState = startGame()

  def startGame(): GameState = {
    val cellPositions =
      Seq(LeftTop, CenterTop, RightTop, LeftCenter, CenterCenter, RightCenter, LeftBottom, CenterBottom, RightBottom)
    val cells         = cellPositions.map(cp => Cell(cp, CellState.Empty))
    GameState(cells)
  }
}
