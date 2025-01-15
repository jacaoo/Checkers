package tds.model

import kotlin.math.abs


abstract class Piece(val player: Player) {
    abstract fun canMove(to: Square, from: Square, board: Board): Boolean
    abstract fun canKill(to: Square,from: Square,board: Board): Boolean
    abstract fun genericCanKill(from: Square, board: Board): Boolean
    abstract fun genericCanMove(from: Square, board: Board): Boolean
    abstract fun canMove_nd(from: Square, board: Board): MutableList<Square>
    abstract fun possiblekill(from: Square, board: Board): List<Square>

}
