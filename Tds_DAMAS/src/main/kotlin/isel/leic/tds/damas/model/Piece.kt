package isel.leic.tds.damas.model


abstract class Piece(val player: Player) {

    override fun equals(other: Any?): Boolean {
        return if ( other is Piece ) player == other.player else error("Can't compare different types")
    }
    abstract fun canMove(to: Square, from: Square, board: Board): Boolean
    abstract fun canKill(to: Square,from: Square,board: Board): Boolean
}

/*
fun checkQueen(to: Square, player: Player) : Boolean {
    return if (player == Player.b) {
        to.row.index == BOARD_DIM - 1
    }
    else if(player == Player.w){
        to.row.index == 0
    }
    else false
}

fun Board.toQueen(pos: Square,player: Player) : Board {
    val list = this.moves.toMutableMap()
    if (player == Player.b || player == Player.w){
        list[pos] = Piece(player.toQueen)
    }
    else return this
    return BoardRun(list,player)
}

*/

