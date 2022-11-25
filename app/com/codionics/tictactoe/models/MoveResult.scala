package com.codionics.tictactoe.models

sealed trait MoveResult

case class PlayerXToMove(state: GameState, validMoves: ValidMovesForPlayerX) extends MoveResult
case class PlayerOToMove(state: GameState, validMoves: ValidMovesForPlayerO) extends MoveResult
case class GameWon(state: GameState)
