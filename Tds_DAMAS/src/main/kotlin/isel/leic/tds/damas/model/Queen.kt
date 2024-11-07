package isel.leic.tds.damas.model

import kotlin.math.abs

class Queen(player: Player ): Piece(player) {
    override fun canMove(to: Square, from: Square, board: Board): Boolean {
        require(to.index in 0..<board.moves.keys.size) {"Position not in Board"}
        require(to.black && from.black) {"Can´t use non black squares"}
        require( board.moves.values.toList()[to.index] == null ) {" Position already occupied"}
        require( abs((to.index - from.index)) % (BOARD_DIM - 1) == 0 || abs(to.index - from.index) % (BOARD_DIM + 1) == 0 )
        {"Queen can only move in diagonals "}
        val steper = checkquarter(from, to)
        val dif = abs(from.row.index - to.row.index)
        if (dif > 2){ // Para verificar se o to for na 3 casa
            for ( i in 1..dif - 2) {
                val num = from.index + i * steper
                require(board.moves.values.toList()[num] == null) {" Invalid Move, there is a piece on the way "}
            }
        }
        return (board.moves.values.toList()[to.index] == null && board.moves.values.toList()[(to.index) - steper] == null)
    }

    override fun canKill(to: Square, from: Square, board: Board): Boolean {
        require(to.index in 0..<board.moves.keys.size) {"Position not in Board"}
        require(to.black && from.black) {"Can´t use non black squares"}
        require( board.moves.values.toList()[to.index] == null ) {" Position already occupied"}
        require( abs((to.index - from.index)) % (BOARD_DIM - 1) == 0 || abs(to.index - from.index) % (BOARD_DIM + 1) == 0 )
        {"Queen can only move in diagonals "}
        val steper = checkquarter(from, to)
        val dif = abs(from.row.index - to.row.index)
        if (dif > 2){ // Para verificar se o to for na 3 casa
            for ( i in 1..dif - 2) { // problema quando ha intervalo
                val num = from.index + i * steper
                require(board.moves.values.toList()[num] == null) {" Invalid Move, there is a piece on the way "}
            }
        }
        return (board.moves.values.toList()[to.index] == null && board.moves.values.toList()[(to.index) - steper]?.player == player.other)
    }


}


fun checkquarter(from: Square,to: Square): Int {
    return if (from.row.index > to.row.index && from.column.index < to.column.index || from.row.index < to.row.index &&
        from.column.index > to.column.index) {
        if (from.row.index < to.row.index &&
            from.column.index > to.column.index) {
            -(BOARD_DIM - 1)
        }
        else BOARD_DIM - 1
    }
    else {
        if (from.row.index > to.row.index &&
            from.column.index > to.column.index) {
            -(BOARD_DIM + 1)
        }
        else BOARD_DIM + 1
    }
}
