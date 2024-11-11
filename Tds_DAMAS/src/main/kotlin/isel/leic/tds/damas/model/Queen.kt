/*
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

 */
package isel.leic.tds.damas.model

import kotlin.math.abs

class Queen(player: Player ): Piece(player) {
    override fun canMove(to: Square, from: Square, board: Board): Boolean {
        require(to.index in 0..<board.moves.keys.size) { "Position not in Board" }
        require(to.black && from.black) { "Can´t use non black squares" }
        require(board.moves.values.toList()[to.index] == null) { " Position already occupied" }
        require(abs((to.index - from.index)) % (BOARD_DIM - 1) == 0 || abs(to.index - from.index) % (BOARD_DIM + 1) == 0)
        { "Queen can only move in diagonals " }
        val steper = checkquadrant(from, to)
        val dif = abs(from.row.index - to.row.index)
        if (dif > 2) { // Para verificar se o to for na 3 casa
            for (i in 1..dif - 2) {
                val num = from.index + i * steper
                require(board.moves.values.toList()[num] == null) { " Invalid Move, there is a piece on the way " }
            }
        }
        return (board.moves.values.toList()[to.index] == null && (board.moves.values.toList()[(to.index) - steper] == null || board.moves.values.toList()[(to.index) - steper] == board.moves.values.toList()[(from.index)]) )
    }

    override fun canKill(to: Square, from: Square, board: Board): Boolean {
        require(to.index in 0..<board.moves.keys.size) { "Position not in Board" }
        require(to.black && from.black) { "Can´t use non black squares" }
        require(board.moves.values.toList()[to.index] == null) { " Position already occupied" }
        require(abs((to.index - from.index)) % (BOARD_DIM - 1) == 0 || abs(to.index - from.index) % (BOARD_DIM + 1) == 0)
        { "Queen can only move in diagonals " }
        val steper = checkquadrant(from, to)
        val dif = abs(from.row.index - to.row.index)
        if (dif > 2) { // Para verificar se o to for na 3 casa
            for (i in 1..dif - 2) { // problema quando ha intervalo
                val num = from.index + i * steper
                require(board.moves.values.toList()[num] == null) { " Invalid Move, there is a piece on the way " }
            }
        }
        return (board.moves.values.toList()[to.index] == null && board.moves.values.toList()[(to.index) - steper]?.player == player.other)
    }

    override fun GenericCanKill(from: Square, board: Board): Boolean {

        require(from.black) { "Can´t use non black squares" }
        require(board.moves.values.toList()[from.index] != null) { "Empty Position" }

        val allSteppers = listOf(7, -7, 9, -9)
        for (steper in allSteppers) {
            val Difference = CheckLimits(from,steper, board)
            for( j in 1..Difference - 1){
                val num = from.index + j * steper
                val posAtual = board.moves.values.toList()[num]
                if(posAtual != null && posAtual.player == player.other){
                    if(board.moves.values.toList()[num+steper] == null ) return true
                    else break
                }
            }
        }
        return false
    }

    override fun GenericCanMove(from: Square, board: Board): Boolean {
        if (board.moves.keys.toList()[board.moves.keys.indexOf(from)].row.index != 0
            &&
            (board.moves.values.toList()[board.moves.keys.indexOf(from) - BOARD_DIM - 1] == null
                    ||
                    board.moves.values.toList()[board.moves.keys.indexOf(from) - BOARD_DIM + 1] == null
                    )||(
                    board.moves.keys.toList()[board.moves.keys.indexOf(from)].row.index != BOARD_DIM - 1
                            &&
                            board.moves.values.toList()[board.moves.keys.indexOf(from) + BOARD_DIM - 1] == null
                            ||
                            board.moves.values.toList()[board.moves.keys.indexOf(from) + BOARD_DIM + 1] == null) )return true
        else return false
    }
}
fun CheckLimits(from : Square, stepper: Int, board: Board) : Int{// returns an int based on the quadrant/diagonal (stepper), this functions calculates the amount of squares on that diagonal based on the row and column of the fromSquare

    if (stepper == -7) { // 1ºQ
        val difRow = board.moves.keys.toList()[from.index].row.index
        val difColumn = 7 - board.moves.keys.toList()[from.index].column.index
        return if (difColumn > difRow) difRow else difColumn

    } else if (stepper == 9) {//4ºQ
        val difRow = 7 - board.moves.keys.toList()[from.index].row.index
        val difColumn = 7 - board.moves.keys.toList()[from.index].column.index
        return if (difColumn > difRow) difRow else difColumn

    } else if (stepper == -9) {// 2ªQ
        val difRow = board.moves.keys.toList()[from.index].row.index
        val difColumn = board.moves.keys.toList()[from.index].column.index
        return if (difColumn > difRow) difRow else difColumn

    } else {// 3ªQ
        val difRow = 7 - board.moves.keys.toList()[from.index].row.index
        val difColumn = board.moves.keys.toList()[from.index].column.index
        return  if (difColumn > difRow) difRow else difColumn
    }


}



fun checkquadrant(from: Square, to: Square): Int {
    return if (from.row.index > to.row.index && from.column.index < to.column.index || from.row.index < to.row.index &&
        from.column.index > to.column.index
    ) {
        if (from.row.index < to.row.index &&
            from.column.index > to.column.index
        ) {
            (BOARD_DIM - 1)// 3ºQuadrante
        } else -(BOARD_DIM - 1) // 1ºQuadrante
    } else {
        if (from.row.index > to.row.index &&
            from.column.index > to.column.index
        ) {
            -(BOARD_DIM + 1) //2ºQuadrante
        } else BOARD_DIM + 1 //4ºQuadrante
    }
}
