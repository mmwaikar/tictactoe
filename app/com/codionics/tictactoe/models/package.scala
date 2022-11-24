package com.codionics.tictactoe

package object models {
  type PlayerXPosition = CellPosition
  type PlayerOPosition = CellPosition

  type ValidMovesForPlayerX = Seq[PlayerXPosition]
  type ValidMovesForPlayerO = Seq[PlayerOPosition]
}
