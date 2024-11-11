package isel.leic.tds.damas.model

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
            require(
                board.moves.keys.indexOf(to) == board.moves.keys.indexOf(from) + (BOARD_DIM - 1) ||
                        board.moves.keys.indexOf(to) == board.moves.keys.indexOf(from) + (BOARD_DIM + 1)
            ) { "Invalid Position" }
            require(Square(board.moves.keys.indexOf(to)).black){"To isn't Black"}
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

    override fun GenericCanKill(from: Square, board: Board): Boolean {
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

    override fun GenericCanMove(from: Square, board: Board): Boolean { // testar ainda
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
}
fun getPawn(sq: Square) : Pawn?{
    if(!sq.black ) return null
    if(sq.row.index  in 0 .. 2) return Pawn(Player.b)
    else if (sq.row.index  in BOARD_DIM - 3..BOARD_DIM-1 ) return Pawn(Player.w)
    else return null
}
