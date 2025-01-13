package tds.model

class Pawn (player: Player) : Piece(player) {
    override fun canMove(to: Square, from: Square, board: Board): Boolean {

        if(player== Player.w) {
            require(Square(board.moves.keys.indexOf(to)).black){"To isn't Black"}
            require(
                (board.moves.keys.indexOf(to) == board.moves.keys.indexOf(from) - (BOARD_DIM - 1) ) ||
                        (board.moves.keys.indexOf(to) == board.moves.keys.indexOf(from) - (BOARD_DIM + 1) )
            ) { "Invalid Position" }// não necessita verificar cantos pq é sempre branco

        }
        else{
            require(Square(board.moves.keys.indexOf(to)).black){"To isn't Black"}
            require(
                board.moves.keys.indexOf(to) == board.moves.keys.indexOf(from) + (BOARD_DIM - 1) ||
                        board.moves.keys.indexOf(to) == board.moves.keys.indexOf(from) + (BOARD_DIM + 1)
            ) { "Invalid Position" }
        }
        require(board.moves.values.toList()[to.index] == null) { "Position already occupied" }
        return true
    }

    override fun canKill(to: Square, from: Square, board: Board): Boolean {  //simplificar/otimizar

        if (board.player == Player.w) {
            require(
                board.moves.keys.indexOf(to) == board.moves.keys.indexOf(from) - (BOARD_DIM * 2 - 2) || board.moves.keys.indexOf(to) == board.moves.keys.indexOf(from) - (BOARD_DIM * 2 + 2)
            ) { "Invalid PLAY" }
            require(board.moves.values.toList()[(board.moves.keys.indexOf(from) - (BOARD_DIM - 1))] != null &&
                    board.moves.values.toList()[(board.moves.keys.indexOf(from) - (BOARD_DIM - 1))]?.player == board.player.other
                    || board.moves.values.toList()[(board.moves.keys.indexOf(from) - (BOARD_DIM + 1))] != null &&
                    board.moves.values.toList()[(board.moves.keys.indexOf(from) - (BOARD_DIM + 1))]?.player == board.player.other
            ) { "Impossible Play" }
        }
        else {
            require(
                board.moves.keys.indexOf(to) == board.moves.keys.indexOf(from) + (BOARD_DIM * 2 - 2) ||
                        board.moves.keys.indexOf(to) == board.moves.keys.indexOf(from) + (BOARD_DIM * 2 + 2)
            ) { "Invalid PLAY" }
            require(board.moves.values.toList()[(board.moves.keys.indexOf(from) + (BOARD_DIM - 1))] != null &&
                    board.moves.values.toList()[(board.moves.keys.indexOf(from) + (BOARD_DIM - 1))]?.player == board.player.other
                    || board.moves.values.toList()[(board.moves.keys.indexOf(from) + (BOARD_DIM + 1))] != null &&
                    board.moves.values.toList()[(board.moves.keys.indexOf(from) + (BOARD_DIM + 1))]?.player == board.player.other

            ) { "Impossible Play" }
        }
        return true
    }

    override fun genericCanKill(from: Square, board: Board): Boolean {
        require(board.moves.values.toList()[board.moves.keys.indexOf(from)] != null){"Checking wrong square"}

        if (player == Player.w && board.moves.keys.toList()[from.index].row.index > 1 ){
            val NextLeft = board.moves.values.toList()[board.moves.keys.indexOf(from) - BOARD_DIM -1]
            val NextRigth = board.moves.values.toList()[board.moves.keys.indexOf(from) - BOARD_DIM +1]

            return (NextLeft ?.player == player.other
                    && board.moves.values.toList()[board.moves.keys.indexOf(from) - BOARD_DIM * 2 - 2] == null
                    && board.moves.keys.toList()[board.moves.keys.indexOf(from) - BOARD_DIM * 2 - 2].black
                    ||
                    NextRigth ?.player == player.other
                    && board.moves.values.toList()[board.moves.keys.indexOf(from) - BOARD_DIM * 2 +2] == null
                    && board.moves.keys.toList()[board.moves.keys.indexOf(from) - BOARD_DIM * 2 +2].black)

        }else{
            if( board.moves.keys.toList()[from.index].row.index < 6) {
                val NextLeft = board.moves.values.toList()[board.moves.keys.indexOf(from) + BOARD_DIM - 1]
                val NextRigth = board.moves.values.toList()[board.moves.keys.indexOf(from) + BOARD_DIM + 1]

                return (NextLeft?.player == player.other
                        && board.moves.values.toList()[board.moves.keys.indexOf(from) + BOARD_DIM * 2 - 2] == null
                        && board.moves.keys.toList()[board.moves.keys.indexOf(from) + BOARD_DIM * 2 - 2].black
                        ||
                        NextRigth?.player == player.other
                        && board.moves.values.toList()[board.moves.keys.indexOf(from) + BOARD_DIM * 2 + 2] == null
                        && board.moves.keys.toList()[board.moves.keys.indexOf(from) + BOARD_DIM * 2 + 2].black)
            }
            return false
        }
    }

