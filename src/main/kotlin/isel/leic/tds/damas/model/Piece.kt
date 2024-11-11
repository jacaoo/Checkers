package isel.leic.tds.damas.model


abstract class Piece(val player: Player) {
/*
    override fun equals(other: Any?): Boolean {
        return if ( other is Piece ) player == other.player else error("Can't compare different types")
    }

 */
    abstract fun canMove(to: Square, from: Square, board: Board): Boolean
    abstract fun canKill(to: Square,from: Square,board: Board): Boolean
    abstract fun GenericCanKill(from: Square, board: Board): Boolean
    abstract fun GenericCanMove(from: Square, board: Board): Boolean
}

