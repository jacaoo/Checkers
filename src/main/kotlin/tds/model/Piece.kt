package tds.model

import kotlin.math.abs


abstract class Piece(val player: Player) {
    abstract fun canMove(to: Square, from: Square, board: Board): Boolean
    abstract fun canKill(to: Square,from: Square,board: Board): Boolean
    abstract fun genericCanKill(from: Square, board: Board): Boolean
    abstract fun genericCanMove(from: Square, board: Board): Boolean
    abstract fun canMove_nd(from: Square, moves: Moves): MutableList<Square>
    abstract fun possiblekill(from: Square, board: Board): List<Square>

}
/*
// Foi criada esta função e têm que ser alterada para funcionar caso seja uma queen
fun Piece.canMove_nd(to: Square, from: Square, moves: Moves): Boolean {
    if (moves[to] != null) return false

    // Regra para movimentos diagonais simples (1 casa)
    val deltaRow = to.row.index - from.row.index
    val deltaCol = to.column.index - from.column.index

    return abs(deltaRow) == 1 && abs(deltaCol) == 1 &&
            ((this.player == Player.w && deltaRow == -1) ||
                    (this.player == Player.b && deltaRow == 1))

}

 */