    override fun genericCanMove(from: Square, board: Board): Boolean { // testar ainda
        if (player == Player.w) {
            if (board.moves.keys.toList()[board.moves.keys.indexOf(from)].row.index != 0 // apenas para garantir
                && (board.moves.values.toList()[board.moves.keys.indexOf(from) - BOARD_DIM - 1] == null ||
                        board.moves.values.toList()[board.moves.keys.indexOf(from) - BOARD_DIM + 1] == null)
            ) return true
            else return false

        } else {
            if (board.moves.keys.toList()[board.moves.keys.indexOf(from)].row.index != BOARD_DIM - 1 // apenas para garantir
                && (board.moves.values.toList()[board.moves.keys.indexOf(from) + BOARD_DIM - 1] == null ||
                        board.moves.values.toList()[board.moves.keys.indexOf(from) + BOARD_DIM + 1] == null)
            ) return true
            else return false

        }
    }
    override fun canMove_nd(from: Square, moves: Moves): MutableList<Square> {
        val possibleMoves = mutableListOf<Square>()

        val fromSquare = moves.values.toList()[from.index]
        if (fromSquare != null) {
            val board_dim = getBycolour(fromSquare)
            val leftDiagonal = from.index - board_dim - 1
            val rightDiagonal = from.index - board_dim + 1
            if (moves.values.toList()[rightDiagonal] == null && moves.keys.toList()[rightDiagonal].black) {
                possibleMoves.add(moves.keys.toList()[rightDiagonal])
            }
            if (moves.values.toList()[leftDiagonal] == null && moves.keys.toList()[leftDiagonal].black) {
                possibleMoves.add(moves.keys.toList()[leftDiagonal])
            }
            return possibleMoves
        }
        else{
            return mutableListOf<Square>()
        }
    }

    override fun possiblekill(from: Square, board: Board): List<Square> {
        val list = board.moves.values.toList()
        val possiblemoves2 = mutableListOf<Square>()

        if (list[(from.index - (BOARD_DIM*2+2))] == null) { // Para vêr se existe kill possível à esquerda
            if (list[(from.index - (BOARD_DIM+1))] != null) {
                if(list[(from.index - (BOARD_DIM+1))]?.player == this.player.other) {
                    possiblemoves2.add(Square(from.index - (BOARD_DIM*2+2)))
                }
                }
            }
            if (list[(from.index - (BOARD_DIM*2-2))] == null) { // Para vêr se existe kill possível à direita
                if(list[(from.index - (BOARD_DIM-1))]?.player == this.player.other) {
                    possiblemoves2.add(Square(from.index - (BOARD_DIM*2-2)))
                }
            }

        return possiblemoves2
    }
}

fun getPawn(sq: Square) : Pawn?{
    val rowWf = playerPieces/6
    val rowBi = rowWf+3
    if(!sq.black ) return null
    if(sq.row.index  in 0 .. rowWf) return Pawn(Player.b)
    else if (sq.row.index  in rowBi..rowBi+rowWf) return Pawn(Player.w)
    else return null
}


fun getBycolour(piece : Piece): Int{
    if(piece.player == Player.w ) {
        return BOARD_DIM
    }else{
        return - BOARD_DIM
    }
}