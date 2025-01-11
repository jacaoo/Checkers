package tds.model

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

    override fun genericCanKill(from: Square, board: Board): Boolean {

        require(from.black) { "Can´t use non black squares" }
        require(board.moves.values.toList()[from.index] != null) { "Empty Position" }

        val allSteppers = listOf((BOARD_DIM-1), -(BOARD_DIM-1), (BOARD_DIM+1), -(BOARD_DIM+1))
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

    override fun genericCanMove(from: Square, board: Board): Boolean {
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
    override fun canMove_nd(from: Square, moves: Moves): MutableList<Square> {

        val steppers = arrayOf((BOARD_DIM-1), -(BOARD_DIM-1), (BOARD_DIM+1), -(BOARD_DIM+1))
        val possibleMoves = mutableListOf<Square>()

        for (i in steppers) {
            for (j in 1..BOARD_DIM) {
                val num = from.index +( i * j)
                if (num in 0..<BOARD_CELLS) {
                    val squareToCheck = moves.keys.toList()[from.index + (i * j)].index
                    if (moves.values.toList()[squareToCheck] == null && moves.keys.toList()[squareToCheck].black) {
                        println(moves.keys.toList()[from.index + (i * j)])
                        possibleMoves.add(moves.keys.toList()[from.index + (i * j)])
                    } else {
                        break
                    }
                } else {
                    break
                }
            }
        }
        return possibleMoves
    }

    override fun possiblekill(from: Square, board: Board): List<Square> {
        val steppers = arrayOf((BOARD_DIM-1), -(BOARD_DIM-1), (BOARD_DIM+1), -(BOARD_DIM+1))
        val possiblekills = mutableListOf<Square>()
        val boardListPiece = board.moves.values.toList()
        val boardListSquare = board.moves.keys.toList()

        for (i in steppers) {
            for (j in 1..BOARD_DIM) {
                val num = from.index +( i * j)
                if (num in 0..<BOARD_CELLS) {
                    val squareToCheck = boardListSquare[from.index + (i * j)].index
                    if (boardListPiece[squareToCheck] == null && boardListSquare[squareToCheck].black) {

                        //possibleMoves.add(board.moves.keys.toList()[from.index + (i * j)])
                    } else {
                        val check = squareToCheck + i
                        if (boardListSquare[squareToCheck].black && check in 0..<BOARD_CELLS) {
                            if (boardListPiece[check] == null && boardListSquare[check].black) {
                                possiblekills.add(boardListSquare[check])
                                break
                            }
                        }
                        else break
                    }
                } else {
                    break
                }
            }
        }
        return possiblekills
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
